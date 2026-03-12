-- ===============================================================
-- Keystone RBAC 初始化数据 (DML)
-- 说明: 默认管理员账号 admin / admin123
-- ===============================================================

USE `keystone`;

-- ----------------------------
-- 1. 初始化部门数据
-- ----------------------------
INSERT INTO `sys_dept` VALUES (100, 0, '0',       'Keystone科技',  0, 'admin', '15888888888', 'admin@keystone.com', '0', '0', 'admin', NOW(), '', NULL, '总公司');
INSERT INTO `sys_dept` VALUES (101, 100, '0,100',  '技术研发部',    1, NULL, NULL, NULL, '0', '0', 'admin', NOW(), '', NULL, NULL);
INSERT INTO `sys_dept` VALUES (102, 100, '0,100',  '产品运营部',    2, NULL, NULL, NULL, '0', '0', 'admin', NOW(), '', NULL, NULL);
INSERT INTO `sys_dept` VALUES (103, 100, '0,100',  '市场推广部',    3, NULL, NULL, NULL, '0', '0', 'admin', NOW(), '', NULL, NULL);

-- ----------------------------
-- 2. 初始化用户数据
-- 密码: admin123 → BCrypt 加密
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 101, 'admin', '超级管理员', 'admin@keystone.com', '15888888888', '0', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', NOW(), 'admin', NOW(), '', NULL, '超级管理员');
INSERT INTO `sys_user` VALUES (2, 102, 'keystone', '普通用户', 'user@keystone.com', '15666666666', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', NOW(), 'admin', NOW(), '', NULL, '普通用户');

-- ----------------------------
-- 3. 初始化角色数据
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin',   1, '1', '0', '0', 'admin', NOW(), '', NULL, '超级管理员');
INSERT INTO `sys_role` VALUES (2, '普通角色',   'common',  2, '2', '0', '0', 'admin', NOW(), '', NULL, '普通角色');

-- ----------------------------
-- 4. 初始化菜单数据
-- ----------------------------
-- 一级目录
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 1, 'system',   NULL, '', 'System',    1, 0, 'M', '0', '0', '', 'setting',   'admin', NOW(), '', NULL, '系统管理目录');
INSERT INTO `sys_menu` VALUES (2, 'AI 中心', 0, 2, 'ai',       NULL, '', 'Ai',        1, 0, 'M', '0', '0', '', 'robot',     'admin', NOW(), '', NULL, 'AI 中心目录');

-- 系统管理 > 二级菜单
INSERT INTO `sys_menu` VALUES (100, '用户管理', 1, 1, 'user',     'system/user/index',     '', 'User',     1, 0, 'C', '0', '0', 'system:user:list',     'peoples',    'admin', NOW(), '', NULL, '用户管理菜单');
INSERT INTO `sys_menu` VALUES (101, '角色管理', 1, 2, 'role',     'system/role/index',     '', 'Role',     1, 0, 'C', '0', '0', 'system:role:list',     'role',       'admin', NOW(), '', NULL, '角色管理菜单');
INSERT INTO `sys_menu` VALUES (102, '菜单管理', 1, 3, 'menu',     'system/menu/index',     '', 'Menu',     1, 0, 'C', '0', '0', 'system:menu:list',     'tree-table', 'admin', NOW(), '', NULL, '菜单管理菜单');
INSERT INTO `sys_menu` VALUES (103, '部门管理', 1, 4, 'dept',     'system/dept/index',     '', 'Dept',     1, 0, 'C', '0', '0', 'system:dept:list',     'tree',       'admin', NOW(), '', NULL, '部门管理菜单');
INSERT INTO `sys_menu` VALUES (104, '字典管理', 1, 5, 'dict',     'system/dict/index',     '', 'Dict',     1, 0, 'C', '0', '0', 'system:dict:list',     'dict',       'admin', NOW(), '', NULL, '字典管理菜单');

