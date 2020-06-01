package com.blog.blog.config.execl;

import java.lang.annotation.*;

/**
 * @author sean
 * @desc
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExportField {

    Class<? extends ExcelExportConverter> converter();
}
