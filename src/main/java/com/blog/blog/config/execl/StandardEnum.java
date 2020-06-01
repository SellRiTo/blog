package com.blog.blog.config.execl;

import com.baomidou.mybatisplus.core.enums.IEnum;

import java.io.Serializable;

/**
 * @author sean
 * @desc
 */
public interface StandardEnum<T extends Serializable> extends IEnum<T> {

    String getText();
}
