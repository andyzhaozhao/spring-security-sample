SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `tdf_permission`;
CREATE TABLE `tdf_permission` (
  `id` varchar(255) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `flag` int(11) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `permission_description` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `pid` varchar(255) DEFAULT NULL,
  `permission_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `tdf_permission`
-- ----------------------------
BEGIN;
INSERT INTO `tdf_permission` VALUES ('5b66ecf45d634159a08468898b1b3218', null, '2018-07-26 10:56:37', '1', null, '2018-07-26 10:56:56', null, 'home', '/users', null, 'ROLE_USER');
INSERT INTO `tdf_permission` VALUES ('cc377e1b32e74e71953ddcd595d54918', null, '2018-07-26 10:56:37', '1', null, '2018-07-26 10:56:56', null, 'ABel', '/admins', null, 'ROLE_ADMIN');
COMMIT;


-- ----------------------------
--  Table structure for `tdf_permission_role`
-- ----------------------------
DROP TABLE IF EXISTS `tdf_permission_role`;
CREATE TABLE `tdf_permission_role` (
  `role_id` varchar(255) NOT NULL,
  `permission_id` varchar(255) NOT NULL,
  PRIMARY KEY (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `tdf_permission_role`
-- ----------------------------
BEGIN;
INSERT INTO `tdf_permission_role` VALUES
('5b66ecf45d634159a08468898b1b3217', '5b66ecf45d634159a08468898b1b3218'),
('5b66ecf45d634159a08468898b1b3217', 'cc377e1b32e74e71953ddcd595d54918'),
('cc377e1b32e74e71953ddcd595d5498b', '5b66ecf45d634159a08468898b1b3218');
COMMIT;