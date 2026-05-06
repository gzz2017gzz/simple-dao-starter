package com.hq.common.tools;

import com.hq.common.aop.Dict;
import com.hq.common.aop.DictTable;
import com.hq.manage.base.select.SelectService;
import com.hq.manage.sys.dict.DictService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.logging.log4j.util.Strings;
import org.apache.poi.util.StringUtil;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * @author 吴志建
 * @summary 字典解析工具类
 * @date 2024-07-24 21:44:15
 **/
@Slf4j
public final class DictTools {
    private DictTools() {
    }

    //可翻译字典的数值类型
    private static final Set<Class<?>> DATA_TYPES = Set.of(Byte.class, Integer.class, Long.class, Short.class);
    public static DictService dictService;
    public static SelectService selectService;

    /**
     * 解析列表中的字典
     *
     * @param list 数据列表
     */
    public static <T> void parseList(List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        for (Object v : list) {
            parseObject(v);
        }
    }

    /**
     * 解析一个对象的字典
     *
     * @param item 数据对象
     */
    public static void parseObject(Object item) {
        //所有属性
        List<Field> allFields = FieldUtils.getAllFieldsList(item.getClass());

        //有字典注解的属性
        allFields.stream().filter(f -> f.isAnnotationPresent(Dict.class) && Objects.nonNull(f.getAnnotation(Dict.class).code()) && Objects.nonNull(f.getAnnotation(Dict.class).field())).forEach(f -> {
            Dict dict = f.getAnnotation(Dict.class);
            String dictField = dict.field();
            Optional<Field> dictValueField = allFields.stream().filter(v -> dictField.equals(v.getName())).findFirst();
            if (dictValueField.isPresent()) {
                setDictTextField(item, f, dictValueField.get(), dict.code());
            } else {
                log.error("存字典值的字段不存在：{}", dictField);
            }
        });

        //有表翻译注解的属性
        allFields.stream().filter(f -> f.isAnnotationPresent(DictTable.class) && Objects.nonNull(f.getAnnotation(DictTable.class).table()) && Objects.nonNull(f.getAnnotation(DictTable.class).id()) && Objects.nonNull(f.getAnnotation(DictTable.class).field())).forEach(f -> {
            DictTable table = f.getAnnotation(DictTable.class);
            String dictField = table.field();
            Optional<Field> dictValueField = allFields.stream().filter(v -> dictField.equals(v.getName())).findFirst();
            if (dictValueField.isPresent()) {
                setTableTextField(item, f, dictValueField.get(), table.table(), table.id(), table.name());
            } else {
                log.error("存表ID值的字段不存在：{}", dictField);
            }
        });

    }

    /**
     * 设置字典的内容
     *
     * @param item         当前数据
     * @param currentField 当前属性
     * @param dictValField 存储字典值的属性
     * @param code         字典类型编码
     */
    @SneakyThrows
    private static void setDictTextField(Object item, Field currentField, Field dictValField, String code) {
        dictValField.setAccessible(true);
        Object val = dictValField.get(item);

        if (Objects.isNull(val)) {
            return;
        }

        //数值类型的处理
        if (DATA_TYPES.contains(val.getClass())) {
            String dictText = dictService.parseDict(code, Long.parseLong(String.valueOf(val)));
            currentField.setAccessible(true);
            currentField.set(item, dictText);
            return;
        }

        //若为字符串类型，处理多个内容
        if (String.class.equals(val.getClass())) {
            String[] valArr = val.toString().split(",");
            if (valArr.length == 0) {
                return;
            }

            StringBuilder vsb = new StringBuilder();
            for (String v : valArr) {
                if (StringUtil.isBlank(v)) {
                    vsb.append(Strings.EMPTY).append(",");
                    continue;
                }

                String dictText = dictService.parseDict(code, Long.parseLong(v));
                vsb.append(dictText).append(",");
            }

            vsb.deleteCharAt(vsb.length() - 1);
            currentField.setAccessible(true);
            currentField.set(item, vsb.toString());
        }
    }

    @SneakyThrows
    private static void setTableTextField(Object item, Field currentField, Field dictValField, String table, String id, String name) {
        dictValField.setAccessible(true);
        Object val = dictValField.get(item);

        if (Objects.isNull(val)) {
            return;
        }

        //数值类型的处理
        if (DATA_TYPES.contains(val.getClass())) {
            String dictText = selectService.parseTableText(table, id, name, String.valueOf(val));
            currentField.setAccessible(true);
            currentField.set(item, dictText);
            return;
        }

        //若为字符串类型，处理多个内容
        if (String.class.equals(val.getClass())) {
            String[] valArr = val.toString().split(",");
            if (valArr.length == 0) {
                return;
            }

            StringBuilder vsb = new StringBuilder();
            for (String v : valArr) {
                if (StringUtil.isBlank(v)) {
                    vsb.append(Strings.EMPTY).append(",");
                    continue;
                }

                String dictText = selectService.parseTableText(table, id, name, v);
                vsb.append(dictText).append(",");
            }

            vsb.deleteCharAt(vsb.length() - 1);
            currentField.setAccessible(true);
            currentField.set(item, vsb.toString());
        }
    }
}
