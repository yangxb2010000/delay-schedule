/*
 Navicat Premium Data Transfer

 Source Server         : remoteMysql
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : 176.122.169.95:13306
 Source Schema         : delay_schedule

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 16/01/2020 21:13:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for delay_task
-- ----------------------------
DROP TABLE IF EXISTS `delay_task`;
CREATE TABLE `delay_task` (
  `id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `slot_id` int(11) NOT NULL,
  `type` varchar(255) NOT NULL,
  `payload` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `publish_time` bigint(20) NOT NULL,
  `schedule_time` bigint(20) NOT NULL,
  `execute_time` bigint(20) DEFAULT NULL,
  `finished_time` bigint(20) DEFAULT NULL,
  `ttr` int(11) DEFAULT NULL,
  `executed_count` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `create_time` char(19) NOT NULL,
  `update_time` char(19) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

SET FOREIGN_KEY_CHECKS = 1;
