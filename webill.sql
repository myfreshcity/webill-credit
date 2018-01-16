/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Version : 50717
 Source Host           : localhost
 Source Database       : mmh

 Target Server Version : 50717
 File Encoding         : utf-8

 Date: 05/11/2017 15:51:55 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

---------------------------------------
#2017-8-30
#用户管理——车辆信息补充资料，car_user_rel表增加字段

ALTER TABLE `car_user_rel`
ADD COLUMN `id_card_p_path`  varchar(200) NULL COMMENT '身份证正面路径' AFTER `mobile_no`,
ADD COLUMN `id_card_n_path`  varchar(200) NULL COMMENT '身份证反面路径' AFTER `id_card_p_path`,
ADD COLUMN `vehicle_license_p_path`  varchar(200) NULL COMMENT '行驶证正面路径' AFTER `id_card_n_path`,
ADD COLUMN `vehicle_license_n_path`  varchar(200) NULL COMMENT '行驶证反面路径' AFTER `vehicle_license_p_path`,
ADD COLUMN `drive_license_p_path`  varchar(200) NULL COMMENT '驾驶证正面路径' AFTER `vehicle_license_n_path`,
ADD COLUMN `drive_license_n_path`  varchar(200) NULL COMMENT '驾驶证反面路径' AFTER `drive_license_p_path`;


-- ----------------------------
--  Table structure for `car_info`
-- ----------------------------
DROP TABLE IF EXISTS `car_info`;
CREATE TABLE `car_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `license_no` varchar(255) NOT NULL COMMENT '车牌号',
  `engine_no` varchar(255) NOT NULL COMMENT '发动机号',
  `frame_no` varchar(255) NOT NULL COMMENT '车架号',
  `car_owner` varchar(255) NOT NULL COMMENT '车主姓名',
  `owner_phone` varchar(20) DEFAULT NULL COMMENT '车主联系电话(deprecated)',
  `search_sequence_no` varchar(255) NOT NULL COMMENT '查询码(deprecated)',
  `user_id` int(11) DEFAULT NULL COMMENT '车辆管理人ID(deprecated)',
  `brand_name` varchar(255) DEFAULT NULL COMMENT '厂牌型号',
  `model_code` varchar(255) DEFAULT NULL COMMENT '车型代码',
  `seat_count` tinyint(2) DEFAULT NULL COMMENT '核定载客',
  `exhaust_scale` varchar(50) DEFAULT NULL COMMENT '排量／功率',
  `purchase_price` int(11) DEFAULT NULL COMMENT '新车购置价',
  `purchase_price_lb` int(11) DEFAULT NULL COMMENT '车型类比价',
  `enroll_date` date DEFAULT NULL COMMENT '初次登记日期',
  `complete_kerb_mass` varchar(255) DEFAULT NULL COMMENT '整备质量',
  `last_illege_qtime` datetime DEFAULT NULL COMMENT '上一次违章查询时间',
  `prm_end_time` datetime DEFAULT NULL COMMENT '保险到期时间',
  `insurer_com` varchar(255) DEFAULT NULL COMMENT '投保公司',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COMMENT='车辆基础信息表';

-- ----------------------------
--  Table structure for `car_user_rel`
-- ----------------------------
DROP TABLE IF EXISTS `car_user_rel`;
CREATE TABLE `car_user_rel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `car_id` int(11) NOT NULL COMMENT '车辆ID,关联car_info的编号',
  `user_id` int(11) NOT NULL COMMENT '用户ID,关联user的编号',
  `license_no` varchar(50) NOT NULL COMMENT '车牌号',
  `mobile_no` varchar(50) DEFAULT NULL COMMENT '车主联系电话',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='车辆用户关联表';

