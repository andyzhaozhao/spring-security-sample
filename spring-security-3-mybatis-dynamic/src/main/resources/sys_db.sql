SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` varchar(255) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `flag` int(11) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `role_description` varchar(255) DEFAULT NULL,
  `role_index` int(11) DEFAULT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  `show_users` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('5b66ecf45d634159a08468898b1b3217', null, '2018-03-02 16:12:07', '1', null, null, null, null, null, 'ROLE_ADMIN', null);
INSERT INTO `role` VALUES ('cc377e1b32e74e71953ddcd595d5498b', null, '2018-03-02 16:12:07', '1', null, null, null, null, null, 'ROLE_USER', null);

-- ----------------------------
-- Table structure for role_user
-- ----------------------------
DROP TABLE IF EXISTS `role_user`;
CREATE TABLE `role_user` (
  `role_id` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`role_id`,`user_id`),
  KEY `FKpdk1hvijgq1tq3jq7pbj2s27x` (`user_id`),
  CONSTRAINT `FKiqpmjd2qb4rdkej916ymonic6` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKpdk1hvijgq1tq3jq7pbj2s27x` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_user
-- ----------------------------
INSERT INTO `role_user` VALUES ('5b66ecf45d634159a08468898b1b3217', '6613831cac9e4597abbd0138116a8f3c');
INSERT INTO `role_user` VALUES ('cc377e1b32e74e71953ddcd595d5498b', '6613831cac9e4597abbd0138116a8f3c');
INSERT INTO `role_user` VALUES ('cc377e1b32e74e71953ddcd595d5498b', '34e68107e48a4c2180451c0f1f643308');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` varchar(255) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `flag` int(11) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `birthday` varchar(255) DEFAULT NULL,
  `blog` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `id_number` varchar(255) DEFAULT NULL,
  `integral` tinyint(4) DEFAULT '0',
  `login_name` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_num` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `tag` varchar(255) DEFAULT NULL,
  `user_index` int(11) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `avatar_content` longblob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_info
-- ----------------------------
BEGIN;
INSERT INTO `user_info` VALUES ('34e68107e48a4c2180451c0f1f643308',
null, '2018-07-20 13:52:16', '1', null, '2018-07-20 13:52:36', '', '', '2012-06-15 14:45',
'', '', '', '', null, 'user', '', '$2a$10$bZqmPlaGB2AuGR5fIU6kO.cSVvGkaNoKWLeVwrHezFq3SvA0booIa',
'', '', '1', '', '1', 'user', null),
('6613831cac9e4597abbd0138116a8f3c', null, null,
'1', null, '2018-04-26 13:18:59', '', 'user_admin.jpg', '2012-06-15 14:45', '',
'admin@gmail.com', '', '', null, 'admin', '',
 '$2a$10$bZqmPlaGB2AuGR5fIU6kO.cSVvGkaNoKWLeVwrHezFq3SvA0booIa', '',
 '', '1', '', '1', '管理员',NULL );

COMMIT;
