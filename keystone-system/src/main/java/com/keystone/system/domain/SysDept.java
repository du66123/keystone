package com.keystone.system.domain;

import com.keystone.common.core.domain.BaseEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table("sys_dept")
public class SysDept extends BaseEntity {

    @Id(keyType = KeyType.Auto)
    private Long deptId;

    private Long parentId;

    /** 祖级列表 */
    private String ancestors;

    private String deptName;

    private Integer orderNum;

    private String leader;

    private String phone;

    private String email;

    private String status;

    private String delFlag;
}
