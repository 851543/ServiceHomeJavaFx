/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : 192.168.58.1:3306
 Source Schema         : service-home

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 27/11/2024 16:17:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dispatch
-- ----------------------------
DROP TABLE IF EXISTS `dispatch`;
CREATE TABLE `dispatch`
(
    `id`                  bigint                                                 NOT NULL AUTO_INCREMENT COMMENT 'id',
    `repair_id`           bigint                                                 NOT NULL COMMENT '报修id',
    `user_id`             bigint                                                 NOT NULL COMMENT '用户id',
    `fault_analysis`      varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '故障分析',
    `maintenance_process` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '维修过程',
    `repair_Results`      varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '维修结果',
    `status`              char(1) CHARACTER SET utf8 COLLATE utf8_general_ci     NULL DEFAULT '0' COMMENT '报修状态（0未处理 1已处理）',
    `del_flag`            char(1) CHARACTER SET utf8 COLLATE utf8_general_ci     NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
    `create_by`           varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time`         datetime                                               NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`           varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time`         datetime                                               NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 100
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '派修信息表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of dispatch
-- ----------------------------

-- ----------------------------
-- Table structure for repair
-- ----------------------------
DROP TABLE IF EXISTS `repair`;
CREATE TABLE `repair`
(
    `id`          bigint                                                 NOT NULL AUTO_INCREMENT COMMENT 'id',
    `user_id`     bigint                                                 NOT NULL COMMENT '用户id',
    `floor`       varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '楼号',
    `room`        varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '房号',
    `content`     text CHARACTER SET utf8 COLLATE utf8_general_ci        NULL COMMENT '内容',
    `status`      char(1) CHARACTER SET utf8 COLLATE utf8_general_ci     NULL DEFAULT '0' COMMENT '报修状态（0未提交 1待受理 2已派工 3维修结束）',
    `del_flag`    char(1) CHARACTER SET utf8 COLLATE utf8_general_ci     NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
    `create_by`   varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                               NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                               NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 100
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '报修信息表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of repair
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`
(
    `id`          bigint                                                 NOT NULL AUTO_INCREMENT COMMENT 'id',
    `role_name`   varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
    `status`      char(1) CHARACTER SET utf8 COLLATE utf8_general_ci     NOT NULL COMMENT '角色状态（0正常 1停用）',
    `del_flag`    char(1) CHARACTER SET utf8 COLLATE utf8_general_ci     NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
    `create_by`   varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                               NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                               NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 100
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '角色信息表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------

-- ----------------------------
-- Table structure for service
-- ----------------------------
DROP TABLE IF EXISTS `service`;
CREATE TABLE `service`
(
    `id`              bigint                                                 NOT NULL AUTO_INCREMENT COMMENT 'id',
    `user_id`         bigint                                                 NOT NULL COMMENT '用户id',
    `repair_id`       bigint                                                 NOT NULL COMMENT '报修id',
    `dispatch_id`     bigint                                                 NOT NULL COMMENT '派修id',
    `fault_analysis`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '故障分析',
    `service_process` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '维修过程',
    `service_result`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '维修结果',
    `del_flag`        char(1) CHARACTER SET utf8 COLLATE utf8_general_ci     NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
    `create_by`       varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time`     datetime                                               NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`       varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time`     datetime                                               NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 100
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '维修信息表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of service
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`           bigint                                                  NOT NULL AUTO_INCREMENT COMMENT 'id',
    `user_name`    varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '用户账号',
    `nick_name`    varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '用户昵称',
    `email`        varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '用户邮箱',
    `phone_number` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '手机号码',
    `sex`          char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
    `avatar`       varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '头像地址',
    `password`     varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '密码',
    `status`       char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
    `del_flag`     char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
    `create_by`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '创建者',
    `create_time`  datetime                                                NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '更新者',
    `update_time`  datetime                                                NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 100
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '用户信息表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`
(
    `user_id` bigint NOT NULL COMMENT '用户id',
    `role_id` bigint NOT NULL COMMENT '角色id',
    PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '用户和角色关联表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_role
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
