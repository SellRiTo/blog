package com.blog.blog.module.sysuser.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.blog.blog.common.validation.DefindRegValidator;
import com.blog.blog.common.validation.MobileNum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 
 * </p>
 *
 * @author txr
 * @since 2020-03-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ExcelProperty("账号")
    @NotBlank
    private String account;

    /**
     * 名称
     */
    @ExcelProperty("姓名")
    @NotBlank
    private String name;

    @ExcelProperty("男女")
    private Integer sex;

   @ExcelProperty("电话号码")
  // @MobileNum(message = "电话号码有误")
   @DefindRegValidator(regexpRef = "^1[3456789]\\d{9}$",message = "电话号码格式不正确")
    private String phone;

    /**
     * 用户状态(0为注销，1为激活)
     */
    @ExcelProperty("状态")
    private Integer status;

    /**
     * 是否删除（0为删除，1为未删除）
     */
    private Integer isDeleted;

    /**
     * 用户密码
     */
    @NotBlank
    private String password;

    /**
     * 加密的盐
     */
    private String salt;

    @NotBlank
    @Email(message = "邮箱格式不正确")
    private String email;
}