-- 用户管理 > 按钮权限
INSERT INTO `sys_menu` VALUES (1000, '用户查询', 100, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:query',    '#', 'admin', NOW(), '', NULL, '');
INSERT INTO `sys_menu` VALUES (1001, '用户新增', 100, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:add',      '#', 'admin', NOW(), '', NULL, '');
INSERT INTO `sys_menu` VALUES (1002, '用户修改', 100, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:edit',     '#', 'admin', NOW(), '', NULL, '');
INSERT INTO `sys_menu` VALUES (1003, '用户删除', 100, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:remove',   '#', 'admin', NOW(), '', NULL, '');
INSERT INTO `sys_menu` VALUES (1004, '重置密码', 100, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:resetPwd', '#', 'admin', NOW(), '', NULL, '');

-- 角色管理 > 按钮权限
INSERT INTO `sys_menu` VALUES (1010, '角色查询', 101, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:query',  '#', 'admin', NOW(), '', NULL, '');
INSERT INTO `sys_menu` VALUES (1011, '角色新增', 101, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:add',    '#', 'admin', NOW(), '', NULL, '');
INSERT INTO `sys_menu` VALUES (1012, '角色修改', 101, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:edit',   '#', 'admin', NOW(), '', NULL, '');
INSERT INTO `sys_menu` VALUES (1013, '角色删除', 101, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:remove', '#', 'admin', NOW(), '', NULL, '');

-- 菜单管理 > 按钮权限
INSERT INTO `sys_menu` VALUES (1020, '菜单查询', 102, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:query',  '#', 'admin', NOW(), '', NULL, '');
INSERT INTO `sys_menu` VALUES (1021, '菜单新增', 102, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:add',    '#', 'admin', NOW(), '', NULL, '');
INSERT INTO `sys_menu` VALUES (1022, '菜单修改', 102, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:edit',   '#', 'admin', NOW(), '', NULL, '');
INSERT INTO `sys_menu` VALUES (1023, '菜单删除', 102, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:remove', '#', 'admin', NOW(), '', NULL, '');

-- ----------------------------
-- 5. 初始化用户角色关联
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);  -- admin → 超级管理员
INSERT INTO `sys_user_role` VALUES (2, 2);  -- keystone → 普通角色

-- ----------------------------
-- 6. 初始化角色菜单关联 (超级管理员拥有所有菜单)
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 1);
INSERT INTO `sys_role_menu` VALUES (1, 2);
INSERT INTO `sys_role_menu` VALUES (1, 100);
INSERT INTO `sys_role_menu` VALUES (1, 101);
INSERT INTO `sys_role_menu` VALUES (1, 102);
INSERT INTO `sys_role_menu` VALUES (1, 103);
INSERT INTO `sys_role_menu` VALUES (1, 104);
INSERT INTO `sys_role_menu` VALUES (1, 1000);
INSERT INTO `sys_role_menu` VALUES (1, 1001);
INSERT INTO `sys_role_menu` VALUES (1, 1002);
INSERT INTO `sys_role_menu` VALUES (1, 1003);
INSERT INTO `sys_role_menu` VALUES (1, 1004);
INSERT INTO `sys_role_menu` VALUES (1, 1010);
INSERT INTO `sys_role_menu` VALUES (1, 1011);
INSERT INTO `sys_role_menu` VALUES (1, 1012);
INSERT INTO `sys_role_menu` VALUES (1, 1013);
INSERT INTO `sys_role_menu` VALUES (1, 1020);
INSERT INTO `sys_role_menu` VALUES (1, 1021);
INSERT INTO `sys_role_menu` VALUES (1, 1022);
INSERT INTO `sys_role_menu` VALUES (1, 1023);

-- 普通角色: 只有系统管理查看权限
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 100);
INSERT INTO `sys_role_menu` VALUES (2, 1000);

-- ----------------------------
-- 7. 初始化字典类型
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, '用户性别',   'sys_user_sex',     '0', 'admin', NOW(), '', NULL, '用户性别列表');
INSERT INTO `sys_dict_type` VALUES (2, '系统状态',   'sys_normal_status', '0', 'admin', NOW(), '', NULL, '系统通用状态');
INSERT INTO `sys_dict_type` VALUES (3, '菜单类型',   'sys_menu_type',    '0', 'admin', NOW(), '', NULL, '菜单类型列表');
INSERT INTO `sys_dict_type` VALUES (4, '系统是否',   'sys_yes_no',       '0', 'admin', NOW(), '', NULL, '系统是否列表');
INSERT INTO `sys_dict_type` VALUES (5, '数据范围',   'sys_data_scope',   '0', 'admin', NOW(), '', NULL, '角色数据范围');

-- ----------------------------
-- 8. 初始化字典数据
-- ----------------------------
-- 用户性别
INSERT INTO `sys_dict_data` VALUES (1, 1, '男',   '0', 'sys_user_sex',     '',  '',  'Y', '0', 'admin', NOW(), '', NULL, '性别男');
INSERT INTO `sys_dict_data` VALUES (2, 2, '女',   '1', 'sys_user_sex',     '',  '',  'N', '0', 'admin', NOW(), '', NULL, '性别女');
INSERT INTO `sys_dict_data` VALUES (3, 3, '未知', '2', 'sys_user_sex',     '',  '',  'N', '0', 'admin', NOW(), '', NULL, '性别未知');

-- 系统状态
INSERT INTO `sys_dict_data` VALUES (4, 1, '正常', '0', 'sys_normal_status', '',  'primary', 'Y', '0', 'admin', NOW(), '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (5, 2, '停用', '1', 'sys_normal_status', '',  'danger',  'N', '0', 'admin', NOW(), '', NULL, '停用状态');

-- 菜单类型
INSERT INTO `sys_dict_data` VALUES (6, 1, '目录', 'M', 'sys_menu_type', '', '', 'N', '0', 'admin', NOW(), '', NULL, '目录');
INSERT INTO `sys_dict_data` VALUES (7, 2, '菜单', 'C', 'sys_menu_type', '', '', 'N', '0', 'admin', NOW(), '', NULL, '菜单');
INSERT INTO `sys_dict_data` VALUES (8, 3, '按钮', 'F', 'sys_menu_type', '', '', 'N', '0', 'admin', NOW(), '', NULL, '按钮');

-- 是否
INSERT INTO `sys_dict_data` VALUES (9,  1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', '0', 'admin', NOW(), '', NULL, '系统默认是');
INSERT INTO `sys_dict_data` VALUES (10, 2, '否', 'N', 'sys_yes_no', '', 'danger',  'N', '0', 'admin', NOW(), '', NULL, '系统默认否');
