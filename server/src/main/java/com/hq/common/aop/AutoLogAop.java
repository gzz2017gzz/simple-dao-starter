package com.hq.common.aop;

import static com.simple.common.base.key.Const.LONG_0;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.hq.common.tools.JsonTools;
import com.hq.manage.sys.log.Log;
import com.hq.manage.sys.log.LogDao;
import com.hq.manage.sys.user.User;
import com.hq.manage.sys.user.vo.LoginVO;
import com.simple.common.base.UserIdProvider;
import com.simple.common.base.key.SnowflakeId;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
/**
 * 日志记录切面，用于自动记录系统操作日志
 *
 * @author 高振中
 * @date 2025-09-09 21:44:15
 */
@Aspect
@Component
public class AutoLogAop {
    // 需要排除的字段列表
    private static final List<String> EXCLUDE_FIELDS = Arrays.asList("fields", "paramList", "condition", "orders", "dataAuth", "size", "page", "studentSign", "guardSign");

    // 需要跳过日志记录结果的URL关键字
    private static final List<String> SKIP_RESULT_KEYWORDS = Arrays.asList(
            "page", "list", "login", "tree", "billItemFee", "queryClass", "sourceType", "allOutList", "findByKey", "findEnable", "view", "findOne", "fields", "crmCfg", "waitAuditList",
            "receipt", "canList", "listRate", "canUseList", "queryUsable", "listItem", "createOnlineOrder", "studentNumber"
    );

    // 文件导出相关的URL关键字
    private static final List<String> FILE_EXPORT_KEYWORDS = Arrays.asList("/export", "/download");

    // 二进制响应的Content-Type关键字
    private static final List<String> BINARY_CONTENT_TYPES = Arrays.asList("application/octet-stream", "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument");

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RedisTemplate<Long, LoginVO> redisTemplate;

    @Autowired
    private LogDao logDao;
    @Autowired
    private ExecutorService executorService; // 注入你在 GlobalConfig 里配好的线程池
    @Autowired
    private UserIdProvider userIdProvider;
    /**
     * 环绕通知，用于记录Controller层的操作日志
     */
    @Around("execution(public * com.hq.manage..*.*Controller.*(..)) && @annotation(autoLog)")
    public Object recordLog(ProceedingJoinPoint joinPoint, AutoLog autoLog) throws Throwable {
        Object result = joinPoint.proceed();
        User currentUser = getCurrentUser();
        String params = processRequestParams(joinPoint.getArgs(), request.getParameterMap());
        String uri = request.getServletPath();
        String resultStr;
        if (isSkipResultRecording(uri) || autoLog.skipResult()) {
            resultStr = "结果略";
        } else if (isFileExportScene(result, uri)) {
            resultStr = "[binary]";
        } else {
            resultStr = JsonTools.toString(result);
        }
        executorService.submit(() -> logDao.save(Log.builder()
                .id(SnowflakeId.nextId())
                .userId(currentUser.getUserId())
                .userName(currentUser.getName())
                .title(autoLog.value())
                .url(uri)
                .method(joinPoint.getSignature().toShortString())
                .result(resultStr)
                .operateTime(LocalDateTime.now())
                .params(params)
                .build()));
        return result;
    }


    /**
     * 获取当前用户信息
     */
    private User getCurrentUser() {
        Long userId = userIdProvider.userId();

        // 匿名用户处理
        if (Objects.equals(userId,  LONG_0)) {
            return User.builder().userId(userId).name("见参数").build();
        }

        // 从Redis获取登录用户信息
        LoginVO loginVO = redisTemplate.opsForValue().get(userId);
        if (Objects.isNull(loginVO)) {
            return User.builder().userId(userId).name("未知用户").build();
        }

        return loginVO.getUser();
    }

