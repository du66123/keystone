-- ===============================================================
-- Keystone RBAC 数据库结构定义 (DDL)
-- 数据库: MySQL 8.x
-- 字符集: utf8mb4
-- ===============================================================

-- ----------------------------
-- 创建数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS `keystone` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `keystone`;

-- ----------------------------
-- 1. 部门表
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
    `dept_id`     BIGINT       NOT NULL AUTO_INCREMENT COMMENT '部门ID',
    `parent_id`   BIGINT       DEFAULT 0               COMMENT '父部门ID (0 = 顶级)',
    `ancestors`   VARCHAR(512) DEFAULT ''               COMMENT '祖级列表 (如: 0,100,101)',
    `dept_name`   VARCHAR(64)  NOT NULL                 COMMENT '部门名称',
    `order_num`   INT          DEFAULT 0                COMMENT '显示顺序',
    `leader`      VARCHAR(64)  DEFAULT NULL             COMMENT '负责人',
    `phone`       VARCHAR(16)  DEFAULT NULL             COMMENT '联系电话',
    `email`       VARCHAR(128) DEFAULT NULL             COMMENT '邮箱',
    `status`      CHAR(1)      DEFAULT '0'              COMMENT '状态 (0=正常, 1=停用)',
    `del_flag`    CHAR(1)      DEFAULT '0'              COMMENT '删除标志 (0=存在, 2=已删除)',
    `create_by`   VARCHAR(64)  DEFAULT ''               COMMENT '创建者',
    `create_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`   VARCHAR(64)  DEFAULT ''               COMMENT '更新者',
    `update_time` DATETIME     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark`      VARCHAR(512) DEFAULT NULL             COMMENT '备注',
    PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=200 COMMENT='部门表';

-- ----------------------------
-- 2. 用户表
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
    `user_id`     BIGINT       NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `dept_id`     BIGINT       DEFAULT NULL             COMMENT '部门ID',
    `username`    VARCHAR(64)  NOT NULL                 COMMENT '用户账号',
    `nickname`    VARCHAR(64)  NOT NULL                 COMMENT '用户昵称',
    `email`       VARCHAR(128) DEFAULT ''               COMMENT '邮箱',
    `phone`       VARCHAR(16)  DEFAULT ''               COMMENT '手机号码',
    `sex`         CHAR(1)      DEFAULT '0'              COMMENT '性别 (0=男, 1=女, 2=未知)',
    `avatar`      VARCHAR(256) DEFAULT ''               COMMENT '头像地址',
    `password`    VARCHAR(128) NOT NULL                 COMMENT '密码 (BCrypt 加密)',
    `status`      CHAR(1)      DEFAULT '0'              COMMENT '状态 (0=正常, 1=停用)',
    `del_flag`    CHAR(1)      DEFAULT '0'              COMMENT '删除标志 (0=存在, 2=已删除)',
    `login_ip`    VARCHAR(128) DEFAULT ''               COMMENT '最后登录IP',
    `login_date`  DATETIME     DEFAULT NULL             COMMENT '最后登录时间',
    `create_by`   VARCHAR(64)  DEFAULT ''               COMMENT '创建者',
    `create_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`   VARCHAR(64)  DEFAULT ''               COMMENT '更新者',
    `update_time` DATETIME     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark`      VARCHAR(512) DEFAULT NULL             COMMENT '备注',
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT='用户表';

