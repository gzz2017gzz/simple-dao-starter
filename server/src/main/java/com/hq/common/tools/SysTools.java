package com.hq.common.tools;

import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.TimeUnit;


/**
 * @author 高振中
 * @类说明 系统工具
 * @date 2024-05-10 21:44:15
 **/
public final class SysTools {
    private SysTools() {
    } // Cannot be constructed


    /**
     * 向浏览器写出excel文件
     */
    @SneakyThrows
    public static ResponseEntity<byte[]> write(XSSFWorkbook workBook) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workBook.write(bos);
        byte[] bytes = bos.toByteArray();
        bos.close();
        workBook.close();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        header.setContentDispositionFormData("attachment", "filename=workBook.xls");
        return new ResponseEntity<>(bytes, header, HttpStatus.OK);
    }

    /**
     * sleep指定毫秒数
     */
    @SneakyThrows
    public static void sleep(long millis) {
        TimeUnit.MILLISECONDS.sleep(millis);
    }

    /**
     * 系统密码加密算法
     */
    public static String password(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }

    /**
     * 获取IP地址
     * <p>
     * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
     * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
     */
    public static String ipAddress(HttpServletRequest request) {
        String ip;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (StringUtils.hasLength(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.hasLength(ip) || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.hasLength(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.hasLength(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.hasLength(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception e) {
            return "0.0.0.0";
        }
        return ip;
    }
}
