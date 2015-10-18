
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id_` int(10) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `uuid_` varchar(255) NOT NULL COMMENT 'UUID',
  `role_uuid_` varchar(255) NOT NULL COMMENT 'ROEL_UUID',
  `permission_uuid_` varchar(255) NOT NULL COMMENT 'PERMISSION_UUID',
  `create_time_` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id_`),
  UNIQUE KEY `sys_role_permission.uuid_` (`uuid_`),
  UNIQUE KEY `sys_role_permission.user_role_` (`role_uuid_`,`permission_uuid_`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO sys_role_permission (uuid_, role_uuid_,permission_uuid_, create_time_) VALUES ('1', '1','1', '2015-08-27 16:44:56');
INSERT INTO sys_role_permission (uuid_, role_uuid_,permission_uuid_, create_time_) VALUES ('2', '2', '2',  '2015-09-01 08:26:39');
