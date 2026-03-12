package com.keystone.system.domain;

import com.keystone.common.core.domain.BaseEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table("sys_user")
public class SysUser extends BaseEntity {

    /** 用户ID */
    @Id(keyType = KeyType.Auto)
    private Long userId;

    /** 部门ID */
    private Long deptId;

    /** 用户账号 */
    private String username;

    /** 用户昵称 */
    private String nickname;

    /** 邮箱 */
    private String email;

    /** 手机号码 */
    private String phone;

    /** 性别 */
    private String sex;

    /** 头像地址 */
    private String avatar;

    /** 密码 */
    private String password;

    /** 状态 (0=正常, 1=停用) */
    private String status;

    /** 删除标志 (0=存在, 2=已删除) */
    private String delFlag;

    /** 最后登录IP */
    private String loginIp;

    /** 最后登录时间 */
    private java.time.LocalDateTime loginDate;
}
