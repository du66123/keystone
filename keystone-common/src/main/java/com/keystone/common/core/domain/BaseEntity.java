package com.keystone.common.core.domain;

import com.mybatisflex.annotation.Column;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Entity 基类 - 公共审计字段
 */
@Data
public class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 创建者 */
    @Column(value = "create_by")
    private String createBy;

    /** 创建时间 */
    @Column(value = "create_time")
    private LocalDateTime createTime;

    /** 更新者 */
    @Column(value = "update_by")
    private String updateBy;

    /** 更新时间 */
    @Column(value = "update_time")
    private LocalDateTime updateTime;

    /** 备注 */
    private String remark;
}