-- ----------------------------
--  Table structure for `illegal`
-- ----------------------------
DROP TABLE IF EXISTS `illegal`;
CREATE TABLE `illegal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `license_no` varchar(255) NOT NULL COMMENT '车辆编号',
  `carorg` varchar(50) NOT NULL COMMENT '管局名称',
  `count` int(11) NOT NULL COMMENT '违章次数',
  `total_price` int(11) DEFAULT NULL COMMENT '罚款数',
  `total_score` int(11) DEFAULT NULL COMMENT '扣分数',
  `query_times` int(5) DEFAULT '0' COMMENT '远程请求次数',
  `update_time` datetime DEFAULT NULL,
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='违章记录主表';

-- ----------------------------
--  Table structure for `illegal_detail`
-- ----------------------------
DROP TABLE IF EXISTS `illegal_detail`;
CREATE TABLE `illegal_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `illegal_id` int(11) NOT NULL COMMENT '违章主表编号',
  `occur_time` datetime NOT NULL COMMENT '发生时间',
  `address` varchar(500) NOT NULL COMMENT '地点',
  `content` varchar(5000) NOT NULL COMMENT '违章内容',
  `legalnum` varchar(50) DEFAULT NULL COMMENT '违章代码',
  `illegalid` varchar(50) DEFAULT NULL COMMENT '违章ID',
  `price` int(11) DEFAULT NULL COMMENT '罚款数',
  `score` int(11) DEFAULT '0' COMMENT '扣分数',
  `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '状态。-1:删除，0-正常',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COMMENT='违章记录子表';

-- ----------------------------
--  Table structure for `premium`
-- ----------------------------
DROP TABLE IF EXISTS `premium`;
CREATE TABLE `premium` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `car_id` int(11) DEFAULT NULL COMMENT '车辆编号',
  `start_date` datetime DEFAULT NULL COMMENT '开始日期',
  `end_date` datetime DEFAULT NULL COMMENT '结束日期',
  `ci_value` double DEFAULT NULL COMMENT '交强险保费',
  `bi_value` double DEFAULT NULL COMMENT '商业险保费',
  `tax_value` double DEFAULT NULL COMMENT '车船税',
  `insurer_com` varchar(255) DEFAULT NULL COMMENT '承保公司',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='保险（交强险）基本信息';

-- ----------------------------
--  Table structure for `premium_detail`
-- ----------------------------
DROP TABLE IF EXISTS `premium_detail`;
CREATE TABLE `premium_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `prm_id` int(11) NOT NULL COMMENT '交强险编号',
  `start_date` date DEFAULT NULL COMMENT '起始日期',
  `end_date` date DEFAULT NULL COMMENT '终止日期',
  `amount` double DEFAULT NULL COMMENT '保额',
  `pre_value` double DEFAULT NULL COMMENT '保费',
  `prm_name` varchar(255) DEFAULT NULL COMMENT '保费名称',
  `prm_code` varchar(255) DEFAULT NULL COMMENT '保费代码',
  `insurer_com` varchar(255) DEFAULT NULL COMMENT '承保公司名称',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商业险明细';

-- ----------------------------
--  Table structure for `req_error_log`
-- ----------------------------
DROP TABLE IF EXISTS `req_error_log`;
CREATE TABLE `req_error_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `action` varchar(50) DEFAULT NULL COMMENT '动作名称',
  `key` varchar(255) DEFAULT NULL COMMENT '信息键值',
  `msg` varchar(2000) DEFAULT NULL COMMENT '信息内容',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='异常网络请求记录表';

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `open_id` varchar(255) NOT NULL COMMENT '微信ID',
  `weixin_nick` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '微信昵称',
  `username` varchar(50) DEFAULT NULL COMMENT '用户登录名',
  `password` varchar(50) DEFAULT NULL COMMENT '用户密码',
  `mobile_no` varchar(255) DEFAULT NULL COMMENT '联系移动电话',
  `id_no` varchar(255) DEFAULT NULL COMMENT '身份证号码',
  `real_name` varchar(255) DEFAULT NULL COMMENT '真实姓名',
  `is_staff` tinyint(4) DEFAULT '0' COMMENT '是否为企业员工 0:否，1:是',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态 -1:删除，0:正常',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
