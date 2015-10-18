
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id_` int(11) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `uuid_` varchar(255) NOT NULL COMMENT 'UUID',
  `user_name_` varchar(255) NOT NULL COMMENT '账号',
  `nick_name_` varchar(255) NOT NULL COMMENT '昵称',
  `real_name_` varchar(255) NOT NULL COMMENT '姓名',
  `password_` varchar(255) NOT NULL COMMENT '密码',
  `create_time_` datetime NOT NULL COMMENT '注册时间',
  PRIMARY KEY (`id_`),
  UNIQUE KEY `sys_user.user_name_` (`user_name_`),
    UNIQUE KEY `sys_user.uuid_` (`uuid_`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO sys_user (uuid_, user_name_, nick_name_, real_name_,  password_, create_time_) VALUES ('1', 'admin','管理员','管理员姓名', '123456', '2015-08-27 16:44:56');
INSERT INTO sys_user (uuid_, user_name_, nick_name_, real_name_,  password_, create_time_) VALUES ('2', 'test', '测试', '测试姓名','123456', '2015-09-01 08:26:39');
