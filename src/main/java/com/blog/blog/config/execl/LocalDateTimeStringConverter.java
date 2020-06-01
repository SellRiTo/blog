package com.blog.blog.config.execl;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeStringConverter implements Converter<LocalDateTime> {

    public static String PATTERN_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static String PATTERN_YYYYMMDDHHMMSS_EXCEL = "yyyy-MM-dd HH:mm:ss";
    public LocalDateTimeStringConverter() {
    }

    @Override
    public Class supportJavaTypeKey() {
        return LocalDateTime.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public LocalDateTime convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws ParseException {
        return contentProperty != null && contentProperty.getDateTimeFormatProperty() != null ? LocalDateTime.parse(cellData.getStringValue()) : LocalDateTime.parse(cellData.getStringValue());
    }

    @Override
    public CellData convertToExcelData(LocalDateTime value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new CellData(value.format(DateTimeFormatter.ofPattern(PATTERN_YYYYMMDDHHMMSS_EXCEL)));
    }

}
