package com.blog.blog.config.execl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * @author sean
 * @desc Excel工具类
 */
@Slf4j
public class ExcelUtils {

    private static List<Converter> converters = new ArrayList<>();
    private final static String defaultSheetName = "sheet";


    static {
        converters.add(new LocalDateTimeStringConverter());
        converters.add(new TimestampConverter());
    }

    private static String convert(String str) {
        return str.substring(0, 1).toUpperCase().concat(str.substring(1));
    }

    public static void exportToResp(String filename, List<?> data, String[] headers, String[] fields) throws IOException {
        exportToResp(filename, data, headers, fields, null, defaultSheetName);
    }

    public static void exportToResp(String filename, List<?> data, String[] headers, String[] fields,String sheetName) throws IOException {
        exportToResp(filename, data, headers, fields, null, sheetName);
    }

    public static void exportToResp(String filename, List<?> data, String[] headers, String[] fields, Class<?> clazz) throws IOException {
        exportToResp(filename, data, headers, fields, clazz, defaultSheetName);
    }

    public static void exportMapToResp(String filename, List<?> data, String[] headers, String[] fields, String sheetName) throws IOException {
        exportToResp(filename, data, headers, fields, HashMap.class, sheetName==null?defaultSheetName:sheetName);
    }

    public static void exportMapToResp(String filename, List<?> data, String[] headers, String[] fields) throws IOException {
        exportToResp(filename, data, headers, fields, HashMap.class, defaultSheetName);
    }

    public static void    exportToResp(String filename, List<?> data, String[] headers, String[] fields, Class<?> clazz, String sheetName) throws IOException {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletResponse response = ((ServletRequestAttributes) requestAttributes).getResponse();
        response.setCharacterEncoding("utf-8");
        String fileName = new String(filename.getBytes(), StandardCharsets.ISO_8859_1);
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        export(data, response.getOutputStream(), headers, fields, clazz, sheetName);
    }

    public static void export(List<?> data, OutputStream out, String[] headers, String[] fields) {
        export(data, out, headers, fields, null, "模板");
    }

    public static void export(List<?> data, OutputStream out, String[] headers, String[] fields, Class<?> clazz, String sheetName) {
        try {
            if (fields != null && headers.length != fields.length) {
                throw new ValidationException("表头和数据字段数量不一致，请检查入参");
            }
            List<List<String>> head = buildHeaders(headers);
            ExcelWriterSheetBuilder writerSheetBuilder = EasyExcel.write()
                    .file(out)
                    .autoCloseStream(true)
                    .sheet(sheetName)
                    .head(head)
                    .needHead(true)
                    .registerWriteHandler(getDefaultStyle());
            converters.forEach(writerSheetBuilder::registerConverter);
            List<List<Object>> data1 = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(data)) {
                if (clazz == null) {
                    data1 = buildData(data, fields);
                } else if (clazz.isAssignableFrom(HashMap.class)) {
                    data1 = buildMapData(((List<HashMap>) data), fields);
                }
            }
            writerSheetBuilder.doWrite(data1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<List<Object>> buildData(List<?> data, String[] fields) throws NoSuchMethodException {
        List<List<Object>> object = new ArrayList<>();
        for (Object obj : data) {
            List<Object> da = new ArrayList<>();
            Class<?> c = obj.getClass();
            for (String field : fields) {
                try {
                    Field declaredField = getField(c, field);
                    declaredField.setAccessible(true);
                    Method method = c.getMethod("get" + convert(field));
                    Object invokeResult = ReflectionUtils.invokeMethod(method, obj);
                    ExportField exportFieldAnnotation = declaredField.getDeclaredAnnotation(ExportField.class);
                    if (exportFieldAnnotation != null) {
                        Class<? extends ExcelExportConverter> converter = exportFieldAnnotation.converter();
                        ExcelExportConverter excelExportConverter = converter.newInstance();
                        invokeResult = excelExportConverter.convert(invokeResult);
                    }
                    // 如果实现了StandardEnum接口的话将调用getText方法获取显示文本
                    if (invokeResult != null && StandardEnum.class.isAssignableFrom(invokeResult.getClass())) {
                        invokeResult = ReflectionUtils.invokeMethod(invokeResult.getClass().getMethod("getText"), invokeResult);
                    }
                    da.add(invokeResult);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            object.add(da);
        }
        return object;
    }

    private static List<List<Object>> buildMapData(List<HashMap> data, String[] fields) {
        List<List<Object>> object = new ArrayList<>();
        data.forEach(datum -> {
            List<Object> da = new ArrayList<>();
            for (String field : fields) {
                try {
                    Object invokeResult = datum.get(field);
                    da.add(invokeResult);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            object.add(da);
        });
        return object;
    }

    public static Field getField(Class clazz, String fieldName) throws NoSuchFieldException {
        Field declaredField = null;
        try {
            declaredField = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            //
        }
        if (declaredField != null) {
            return declaredField;
        }
        Class superClazz = clazz.getSuperclass();
        while (superClazz != null) {
            try {
                declaredField = superClazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                //
            }
            if (declaredField != null) {
                return declaredField;
            }
            superClazz = superClazz.getSuperclass();
        }

        throw new NoSuchFieldException(fieldName);
    }


    private static List<List<String>> buildHeaders(String[] headers) {
        List<List<String>> heads = new ArrayList<>();
        for (String header : headers) {
            List<String> headColumn = new ArrayList<>();
            headColumn.add(header);
            heads.add(headColumn);
        }
        return heads;
    }

    private static HorizontalCellStyleStrategy getDefaultStyle() {
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景设置为红色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 10);
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        return horizontalCellStyleStrategy;
    }

}
