package com.keystone.system.domain;

import com.keystone.common.core.domain.BaseEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table("sys_role")
public class SysRole extends BaseEntity {

    @Id(keyType = KeyType.Auto)
    private Long roleId;

    private String roleName;

    private String roleKey;

    private Integer orderNum;

    /** 数据范围 (1=全部, 2=自定义, 3=本部门, 4=本部门及以下, 5=仅本人) */
    private String dataScope;

    private String status;

    private String delFlag;
}