    /**
     * 处理请求参数，包括body参数和param参数
     */
    private String processRequestParams(Object[] args, Map<String, String[]> requestParamMap) {
        // 处理body参数
        List<Object> bodyParams = Arrays.stream(args).map(this::processParamObject).filter(Objects::nonNull).collect(Collectors.toList());

        // 处理param参数
        Map<String, Object> paramMap = requestParamMap.entrySet().stream().filter(entry -> entry.getValue() != null && entry.getValue().length > 0)
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> Arrays.asList(entry.getValue())));

        // 拼接参数字符串
        StringBuilder paramsSb = new StringBuilder();
        if (!bodyParams.isEmpty()) {
            paramsSb.append("body:").append(JsonTools.toString(bodyParams));
        }
        if (!paramMap.isEmpty()) {
            if (!paramsSb.isEmpty()) {
                paramsSb.append(",");
            }
            paramsSb.append("param:").append(JsonTools.toString(paramMap));
        }

        return paramsSb.toString();
    }

    /**
     * 处理单个参数对象
     */
    private Object processParamObject(Object param) {
        if (param == null) {
            return null;
        }

        // 处理文件上传对象
        if (param instanceof MultipartFile) {
            return convertToFileInfo(param);
        }

        // 处理简单类型直接返回
        if (isSimpleType(param.getClass())) {
            return param;
        }

        // 处理数组类型
        if (param.getClass().isArray()) {
            return Arrays.stream((Object[]) param).map(this::convertToFileInfo).toArray();
        }

        // 处理复杂对象
        return filterTopLevelFieldsAndNull(param);
    }


    /**
     * 过滤对象顶层指定字段和空值
     */
    @SneakyThrows
    private Object filterTopLevelFieldsAndNull(Object obj) {

        if (obj.getClass().getPackage().getName().startsWith("java.")) {
            return obj;
        }
        Map<String, Object> fieldMap = new HashMap<>();
        Field[] declaredFields = obj.getClass().getDeclaredFields();

        for (Field field : declaredFields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            // 跳过需要排除的字段
            if (EXCLUDE_FIELDS.contains(fieldName)) {
                continue;
            }
            Object fieldValue = field.get(obj);
            // 跳过空值
            if (fieldValue != null) {
                fieldMap.put(fieldName, fieldValue);
            }
        }
        return fieldMap;
    }

    /**
     * 将文件对象转换为文件信息描述
     */
    private Object convertToFileInfo(Object obj) {
        if (obj instanceof MultipartFile file) {
            Map<String, Object> fileInfo = new HashMap<>(3);

            // 只记录非空的文件元数据
            if (file.getOriginalFilename() != null) {
                fileInfo.put("fileName", file.getOriginalFilename());
            }
            if (file.getSize() > 0) {
                fileInfo.put("fileSize", file.getSize() + " bytes");
            }
            if (file.getContentType() != null) {
                fileInfo.put("contentType", file.getContentType());
            }
            return fileInfo;
        }
        return obj;
    }

    /**
     * 判断是否为简单类型（基本类型及其包装类、String、日期等）
     */
    private boolean isSimpleType(Class<?> clazz) {
        return clazz.isPrimitive() || clazz.equals(String.class) || Number.class.isAssignableFrom(clazz) || clazz.equals(Boolean.class) || clazz.equals(LocalDateTime.class) || clazz.equals(Date.class);
    }

    /**
     * 判断是否为文件导出场景
     */
    private boolean isFileExportScene(Object result, String uri) {
        // 检查URL是否包含导出关键字
        boolean isExportUrl = FILE_EXPORT_KEYWORDS.stream().anyMatch(uri::contains);
        // 检查响应是否为二进制类型
        boolean isBinaryResponse = result instanceof ResponseEntity<?> && isBinaryContentType(((ResponseEntity<?>) result).getHeaders().getContentType());
        return isExportUrl || isBinaryResponse;
    }

    /**
     * 判断Content-Type是否为二进制类型
     */
    private boolean isBinaryContentType(Object contentType) {
        if (contentType == null) {
            return false;
        }
        String contentTypeStr = contentType.toString();
        return BINARY_CONTENT_TYPES.stream().anyMatch(contentTypeStr::contains);
    }

    /**
     * 判断是否需要跳过结果记录
     */
    private boolean isSkipResultRecording(String uri) {
        return SKIP_RESULT_KEYWORDS.stream().anyMatch(uri::endsWith);
    }
}
