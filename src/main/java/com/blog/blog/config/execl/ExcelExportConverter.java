package com.blog.blog.config.execl;

/**
 * @author sean
 * @desc
 */
public interface ExcelExportConverter<In, Out> {

    Out convert(In value);
}
