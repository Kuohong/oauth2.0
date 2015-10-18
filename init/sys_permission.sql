SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id_` int(11) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `uuid_` varchar(255) NOT NULL COMMENT 'UUID',
  `key_` varchar(255) NOT NULL COMMENT '权限key',
  `name_` varchar(255) NOT NULL COMMENT '权限名字',
  `parent_uuid_` varchar(255) NOT NULL COMMENT 'parentUUID',
  `order_` int(11) NOT NULL DEFAULT '0' COMMENT 'order',
  `create_time_` TIMESTAMP NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id_`),
  UNIQUE KEY `sys_permission.key_` (`key_`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO sys_permission (uuid_, name_, key_, parent_uuid_, order_) VALUES ('1', '权限', '*', '', 0);
INSERT INTO sys_permission (uuid_, name_, key_, parent_uuid_, order_) VALUES ('2', '系统设置', 'system:setting', '1', 1);
INSERT INTO sys_permission (uuid_, name_, key_, parent_uuid_, order_) VALUES ('6', '查看权限', 'sys_permission:check', '25', 0);
INSERT INTO sys_permission (uuid_, name_, key_, parent_uuid_, order_) VALUES ('7', '删除权限', 'sys_permission:delete', '25', 0);
INSERT INTO sys_permission (uuid_, name_, key_, parent_uuid_, order_) VALUES ('8', '添加权限', 'sys_permission:add', '25', 0);
INSERT INTO sys_permission (uuid_, name_, key_, parent_uuid_, order_) VALUES ('9', '编辑权限', 'sys_permission:edit', '25', 0);
INSERT INTO sys_permission (uuid_, name_, key_, parent_uuid_, order_) VALUES ('11', '编辑菜单', 'menu:edit', '39', 0);
INSERT INTO sys_permission (uuid_, name_, key_, parent_uuid_, order_) VALUES ('12', '删除菜单', 'menu:delete', '39', 0);
INSERT INTO sys_permission (uuid_, name_, key_, parent_uuid_, order_) VALUES ('13', '添加菜单', 'menu:add', '39', 0);
INSERT INTO sys_permission (uuid_, name_, key_, parent_uuid_, order_) VALUES ('16', '查看菜单', 'menu:check', '39', 0);
INSERT INTO sys_permission (uuid_, name_, key_, parent_uuid_, order_) VALUES ('17', '菜单授权', 'menu:grant', '39', 0);
INSERT INTO sys_permission (uuid_, name_, key_, parent_uuid_, order_) VALUES ('22', '添加角色', 'role:add', '28', 1);
INSERT INTO sys_permission (uuid_, name_, key_, parent_uuid_, order_) VALUES ('23', '删除角色', 'role:delete', '28', 2);
INSERT INTO sys_permission (uuid_, name_, key_, parent_uuid_, order_) VALUES ('24', '编辑角色', 'role:edit', '28', 3);
INSERT INTO sys_permission (uuid_, name_, key_, parent_uuid_, order_) VALUES ('25', '权限管理(页面)', 'sys_permission:manage', '2', 2);
INSERT INTO sys_permission (uuid_, name_, key_, parent_uuid_, order_) VALUES ('26', '查看角色', 'role:check', '28', 0);
INSERT INTO sys_permission (uuid_, name_, key_, parent_uuid_, order_) VALUES ('27', '角色授权', 'role:grant', '28', 4);
INSERT INTO sys_permission (uuid_, name_, key_, parent_uuid_, order_) VALUES ('28', '角色管理(页面)', 'role:manage', '2', 1);
INSERT INTO sys_permission (uuid_, name_, key_, parent_uuid_, order_) VALUES ('31', '添加用户', 'user:add', '35', 0);
INSERT INTO sys_permission (uuid_, name_, key_, parent_uuid_, order_) VALUES ('32', '删除用户', 'user:delete', '35', 1);
INSERT INTO sys_permission (uuid_, name_, key_, parent_uuid_, order_) VALUES ('33', '编辑用户', 'user:edit', '35', 2);
INSERT INTO sys_permission (uuid_, name_, key_, parent_uuid_, order_) VALUES ('34', '查看用户', 'user:check', '35', 3);
INSERT INTO sys_permission (uuid_, name_, key_, parent_uuid_, order_) VALUES ('35', '用户管理(页面)', 'user:manage', '18', 4);
INSERT INTO sys_permission (uuid_, name_, key_, parent_uuid_, order_) VALUES ('36', '查看角色权限', 'rolesys_permission:check', '28', 0);
INSERT INTO sys_permission (uuid_, name_, key_, parent_uuid_, order_) VALUES ('37', '查询权限，不包括自己以及子级权限', 'sys_permission:check:notselfchildren', '25', 1);
INSERT INTO sys_permission (uuid_, name_, key_, parent_uuid_, order_) VALUES ('38', '查询菜单权限', 'menupermisson:check', '39', 1);
INSERT INTO sys_permission (uuid_, name_, key_, parent_uuid_, order_) VALUES ('39', '菜单管理（页面）', 'menu:manage', '2', 0);
INSERT INTO sys_permission (uuid_, name_, key_, parent_uuid_, order_) VALUES ('40', '查看菜单，不包括自己以及子级菜单', 'menu:check:notselfchildren', '39', 1);
INSERT INTO sys_permission (uuid_, name_, key_, parent_uuid_, order_) VALUES ('41', '日志记录', 'logs:record', '1', 4);
INSERT INTO sys_permission (uuid_, name_, key_, parent_uuid_, order_) VALUES ('42', '登录日志（页面）', 'loginlog:manage', '41', 1);
INSERT INTO sys_permission (uuid_, name_, key_, parent_uuid_, order_) VALUES ('43', '登录日志查看', 'loginlog:check', '42', 2);
INSERT INTO sys_permission (uuid_, name_, key_, parent_uuid_, order_) VALUES ('44', '用户分布(页面)', 'loginlog:userlocations', '41', 3);
INSERT INTO sys_permission (uuid_, name_, key_, parent_uuid_, order_) VALUES ('45', '获取用户坐标', 'loginlog:getuserlocations', '44', 4);
INSERT INTO sys_permission (uuid_, name_, key_, parent_uuid_, order_) VALUES ('46', '分配角色', 'distribute:role', '35', 4);
