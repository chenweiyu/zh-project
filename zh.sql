/*
Navicat MySQL Data Transfer

Source Server         : myconnection
Source Server Version : 50726
Source Host           : localhost:3306
Source Database       : zh

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2022-02-11 11:16:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `address`
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '地址id',
  `householder_street` varchar(255) DEFAULT NULL COMMENT '街道',
  `householder_unit` varchar(255) DEFAULT NULL COMMENT '门牌号',
  `householder_house_num` varchar(255) DEFAULT NULL COMMENT '单元室',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of address
-- ----------------------------
INSERT INTO `address` VALUES ('1', 'utvpl11', 'lloux11', 'cttxk11');
INSERT INTO `address` VALUES ('2', 'utvpl11', 'lloux11', 'cttxk11');
INSERT INTO `address` VALUES ('3', 'rhpek', 'ephjk', 'mokdh');
INSERT INTO `address` VALUES ('4', '地方VS', '3', '65');
INSERT INTO `address` VALUES ('5', 'xegyb', 'dvvob', 'ydidv');
INSERT INTO `address` VALUES ('6', 'bahww', 'rdpgz', 'ggkux');
INSERT INTO `address` VALUES ('7', 'neelv', 'cpctu', 'csinm');
INSERT INTO `address` VALUES ('8', 'ywjdl', 'kygci', 'golht');
INSERT INTO `address` VALUES ('9', 'etaat', 'bohgk', 'rjmwo');
INSERT INTO `address` VALUES ('10', 'bdbtc', 'wufte', 'rbkdy');
INSERT INTO `address` VALUES ('11', '地方', '2', '32');
INSERT INTO `address` VALUES ('12', '防守打法', '3', '54');
INSERT INTO `address` VALUES ('13', '阿凡达', '5', '341');
INSERT INTO `address` VALUES ('14', '地方VS', '3', '65');
INSERT INTO `address` VALUES ('15', '天蝎', '34', '546');
INSERT INTO `address` VALUES ('19', '321413', '134124', '12412');

-- ----------------------------
-- Table structure for `community`
-- ----------------------------
DROP TABLE IF EXISTS `community`;
CREATE TABLE `community` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '社区id',
  `community_name` varchar(255) NOT NULL COMMENT '社区名称',
  `community_year` date DEFAULT NULL COMMENT '成立年份',
  `community_cover_area` int(11) DEFAULT NULL COMMENT '占地面积',
  `community_building` int(11) DEFAULT NULL COMMENT '楼栋数',
  `community_unit` int(11) DEFAULT NULL COMMENT '单元数',
  `community_holder_sum` int(11) DEFAULT NULL COMMENT '总户数',
  `community_person_sum` int(11) DEFAULT NULL COMMENT '总人数',
  `community_other` varchar(255) DEFAULT NULL COMMENT '其他',
  PRIMARY KEY (`id`),
  UNIQUE KEY `community_name` (`community_name`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of community
-- ----------------------------
INSERT INTO `community` VALUES ('3', 'ghonk', '1993-08-05', '879', '105', '788', '258', '888', 'lqmot');
INSERT INTO `community` VALUES ('4', 'ocyer', '1992-07-22', '244', '478', '612', '535', '465', 'omqjh');
INSERT INTO `community` VALUES ('5', 'pkyyd', '1989-03-10', '197', '256', '792', '401', '1008', 'mjrso');
INSERT INTO `community` VALUES ('7', 'eotjr', '1970-04-15', '13', '472', '364', '274', '526', 'fyzjx');
INSERT INTO `community` VALUES ('8', 'ronkn', '1992-06-21', '837', '917', '970', '93', '104', 'wepgk');
INSERT INTO `community` VALUES ('9', 'dzybh', '2019-09-17', '292', '869', '802', '871', '286', 'shzms');
INSERT INTO `community` VALUES ('10', 'weyqz', '2012-04-01', '669', '891', '939', '807', '933', 'gwaik');
INSERT INTO `community` VALUES ('11', '天佑社区', '2019-12-31', '2314', '324', '231', '3421', '42341', '发生对方的法');
INSERT INTO `community` VALUES ('13', '福字社区', '1999-12-31', '324134', '321', '324', '4143', '234143', '而非擦打工v是否大V');
INSERT INTO `community` VALUES ('15', '天造乾社区', '2009-12-31', '41234', '123', '123', '23144', '234143', 'v这爱过VR把水果v');
INSERT INTO `community` VALUES ('16', '算法社区', '2009-12-31', '234', '1231', '14', '23412', '123413', '的方法v爱对方');

-- ----------------------------
-- Table structure for `community_expand`
-- ----------------------------
DROP TABLE IF EXISTS `community_expand`;
CREATE TABLE `community_expand` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '社区拓展id',
  `ce_occupancy` double(11,0) DEFAULT NULL COMMENT '社区入住率',
  `ce_unit_rate` double(11,0) DEFAULT NULL COMMENT '是否为单位集资',
  `ce_vaccination_rate` double(11,0) DEFAULT NULL COMMENT '社区疫苗接种率',
  `ce_other` varchar(255) DEFAULT NULL COMMENT '其他',
  `community_id` bigint(11) NOT NULL COMMENT '社区id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of community_expand
-- ----------------------------
INSERT INTO `community_expand` VALUES ('3', null, null, null, null, '3');
INSERT INTO `community_expand` VALUES ('4', null, null, null, null, '4');
INSERT INTO `community_expand` VALUES ('5', null, null, null, null, '5');
INSERT INTO `community_expand` VALUES ('7', null, null, null, null, '7');
INSERT INTO `community_expand` VALUES ('8', null, null, null, null, '8');
INSERT INTO `community_expand` VALUES ('9', null, null, null, null, '9');
INSERT INTO `community_expand` VALUES ('10', null, null, null, null, '10');
INSERT INTO `community_expand` VALUES ('11', null, null, null, null, '11');
INSERT INTO `community_expand` VALUES ('13', null, null, null, null, '13');
INSERT INTO `community_expand` VALUES ('15', null, null, null, null, '15');
INSERT INTO `community_expand` VALUES ('16', null, null, null, null, '16');

-- ----------------------------
-- Table structure for `dict`
-- ----------------------------
DROP TABLE IF EXISTS `dict`;
CREATE TABLE `dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '字典名称',
  `type` varchar(100) NOT NULL COMMENT '字典类型',
  `code` varchar(100) NOT NULL COMMENT '字典码',
  `value` varchar(1000) NOT NULL COMMENT '字典值',
  `order_num` int(11) DEFAULT '0' COMMENT '排序',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `type` (`type`,`code`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COMMENT='数据字典表';

-- ----------------------------
-- Records of dict
-- ----------------------------
INSERT INTO `dict` VALUES ('19', '性别', 'sex', '男', 'man', '0', '');
INSERT INTO `dict` VALUES ('20', '性别', 'sex', '女', 'woman', '1', '');
INSERT INTO `dict` VALUES ('23', '性别', 'sex', '不详', 'bx', '2', '');
INSERT INTO `dict` VALUES ('26', '是否', 'yesOrNo', '是', 'yes', '1', '');
INSERT INTO `dict` VALUES ('27', '是否', 'yesOrNo', '否', 'no', '2', '');
INSERT INTO `dict` VALUES ('28', '政治面貌', 'outlook', '团员', 'league_member', '0', '');
INSERT INTO `dict` VALUES ('29', '政治面貌', 'outlook', '党员', 'party_member', '1', '');
INSERT INTO `dict` VALUES ('30', '政治面貌', 'outlook', '普通群众', 'common_person', '2', null);
INSERT INTO `dict` VALUES ('31', '政治面貌', 'outlook', '其他', 'other', '3', '');
INSERT INTO `dict` VALUES ('32', '与户主关系', 'andHouseholderRation', '户主', 'householder', '0', '');
INSERT INTO `dict` VALUES ('33', '与户主关系', 'andHouseholderRation', '父亲', 'father', '1', '');
INSERT INTO `dict` VALUES ('34', '与户主关系', 'andHouseholderRation', '母亲', 'mother', '2', '');
INSERT INTO `dict` VALUES ('35', '与户主关系', 'andHouseholderRation', '妻子', 'wife', '4', '');
INSERT INTO `dict` VALUES ('36', '与户主关系', 'andHouseholderRation', '丈夫', 'husband', '3', '');
INSERT INTO `dict` VALUES ('37', '与户主关系', 'andHouseholderRation', '儿子', 'son', '5', '');
INSERT INTO `dict` VALUES ('38', '与户主关系', 'andHouseholderRation', '女儿', 'daughter', '6', '');
INSERT INTO `dict` VALUES ('39', '与户主关系', 'andHouseholderRation', '其他', 'other', '7', '');

-- ----------------------------
-- Table structure for `electric`
-- ----------------------------
DROP TABLE IF EXISTS `electric`;
CREATE TABLE `electric` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '序号id',
  `electric_price` double(11,2) DEFAULT '0.00' COMMENT '电费单价',
  `electric_money` double(11,2) DEFAULT '0.00' COMMENT '住户用电金额',
  `electric_num` double(11,2) DEFAULT '0.00' COMMENT '每月用电度数',
  `electric_date` date DEFAULT NULL COMMENT '月份',
  `householder_id` bigint(11) NOT NULL COMMENT '户主id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of electric
-- ----------------------------
INSERT INTO `electric` VALUES ('1', '1.00', '451.00', '451.00', '2022-01-01', '1');
INSERT INTO `electric` VALUES ('2', '38.00', '55.00', '49.00', '2022-01-01', '2');
INSERT INTO `electric` VALUES ('3', '36.00', '18.00', '78.00', '2022-01-01', '3');
INSERT INTO `electric` VALUES ('4', '100.00', '90.00', '67.00', '2022-01-01', '4');
INSERT INTO `electric` VALUES ('5', '46.00', '81.00', '24.00', '2022-01-01', '5');
INSERT INTO `electric` VALUES ('6', '39.00', '34.00', '62.00', '2022-01-01', '6');
INSERT INTO `electric` VALUES ('7', '37.00', '33.00', '14.00', '2022-01-01', '7');
INSERT INTO `electric` VALUES ('8', '31.00', '38.00', '77.00', '2022-01-01', '8');
INSERT INTO `electric` VALUES ('9', '11.00', '86.00', '21.00', '2022-01-01', '9');
INSERT INTO `electric` VALUES ('10', '61.00', '25.00', '72.00', '2022-01-01', '10');
INSERT INTO `electric` VALUES ('11', '66.00', '12.00', '42.00', '2022-01-01', '1');
INSERT INTO `electric` VALUES ('12', '56.00', '45.00', '7.00', '2022-01-01', '2');
INSERT INTO `electric` VALUES ('13', '90.00', '93.00', '16.00', '2022-01-01', '3');
INSERT INTO `electric` VALUES ('14', '12.00', '51.00', '58.00', '2022-01-01', '4');
INSERT INTO `electric` VALUES ('15', '80.00', '62.00', '80.00', '2022-01-01', '5');
INSERT INTO `electric` VALUES ('16', '25.00', '5.00', '37.00', '2022-01-01', '6');
INSERT INTO `electric` VALUES ('17', '1.00', '87.00', '62.00', '2022-01-01', '7');
INSERT INTO `electric` VALUES ('18', '95.00', '59.00', '3.00', '2022-01-01', '8');
INSERT INTO `electric` VALUES ('19', '53.00', '64.00', '82.00', '2022-01-01', '9');
INSERT INTO `electric` VALUES ('20', '23.00', '87.00', '95.00', '2021-08-01', '10');
INSERT INTO `electric` VALUES ('21', '0.00', '0.00', '0.00', null, '11');
INSERT INTO `electric` VALUES ('22', '0.00', '0.00', '0.00', null, '12');
INSERT INTO `electric` VALUES ('23', '0.00', '0.00', '0.00', null, '13');
INSERT INTO `electric` VALUES ('24', '0.00', '0.00', '0.00', null, '14');
INSERT INTO `electric` VALUES ('25', '0.00', '0.00', '0.00', null, '15');
INSERT INTO `electric` VALUES ('26', '0.00', '0.00', '0.00', null, '16');
INSERT INTO `electric` VALUES ('27', '0.00', '0.00', '0.00', null, '17');
INSERT INTO `electric` VALUES ('28', '0.00', '0.00', '0.00', null, '18');
INSERT INTO `electric` VALUES ('29', '2.00', '108.00', '54.00', '2021-06-01', '1');
INSERT INTO `electric` VALUES ('30', '1.00', '78.00', '78.00', '2021-06-01', '2');
INSERT INTO `electric` VALUES ('31', '1.00', '123.00', '123.00', '2021-07-01', '3');
INSERT INTO `electric` VALUES ('32', '1.00', '123.00', '123.00', '2021-09-01', '15');
INSERT INTO `electric` VALUES ('33', '1.00', '321.00', '321.00', '2021-07-01', '14');

-- ----------------------------
-- Table structure for `household`
-- ----------------------------
DROP TABLE IF EXISTS `household`;
CREATE TABLE `household` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '住户id',
  `household_name` varchar(32) NOT NULL COMMENT '姓名',
  `household_phone` varchar(11) DEFAULT NULL COMMENT '联系方式',
  `household_identify` varchar(18) DEFAULT NULL COMMENT '身份号码',
  `household_sex` varchar(10) DEFAULT NULL COMMENT '性别',
  `household_birth` date DEFAULT NULL COMMENT '出身日期',
  `household_is` varchar(10) NOT NULL COMMENT '是否为户主',
  `household_relation` varchar(32) DEFAULT NULL COMMENT '与户主关系',
  `household_native_place` varchar(255) DEFAULT NULL COMMENT '籍贯',
  `household_hj_place` varchar(255) DEFAULT NULL COMMENT '户籍所在地',
  `household_outlook` varchar(20) DEFAULT NULL COMMENT '政治面貌',
  `household_veterans` varchar(10) DEFAULT NULL COMMENT '退役军人',
  `household_soldier_fam` varchar(10) DEFAULT NULL COMMENT '是否军人家庭',
  `household_martyr_fam` varchar(10) DEFAULT NULL COMMENT '是否为烈士家属',
  `householder_id` bigint(11) NOT NULL COMMENT '户主id',
  `community_id` bigint(11) NOT NULL COMMENT '社区id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `household_name` (`household_name`),
  UNIQUE KEY `household_identify` (`household_identify`),
  KEY `householder_id` (`householder_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of household
-- ----------------------------
INSERT INTO `household` VALUES ('1', 'ursky', '13514116676', 'ubbew', '男', '1995-12-29', '是', '户主', 'ytgyr', 'hkckr', '党员', '是', '是', '是', '1', '3');
INSERT INTO `household` VALUES ('4', 'efwce', 'phslg', 'mdvzb', 'fmclu', '2013-03-14', 'kvmty', 'bbdkg', 'gcduw', 'colkq', 'kzusd', 'vuomz', 'lipcu', 'vwrnc', '1', '11');
INSERT INTO `household` VALUES ('5', 'shzdr', 'cqhhu', 'mnbwn', 'ikswv', '1975-08-31', 'qjpyh', 'sbhww', 'jesuv', 'ioohu', 'lkars', 'weuwt', 'tgncd', 'oukoo', '1', '11');
INSERT INTO `household` VALUES ('6', 'yntld', 'vijhm', 'nsubc', 'edwrk', '1999-12-21', 'zdwjq', 'tvble', 'tahmj', 'cjuoe', 'ueqst', 'letih', 'fytsd', 'ecbud', '2', '11');
INSERT INTO `household` VALUES ('7', 'opznm', 'uiurg', 'wfbeu', 'jciyw', '1972-02-28', 'ykkuq', 'pwmmn', 'eirce', 'xdzhw', 'gbjsv', 'qbkcm', 'juqqs', 'unyly', '2', '11');
INSERT INTO `household` VALUES ('9', 'luekm11', '13514116576', 'uvsyl11', '男', '2016-10-03', '否', '儿子', 'qhmxs11', 'gjjgv11', '普通群众', '否', '否', '否', '3', '11');
INSERT INTO `household` VALUES ('10', 'dshes', '13514116677', 'zmmni', 'dpkgi', '1991-11-10', '否', 'yubdb', 'xbaur', 'ulevq', '党员', '否', '否', '是', '4', '11');
INSERT INTO `household` VALUES ('11', '无极', '13514116676', '3241341341341324', '1', '2021-10-14', '否', '儿子', '发达发顺丰', 'vafdfvdzf', '团员', '2', '2', '2', '2', '3');
INSERT INTO `household` VALUES ('12', '天谴', '13514116675', '324145135135413', '男', '2021-10-05', '否', '儿子', '的发生地方', 'v大V等发达', '团员', '否', '否', '否', '3', '7');
INSERT INTO `household` VALUES ('13', '天下', '15362999697', '2341314411341341', '男', '2021-10-19', '否', '儿子', '父亲而发', '爱地方v的', '团员', '否', '否', '否', '4', '4');

-- ----------------------------
-- Table structure for `householder`
-- ----------------------------
DROP TABLE IF EXISTS `householder`;
CREATE TABLE `householder` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '住户户主id',
  `householder_name` varchar(32) NOT NULL COMMENT '户主名称',
  `householder_num` int(11) DEFAULT NULL COMMENT '住户人数',
  `address_id` bigint(11) NOT NULL COMMENT '地址id',
  `community_id` bigint(11) NOT NULL COMMENT '社区id',
  `household_id` bigint(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`householder_name`),
  KEY `address_id` (`address_id`),
  KEY `community_id` (`community_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of householder
-- ----------------------------
INSERT INTO `householder` VALUES ('1', 'qley', '4', '1', '2', '0');
INSERT INTO `householder` VALUES ('2', 'lrzbo', '6', '2', '2', '0');
INSERT INTO `householder` VALUES ('3', '	\r\nursky', '3', '3', '3', '1');
INSERT INTO `householder` VALUES ('4', 'cdqbi', '4', '4', '4', '0');
INSERT INTO `householder` VALUES ('5', 'hfodi', '2', '5', '5', '0');
INSERT INTO `householder` VALUES ('6', 'xnmqe', '4', '6', '1', '0');
INSERT INTO `householder` VALUES ('7', 'uegef', '3', '7', '2', '0');
INSERT INTO `householder` VALUES ('8', 'xwgjn', '5', '8', '3', '0');
INSERT INTO `householder` VALUES ('9', 'pgnqq', '7', '9', '4', '0');
INSERT INTO `householder` VALUES ('10', 'dcukk', '3', '10', '10', '0');
INSERT INTO `householder` VALUES ('11', '小唐', '4', '11', '1', '0');
INSERT INTO `householder` VALUES ('12', '李先生', '5', '12', '2', '0');
INSERT INTO `householder` VALUES ('13', '陈天', '5', '13', '1', '0');
INSERT INTO `householder` VALUES ('14', '无敌', '3', '14', '4', '0');
INSERT INTO `householder` VALUES ('15', '冯酱', '6', '15', '5', '0');
INSERT INTO `householder` VALUES ('19', '测试', '4', '19', '4', '0');

-- ----------------------------
-- Table structure for `household_expand`
-- ----------------------------
DROP TABLE IF EXISTS `household_expand`;
CREATE TABLE `household_expand` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '序号id',
  `holdexpand_position` varchar(255) DEFAULT NULL COMMENT '职业',
  `holdexpand_vaccination` varchar(10) DEFAULT NULL COMMENT '是否接种疫苗',
  `holdexpand_other` varchar(255) DEFAULT NULL COMMENT '其他',
  `household_id` bigint(11) NOT NULL COMMENT '住户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of household_expand
-- ----------------------------

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(64) NOT NULL COMMENT '权限名',
  `role_perms` varchar(225) DEFAULT NULL COMMENT '权限码',
  `remark` varchar(225) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '超级管理员', 'super;', '拥有所有权限');
INSERT INTO `role` VALUES ('2', '普通管理员', 'common;', '拥有住户、社区的管理权限');
INSERT INTO `role` VALUES ('3', '测试管理员', 'test;', '拥有住户、社区的管理权限');
INSERT INTO `role` VALUES ('5', '社区管理员', 'community;', '管理社区等事务');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '管理员id',
  `username` varchar(32) NOT NULL COMMENT '姓名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `position` varchar(255) DEFAULT NULL COMMENT '职位',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系方式',
  `create_time` date DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '123456', 'qdysa', 'lpltt', '1970-06-16');
INSERT INTO `user` VALUES ('2', 'iswjj', '123456', 'bohdl', 'olroo', '1984-12-16');
INSERT INTO `user` VALUES ('3', 'pawpi', '123456', 'hdyqx', 'aykle', '2015-09-15');
INSERT INTO `user` VALUES ('5', 'wvwsn', '123456', 'ustej', 'frkav', '1998-03-27');
INSERT INTO `user` VALUES ('6', 'tdrbp', '123456', 'egpbh', 'xjbvt', '2019-03-25');
INSERT INTO `user` VALUES ('7', 'tgxno', '123456', 'abniv', 'pxtfo', '2014-11-28');
INSERT INTO `user` VALUES ('8', 'hhryf', '123456', 'egbvi', 'dvekw', '2011-07-24');
INSERT INTO `user` VALUES ('9', 'gysww', '123456', 'ksupo', 'mfpzy', '1996-11-02');
INSERT INTO `user` VALUES ('10', 'gbfhb', '123456', 'kjwsn', 'ejrwi', '2018-11-24');
INSERT INTO `user` VALUES ('11', '老钟', '123456', '社区管理员', '13514116676', '2022-01-19');

-- ----------------------------
-- Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('2', '1', '2');
INSERT INTO `user_role` VALUES ('3', '2', '2');
INSERT INTO `user_role` VALUES ('7', '6', '2');
INSERT INTO `user_role` VALUES ('8', '7', '3');
INSERT INTO `user_role` VALUES ('11', '9', '2');
INSERT INTO `user_role` VALUES ('12', '9', '3');
INSERT INTO `user_role` VALUES ('13', '10', '2');
INSERT INTO `user_role` VALUES ('18', '11', '2');
INSERT INTO `user_role` VALUES ('19', '11', '3');
INSERT INTO `user_role` VALUES ('25', '1', '1');
INSERT INTO `user_role` VALUES ('26', '3', '1');
INSERT INTO `user_role` VALUES ('27', '5', '1');
INSERT INTO `user_role` VALUES ('28', '8', '1');
INSERT INTO `user_role` VALUES ('29', '9', '1');
INSERT INTO `user_role` VALUES ('30', '11', '1');

-- ----------------------------
-- Table structure for `water`
-- ----------------------------
DROP TABLE IF EXISTS `water`;
CREATE TABLE `water` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '序号id',
  `water_price` double(11,2) DEFAULT '0.00' COMMENT '水费单价',
  `water_money` double(11,2) DEFAULT '0.00' COMMENT '住户用水金额',
  `water_num` double(11,2) DEFAULT '0.00' COMMENT '每月用水度数',
  `water_date` date DEFAULT NULL COMMENT '月份',
  `householder_id` bigint(11) NOT NULL COMMENT '户主id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of water
-- ----------------------------
INSERT INTO `water` VALUES ('1', '2.50', '702.50', '281.00', '2021-07-01', '1');
INSERT INTO `water` VALUES ('2', '70.00', '94.00', '54.00', '2021-09-01', '2');
INSERT INTO `water` VALUES ('3', '90.00', '45.00', '3.00', '2021-09-01', '3');
INSERT INTO `water` VALUES ('4', '81.00', '26.00', '65.00', '2021-09-01', '4');
INSERT INTO `water` VALUES ('5', '28.00', '22.00', '58.00', '2021-09-01', '5');
INSERT INTO `water` VALUES ('6', '49.00', '38.00', '92.00', '2021-09-01', '6');
INSERT INTO `water` VALUES ('7', '8.00', '76.00', '30.00', '2021-09-01', '7');
INSERT INTO `water` VALUES ('8', '75.00', '92.00', '89.00', '2021-09-01', '8');
INSERT INTO `water` VALUES ('9', '43.00', '53.00', '70.00', '2021-09-01', '9');
INSERT INTO `water` VALUES ('10', '90.00', '6.00', '14.00', '2021-09-01', '10');
INSERT INTO `water` VALUES ('101', '20.00', '27.00', '15.00', '2021-08-01', '1');
INSERT INTO `water` VALUES ('102', '54.00', '43.00', '62.00', '2021-08-01', '2');
INSERT INTO `water` VALUES ('103', '20.00', '21.00', '95.00', '2021-08-01', '3');
INSERT INTO `water` VALUES ('104', '6.00', '30.00', '75.00', '2021-08-01', '4');
INSERT INTO `water` VALUES ('105', '29.00', '86.00', '44.00', '2021-08-01', '5');
INSERT INTO `water` VALUES ('106', '75.00', '97.00', '38.00', '2021-08-01', '6');
INSERT INTO `water` VALUES ('107', '75.00', '3.00', '42.00', '2021-08-01', '7');
INSERT INTO `water` VALUES ('108', '66.00', '35.00', '41.00', '2021-08-01', '8');
INSERT INTO `water` VALUES ('109', '22.00', '100.00', '17.00', '2021-08-01', '9');
INSERT INTO `water` VALUES ('110', '37.00', '34.00', '7.00', '2021-08-01', '10');
INSERT INTO `water` VALUES ('111', '0.00', '0.00', '0.00', null, '11');
INSERT INTO `water` VALUES ('112', '0.00', '0.00', '0.00', null, '12');
INSERT INTO `water` VALUES ('113', '0.00', '0.00', '0.00', null, '13');
INSERT INTO `water` VALUES ('114', '0.00', '0.00', '0.00', null, '14');
INSERT INTO `water` VALUES ('115', '0.00', '0.00', '0.00', null, '15');
INSERT INTO `water` VALUES ('116', '0.00', '0.00', '0.00', null, '16');
INSERT INTO `water` VALUES ('117', '0.00', '0.00', '0.00', null, '17');
INSERT INTO `water` VALUES ('118', '0.00', '0.00', '0.00', null, '18');
INSERT INTO `water` VALUES ('119', '0.00', '0.00', '0.00', null, '19');
INSERT INTO `water` VALUES ('120', '2.50', '85.00', '34.00', '2021-07-01', '1');
INSERT INTO `water` VALUES ('121', '2.50', '80.00', '32.00', '2021-07-01', '2');
INSERT INTO `water` VALUES ('122', '2.50', '85.00', '34.00', '2021-07-01', '4');
