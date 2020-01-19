/*
 Navicat Premium Data Transfer

 Source Server         : remoteMysql
 Source Server Type    : MySQL
 Source Server Version : 50564
 Source Host           : 176.122.169.95:13306
 Source Schema         : delay_schedule

 Target Server Type    : MySQL
 Target Server Version : 50564
 File Encoding         : 65001

 Date: 19/01/2020 09:56:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for delay_task
-- ----------------------------
DROP TABLE IF EXISTS `delay_task`;
CREATE TABLE `delay_task` (
  `record_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id` char(36) NOT NULL,
  `slot_id` int(11) NOT NULL,
  `type` varchar(255) NOT NULL,
  `payload` varchar(255) DEFAULT NULL,
  `publish_time` bigint(20) NOT NULL,
  `schedule_time` bigint(20) NOT NULL,
  `execute_time` bigint(20) DEFAULT NULL,
  `finished_time` bigint(20) DEFAULT NULL,
  `ttr` int(11) DEFAULT NULL,
  `executed_count` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `create_time` char(19) NOT NULL,
  `update_time` char(19) NOT NULL,
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;
