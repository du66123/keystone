package com.keystone.system.domain;

import com.keystone.common.core.domain.BaseEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典类型实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table("sys_dict_type")
public class SysDictType extends BaseEntity {

    @Id(keyType = KeyType.Auto)
    private Long dictId;

    private String dictName;

    private String dictType;

    private String status;
}
