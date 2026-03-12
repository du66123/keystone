package com.keystone.system.domain;

import com.keystone.common.core.domain.BaseEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 菜单权限实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table("sys_menu")
public class SysMenu extends BaseEntity {

    @Id(keyType = KeyType.Auto)
    private Long menuId;

    private String menuName;

    private Long parentId;

    private Integer orderNum;

    /** 路由地址 */
    private String path;

    /** 组件路径 */
    private String component;

    /** 路由参数 */
    private String query;

    /** 路由名称 */
    private String routeName;

    /** 是否外链 (0=是, 1=否) */
    private Integer isFrame;

    /** 是否缓存 (0=缓存, 1=不缓存) */
    private Integer isCache;

    /** 菜单类型 (M=目录, C=菜单, F=按钮) */
    private String menuType;

    /** 是否显示 */
    private String visible;

    private String status;

    /** 权限标识 */
    private String perms;

    /** 图标 */
    private String icon;

    /** 子菜单 (非数据库字段) */
    @com.mybatisflex.annotation.Column(ignore = true)
    private List<SysMenu> children;
}
