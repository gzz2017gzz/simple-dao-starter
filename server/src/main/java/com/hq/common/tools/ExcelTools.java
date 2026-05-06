package com.hq.common.tools;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.hq.common.config.CrmException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author 吴志建
 * @summary excel导出工具类
 * @date 2024-07-24 21:44:15
 **/
@Slf4j
public final class ExcelTools {
    private ExcelTools() {
    }

    //脱敏字段列表
    public static List<String> unSecrecyFieldList = new ArrayList<>();

    /**
     * 无排除字段
     *
     * @param dataList   数据列表
     * @param clazz      数据类型
     * @param headName   标题名
     * @param selections 包含列
     * @return ResponseEntity<byte [ ]> 文件
     */
    public static <T> ResponseEntity<byte[]> export(List<T> dataList, Class<?> clazz, String headName, List<String> selections) {
        return export(dataList, clazz, headName, selections, null, null);
    }

    /**
     * 有排除字段
     *
     * @param dataList        数据列表
     * @param clazz           数据类型
     * @param headName        标题名
     * @param selections      包含列
     * @param unSecrecyFields 排除列
     * @return ResponseEntity<byte [ ]> 文件
     */
    public static <T> ResponseEntity<byte[]> export(List<T> dataList, Class<?> clazz, String headName, List<String> selections, List<String> unSecrecyFields) {
        unSecrecyFieldList = unSecrecyFields;
        return export(dataList, clazz, headName, selections, null, null);
    }

    /**
     * 有自定义写入手柄
     *
     * @param dataList    数据列表
     * @param clazz       数据类型
     * @param headName    标题名
     * @param selections  包含列
     * @param headHandler 写入器
     * @param headCount   头行数
     * @return ResponseEntity<byte [ ]>
     */
    public static <T> ResponseEntity<byte[]> export(List<T> dataList, Class<?> clazz, String headName, List<String> selections, SheetWriteHandler headHandler, Integer headCount) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            // 设置响应头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", URLEncoder.encode(headName, StandardCharsets.UTF_8) + ".xlsx");

            // 创建表头样式
            WriteCellStyle headStyle = new WriteCellStyle();
            headStyle.setBorderTop(BorderStyle.THIN);
            headStyle.setBorderBottom(BorderStyle.THIN);
            headStyle.setBorderLeft(BorderStyle.THIN);
            headStyle.setBorderRight(BorderStyle.THIN);
            headStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
            headStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            // 创建单元格样式
            WriteCellStyle cellStyle = new WriteCellStyle();
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);


            // 创建头策略
            HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headStyle, cellStyle);

            List<Field> allFields = FieldUtils.getAllFieldsList(clazz);
            List<String> hasExcelExportFields = allFields.stream().filter(v -> v.isAnnotationPresent(ExcelProperty.class)).map(Field::getName).filter(v -> {
                if (CollectionUtils.isEmpty(selections)) {
                    return true;
                }
                return selections.contains(v);
            }).toList();

            //脱敏
            if (!CollectionUtils.isEmpty(unSecrecyFieldList)) {
                if (unSecrecyFieldList.contains("phone")) {
                    DataMaskTools.doMaskingPhone(dataList, "phone");
                }
                if (unSecrecyFieldList.contains("guardPhone")) {
                    DataMaskTools.doMaskingPhone(dataList, "guardPhone");
                }
                if (unSecrecyFieldList.contains("idNumber")) {
                    DataMaskTools.doMaskingIdNumber(dataList, "idNumber");
                }
                if (unSecrecyFieldList.contains("guardIdNumber")) {
                    DataMaskTools.doMaskingIdNumber(dataList, "guardIdNumber");
                }
            }

            if (Objects.nonNull(headHandler)) {
                EasyExcel.write(bos, clazz)
                        .registerWriteHandler(horizontalCellStyleStrategy)
                        .head(clazz)
                        .sheet(headName)
                        .registerWriteHandler(headHandler)
                        .includeColumnFieldNames(hasExcelExportFields)
                        .relativeHeadRowIndex(headCount)
                        .doWrite(dataList);
            } else {
                NormalHeadHandler normalHeadHandler = new NormalHeadHandler(hasExcelExportFields.size() - 1);
                EasyExcel.write(bos, clazz)
                        .registerWriteHandler(horizontalCellStyleStrategy)
                        .registerWriteHandler(normalHeadHandler).head(clazz)
                        .sheet(headName)
                        .includeColumnFieldNames(hasExcelExportFields)
                        .relativeHeadRowIndex(1)
                        .doWrite(dataList);
            }
            byte[] bytes = bos.toByteArray();
            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error("导出数据失败:headName={},clazz={}", headName, clazz, e);
            throw new CrmException("导出数据失败");
        }
    }

    /**
     * 标准Excel导出头
     */
    @Setter
    public static class NormalHeadHandler implements SheetWriteHandler {

        //列的数量
        private Integer columnTotal;

        public NormalHeadHandler(Integer columnTotal) {
            this.columnTotal = columnTotal;
        }

        @Override
        public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
            Workbook workbook = writeWorkbookHolder.getWorkbook();

            Sheet sheet = workbook.getSheetAt(0);
            Row row1 = sheet.createRow(0);
            Cell cell10 = row1.createCell(0);

            cell10.setCellValue(sheet.getSheetName());
            cell10.setCellStyle(titleStyle(workbook));
            if (this.columnTotal != null && this.columnTotal > 2) {
                sheet.addMergedRegionUnsafe(new CellRangeAddress(0, 0, 0, this.columnTotal));
            }
            sheet.createFreezePane(0, 2);
        }


        /**
         * 标题样式
         **/
        private CellStyle titleStyle(Workbook workbook) {
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());

            Font font = workbook.createFont();
            font.setBold(true);
            font.setFontName("微软雅黑");
            font.setFontHeightInPoints((short) 22);

            cellStyle.setFont(font);
            return cellStyle;
        }
    }
}
