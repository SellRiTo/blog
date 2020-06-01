package com.blog.blog.config.execl;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.sql.Timestamp;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;

public class TimestampConverter implements Converter<Timestamp> {
    public static String PATTERN_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static String PATTERN_YYYYMMDDHHMMSS_EXCEL = "yyyy-MM-dd HH:mm:ss";
    public TimestampConverter() {
    }

    @Override
    public Class supportJavaTypeKey() {
        return Timestamp.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Timestamp convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws ParseException {
        return contentProperty != null && contentProperty.getDateTimeFormatProperty() != null ? Timestamp.valueOf(cellData.getStringValue()) : Timestamp.valueOf(cellData.getStringValue());
    }

    @Override
    public CellData convertToExcelData(Timestamp value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new CellData(value.toLocalDateTime().format(DateTimeFormatter.ofPattern(PATTERN_YYYYMMDDHHMMSS_EXCEL)));
    }

}