-- ----------------------------
-- 3. 角色表
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
    `role_id`     BIGINT       NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `role_name`   VARCHAR(64)  NOT NULL                 COMMENT '角色名称',
    `role_key`    VARCHAR(128) NOT NULL                 COMMENT '角色权限字符串 (如: admin)',
    `order_num`   INT          DEFAULT 0                COMMENT '显示顺序',
    `data_scope`  CHAR(1)      DEFAULT '1'              COMMENT '数据范围 (1=全部, 2=自定义, 3=本部门, 4=本部门及以下, 5=仅本人)',
    `status`      CHAR(1)      DEFAULT '0'              COMMENT '状态 (0=正常, 1=停用)',
    `del_flag`    CHAR(1)      DEFAULT '0'              COMMENT '删除标志 (0=存在, 2=已删除)',
    `create_by`   VARCHAR(64)  DEFAULT ''               COMMENT '创建者',
    `create_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`   VARCHAR(64)  DEFAULT ''               COMMENT '更新者',
    `update_time` DATETIME     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark`      VARCHAR(512) DEFAULT NULL             COMMENT '备注',
    PRIMARY KEY (`role_id`),
    UNIQUE KEY `uk_role_key` (`role_key`)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT='角色表';

-- ----------------------------
-- 4. 菜单权限表
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
    `menu_id`     BIGINT       NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    `menu_name`   VARCHAR(64)  NOT NULL                 COMMENT '菜单名称',
    `parent_id`   BIGINT       DEFAULT 0                COMMENT '父菜单ID (0 = 顶级)',
    `order_num`   INT          DEFAULT 0                COMMENT '显示顺序',
    `path`        VARCHAR(256) DEFAULT ''               COMMENT '路由地址',
    `component`   VARCHAR(256) DEFAULT NULL             COMMENT '组件路径',
    `query`       VARCHAR(256) DEFAULT NULL             COMMENT '路由参数',
    `route_name`  VARCHAR(64)  DEFAULT ''               COMMENT '路由名称',
    `is_frame`    INT          DEFAULT 1                COMMENT '是否外链 (0=是, 1=否)',
    `is_cache`    INT          DEFAULT 0                COMMENT '是否缓存 (0=缓存, 1=不缓存)',
    `menu_type`   CHAR(1)      DEFAULT ''               COMMENT '菜单类型 (M=目录, C=菜单, F=按钮)',
    `visible`     CHAR(1)      DEFAULT '0'              COMMENT '是否显示 (0=显示, 1=隐藏)',
    `status`      CHAR(1)      DEFAULT '0'              COMMENT '状态 (0=正常, 1=停用)',
    `perms`       VARCHAR(128) DEFAULT NULL             COMMENT '权限标识 (如: system:user:list)',
    `icon`        VARCHAR(128) DEFAULT '#'              COMMENT '菜单图标',
    `create_by`   VARCHAR(64)  DEFAULT ''               COMMENT '创建者',
    `create_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`   VARCHAR(64)  DEFAULT ''               COMMENT '更新者',
    `update_time` DATETIME     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark`      VARCHAR(512) DEFAULT NULL             COMMENT '备注',
    PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2000 COMMENT='菜单权限表';

-- ----------------------------
-- 5. 用户与角色关联表
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `role_id` BIGINT NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`user_id`, `role_id`)
) ENGINE=InnoDB COMMENT='用户与角色关联表';

-- ----------------------------
-- 6. 角色与菜单关联表
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
    `role_id` BIGINT NOT NULL COMMENT '角色ID',
    `menu_id` BIGINT NOT NULL COMMENT '菜单ID',
    PRIMARY KEY (`role_id`, `menu_id`)
) ENGINE=InnoDB COMMENT='角色与菜单关联表';

-- ----------------------------
-- 7. 字典类型表
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type` (
    `dict_id`     BIGINT       NOT NULL AUTO_INCREMENT COMMENT '字典ID',
    `dict_name`   VARCHAR(128) NOT NULL                 COMMENT '字典名称',
    `dict_type`   VARCHAR(128) NOT NULL                 COMMENT '字典类型 (唯一标识)',
    `status`      CHAR(1)      DEFAULT '0'              COMMENT '状态 (0=正常, 1=停用)',
    `create_by`   VARCHAR(64)  DEFAULT ''               COMMENT '创建者',
    `create_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`   VARCHAR(64)  DEFAULT ''               COMMENT '更新者',
    `update_time` DATETIME     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark`      VARCHAR(512) DEFAULT NULL             COMMENT '备注',
    PRIMARY KEY (`dict_id`),
    UNIQUE KEY `uk_dict_type` (`dict_type`)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT='字典类型表';

-- ----------------------------
-- 8. 字典数据表
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data` (
    `dict_code`   BIGINT       NOT NULL AUTO_INCREMENT COMMENT '字典编码',
    `dict_sort`   INT          DEFAULT 0                COMMENT '字典排序',
    `dict_label`  VARCHAR(128) NOT NULL                 COMMENT '字典标签',
    `dict_value`  VARCHAR(128) NOT NULL                 COMMENT '字典键值',
    `dict_type`   VARCHAR(128) NOT NULL                 COMMENT '字典类型',
    `css_class`   VARCHAR(128) DEFAULT NULL             COMMENT '样式属性',
    `list_class`  VARCHAR(128) DEFAULT NULL             COMMENT '表格回显样式',
    `is_default`  CHAR(1)      DEFAULT 'N'              COMMENT '是否默认 (Y=是, N=否)',
    `status`      CHAR(1)      DEFAULT '0'              COMMENT '状态 (0=正常, 1=停用)',
    `create_by`   VARCHAR(64)  DEFAULT ''               COMMENT '创建者',
    `create_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`   VARCHAR(64)  DEFAULT ''               COMMENT '更新者',
    `update_time` DATETIME     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark`      VARCHAR(512) DEFAULT NULL             COMMENT '备注',
    PRIMARY KEY (`dict_code`)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT='字典数据表';
