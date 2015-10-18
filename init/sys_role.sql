SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id_` int(10) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `uuid_` varchar(255) NOT NULL COMMENT 'UUID',
  `key_` varchar(255) NOT NULL COMMENT '角色key',
  `role_name_` varchar(255) NOT NULL COMMENT '角色名字',
  `status_` varchar(255) NOT NULL COMMENT 'status',
  `create_time_` TIMESTAMP NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id_`),
  UNIQUE KEY `key` (`key_`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO sys_role (uuid_, name_, key_) VALUES ('1', '管理员', 'admin');
INSERT INTO sys_role (uuid_, name_, key_) VALUES ('2', '测试角色', 'test');
