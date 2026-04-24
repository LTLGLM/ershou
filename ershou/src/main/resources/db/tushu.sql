/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80035
 Source Host           : localhost:3306
 Source Schema         : tushu

 Target Server Type    : MySQL
 Target Server Version : 80035
 File Encoding         : 65001

 Date: 24/04/2026 11:20:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for store_address
-- ----------------------------
DROP TABLE IF EXISTS `store_address`;
CREATE TABLE `store_address`  (
  `address_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '收货人名称',
  `user_id` int NOT NULL DEFAULT 0 COMMENT '用户表的用户ID',
  `floor_id` int NOT NULL COMMENT '校园楼ID',
  `address_detail` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '详细收货地址',
  `tel` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '手机号码',
  `is_default` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否默认地址',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  `campus_id` int NOT NULL COMMENT '校区ID',
  PRIMARY KEY (`address_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '收货地址表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of store_address
-- ----------------------------
INSERT INTO `store_address` VALUES (1, '林帅', 6, 2, '如果热狗', '13214144421', 0, '2024-11-07 13:28:38', '2025-04-08 20:57:34', 0, 1);
INSERT INTO `store_address` VALUES (2, '666', 6, 1, '666', '13959363045', 1, '2025-04-08 21:04:50', '2025-04-08 21:04:50', 0, 1);
INSERT INTO `store_address` VALUES (3, '554', 6, 1, '333', '13959363465', 1, '2025-04-08 21:58:30', '2025-04-09 21:05:46', 0, 1);

-- ----------------------------
-- Table structure for store_campus
-- ----------------------------
DROP TABLE IF EXISTS `store_campus`;
CREATE TABLE `store_campus`  (
  `campus_id` int NOT NULL AUTO_INCREMENT COMMENT '校区唯一标识',
  `campus_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '校区名称',
  `campus_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '校区图片',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`campus_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '校区信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of store_campus
-- ----------------------------
INSERT INTO `store_campus` VALUES (1, 'xx校区1', '/dev-api/images/屏幕截图 2023-06-16 000110.png', '2025-04-06 14:18:56', 0);
INSERT INTO `store_campus` VALUES (2, 'xx校区2', '/dev-api/images/屏幕截图 2023-06-16 000110.png', '2025-04-09 20:50:19', 1);

-- ----------------------------
-- Table structure for store_cart
-- ----------------------------
DROP TABLE IF EXISTS `store_cart`;
CREATE TABLE `store_cart`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '购物车表ID',
  `uid` int UNSIGNED NOT NULL COMMENT '用户ID',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '添加时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '更新时间',
  `status` int NOT NULL DEFAULT 0 COMMENT '购物车状态',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`uid`) USING BTREE,
  INDEX `uid`(`uid`) USING BTREE,
  INDEX `uid_2`(`uid`) USING BTREE,
  INDEX `uid_3`(`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '购物车表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of store_cart
-- ----------------------------
INSERT INTO `store_cart` VALUES (1, 6, '2025-04-05 21:56:42', '2025-04-05 21:56:42', 0);

-- ----------------------------
-- Table structure for store_cart_info
-- ----------------------------
DROP TABLE IF EXISTS `store_cart_info`;
CREATE TABLE `store_cart_info`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `cart_id` bigint UNSIGNED NOT NULL COMMENT '购物车ID',
  `good_id` int UNSIGNED NOT NULL COMMENT '商品ID',
  `add_num` int UNSIGNED NOT NULL DEFAULT 1 COMMENT '加入数量',
  `checked` int NOT NULL DEFAULT 0 COMMENT '是否选中（1=是，0=否）',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_cart_id`(`cart_id`) USING BTREE,
  INDEX `idx_product_id`(`good_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '购物车详情表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of store_cart_info
-- ----------------------------

-- ----------------------------
-- Table structure for store_cate
-- ----------------------------
DROP TABLE IF EXISTS `store_cate`;
CREATE TABLE `store_cate`  (
  `cate_id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `cate_pid` bigint NOT NULL COMMENT '分类父ID',
  `cate_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类名称',
  `cate_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类图片',
  `cate_status` int NOT NULL DEFAULT 0 COMMENT '分类状态\r\n',
  `cate_order` int NOT NULL COMMENT '分类排序',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `del_flag` int NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`cate_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of store_cate
-- ----------------------------
INSERT INTO `store_cate` VALUES (1, 0, '电子产品', '/dev-api/images/c522dcc2977bc9fbd9bee6099c0a31fd6bc71596965f8f77999115606d14561d.jpg', 0, 1, '2024-11-11 13:28:18', 0);
INSERT INTO `store_cate` VALUES (2, 1, '手机', '/dev-api/images/c522dcc2977bc9fbd9bee6099c0a31fd6bc71596965f8f77999115606d14561d.jpg', 0, 1, '2024-11-11 13:29:59', 0);
INSERT INTO `store_cate` VALUES (3, 0, '书籍', '/dev-api/images/c522dcc2977bc9fbd9bee6099c0a31fd6bc71596965f8f77999115606d14561d.jpg', 0, 2, '2024-11-11 21:19:24', 0);
INSERT INTO `store_cate` VALUES (4, 3, '课本', '/dev-api/images/c522dcc2977bc9fbd9bee6099c0a31fd6bc71596965f8f77999115606d14561d.jpg', 0, 1, '2024-11-11 21:20:17', 0);
INSERT INTO `store_cate` VALUES (5, 3, '读物', '/dev-api/images/c522dcc2977bc9fbd9bee6099c0a31fd6bc71596965f8f77999115606d14561d.jpg', 0, 2, '2024-11-11 21:21:28', 0);
INSERT INTO `store_cate` VALUES (6, 0, '个人护理', '/dev-api/images/c522dcc2977bc9fbd9bee6099c0a31fd6bc71596965f8f77999115606d14561d.jpg', 0, 3, '2024-11-11 21:21:59', 0);
INSERT INTO `store_cate` VALUES (7, 6, '口红', '/dev-api/images/c522dcc2977bc9fbd9bee6099c0a31fd6bc71596965f8f77999115606d14561d.jpg', 0, 1, '2024-11-11 21:22:25', 0);
INSERT INTO `store_cate` VALUES (8, 0, '66', '/dev-api/images/c522dcc2977bc9fbd9bee6099c0a31fd6bc71596965f8f77999115606d14561d.jpg', 0, 1, '2024-11-28 16:15:34', 1);
INSERT INTO `store_cate` VALUES (9, 8, '55', '/dev-api/images/c522dcc2977bc9fbd9bee6099c0a31fd6bc71596965f8f77999115606d14561d.jpg', 0, 1, '2024-11-28 16:15:55', 1);
INSERT INTO `store_cate` VALUES (10, 0, '666', '/dev-api/images/c522dcc2977bc9fbd9bee6099c0a31fd6bc71596965f8f77999115606d14561d.jpg', 0, 1, '2024-11-28 16:45:23', 1);
INSERT INTO `store_cate` VALUES (11, 10, '655', '/dev-api/images/c522dcc2977bc9fbd9bee6099c0a31fd6bc71596965f8f77999115606d14561d.jpg', 0, 1, '2024-11-28 16:45:29', 1);

-- ----------------------------
-- Table structure for store_floor
-- ----------------------------
DROP TABLE IF EXISTS `store_floor`;
CREATE TABLE `store_floor`  (
  `floor_id` int NOT NULL AUTO_INCREMENT,
  `floor_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '校园楼名称',
  `floor_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '校园楼图片',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`floor_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '行政区域表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of store_floor
-- ----------------------------
INSERT INTO `store_floor` VALUES (1, '1楼', '/dev-api/images/屏幕截图 2023-06-16 000110.png', '2024-11-07 13:28:55', 0);
INSERT INTO `store_floor` VALUES (2, '2楼', NULL, '2024-11-08 21:53:41', 0);
INSERT INTO `store_floor` VALUES (3, '鹏峰1', NULL, '2024-11-08 21:54:11', 1);
INSERT INTO `store_floor` VALUES (4, '鹏峰2', '/dev-api/images/c522dcc2977bc9fbd9bee6099c0a31fd6bc71596965f8f77999115606d14561d.jpg', '2024-11-09 20:53:33', 1);
INSERT INTO `store_floor` VALUES (5, '鹏峰3', '/dev-api/images/c522dcc2977bc9fbd9bee6099c0a31fd6bc71596965f8f77999115606d14561d.jpg', '2024-11-09 20:54:24', 1);

-- ----------------------------
-- Table structure for store_good
-- ----------------------------
DROP TABLE IF EXISTS `store_good`;
CREATE TABLE `store_good`  (
  `good_id` mediumint NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `user_id` int NOT NULL COMMENT '商品所属用户',
  `mer_id` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '商户Id(0为总后台管理员创建,不为0的时候是商户后台创建)',
  `image` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品图片',
  `good_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品名称',
  `slider_image` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '轮播图',
  `good_info` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品简介',
  `keyword` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '关键字',
  `cate_id` int NOT NULL COMMENT '分类id',
  `price` decimal(8, 2) UNSIGNED NOT NULL DEFAULT 0.00 COMMENT '商品价格',
  `original_price` decimal(8, 2) UNSIGNED NOT NULL DEFAULT 0.00 COMMENT '原价',
  `postage` decimal(8, 2) UNSIGNED NULL DEFAULT 0.00 COMMENT '跑腿费',
  `unit_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单位名',
  `sort` smallint NOT NULL DEFAULT 0 COMMENT '排序',
  `stock` mediumint UNSIGNED NOT NULL DEFAULT 0 COMMENT '库存',
  `is_show` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态（0：未上架，1：上架）',
  `add_time` datetime(0) NOT NULL COMMENT '添加时间',
  `is_postage` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否包邮',
  `is_del` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除',
  `browse` int NULL DEFAULT 0 COMMENT '浏览量',
  PRIMARY KEY (`good_id`) USING BTREE,
  INDEX `cate_id`(`cate_id`) USING BTREE,
  INDEX `toggle_on_sale, is_del`(`is_del`) USING BTREE,
  INDEX `price`(`price`) USING BTREE,
  INDEX `is_show`(`is_show`) USING BTREE,
  INDEX `sort`(`sort`) USING BTREE,
  INDEX `add_time`(`add_time`) USING BTREE,
  INDEX `is_postage`(`is_postage`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of store_good
-- ----------------------------
INSERT INTO `store_good` VALUES (9, 6, 0, '/images/c522dcc2977bc9fbd9bee6099c0a31fd6bc71596965f8f77999115606d14561d.jpg', '123', '/images/c522dcc2977bc9fbd9bee6099c0a31fd6bc71596965f8f77999115606d14561d.jpg,/images/c522dcc2977bc9fbd9bee6099c0a31fd6bc71596965f8f77999115606d14561d.jpg,/images/c522dcc2977bc9fbd9bee6099c0a31fd6bc71596965f8f77999115606d14561d.jpg', '123 成色：撒法发', '手机，小米', 5, 21312.00, 0.00, 0.00, '件', 1, 2, 1, '2024-11-25 16:40:49', 0, 0, 0);
INSERT INTO `store_good` VALUES (10, 6, 0, '/images/屏幕截图 2023-06-16 000110.png', '555', '/images/屏幕截图 2023-06-16 000110.png,/images/屏幕截图 2023-06-16 000110.png,/images/屏幕截图 2023-06-16 000110.png', '555', '小米', 2, 3555.00, 0.00, 0.00, '部', 2, 0, 1, '2025-04-01 19:10:13', 0, 1, 0);
INSERT INTO `store_good` VALUES (13, 5, 0, '/images/eT4bTNau38SG00ff9957eae55a3994d809164ead5aad.png', '777', '/images/GcebuLXMNXdn00ff9957eae55a3994d809164ead5aad.png,/images/dSRWyBVry6fp5dccf6fd616616c8f92d6090d4d4ebcd.png,/images/djuhrcetjnfP8f68017eef20255240c7c735f4ca9ab5.png', '成色：9成新\n使用情况：777', '777', 4, 777.00, 777.00, 7.00, '7', 0, 0, 1, '2025-04-07 20:58:30', 0, 0, 0);

-- ----------------------------
-- Table structure for store_good_comment
-- ----------------------------
DROP TABLE IF EXISTS `store_good_comment`;
CREATE TABLE `store_good_comment`  (
  `comment_id` int NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `user_id` int NOT NULL COMMENT '买家ID',
  `good_id` int NOT NULL COMMENT '商品ID',
  `content` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论内容',
  `reply_content` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '卖家回复内容',
  `reply_time` datetime(0) NULL DEFAULT NULL COMMENT '回复时间',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `is_show` tinyint(1) NULL DEFAULT 1 COMMENT '是否显示(0:隐藏 1:显示)',
  PRIMARY KEY (`comment_id`) USING BTREE,
  INDEX `idx_good_id`(`good_id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品评论表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of store_good_comment
-- ----------------------------

-- ----------------------------
-- Table structure for store_messages
-- ----------------------------
DROP TABLE IF EXISTS `store_messages`;
CREATE TABLE `store_messages`  (
  `message_id` int NOT NULL AUTO_INCREMENT,
  `sender_id` int NOT NULL,
  `receiver_id` int NOT NULL,
  `message_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `send_time` datetime(0) NOT NULL,
  `receive_time` datetime(0) NULL DEFAULT NULL,
  `message_status` enum('sent','received','read','unread') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'sent',
  `is_deleted` tinyint NOT NULL DEFAULT 0,
  PRIMARY KEY (`message_id`, `sender_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of store_messages
-- ----------------------------

-- ----------------------------
-- Table structure for store_order
-- ----------------------------
DROP TABLE IF EXISTS `store_order`;
CREATE TABLE `store_order`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单号',
  `good_id` int NOT NULL COMMENT '商品ID',
  `uid` int UNSIGNED NOT NULL COMMENT '用户id',
  `real_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户姓名',
  `user_phone` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户电话',
  `address_id` int NULL DEFAULT NULL COMMENT '地址ID',
  `freight_price` decimal(8, 2) NOT NULL DEFAULT 0.00 COMMENT '跑腿费金额',
  `total_num` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '订单商品总数',
  `total_price` decimal(8, 2) UNSIGNED NOT NULL DEFAULT 0.00 COMMENT '订单总价',
  `pay_postage` decimal(8, 2) UNSIGNED NOT NULL DEFAULT 0.00 COMMENT '支付跑腿费',
  `paid` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '支付状态',
  `pay_time` timestamp(0) NULL DEFAULT NULL COMMENT '支付时间',
  `pay_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付方式',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '订单状态（0：待发货；1：待收货；2：已完成；3：已取消）',
  `delivery_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '快递单号/手机号',
  `mark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `is_del` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除',
  `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '管理员备注',
  `mer_id` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '商户用户ID',
  `is_remind` tinyint UNSIGNED NULL DEFAULT 0 COMMENT '消息提醒',
  `is_system_del` tinyint(1) NULL DEFAULT 0 COMMENT '后台是否删除',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `order_id_2`(`order_id`, `uid`) USING BTREE,
  INDEX `uid`(`uid`) USING BTREE,
  INDEX `add_time`(`create_time`) USING BTREE,
  INDEX `paid`(`paid`) USING BTREE,
  INDEX `pay_time`(`pay_time`) USING BTREE,
  INDEX `pay_type`(`pay_type`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `is_del`(`is_del`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of store_order
-- ----------------------------
INSERT INTO `store_order` VALUES (19, '26a2c6cf2ffc439cb301936dfd0fdb2c', 13, 6, 'LTLGLM', '13959363012', 1, 0.00, 1, 777.00, 0.00, 1, '2025-04-07 21:04:18', NULL, '2025-04-07 21:04:16', 0, NULL, NULL, 0, NULL, 5, 0, 0, '2025-04-07 21:04:18');
INSERT INTO `store_order` VALUES (20, '086c6c1671b242b2965c012015591d36', 13, 6, 'LTLGLM', '13959363012', 1, 0.00, 1, 777.00, 0.00, 1, '2025-04-07 21:31:57', NULL, '2025-04-07 21:31:49', 0, NULL, NULL, 0, NULL, 5, 0, 0, '2025-04-07 21:31:57');
INSERT INTO `store_order` VALUES (21, '31426735b6114e75bfe63536ab4654b0', 13, 6, 'LTLGLM', '13959363012', 1, 0.00, 1, 777.00, 0.00, 1, '2025-04-08 19:44:42', NULL, '2025-04-08 19:44:37', 2, NULL, NULL, 0, NULL, 5, 0, 0, '2025-04-08 20:45:20');
INSERT INTO `store_order` VALUES (22, '7f5b22eb444445049d3b418cbeba5b57', 13, 6, 'LTLGLM', '13959363012', 2, 0.00, 1, 777.00, 0.00, 1, '2025-04-08 21:05:13', NULL, '2025-04-08 21:05:09', 0, NULL, NULL, 0, NULL, 5, 0, 0, '2025-04-08 21:05:13');
INSERT INTO `store_order` VALUES (23, 'b813d09ad13f47578f05abbc43e6c830', 13, 6, 'LTLGLM', '13959363024', 2, 0.00, 1, 777.00, 0.00, 1, '2025-04-08 22:08:32', NULL, '2025-04-08 22:08:31', 0, NULL, NULL, 0, NULL, 5, 0, 0, '2025-04-08 22:08:32');
INSERT INTO `store_order` VALUES (24, '52fd0bad995c41f39ceb197315e74c31', 13, 6, 'LTLGLM', '13959363024', 2, 0.00, 1, 777.00, 0.00, 0, NULL, NULL, '2025-04-19 14:02:02', 3, NULL, NULL, 0, NULL, 5, 0, 0, '2025-04-19 14:02:53');
INSERT INTO `store_order` VALUES (25, 'dd982f7527c141e3833f215b4ddc0284', 13, 6, 'LTLGLM', '13959363023', 2, 0.00, 1, 777.00, 0.00, 1, '2025-04-19 17:39:25', NULL, '2025-04-19 17:38:51', 0, NULL, NULL, 0, NULL, 5, 0, 0, '2025-04-19 17:39:25');

-- ----------------------------
-- Table structure for store_order_info
-- ----------------------------
DROP TABLE IF EXISTS `store_order_info`;
CREATE TABLE `store_order_info`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_id` int UNSIGNED NOT NULL COMMENT '订单id',
  `good_uid` int NOT NULL COMMENT '商品用户ID',
  `good_id` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '商品ID',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `order_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单号',
  `pay_num` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '购买数量',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `oid`(`order_id`) USING BTREE,
  INDEX `product_id`(`good_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单购物详情表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of store_order_info
-- ----------------------------
INSERT INTO `store_order_info` VALUES (1, 1, 6, 9, '2025-04-06 16:21:51', '2025-04-06 16:21:51', '3ab626c0614d4189a5af39370764b932', 1);

-- ----------------------------
-- Table structure for store_user
-- ----------------------------
DROP TABLE IF EXISTS `store_user`;
CREATE TABLE `store_user`  (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名称',
  `password` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '用户密码',
  `gender` tinyint NULL DEFAULT 0 COMMENT '性别：0 未知， 1男， 2 女',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '最近一次登录时间',
  `last_login_ip` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '最近一次登录IP地址',
  `nickname` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '用户昵称或网络名称',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '用户手机号码',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '用户头像图片',
  `weixin_openid` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '微信登录openid',
  `session_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '微信登录会话KEY',
  `status` tinyint NULL DEFAULT 0 COMMENT '0 可用, 1 禁用',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `user_name`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of store_user
-- ----------------------------
INSERT INTO `store_user` VALUES (1, 'zhangsan', '123456', 2, '2024-11-07', '2024-11-07 13:26:46', '127.0.0.1', '林帅', '13075643564', '/dev-api/images/c522dcc2977bc9fbd9bee6099c0a31fd6bc71596965f8f77999115606d14561d.jpg', '1', '1', 0, '2024-11-07 13:27:33', '2024-11-08 16:00:16', 0);
INSERT INTO `store_user` VALUES (3, '666', '$2a$10$gly31JY6h3zfkrvrraM3/ejdnz33pBKt/cBp0ZsjauuS7Trs.WJNa', 1, '2024-11-17', NULL, '', '', '13935325255', '/dev-api/images/c522dcc2977bc9fbd9bee6099c0a31fd6bc71596965f8f77999115606d14561d.jpg', '', '', 0, '2024-11-08 15:28:48', '2024-11-08 16:00:20', 0);
INSERT INTO `store_user` VALUES (4, '555', '$2a$10$67EOBrXOvTXE0CTCdOM68utGAjV1rxHFFnxlmjLeS/BAcweyMNBri', 1, '2024-11-11', NULL, '', '', '13645465467', '/dev-api/images/c522dcc2977bc9fbd9bee6099c0a31fd6bc71596965f8f77999115606d14561d.jpg', '', '', 0, '2024-11-08 16:04:15', '2024-11-08 16:06:26', 0);
INSERT INTO `store_user` VALUES (5, '121', '$2a$10$D0DKpnA8vltYP4VXD9mkne4dwytUcVYGkl8lmkZWiUFSCXctua5d6', 0, '2024-11-05', NULL, '', '', '13321412413', '/dev-api/images/c522dcc2977bc9fbd9bee6099c0a31fd6bc71596965f8f77999115606d14561d.jpg', '', '', 0, '2024-11-08 16:05:05', '2024-11-08 16:06:29', 0);
INSERT INTO `store_user` VALUES (6, 'LTLGLM', '', 0, '2000-01-01', NULL, '', 'LTLGLM', '13959363023', 'https://thirdwx.qlogo.cn/mmopen/vi_32/POgEwh4mIHO4nibH0KlMECNjjGxQUq24ZEaGT4poC6icRiccVGKSyXwibcPq4BWmiaIGuG1icwxaQX6grC9VemZoJ8rg/132', 'otiqY5dF0cJ1tDWWHOjyoHV_2Gjk', '', 0, '2025-04-08 21:51:18', '2025-04-19 17:36:56', 0);

-- ----------------------------
-- Table structure for store_user_collect
-- ----------------------------
DROP TABLE IF EXISTS `store_user_collect`;
CREATE TABLE `store_user_collect`  (
  `collect_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '小程序用户ID',
  `good_id` bigint NOT NULL COMMENT '商品ID',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`collect_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of store_user_collect
-- ----------------------------

-- ----------------------------
-- Table structure for store_user_footmark
-- ----------------------------
DROP TABLE IF EXISTS `store_user_footmark`;
CREATE TABLE `store_user_footmark`  (
  `footmark_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '小程序用户ID',
  `good_id` bigint NOT NULL COMMENT '商品ID',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`footmark_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of store_user_footmark
-- ----------------------------
INSERT INTO `store_user_footmark` VALUES (2, 6, 13, '2025-04-19 17:38:30');
INSERT INTO `store_user_footmark` VALUES (3, 6, 9, '2026-04-24 10:10:14');

-- ----------------------------
-- Table structure for sys_admin
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin`;
CREATE TABLE `sys_admin`  (
  `admin_id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '管理员名称',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '管理员密码',
  `last_login_ip` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '最近一次登录IP地址',
  `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '最近一次登录时间',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '\'' COMMENT '头像图片',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `create_by` bigint NOT NULL COMMENT '创建用户',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`admin_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '管理员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_admin
-- ----------------------------
INSERT INTO `sys_admin` VALUES (1, 'admin', '$2a$10$zYjIs/lEfkZw28rIt2v4U.dVEcp/YsVkGsfYyHz9neIv6daxkwxla', '', '2026-04-24 09:16:18', '/dev-api/images/c522dcc2977bc9fbd9bee6099c0a31fd6bc71596965f8f77999115606d14561d.jpg', '2024-10-31 15:26:54', 1, 1, '2024-11-01 14:41:32', 0);
INSERT INTO `sys_admin` VALUES (31, '123456', '$2a$10$zYjIs/lEfkZw28rIt2v4U.dVEcp/YsVkGsfYyHz9neIv6daxkwxla', '', '2025-04-19 17:44:21', '/dev-api/images/c522dcc2977bc9fbd9bee6099c0a31fd6bc71596965f8f77999115606d14561d.jpg', '2024-10-31 15:26:54', 1, 1, '2024-11-01 14:41:32', 0);

-- ----------------------------
-- Table structure for sys_admin_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin_notice`;
CREATE TABLE `sys_admin_notice`  (
  `admin_id` bigint NOT NULL COMMENT '用户id',
  `notice_id` bigint NOT NULL COMMENT '通知id',
  `is_confirm` int NOT NULL DEFAULT 0 COMMENT '是否确认',
  `confirm_time` datetime(0) NULL DEFAULT NULL COMMENT '确认时间',
  `del_flag` int NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`admin_id`, `notice_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_admin_notice
-- ----------------------------
INSERT INTO `sys_admin_notice` VALUES (31, 34, 1, '2025-02-11 14:45:25', 0);
INSERT INTO `sys_admin_notice` VALUES (31, 35, 1, '2025-02-11 14:45:25', 0);
INSERT INTO `sys_admin_notice` VALUES (31, 36, 1, '2025-02-11 14:45:25', 0);
INSERT INTO `sys_admin_notice` VALUES (31, 37, 1, '2025-02-11 14:45:25', 0);
INSERT INTO `sys_admin_notice` VALUES (31, 38, 1, '2025-02-11 14:45:25', 0);
INSERT INTO `sys_admin_notice` VALUES (31, 39, 1, '2025-02-10 22:34:17', 0);
INSERT INTO `sys_admin_notice` VALUES (31, 40, 1, '2025-02-10 22:33:47', 0);
INSERT INTO `sys_admin_notice` VALUES (31, 41, 1, '2025-02-11 14:45:25', 0);
INSERT INTO `sys_admin_notice` VALUES (31, 42, 1, '2025-02-11 14:45:25', 0);
INSERT INTO `sys_admin_notice` VALUES (31, 43, 1, '2025-02-11 14:45:25', 0);
INSERT INTO `sys_admin_notice` VALUES (31, 44, 1, '2025-02-10 23:21:16', 0);
INSERT INTO `sys_admin_notice` VALUES (31, 45, 1, '2025-02-10 23:21:12', 0);
INSERT INTO `sys_admin_notice` VALUES (31, 46, 1, '2025-02-10 23:19:49', 0);
INSERT INTO `sys_admin_notice` VALUES (31, 47, 1, '2025-02-10 23:06:55', 0);
INSERT INTO `sys_admin_notice` VALUES (31, 48, 1, '2025-04-01 19:13:20', 0);
INSERT INTO `sys_admin_notice` VALUES (31, 49, 1, '2025-04-01 19:13:17', 0);
INSERT INTO `sys_admin_notice` VALUES (31, 50, 1, '2025-04-01 20:02:21', 0);
INSERT INTO `sys_admin_notice` VALUES (31, 51, 1, '2025-04-07 21:24:37', 0);
INSERT INTO `sys_admin_notice` VALUES (31, 52, 1, '2025-04-19 17:44:46', 0);

-- ----------------------------
-- Table structure for sys_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin_role`;
CREATE TABLE `sys_admin_role`  (
  `admin_id` bigint NOT NULL COMMENT '用户id',
  `role_id` bigint NOT NULL COMMENT '角色id',
  PRIMARY KEY (`admin_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_admin_role
-- ----------------------------
INSERT INTO `sys_admin_role` VALUES (1, 1);
INSERT INTO `sys_admin_role` VALUES (31, 2);

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `log_id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名称',
  `login_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录地址',
  `browser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '浏览器',
  `os` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作系统',
  `status` int NOT NULL COMMENT '登录状态',
  `msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作信息',
  `login_time` datetime(0) NOT NULL COMMENT '登录时间',
  `del_flag` int NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 385 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES (1, 'admin', '127.0.0.1', 'Chrome', 'Windows', 1, '验证码不正确', '2024-11-06 17:02:27', 0);
INSERT INTO `sys_log` VALUES (2, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-06 17:02:42', 0);
INSERT INTO `sys_log` VALUES (3, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2024-11-06 17:06:31', 0);
INSERT INTO `sys_log` VALUES (4, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-06 17:06:54', 1);
INSERT INTO `sys_log` VALUES (5, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-06 19:47:28', 0);
INSERT INTO `sys_log` VALUES (6, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2024-11-06 20:00:52', 0);
INSERT INTO `sys_log` VALUES (7, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-06 20:00:55', 0);
INSERT INTO `sys_log` VALUES (8, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2024-11-06 20:01:12', 0);
INSERT INTO `sys_log` VALUES (9, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-06 20:01:15', 0);
INSERT INTO `sys_log` VALUES (10, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-07 13:19:05', 0);
INSERT INTO `sys_log` VALUES (11, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-07 19:59:41', 0);
INSERT INTO `sys_log` VALUES (12, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-08 14:08:43', 0);
INSERT INTO `sys_log` VALUES (13, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-08 17:21:50', 0);
INSERT INTO `sys_log` VALUES (14, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-08 20:55:40', 0);
INSERT INTO `sys_log` VALUES (15, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-08 23:51:10', 0);
INSERT INTO `sys_log` VALUES (16, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-09 12:26:38', 0);
INSERT INTO `sys_log` VALUES (17, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-09 14:47:32', 0);
INSERT INTO `sys_log` VALUES (18, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-09 20:48:38', 0);
INSERT INTO `sys_log` VALUES (19, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-09 21:40:03', 0);
INSERT INTO `sys_log` VALUES (20, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-09 22:30:39', 0);
INSERT INTO `sys_log` VALUES (21, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-10 22:57:36', 0);
INSERT INTO `sys_log` VALUES (22, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-11 10:21:50', 0);
INSERT INTO `sys_log` VALUES (23, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-11 10:28:15', 0);
INSERT INTO `sys_log` VALUES (24, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2024-11-11 10:30:38', 0);
INSERT INTO `sys_log` VALUES (25, 'admin', '127.0.0.1', 'Chrome', 'Windows', 1, '验证码不正确', '2024-11-11 10:30:41', 0);
INSERT INTO `sys_log` VALUES (26, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-11 10:30:44', 0);
INSERT INTO `sys_log` VALUES (27, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-11 12:34:11', 0);
INSERT INTO `sys_log` VALUES (28, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-11 13:30:09', 0);
INSERT INTO `sys_log` VALUES (29, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-11 16:48:06', 0);
INSERT INTO `sys_log` VALUES (30, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-11 20:54:04', 0);
INSERT INTO `sys_log` VALUES (31, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-11 23:47:16', 0);
INSERT INTO `sys_log` VALUES (32, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-12 12:41:07', 0);
INSERT INTO `sys_log` VALUES (33, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-12 13:32:12', 0);
INSERT INTO `sys_log` VALUES (34, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-12 19:48:35', 0);
INSERT INTO `sys_log` VALUES (35, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-13 12:40:58', 0);
INSERT INTO `sys_log` VALUES (36, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-13 18:45:29', 0);
INSERT INTO `sys_log` VALUES (37, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-13 20:50:43', 0);
INSERT INTO `sys_log` VALUES (38, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-14 13:08:16', 0);
INSERT INTO `sys_log` VALUES (39, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-24 20:10:47', 0);
INSERT INTO `sys_log` VALUES (40, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-24 20:50:07', 0);
INSERT INTO `sys_log` VALUES (41, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-24 21:46:20', 0);
INSERT INTO `sys_log` VALUES (42, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-25 13:59:19', 0);
INSERT INTO `sys_log` VALUES (43, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-25 15:11:09', 0);
INSERT INTO `sys_log` VALUES (44, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-25 16:23:10', 0);
INSERT INTO `sys_log` VALUES (45, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-25 21:04:57', 0);
INSERT INTO `sys_log` VALUES (46, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-28 08:35:51', 0);
INSERT INTO `sys_log` VALUES (47, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-28 09:03:58', 0);
INSERT INTO `sys_log` VALUES (48, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-28 11:58:28', 0);
INSERT INTO `sys_log` VALUES (49, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-28 15:26:59', 0);
INSERT INTO `sys_log` VALUES (50, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-28 20:55:06', 0);
INSERT INTO `sys_log` VALUES (51, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-28 23:05:37', 0);
INSERT INTO `sys_log` VALUES (52, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2024-11-28 23:29:06', 0);
INSERT INTO `sys_log` VALUES (53, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-03 20:42:06', 0);
INSERT INTO `sys_log` VALUES (54, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-04 20:38:14', 0);
INSERT INTO `sys_log` VALUES (55, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-04 21:38:30', 0);
INSERT INTO `sys_log` VALUES (56, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-04 21:50:26', 0);
INSERT INTO `sys_log` VALUES (57, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-04 22:24:25', 0);
INSERT INTO `sys_log` VALUES (58, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-04 22:24:27', 0);
INSERT INTO `sys_log` VALUES (59, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-05 14:26:34', 0);
INSERT INTO `sys_log` VALUES (60, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-05 21:52:03', 0);
INSERT INTO `sys_log` VALUES (61, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-05 23:13:32', 0);
INSERT INTO `sys_log` VALUES (62, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-06 15:18:21', 0);
INSERT INTO `sys_log` VALUES (63, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-06 15:58:00', 0);
INSERT INTO `sys_log` VALUES (64, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-06 16:09:38', 0);
INSERT INTO `sys_log` VALUES (65, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-06 16:23:29', 0);
INSERT INTO `sys_log` VALUES (66, 'admin', '127.0.0.1', 'Chrome', 'Windows', 1, '验证码不正确', '2025-02-06 16:25:05', 0);
INSERT INTO `sys_log` VALUES (67, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-06 16:25:08', 0);
INSERT INTO `sys_log` VALUES (68, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-06 16:30:56', 0);
INSERT INTO `sys_log` VALUES (69, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-06 16:45:23', 0);
INSERT INTO `sys_log` VALUES (70, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-06 16:45:27', 0);
INSERT INTO `sys_log` VALUES (71, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-06 16:50:57', 0);
INSERT INTO `sys_log` VALUES (72, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-06 16:51:59', 0);
INSERT INTO `sys_log` VALUES (73, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-06 19:30:05', 0);
INSERT INTO `sys_log` VALUES (74, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-06 20:36:20', 0);
INSERT INTO `sys_log` VALUES (75, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-06 23:49:30', 0);
INSERT INTO `sys_log` VALUES (76, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-07 15:34:20', 0);
INSERT INTO `sys_log` VALUES (77, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-07 15:49:12', 0);
INSERT INTO `sys_log` VALUES (78, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-07 16:22:00', 0);
INSERT INTO `sys_log` VALUES (79, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-07 16:24:51', 0);
INSERT INTO `sys_log` VALUES (80, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-07 16:25:52', 0);
INSERT INTO `sys_log` VALUES (81, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-07 16:46:20', 0);
INSERT INTO `sys_log` VALUES (82, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-07 17:00:42', 0);
INSERT INTO `sys_log` VALUES (83, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-07 17:41:32', 0);
INSERT INTO `sys_log` VALUES (84, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-07 19:44:43', 0);
INSERT INTO `sys_log` VALUES (85, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-07 20:25:52', 0);
INSERT INTO `sys_log` VALUES (86, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-07 20:37:27', 0);
INSERT INTO `sys_log` VALUES (87, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-07 20:43:28', 0);
INSERT INTO `sys_log` VALUES (88, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-07 20:54:26', 0);
INSERT INTO `sys_log` VALUES (89, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-07 20:54:27', 0);
INSERT INTO `sys_log` VALUES (90, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-07 20:54:33', 0);
INSERT INTO `sys_log` VALUES (91, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-07 20:54:33', 0);
INSERT INTO `sys_log` VALUES (92, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-07 20:54:41', 0);
INSERT INTO `sys_log` VALUES (93, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-07 20:54:42', 0);
INSERT INTO `sys_log` VALUES (94, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-07 23:20:55', 0);
INSERT INTO `sys_log` VALUES (95, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-07 23:33:47', 0);
INSERT INTO `sys_log` VALUES (96, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-07 23:55:18', 0);
INSERT INTO `sys_log` VALUES (97, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-08 00:06:59', 0);
INSERT INTO `sys_log` VALUES (98, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-08 00:08:14', 0);
INSERT INTO `sys_log` VALUES (99, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-08 00:19:39', 0);
INSERT INTO `sys_log` VALUES (100, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-08 00:26:04', 0);
INSERT INTO `sys_log` VALUES (101, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-08 12:47:04', 0);
INSERT INTO `sys_log` VALUES (102, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-08 13:28:57', 0);
INSERT INTO `sys_log` VALUES (103, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-08 16:19:09', 0);
INSERT INTO `sys_log` VALUES (104, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-08 16:43:49', 0);
INSERT INTO `sys_log` VALUES (105, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-08 16:55:32', 0);
INSERT INTO `sys_log` VALUES (106, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-08 17:26:59', 0);
INSERT INTO `sys_log` VALUES (107, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-08 17:59:11', 0);
INSERT INTO `sys_log` VALUES (108, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-08 20:51:16', 0);
INSERT INTO `sys_log` VALUES (109, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-08 22:10:51', 0);
INSERT INTO `sys_log` VALUES (110, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-08 22:51:50', 0);
INSERT INTO `sys_log` VALUES (111, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-08 23:46:22', 0);
INSERT INTO `sys_log` VALUES (112, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-09 14:02:10', 0);
INSERT INTO `sys_log` VALUES (113, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-09 14:39:54', 0);
INSERT INTO `sys_log` VALUES (114, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-09 15:16:10', 0);
INSERT INTO `sys_log` VALUES (115, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-09 15:38:24', 0);
INSERT INTO `sys_log` VALUES (116, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-09 16:06:03', 0);
INSERT INTO `sys_log` VALUES (117, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-09 16:06:11', 0);
INSERT INTO `sys_log` VALUES (118, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-09 16:41:10', 0);
INSERT INTO `sys_log` VALUES (119, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-09 17:10:52', 0);
INSERT INTO `sys_log` VALUES (120, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-09 17:20:46', 0);
INSERT INTO `sys_log` VALUES (121, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-09 17:24:18', 0);
INSERT INTO `sys_log` VALUES (122, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-09 17:24:49', 0);
INSERT INTO `sys_log` VALUES (123, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-09 17:25:10', 0);
INSERT INTO `sys_log` VALUES (124, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-09 17:25:10', 0);
INSERT INTO `sys_log` VALUES (125, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-09 17:26:34', 0);
INSERT INTO `sys_log` VALUES (126, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-09 17:26:35', 0);
INSERT INTO `sys_log` VALUES (127, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-09 17:27:47', 0);
INSERT INTO `sys_log` VALUES (128, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-09 17:27:57', 0);
INSERT INTO `sys_log` VALUES (129, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-09 17:28:35', 0);
INSERT INTO `sys_log` VALUES (130, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-09 17:29:08', 0);
INSERT INTO `sys_log` VALUES (131, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-09 17:29:16', 0);
INSERT INTO `sys_log` VALUES (132, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-09 17:29:25', 0);
INSERT INTO `sys_log` VALUES (133, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-09 17:29:27', 0);
INSERT INTO `sys_log` VALUES (134, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-09 17:33:05', 0);
INSERT INTO `sys_log` VALUES (135, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-09 17:33:06', 0);
INSERT INTO `sys_log` VALUES (136, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-09 17:33:55', 0);
INSERT INTO `sys_log` VALUES (137, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-09 17:33:56', 0);
INSERT INTO `sys_log` VALUES (138, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-09 17:34:47', 0);
INSERT INTO `sys_log` VALUES (139, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-09 17:34:49', 0);
INSERT INTO `sys_log` VALUES (140, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-09 17:35:22', 0);
INSERT INTO `sys_log` VALUES (141, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-09 17:36:33', 0);
INSERT INTO `sys_log` VALUES (142, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-09 19:07:11', 0);
INSERT INTO `sys_log` VALUES (143, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-09 19:43:33', 0);
INSERT INTO `sys_log` VALUES (144, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-09 19:44:27', 0);
INSERT INTO `sys_log` VALUES (145, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-09 19:51:28', 0);
INSERT INTO `sys_log` VALUES (146, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-09 19:51:34', 0);
INSERT INTO `sys_log` VALUES (147, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-09 20:23:30', 0);
INSERT INTO `sys_log` VALUES (148, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-09 21:07:19', 0);
INSERT INTO `sys_log` VALUES (149, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-09 21:07:24', 0);
INSERT INTO `sys_log` VALUES (150, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-09 22:02:42', 0);
INSERT INTO `sys_log` VALUES (151, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-09 22:03:32', 0);
INSERT INTO `sys_log` VALUES (152, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-09 22:03:38', 0);
INSERT INTO `sys_log` VALUES (153, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-09 22:03:45', 0);
INSERT INTO `sys_log` VALUES (154, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-09 22:03:50', 0);
INSERT INTO `sys_log` VALUES (155, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-09 22:04:32', 0);
INSERT INTO `sys_log` VALUES (156, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-09 22:04:37', 0);
INSERT INTO `sys_log` VALUES (157, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-09 23:10:27', 0);
INSERT INTO `sys_log` VALUES (158, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-09 23:10:34', 0);
INSERT INTO `sys_log` VALUES (159, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-09 23:10:42', 0);
INSERT INTO `sys_log` VALUES (160, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-09 23:56:13', 0);
INSERT INTO `sys_log` VALUES (161, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-09 23:56:18', 0);
INSERT INTO `sys_log` VALUES (162, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-09 23:56:33', 0);
INSERT INTO `sys_log` VALUES (163, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-10 21:07:39', 0);
INSERT INTO `sys_log` VALUES (164, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-10 21:07:48', 0);
INSERT INTO `sys_log` VALUES (165, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-10 21:07:53', 0);
INSERT INTO `sys_log` VALUES (166, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-10 21:45:12', 0);
INSERT INTO `sys_log` VALUES (167, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-10 21:50:01', 0);
INSERT INTO `sys_log` VALUES (168, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-10 21:50:06', 0);
INSERT INTO `sys_log` VALUES (169, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-10 21:58:31', 0);
INSERT INTO `sys_log` VALUES (170, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-10 22:25:54', 0);
INSERT INTO `sys_log` VALUES (171, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-10 22:26:00', 0);
INSERT INTO `sys_log` VALUES (172, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-10 22:26:06', 0);
INSERT INTO `sys_log` VALUES (173, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-10 22:26:15', 0);
INSERT INTO `sys_log` VALUES (174, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-10 22:26:21', 0);
INSERT INTO `sys_log` VALUES (175, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-10 22:35:32', 0);
INSERT INTO `sys_log` VALUES (176, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-10 23:20:03', 0);
INSERT INTO `sys_log` VALUES (177, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-10 23:20:08', 0);
INSERT INTO `sys_log` VALUES (178, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 14:36:45', 0);
INSERT INTO `sys_log` VALUES (179, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 14:36:54', 0);
INSERT INTO `sys_log` VALUES (180, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-11 15:16:16', 0);
INSERT INTO `sys_log` VALUES (181, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 15:16:19', 0);
INSERT INTO `sys_log` VALUES (182, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-11 15:16:23', 0);
INSERT INTO `sys_log` VALUES (183, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 15:16:29', 0);
INSERT INTO `sys_log` VALUES (184, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-11 15:48:24', 0);
INSERT INTO `sys_log` VALUES (185, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 15:48:27', 0);
INSERT INTO `sys_log` VALUES (186, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-11 15:48:30', 0);
INSERT INTO `sys_log` VALUES (187, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 15:48:36', 0);
INSERT INTO `sys_log` VALUES (188, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-11 15:48:46', 0);
INSERT INTO `sys_log` VALUES (189, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 15:48:51', 0);
INSERT INTO `sys_log` VALUES (190, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-11 15:49:20', 0);
INSERT INTO `sys_log` VALUES (191, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 15:49:26', 0);
INSERT INTO `sys_log` VALUES (192, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-11 15:51:26', 0);
INSERT INTO `sys_log` VALUES (193, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 15:51:29', 0);
INSERT INTO `sys_log` VALUES (194, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-11 15:54:17', 0);
INSERT INTO `sys_log` VALUES (195, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 15:54:23', 0);
INSERT INTO `sys_log` VALUES (196, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 15:57:31', 0);
INSERT INTO `sys_log` VALUES (197, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-11 15:57:37', 0);
INSERT INTO `sys_log` VALUES (198, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 15:57:41', 0);
INSERT INTO `sys_log` VALUES (199, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-11 16:03:09', 0);
INSERT INTO `sys_log` VALUES (200, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 16:03:15', 0);
INSERT INTO `sys_log` VALUES (201, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-11 16:05:19', 0);
INSERT INTO `sys_log` VALUES (202, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 16:05:24', 0);
INSERT INTO `sys_log` VALUES (203, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-11 16:09:30', 0);
INSERT INTO `sys_log` VALUES (204, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 16:09:34', 0);
INSERT INTO `sys_log` VALUES (205, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-11 16:09:43', 0);
INSERT INTO `sys_log` VALUES (206, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 16:09:48', 0);
INSERT INTO `sys_log` VALUES (207, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-11 16:09:51', 0);
INSERT INTO `sys_log` VALUES (208, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 16:09:54', 0);
INSERT INTO `sys_log` VALUES (209, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-11 16:10:58', 0);
INSERT INTO `sys_log` VALUES (210, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 16:11:01', 0);
INSERT INTO `sys_log` VALUES (211, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-11 16:12:16', 0);
INSERT INTO `sys_log` VALUES (212, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 16:12:21', 0);
INSERT INTO `sys_log` VALUES (213, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-11 16:12:49', 0);
INSERT INTO `sys_log` VALUES (214, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 16:12:52', 0);
INSERT INTO `sys_log` VALUES (215, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 16:13:59', 0);
INSERT INTO `sys_log` VALUES (216, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-11 16:14:02', 0);
INSERT INTO `sys_log` VALUES (217, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 16:14:12', 0);
INSERT INTO `sys_log` VALUES (218, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 16:14:55', 0);
INSERT INTO `sys_log` VALUES (219, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 16:15:01', 0);
INSERT INTO `sys_log` VALUES (220, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-11 16:15:04', 0);
INSERT INTO `sys_log` VALUES (221, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 16:15:12', 0);
INSERT INTO `sys_log` VALUES (222, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-11 16:16:20', 0);
INSERT INTO `sys_log` VALUES (223, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 16:16:26', 0);
INSERT INTO `sys_log` VALUES (224, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-11 16:16:52', 0);
INSERT INTO `sys_log` VALUES (225, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 16:16:56', 0);
INSERT INTO `sys_log` VALUES (226, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-11 16:17:40', 0);
INSERT INTO `sys_log` VALUES (227, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 16:17:51', 0);
INSERT INTO `sys_log` VALUES (228, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-11 16:18:01', 0);
INSERT INTO `sys_log` VALUES (229, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 16:18:08', 0);
INSERT INTO `sys_log` VALUES (230, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-11 16:21:37', 0);
INSERT INTO `sys_log` VALUES (231, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 16:21:42', 0);
INSERT INTO `sys_log` VALUES (232, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-11 16:36:09', 0);
INSERT INTO `sys_log` VALUES (233, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 16:36:11', 0);
INSERT INTO `sys_log` VALUES (234, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 20:10:31', 0);
INSERT INTO `sys_log` VALUES (235, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 20:23:15', 0);
INSERT INTO `sys_log` VALUES (236, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-11 20:26:11', 0);
INSERT INTO `sys_log` VALUES (237, 'admin', '127.0.0.1', 'Chrome', 'Windows', 1, '验证码不正确', '2025-02-11 20:26:14', 0);
INSERT INTO `sys_log` VALUES (238, 'admin', '127.0.0.1', 'Chrome', 'Windows', 1, '验证码不正确', '2025-02-11 20:26:15', 0);
INSERT INTO `sys_log` VALUES (239, 'admin', '127.0.0.1', 'Chrome', 'Windows', 1, '验证码不正确', '2025-02-11 20:26:16', 0);
INSERT INTO `sys_log` VALUES (240, 'admin', '127.0.0.1', 'Chrome', 'Windows', 1, '验证码不正确', '2025-02-11 20:26:16', 0);
INSERT INTO `sys_log` VALUES (241, 'admin', '127.0.0.1', 'Chrome', 'Windows', 1, '验证码不正确', '2025-02-11 20:26:16', 0);
INSERT INTO `sys_log` VALUES (242, 'admin', '127.0.0.1', 'Chrome', 'Windows', 1, '验证码不正确', '2025-02-11 20:26:20', 0);
INSERT INTO `sys_log` VALUES (243, 'admin', '127.0.0.1', 'Chrome', 'Windows', 1, '验证码不正确', '2025-02-11 20:26:22', 0);
INSERT INTO `sys_log` VALUES (244, 'admin', '127.0.0.1', 'Chrome', 'Windows', 1, '验证码不正确', '2025-02-11 20:26:22', 0);
INSERT INTO `sys_log` VALUES (245, 'admin', '127.0.0.1', 'Chrome', 'Windows', 1, '验证码不正确', '2025-02-11 20:26:23', 0);
INSERT INTO `sys_log` VALUES (246, 'admin', '127.0.0.1', 'Chrome', 'Windows', 1, '验证码不正确', '2025-02-11 20:26:23', 0);
INSERT INTO `sys_log` VALUES (247, 'admin', '127.0.0.1', 'Chrome', 'Windows', 1, '验证码不正确', '2025-02-11 20:26:23', 0);
INSERT INTO `sys_log` VALUES (248, 'admin', '127.0.0.1', 'Chrome', 'Windows', 1, '验证码不正确', '2025-02-11 20:26:23', 0);
INSERT INTO `sys_log` VALUES (249, 'admin', '127.0.0.1', 'Chrome', 'Windows', 1, '验证码不正确', '2025-02-11 20:26:24', 0);
INSERT INTO `sys_log` VALUES (250, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 20:27:20', 0);
INSERT INTO `sys_log` VALUES (251, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-11 20:37:53', 0);
INSERT INTO `sys_log` VALUES (252, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 20:37:58', 0);
INSERT INTO `sys_log` VALUES (253, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-11 20:39:13', 0);
INSERT INTO `sys_log` VALUES (254, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 20:40:07', 0);
INSERT INTO `sys_log` VALUES (255, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-11 20:40:33', 0);
INSERT INTO `sys_log` VALUES (256, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 20:41:00', 0);
INSERT INTO `sys_log` VALUES (257, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-11 20:41:05', 0);
INSERT INTO `sys_log` VALUES (258, '123456', '127.0.0.1', 'Chrome', 'Windows', 1, '验证码不正确', '2025-02-11 20:47:03', 0);
INSERT INTO `sys_log` VALUES (259, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 20:47:05', 0);
INSERT INTO `sys_log` VALUES (260, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-11 20:47:12', 0);
INSERT INTO `sys_log` VALUES (261, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 20:47:28', 0);
INSERT INTO `sys_log` VALUES (262, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-11 20:47:33', 0);
INSERT INTO `sys_log` VALUES (263, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 20:47:54', 0);
INSERT INTO `sys_log` VALUES (264, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-11 20:48:02', 0);
INSERT INTO `sys_log` VALUES (265, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 20:49:45', 0);
INSERT INTO `sys_log` VALUES (266, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-11 20:49:52', 0);
INSERT INTO `sys_log` VALUES (267, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 20:50:20', 0);
INSERT INTO `sys_log` VALUES (268, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-11 20:50:32', 0);
INSERT INTO `sys_log` VALUES (269, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 20:50:39', 0);
INSERT INTO `sys_log` VALUES (270, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-11 20:52:13', 0);
INSERT INTO `sys_log` VALUES (271, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 20:52:23', 0);
INSERT INTO `sys_log` VALUES (272, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 22:54:02', 0);
INSERT INTO `sys_log` VALUES (273, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 22:54:07', 0);
INSERT INTO `sys_log` VALUES (274, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-11 23:24:08', 0);
INSERT INTO `sys_log` VALUES (275, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-11 23:24:11', 0);
INSERT INTO `sys_log` VALUES (276, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-12 00:03:53', 0);
INSERT INTO `sys_log` VALUES (277, 'admin', '127.0.0.1', 'Chrome', 'Windows', 1, '验证码不正确', '2025-02-12 00:04:00', 0);
INSERT INTO `sys_log` VALUES (278, 'admin', '127.0.0.1', 'Chrome', 'Windows', 1, '验证码不正确', '2025-02-12 00:04:02', 0);
INSERT INTO `sys_log` VALUES (279, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-12 00:04:06', 0);
INSERT INTO `sys_log` VALUES (280, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-12 00:04:15', 0);
INSERT INTO `sys_log` VALUES (281, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-12 00:12:12', 0);
INSERT INTO `sys_log` VALUES (282, 'admin', '127.0.0.1', 'Chrome', 'Windows', 1, '验证码不正确', '2025-02-12 17:01:59', 0);
INSERT INTO `sys_log` VALUES (283, 'admin', '127.0.0.1', 'Chrome', 'Windows', 1, '验证码不正确', '2025-02-12 17:02:06', 0);
INSERT INTO `sys_log` VALUES (284, 'admin', '127.0.0.1', 'Chrome', 'Windows', 1, '验证码不正确', '2025-02-12 17:04:22', 0);
INSERT INTO `sys_log` VALUES (285, 'admin', '127.0.0.1', 'Chrome', 'Windows', 1, '验证码不正确', '2025-02-12 17:04:23', 0);
INSERT INTO `sys_log` VALUES (286, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-12 17:04:26', 0);
INSERT INTO `sys_log` VALUES (287, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-12 17:04:31', 0);
INSERT INTO `sys_log` VALUES (288, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-12 17:04:38', 0);
INSERT INTO `sys_log` VALUES (289, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-12 17:04:49', 0);
INSERT INTO `sys_log` VALUES (290, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-12 17:11:23', 0);
INSERT INTO `sys_log` VALUES (291, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-12 17:11:30', 0);
INSERT INTO `sys_log` VALUES (292, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-12 17:11:35', 0);
INSERT INTO `sys_log` VALUES (293, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-12 17:11:39', 0);
INSERT INTO `sys_log` VALUES (294, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-12 17:18:07', 0);
INSERT INTO `sys_log` VALUES (295, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-12 17:18:10', 0);
INSERT INTO `sys_log` VALUES (296, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-12 17:18:18', 0);
INSERT INTO `sys_log` VALUES (297, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-12 22:00:15', 0);
INSERT INTO `sys_log` VALUES (298, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-12 22:00:27', 0);
INSERT INTO `sys_log` VALUES (299, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-12 22:01:27', 0);
INSERT INTO `sys_log` VALUES (300, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-12 22:01:30', 0);
INSERT INTO `sys_log` VALUES (301, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-02-12 22:03:45', 0);
INSERT INTO `sys_log` VALUES (302, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-12 22:03:49', 0);
INSERT INTO `sys_log` VALUES (303, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-12 22:11:19', 0);
INSERT INTO `sys_log` VALUES (304, 'admin', '127.0.0.1', 'Chrome', 'Windows', 1, '验证码不正确', '2025-02-12 22:11:21', 0);
INSERT INTO `sys_log` VALUES (305, 'admin', '127.0.0.1', 'Chrome', 'Windows', 1, '验证码不正确', '2025-02-12 22:11:25', 0);
INSERT INTO `sys_log` VALUES (306, 'admin', '127.0.0.1', 'Chrome', 'Windows', 1, '验证码不正确', '2025-02-12 22:12:06', 0);
INSERT INTO `sys_log` VALUES (307, 'admin', '127.0.0.1', 'Chrome', 'Windows', 1, '验证码不正确', '2025-02-12 22:12:07', 0);
INSERT INTO `sys_log` VALUES (308, 'admin', '127.0.0.1', 'Chrome', 'Windows', 1, '验证码不正确', '2025-02-12 22:12:57', 0);
INSERT INTO `sys_log` VALUES (309, 'admin', '127.0.0.1', 'Chrome', 'Windows', 1, '验证码不正确', '2025-02-12 22:12:59', 0);
INSERT INTO `sys_log` VALUES (310, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-12 22:13:01', 0);
INSERT INTO `sys_log` VALUES (311, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-12 22:20:49', 0);
INSERT INTO `sys_log` VALUES (312, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-12 22:20:55', 0);
INSERT INTO `sys_log` VALUES (313, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-12 22:48:30', 0);
INSERT INTO `sys_log` VALUES (314, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-12 22:49:05', 0);
INSERT INTO `sys_log` VALUES (315, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-12 23:03:13', 0);
INSERT INTO `sys_log` VALUES (316, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-12 23:03:19', 0);
INSERT INTO `sys_log` VALUES (317, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-12 23:41:44', 0);
INSERT INTO `sys_log` VALUES (318, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-12 23:48:20', 0);
INSERT INTO `sys_log` VALUES (319, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-12 23:55:31', 0);
INSERT INTO `sys_log` VALUES (320, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-13 00:40:11', 0);
INSERT INTO `sys_log` VALUES (321, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-13 00:42:11', 0);
INSERT INTO `sys_log` VALUES (322, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-13 00:45:29', 0);
INSERT INTO `sys_log` VALUES (323, 'admin', '127.0.0.1', 'Chrome', 'Windows', 1, '验证码不正确', '2025-02-13 00:52:03', 0);
INSERT INTO `sys_log` VALUES (324, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-13 00:52:07', 0);
INSERT INTO `sys_log` VALUES (325, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-13 00:55:47', 0);
INSERT INTO `sys_log` VALUES (326, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-13 01:00:18', 0);
INSERT INTO `sys_log` VALUES (327, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-13 01:05:39', 0);
INSERT INTO `sys_log` VALUES (328, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-13 01:06:30', 0);
INSERT INTO `sys_log` VALUES (329, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-13 01:07:06', 0);
INSERT INTO `sys_log` VALUES (330, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-13 01:07:37', 0);
INSERT INTO `sys_log` VALUES (331, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-13 01:08:13', 0);
INSERT INTO `sys_log` VALUES (332, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-13 01:09:18', 0);
INSERT INTO `sys_log` VALUES (333, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-13 01:10:44', 0);
INSERT INTO `sys_log` VALUES (334, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-13 01:11:42', 0);
INSERT INTO `sys_log` VALUES (335, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-13 01:13:25', 0);
INSERT INTO `sys_log` VALUES (336, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-13 01:14:49', 0);
INSERT INTO `sys_log` VALUES (337, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-13 01:15:48', 0);
INSERT INTO `sys_log` VALUES (338, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-13 01:16:01', 0);
INSERT INTO `sys_log` VALUES (339, 'admin', '127.0.0.1', 'Chrome', 'Windows', 1, '验证码不正确', '2025-02-15 15:04:36', 0);
INSERT INTO `sys_log` VALUES (340, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-15 15:04:39', 0);
INSERT INTO `sys_log` VALUES (341, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-16 13:09:05', 0);
INSERT INTO `sys_log` VALUES (342, 'admin', '127.0.0.1', 'Chrome', 'Windows', 1, '验证码不正确', '2025-02-17 15:30:29', 0);
INSERT INTO `sys_log` VALUES (343, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-17 15:30:34', 0);
INSERT INTO `sys_log` VALUES (344, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-17 15:32:48', 0);
INSERT INTO `sys_log` VALUES (345, '123456', '127.0.0.1', 'Chrome', 'Windows', 1, '验证码不正确', '2025-02-17 15:38:20', 0);
INSERT INTO `sys_log` VALUES (346, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-02-17 15:38:23', 0);
INSERT INTO `sys_log` VALUES (347, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-04-01 18:47:42', 0);
INSERT INTO `sys_log` VALUES (348, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-04-01 19:12:31', 0);
INSERT INTO `sys_log` VALUES (349, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-04-01 19:53:21', 0);
INSERT INTO `sys_log` VALUES (350, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-04-01 19:53:24', 0);
INSERT INTO `sys_log` VALUES (351, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-04-01 20:01:53', 0);
INSERT INTO `sys_log` VALUES (352, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-04-03 14:57:57', 0);
INSERT INTO `sys_log` VALUES (353, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-04-06 20:18:21', 0);
INSERT INTO `sys_log` VALUES (354, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-04-06 20:19:13', 0);
INSERT INTO `sys_log` VALUES (355, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-04-06 20:19:16', 0);
INSERT INTO `sys_log` VALUES (356, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-04-06 21:15:46', 0);
INSERT INTO `sys_log` VALUES (357, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-04-06 21:16:04', 0);
INSERT INTO `sys_log` VALUES (358, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-04-07 21:20:16', 0);
INSERT INTO `sys_log` VALUES (359, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-04-07 21:24:16', 0);
INSERT INTO `sys_log` VALUES (360, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-04-09 20:04:49', 0);
INSERT INTO `sys_log` VALUES (361, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-04-09 20:37:35', 0);
INSERT INTO `sys_log` VALUES (362, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-04-09 20:37:38', 0);
INSERT INTO `sys_log` VALUES (363, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-04-10 19:12:29', 0);
INSERT INTO `sys_log` VALUES (364, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-04-10 22:34:18', 0);
INSERT INTO `sys_log` VALUES (365, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-04-10 22:34:20', 0);
INSERT INTO `sys_log` VALUES (366, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-04-11 13:04:51', 0);
INSERT INTO `sys_log` VALUES (367, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-04-11 21:02:16', 0);
INSERT INTO `sys_log` VALUES (368, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-04-19 14:18:17', 0);
INSERT INTO `sys_log` VALUES (369, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-04-19 16:09:08', 0);
INSERT INTO `sys_log` VALUES (370, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2025-04-19 16:09:12', 0);
INSERT INTO `sys_log` VALUES (371, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-04-19 17:42:02', 0);
INSERT INTO `sys_log` VALUES (372, '123456', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2025-04-19 17:44:21', 0);
INSERT INTO `sys_log` VALUES (373, 'admin', '127.0.0.1', 'Safari', 'Mac OS', 0, '登录成功', '2026-02-11 16:37:11', 0);
INSERT INTO `sys_log` VALUES (374, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2026-02-11 16:50:31', 0);
INSERT INTO `sys_log` VALUES (375, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2026-02-11 16:50:40', 0);
INSERT INTO `sys_log` VALUES (376, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2026-02-11 17:06:26', 0);
INSERT INTO `sys_log` VALUES (377, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2026-02-11 17:06:29', 0);
INSERT INTO `sys_log` VALUES (378, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '退出登录', '2026-02-11 17:12:21', 0);
INSERT INTO `sys_log` VALUES (379, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2026-02-11 17:12:24', 0);
INSERT INTO `sys_log` VALUES (380, 'admin', '127.0.0.1', 'Chrome', 'Windows', 1, '验证码不正确', '2026-02-12 16:51:06', 0);
INSERT INTO `sys_log` VALUES (381, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2026-02-12 16:51:12', 0);
INSERT INTO `sys_log` VALUES (382, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2026-04-23 16:45:05', 0);
INSERT INTO `sys_log` VALUES (383, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2026-04-24 09:15:21', 0);
INSERT INTO `sys_log` VALUES (384, 'admin', '127.0.0.1', 'Chrome', 'Windows', 0, '登录成功', '2026-04-24 09:16:18', 0);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint NOT NULL AUTO_INCREMENT,
  `pid` bigint NOT NULL COMMENT '父菜单ID',
  `menu_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'NULL' COMMENT '菜单名',
  `menu_order` int NULL DEFAULT NULL COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组件路径',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `menu_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `create_by` bigint NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_by` bigint NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int NULL DEFAULT 0 COMMENT '是否删除（0未删除 1已删除）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2084 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, '用户管理', 1, '', '/usermanager', 'M', '0', '0', NULL, '#', 1, '2024-09-12 18:21:39', NULL, NULL, 0, '用户管理目录');
INSERT INTO `sys_menu` VALUES (2, 0, '物品管理', 3, '', '/goodmanager', 'M', '0', '0', NULL, '#', 1, '2024-09-12 18:28:01', NULL, NULL, 0, '物品管理目录');
INSERT INTO `sys_menu` VALUES (3, 0, '系统管理', 4, '', '/systemmanager', 'M', '0', '0', NULL, '#', 1, '2024-09-12 18:29:16', NULL, NULL, 0, '系统管理目录');
INSERT INTO `sys_menu` VALUES (4, 0, '配置管理', 5, '', '/setmanager', 'M', '0', '0', NULL, '#', 1, '2024-09-12 18:29:53', NULL, NULL, 0, '配置管理目录');
INSERT INTO `sys_menu` VALUES (5, 0, '统计报表', 6, '', '/statement', 'M', '0', '0', NULL, '#', 1, '2024-09-12 18:30:42', NULL, NULL, 0, '统计报表目录');
INSERT INTO `sys_menu` VALUES (6, 0, '订单管理', 2, '', '/ordermanager', 'M', '0', '0', NULL, '#', 1, '2024-10-22 23:04:23', NULL, NULL, 0, '订单管理目录');
INSERT INTO `sys_menu` VALUES (100, 1, '会员管理', 1, '/usermanager/vipuser', '/usermanager/vipuser', 'C', '0', '0', 'usermanager:vipuser:view', '#', 1, '2024-09-12 18:37:26', NULL, NULL, 0, '会员管理菜单');
INSERT INTO `sys_menu` VALUES (101, 1, '收货地址', 2, '/usermanager/address', '/usermanager/address', 'C', '0', '0', 'usermanager:address:view', '#', 1, '2024-09-12 18:40:50', NULL, NULL, 0, '收货地址菜单');
INSERT INTO `sys_menu` VALUES (102, 1, '会员收藏', 3, '/usermanager/collect', '/usermanager/collect', 'C', '0', '0', 'usermanager:collect:view', '#', 1, '2024-09-12 18:43:27', NULL, NULL, 0, '会员收藏菜单');
INSERT INTO `sys_menu` VALUES (103, 1, '会员足迹', 4, '/usermanager/footmark', '/usermanager/footmark', 'C', '0', '0', 'usermanager:footmark:view', '#', 1, '2024-09-12 18:44:31', NULL, NULL, 0, '会员足迹菜单');
INSERT INTO `sys_menu` VALUES (104, 2, '商品管理', 1, '/goodmanager/goods', '/goodmanager/goods', 'C', '0', '0', 'goodmanager:goods:view', '#', 1, '2024-09-12 18:45:57', NULL, NULL, 0, '商品管理菜单');
INSERT INTO `sys_menu` VALUES (105, 2, '分类管理', 2, '/goodmanager/category', '/goodmanager/category', 'C', '0', '0', 'goodmanager:category:view', '#', 1, '2024-09-12 18:47:11', NULL, NULL, 0, '分类管理菜单');
INSERT INTO `sys_menu` VALUES (106, 3, '用户管理', 1, '/systemmanager/user', '/systemmanager/user', 'C', '0', '0', 'systemmanager:user:view', '#', 1, '2024-09-12 18:49:15', NULL, NULL, 0, '用户管理菜单');
INSERT INTO `sys_menu` VALUES (107, 3, '通知管理', 4, '/systemmanager/notice', '/systemmanager/notice', 'C', '0', '0', 'systemmanager:notice:view', '#', 1, '2024-09-12 18:50:20', NULL, NULL, 0, '通知管理菜单');
INSERT INTO `sys_menu` VALUES (108, 3, '菜单管理', 3, '/systemmanager/menu', '/systemmanager/menu', 'C', '0', '0', 'systemmanager:menu:view', '#', 1, '2024-10-20 18:11:33', NULL, NULL, 0, '菜单管理菜单');
INSERT INTO `sys_menu` VALUES (109, 3, '角色管理', 2, '/systemmanager/role', '/systemmanager/role', 'C', '0', '0', 'systemmanager:role:view', '#', 1, '2024-10-21 16:59:58', NULL, NULL, 0, '角色管理菜单');
INSERT INTO `sys_menu` VALUES (110, 1, '会员统计', 1, '/usermanager/vipusercount', '/usermanager/vipusercount', 'C', '0', '0', 'usermanager:vipusercount:view', '#', 1, '2024-10-22 23:02:04', NULL, NULL, 0, '会员统计菜单');
INSERT INTO `sys_menu` VALUES (111, 6, '订单统计', 1, '/ordermanager/ordercount', '/ordermanager/ordercount', 'C', '0', '0', 'ordermanager:ordercount:view', '#', 1, '2024-10-22 23:05:35', NULL, NULL, 0, '订单统计菜单');
INSERT INTO `sys_menu` VALUES (112, 6, '订单', 2, '/ordermanager/order', '/ordermanager/order', 'C', '0', '0', 'ordermanager:order:view', '#', 1, '2024-10-22 23:08:01', NULL, NULL, 0, '订单菜单');
INSERT INTO `sys_menu` VALUES (114, 6, '订单配置', 4, '/ordermanager/orderset', '/ordermanager/orderset', 'C', '0', '0', 'ordermanager:orderset:view', '#', 1, '2024-10-22 23:09:57', NULL, NULL, 1, '订单配置菜单');
INSERT INTO `sys_menu` VALUES (115, 3, '日志管理', 5, '/systemmanager/log', '/systemmanager/log', 'C', '0', '0', 'systemmanager:log:view', '#', 1, '2024-10-22 23:44:59', NULL, NULL, 0, '日志管理菜单');
INSERT INTO `sys_menu` VALUES (116, 1, '校园楼管理', 3, '/usermanager/floor', '/usermanager/floor', 'C', '0', '0', 'usermanager:floor:view', '#', 1, '2024-10-23 20:55:43', NULL, NULL, 0, '校园楼管理菜单');
INSERT INTO `sys_menu` VALUES (2000, 100, '会员查询', 1, '#', '#', 'F', '0', '0', 'usermanager:vipuser:list', '#', 1, '2024-09-12 18:55:23', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2001, 100, '会员新增', 2, '#', '#', 'F', '0', '0', 'usermanager:vipuser:add', '#', 1, '2024-09-12 18:56:09', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2002, 0, '666', 6, NULL, '/666', 'M', '0', '0', NULL, '', 1, '2024-11-06 13:44:15', 1, '2024-11-06 14:11:52', 1, NULL);
INSERT INTO `sys_menu` VALUES (2003, 2002, '555', 1, '/666/555', '/666/555', 'C', '0', '0', '666:555:view', '', 1, '2024-11-06 14:01:34', 1, '2024-11-06 14:13:32', 1, NULL);
INSERT INTO `sys_menu` VALUES (2004, 2002, '444', 1, '/666/444', '/666/444', 'C', '0', '0', '666:444:view', '', 1, '2024-11-06 14:02:17', NULL, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (2005, 2003, '添加555', 1, NULL, NULL, 'F', '0', '0', '666:555:add', '', 1, '2024-11-06 14:02:45', 1, '2024-11-06 14:13:40', 1, NULL);
INSERT INTO `sys_menu` VALUES (2006, 2002, '444', 1, '/666/444', '/666/444', 'C', '0', '0', '666:444:view', '', 1, '2024-11-06 14:50:22', NULL, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (2007, 2006, '432', 1, NULL, NULL, 'F', '0', '0', '666:444:432', '', 1, '2024-11-06 14:50:44', NULL, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (2008, 0, '666', 1, NULL, '/666', 'M', '0', '0', NULL, '', 1, '2024-11-06 19:51:53', NULL, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (2009, 0, '666', 1, NULL, '/666', 'M', '0', '0', NULL, '', 1, '2024-11-06 19:53:48', NULL, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (2010, 2009, '555', 1, '/666/555', '/666/555', 'C', '0', '0', '666:555:view', '', 1, '2024-11-06 20:01:52', NULL, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (2011, 2010, '444', 1, NULL, NULL, 'F', '0', '0', '666:555:444', '', 1, '2025-02-07 23:57:06', NULL, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (2012, 2009, '444', 2, '/666/444', '/666/444', 'C', '0', '0', '666:444:view', '', 1, '2025-02-08 13:32:03', NULL, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (2013, 2009, '555', 1, '/666/555', '/666/555', 'C', '0', '0', '666:555:view', '', 1, '2025-02-08 16:48:25', NULL, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (2014, 2009, '444', 1, '/666/444', '/666/444', 'C', '0', '0', '666:444:view', '', 1, '2025-02-08 16:51:45', NULL, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (2015, 2009, '555', 1, '/666/555', '/666/555', 'C', '0', '0', '666:555:view', '', 1, '2025-02-09 16:20:21', 1, '2025-02-09 16:48:30', 1, NULL);
INSERT INTO `sys_menu` VALUES (2016, 2009, '555', 1, '/666/555', '/666/555', 'C', '0', '0', '666:555:view', '', 1, '2025-02-09 16:49:41', NULL, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (2017, 2009, '555', 1, '/666/555', '/666/555', 'C', '0', '0', '666:555:view', '', 1, '2025-02-09 16:52:12', NULL, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (2018, 2009, '555', 1, '/666/555', '/666/555', 'C', '0', '0', '666:555:view', '', 1, '2025-02-09 17:11:13', NULL, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (2019, 100, '会员修改', 3, NULL, NULL, 'F', '0', '0', 'usermanager:vipuser:edit', '', 1, '2025-02-09 20:26:53', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2020, 100, '会员删除', 4, NULL, NULL, 'F', '0', '0', 'usermanager:vipuser:delete', '', 1, '2025-02-09 20:27:25', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2021, 100, '会员批量删除', 5, NULL, NULL, 'F', '0', '0', 'usermanager:vipuser:deletes', '', 1, '2025-02-09 20:28:38', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2022, 101, '新增收获地址', 2, NULL, NULL, 'F', '0', '0', 'usermanager:address:add', '', 1, '2025-02-11 17:10:18', 1, '2025-02-11 17:12:59', 1, NULL);
INSERT INTO `sys_menu` VALUES (2023, 101, '查询收获地址', 1, NULL, NULL, 'F', '0', '0', 'usermanager:address:list', '', 1, '2025-02-11 17:11:29', 1, '2025-02-11 17:12:56', 0, NULL);
INSERT INTO `sys_menu` VALUES (2024, 101, '修改收获地址', 3, NULL, NULL, 'F', '0', '0', 'usermanager:address:edit', '', 1, '2025-02-11 17:11:45', 1, '2025-02-11 17:13:06', 0, NULL);
INSERT INTO `sys_menu` VALUES (2025, 101, '删除收货地址', 4, NULL, NULL, 'F', '0', '0', 'usermanager:address:delete', '', 1, '2025-02-11 17:12:26', 1, '2025-02-11 17:13:11', 0, NULL);
INSERT INTO `sys_menu` VALUES (2026, 101, '批量删除收货地址', 5, NULL, NULL, 'F', '0', '0', 'usermanager:address:deletes', '', 1, '2025-02-11 17:13:29', 1, '2025-02-11 17:13:33', 0, NULL);
INSERT INTO `sys_menu` VALUES (2027, 102, '查询会员收藏', 1, NULL, NULL, 'F', '0', '0', 'usermanager:collect:list', '', 1, '2025-02-11 17:14:33', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2028, 102, '新增会员收藏', 2, NULL, NULL, 'F', '0', '0', 'usermanager:collect:add', '', 1, '2025-02-11 17:14:50', 1, '2025-02-11 17:14:55', 1, NULL);
INSERT INTO `sys_menu` VALUES (2029, 102, '修改会员收藏', 3, NULL, NULL, 'F', '0', '0', 'usermanager:collect:edit', '', 1, '2025-02-11 17:15:27', NULL, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (2030, 102, '删除会员收藏', 4, NULL, NULL, 'F', '0', '0', 'usermanager:collect:delete', '', 1, '2025-02-11 17:15:50', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2031, 102, '批量删除会员收藏', 5, NULL, NULL, 'F', '0', '0', 'usermanager:collect:deletes', '', 1, '2025-02-11 17:16:05', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2032, 103, '查询会员足迹', 1, NULL, NULL, 'F', '0', '0', 'usermanager:footmark:list', '', 1, '2025-02-11 17:16:29', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2033, 103, '新增会员足迹', 2, NULL, NULL, 'F', '0', '0', 'usermanager:footmark:add', '', 1, '2025-02-11 17:16:44', NULL, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (2034, 103, '修改会员足迹', 3, NULL, NULL, 'F', '0', '0', 'usermanager:footmark:edit', '', 1, '2025-02-11 17:17:11', NULL, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (2035, 103, '删除会员足迹', 4, NULL, NULL, 'F', '0', '0', 'usermanager:footmark:delete', '', 1, '2025-02-11 17:17:27', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2036, 103, '批量删除会员足迹', 5, NULL, NULL, 'F', '0', '0', 'usermanager:footmark:deletes', '', 1, '2025-02-11 17:17:45', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2037, 116, '查询校园楼', 1, NULL, NULL, 'F', '0', '0', 'usermanager:floor:list', '', 1, '2025-02-11 17:19:25', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2038, 116, '新增校园楼', 2, NULL, NULL, 'F', '0', '0', 'usermanager:floor:add', '', 1, '2025-02-11 17:19:42', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2039, 116, '修改校园楼', 3, NULL, NULL, 'F', '0', '0', 'usermanager:floor:edit', '', 1, '2025-02-11 17:20:03', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2040, 116, '删除校园楼', 4, NULL, NULL, 'F', '0', '0', 'usermanager:floor:delete', '', 1, '2025-02-11 17:20:20', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2041, 116, '批量删除校园楼', 5, NULL, NULL, 'F', '0', '0', 'usermanager:floor:deletes', '', 1, '2025-02-11 17:20:37', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2042, 104, '查询商品列表', 1, NULL, NULL, 'F', '0', '0', 'goodmanager:goods:list', '', 1, '2025-02-11 17:23:30', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2043, 104, '新增商品', 2, NULL, NULL, 'F', '0', '0', 'goodmanager:goods:add', '', 1, '2025-02-11 17:23:46', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2044, 104, '修改商品', 3, NULL, NULL, 'F', '0', '0', 'goodmanager:goods:edit', '', 1, '2025-02-11 17:23:58', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2045, 104, '删除商品', 4, NULL, NULL, 'F', '0', '0', 'goodmanager:goods:delete', '', 1, '2025-02-11 17:24:13', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2046, 104, '批量删除商品', 5, NULL, NULL, 'F', '0', '0', 'goodmanager:goods:deletes', '', 1, '2025-02-11 17:24:29', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2047, 105, '查询分类', 1, NULL, NULL, 'F', '0', '0', 'goodmanager:category:list', '', 1, '2025-02-11 17:25:41', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2048, 105, '新增分类', 2, NULL, NULL, 'F', '0', '0', 'goodmanager:category:add', '', 1, '2025-02-11 17:25:55', 1, '2025-02-11 17:26:52', 0, NULL);
INSERT INTO `sys_menu` VALUES (2049, 105, '修改分类', 3, NULL, NULL, 'F', '0', '0', 'goodmanager:category:edit', '', 1, '2025-02-11 17:26:05', 1, '2025-02-11 17:29:02', 0, NULL);
INSERT INTO `sys_menu` VALUES (2050, 105, '删除分类', 4, NULL, NULL, 'F', '0', '0', 'goodmanager:category:delete', '', 1, '2025-02-11 17:26:31', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2051, 106, '查询用户', 1, NULL, NULL, 'F', '0', '0', 'systemmanager:user:list', '', 1, '2025-02-11 17:28:10', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2052, 106, '新增用户', 2, NULL, NULL, 'F', '0', '0', 'systemmanager:user:add', '', 1, '2025-02-11 17:28:21', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2053, 106, '修改用户', 3, NULL, NULL, 'F', '0', '0', 'systemmanager:user:edit', '', 1, '2025-02-11 17:28:37', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2054, 106, '删除用户', 4, NULL, NULL, 'F', '0', '0', 'systemmanager:user:delete', '', 1, '2025-02-11 17:29:23', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2055, 106, '批量删除用户', 5, NULL, NULL, 'F', '0', '0', 'systemmanager:user:deletes', '', 1, '2025-02-11 17:29:35', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2056, 107, '查询通知', 1, NULL, NULL, 'F', '0', '0', 'systemmanager:notice:list', '', 1, '2025-02-11 17:30:01', 1, '2025-02-11 17:30:23', 0, NULL);
INSERT INTO `sys_menu` VALUES (2057, 107, '新增通知', 2, NULL, NULL, 'F', '0', '0', 'systemmanager:notice:add', '', 1, '2025-02-11 17:30:31', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2058, 107, '修改通知', 3, NULL, NULL, 'F', '0', '0', 'systemmanager:notice:edit', '', 1, '2025-02-11 17:30:39', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2059, 107, '删除通知', 4, NULL, NULL, 'F', '0', '0', 'systemmanager:notice:delete', '', 1, '2025-02-11 17:30:52', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2060, 107, '批量删除通知', 5, NULL, NULL, 'F', '0', '0', 'systemmanager:notice:deletes', '', 1, '2025-02-11 17:31:06', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2061, 108, '查询菜单', 1, NULL, NULL, 'F', '0', '0', 'systemmanager:menu:list', '', 1, '2025-02-11 17:32:31', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2062, 108, '新增菜单', 2, NULL, NULL, 'F', '0', '0', 'systemmanager:menu:add', '', 1, '2025-02-11 17:32:43', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2063, 108, '修改菜单', 3, NULL, NULL, 'F', '0', '0', 'systemmanager:menu:edit', '', 1, '2025-02-11 17:32:51', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2064, 108, '删除菜单', 4, NULL, NULL, 'F', '0', '0', 'systemmanager:menu:delete', '', 1, '2025-02-11 17:33:01', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2065, 109, '查询角色', 1, NULL, NULL, 'F', '0', '0', 'systemmanager:role:list', '', 1, '2025-02-11 17:33:36', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2066, 109, '新增角色', 2, NULL, NULL, 'F', '0', '0', 'systemmanager:role:add', '', 1, '2025-02-11 17:33:46', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2067, 109, '修改角色', 3, NULL, NULL, 'F', '0', '0', 'systemmanager:role:edit', '', 1, '2025-02-11 17:33:53', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2068, 109, '删除角色', 4, NULL, NULL, 'F', '0', '0', 'systemmanager:role:delete', '', 1, '2025-02-11 17:34:03', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2069, 109, '批量删除角色', 5, NULL, NULL, 'F', '0', '0', 'systemmanager:role:deletes', '', 1, '2025-02-11 17:34:13', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2070, 115, '查询日志', 1, NULL, NULL, 'F', '0', '0', 'systemmanager:log:list', '', 1, '2025-02-11 17:34:35', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2071, 115, '批量删除日志', 2, NULL, NULL, 'F', '0', '0', 'systemmanager:log:deletes', '', 1, '2025-02-11 17:34:47', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2072, 2, '555', 1, '/goodmanager/555', '/goodmanager/555', 'C', '0', '0', 'goodmanager:555', '', 1, '2025-02-17 15:34:20', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2073, 1, '校区管理', 5, '/usermanager/campus', '/usermanager/campus', 'C', '0', '0', 'usermanager:campus:view', '', 1, '2025-04-09 20:33:17', 1, '2025-04-09 20:42:54', 0, NULL);
INSERT INTO `sys_menu` VALUES (2074, 2073, '查询校区', 1, NULL, NULL, 'F', '0', '0', 'usermanager:campus:search', '', 1, '2025-04-09 20:34:03', 1, '2025-04-09 20:34:41', 0, NULL);
INSERT INTO `sys_menu` VALUES (2075, 2073, '新增校区', 2, NULL, NULL, 'F', '0', '0', 'usermanager:campus:add', '', 1, '2025-04-09 20:34:35', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2076, 2073, '修改校区', 3, NULL, NULL, 'F', '0', '0', 'usermanager:campus:edit', '', 1, '2025-04-09 20:35:16', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2077, 2073, '删除校区', 4, NULL, NULL, 'F', '0', '0', 'usermanager:campus:delete', '', 1, '2025-04-09 20:35:39', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2078, 2073, '批量删除校区', 5, NULL, NULL, 'F', '0', '0', 'usermanager:campus:deletes', '', 1, '2025-04-09 20:36:02', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2079, 0, '评论管理', 5, NULL, '/commentManager', 'M', '0', '0', NULL, '', 1, '2026-02-11 16:38:42', 1, '2026-02-11 16:41:07', 1, NULL);
INSERT INTO `sys_menu` VALUES (2080, 1, '评论管理', 6, '/usermanager/comment', '/usermanager/comment', 'C', '0', '0', 'usermanager:comment:view', '', 1, '2026-02-11 16:43:23', 1, '2026-02-11 16:49:31', 0, NULL);
INSERT INTO `sys_menu` VALUES (2081, 2080, '查询评论', 1, NULL, NULL, 'F', '0', '0', 'usermanager:comment:list', '#', 1, '2026-02-11 17:02:31', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2082, 2080, '删除评论', 1, NULL, NULL, 'F', '0', '0', 'usermanager:comment:remove', '#', 1, '2026-02-11 17:02:31', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2083, 2080, '屏蔽回复', 1, NULL, NULL, 'F', '0', '0', 'usermanager:comment:removeReply', '#', 1, '2026-02-11 17:02:31', NULL, NULL, 0, NULL);

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `notice_id` int NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公告标题',
  `notice_content` longblob NOT NULL COMMENT '公告内容',
  `notice_status` int NOT NULL DEFAULT 0 COMMENT '公告状态（0正常 1关闭）',
  `create_by` int NOT NULL COMMENT '创建者',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_by` int NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `del_flag` int NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 52 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '通知公告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice` VALUES (34, '1', 0x3C703E313C2F703E, 0, 1, '2025-02-07 20:39:54', 1, '2025-02-10 22:00:49', NULL, 0);
INSERT INTO `sys_notice` VALUES (35, '2', 0x3C703E323C2F703E, 0, 1, '2025-02-09 21:08:11', NULL, NULL, NULL, 0);
INSERT INTO `sys_notice` VALUES (36, '3', 0x3C703E333C2F703E, 0, 1, '2025-02-10 21:58:54', NULL, NULL, NULL, 0);
INSERT INTO `sys_notice` VALUES (37, '4', 0x3C703E343C2F703E, 0, 1, '2025-02-10 21:59:52', NULL, NULL, NULL, 0);
INSERT INTO `sys_notice` VALUES (38, '5', 0x3C703E353C2F703E, 0, 1, '2025-02-10 22:04:00', NULL, NULL, NULL, 0);
INSERT INTO `sys_notice` VALUES (39, '6', 0x3C703E363C2F703E, 0, 1, '2025-02-10 22:04:52', NULL, NULL, NULL, 0);
INSERT INTO `sys_notice` VALUES (40, '7', 0x3C703E373C2F703E, 0, 1, '2025-02-10 22:05:08', NULL, NULL, NULL, 0);
INSERT INTO `sys_notice` VALUES (41, '8', 0x3C703E383C2F703E, 0, 1, '2025-02-10 22:35:51', NULL, NULL, NULL, 0);
INSERT INTO `sys_notice` VALUES (42, '9', 0x3C703E393C2F703E, 0, 1, '2025-02-10 22:36:33', NULL, NULL, NULL, 0);
INSERT INTO `sys_notice` VALUES (43, '10', 0x3C703E31303C2F703E, 0, 1, '2025-02-10 22:56:00', NULL, NULL, NULL, 0);
INSERT INTO `sys_notice` VALUES (44, '11', 0x3C703E31313C2F703E, 0, 1, '2025-02-10 22:56:30', NULL, NULL, NULL, 0);
INSERT INTO `sys_notice` VALUES (45, '12', 0x3C703E31323C2F703E, 0, 1, '2025-02-10 22:58:19', NULL, NULL, NULL, 0);
INSERT INTO `sys_notice` VALUES (46, '13', 0x3C703E31333C2F703E, 0, 1, '2025-02-10 22:59:30', NULL, NULL, NULL, 0);
INSERT INTO `sys_notice` VALUES (47, '13', 0x3C703E31333C2F703E, 0, 1, '2025-02-10 23:01:19', NULL, NULL, NULL, 0);
INSERT INTO `sys_notice` VALUES (48, '1234', 0x3C703E313233343C2F703E, 0, 1, '2025-02-17 15:41:56', NULL, NULL, NULL, 0);
INSERT INTO `sys_notice` VALUES (49, '234', 0x3C703E3233343C2F703E, 0, 1, '2025-04-01 19:13:11', NULL, NULL, NULL, 0);
INSERT INTO `sys_notice` VALUES (50, '123', 0x3C703E3132333C2F703E, 0, 1, '2025-04-01 20:02:12', NULL, NULL, NULL, 0);
INSERT INTO `sys_notice` VALUES (51, '123', 0x3C703E3132333C2F703E, 0, 1, '2025-04-07 21:24:28', NULL, NULL, NULL, 0);
INSERT INTO `sys_notice` VALUES (52, '12', 0x3C703E31323C2F703E, 0, 1, '2025-04-19 17:44:37', NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint NOT NULL AUTO_INCREMENT,
  `role_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色权限字符串',
  `role_status` int NOT NULL DEFAULT 0 COMMENT '角色状态（0正常 1停用）',
  `del_flag` int NOT NULL DEFAULT 0 COMMENT 'del_flag',
  `create_by` bigint NOT NULL,
  `create_time` datetime(0) NOT NULL,
  `update_by` bigint NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin', 0, 0, 1, '2024-09-12 20:55:39', 1, '2025-02-09 15:16:21', '超级管理员');
INSERT INTO `sys_role` VALUES (2, '普通人员', 'user', 0, 0, 1, '2024-10-26 15:43:36', NULL, NULL, '普通人员');
INSERT INTO `sys_role` VALUES (3, '666', '664', 0, 1, 1, '2024-11-02 15:54:57', 1, '2024-11-02 16:18:01', NULL);
INSERT INTO `sys_role` VALUES (4, '555', '666', 1, 1, 1, '2024-11-02 17:28:53', 1, '2024-11-02 18:32:42', NULL);
INSERT INTO `sys_role` VALUES (5, '777', '777', 0, 1, 1, '2024-11-02 18:15:03', NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES (6, '111', '111', 0, 0, 1, '2024-11-02 18:33:39', NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES (7, '222', '222', 0, 0, 1, '2024-11-02 18:33:43', 1, '2024-11-02 18:51:48', NULL);
INSERT INTO `sys_role` VALUES (8, '333', '333', 0, 1, 1, '2024-11-02 18:33:46', NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES (9, '666', '666', 0, 1, 1, '2024-11-02 18:33:49', NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES (10, '777', '777', 0, 1, 1, '2024-11-02 18:33:53', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `menu_id` bigint NOT NULL DEFAULT 0 COMMENT '菜单id',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 1);
INSERT INTO `sys_role_menu` VALUES (1, 2);
INSERT INTO `sys_role_menu` VALUES (1, 3);
INSERT INTO `sys_role_menu` VALUES (1, 4);
INSERT INTO `sys_role_menu` VALUES (1, 5);
INSERT INTO `sys_role_menu` VALUES (1, 6);
INSERT INTO `sys_role_menu` VALUES (1, 100);
INSERT INTO `sys_role_menu` VALUES (1, 101);
INSERT INTO `sys_role_menu` VALUES (1, 102);
INSERT INTO `sys_role_menu` VALUES (1, 103);
INSERT INTO `sys_role_menu` VALUES (1, 104);
INSERT INTO `sys_role_menu` VALUES (1, 105);
INSERT INTO `sys_role_menu` VALUES (1, 106);
INSERT INTO `sys_role_menu` VALUES (1, 107);
INSERT INTO `sys_role_menu` VALUES (1, 108);
INSERT INTO `sys_role_menu` VALUES (1, 109);
INSERT INTO `sys_role_menu` VALUES (1, 110);
INSERT INTO `sys_role_menu` VALUES (1, 111);
INSERT INTO `sys_role_menu` VALUES (1, 112);
INSERT INTO `sys_role_menu` VALUES (1, 115);
INSERT INTO `sys_role_menu` VALUES (1, 116);
INSERT INTO `sys_role_menu` VALUES (1, 2000);
INSERT INTO `sys_role_menu` VALUES (1, 2001);
INSERT INTO `sys_role_menu` VALUES (1, 2019);
INSERT INTO `sys_role_menu` VALUES (1, 2020);
INSERT INTO `sys_role_menu` VALUES (1, 2021);
INSERT INTO `sys_role_menu` VALUES (1, 2023);
INSERT INTO `sys_role_menu` VALUES (1, 2024);
INSERT INTO `sys_role_menu` VALUES (1, 2025);
INSERT INTO `sys_role_menu` VALUES (1, 2026);
INSERT INTO `sys_role_menu` VALUES (1, 2027);
INSERT INTO `sys_role_menu` VALUES (1, 2030);
INSERT INTO `sys_role_menu` VALUES (1, 2031);
INSERT INTO `sys_role_menu` VALUES (1, 2032);
INSERT INTO `sys_role_menu` VALUES (1, 2035);
INSERT INTO `sys_role_menu` VALUES (1, 2036);
INSERT INTO `sys_role_menu` VALUES (1, 2037);
INSERT INTO `sys_role_menu` VALUES (1, 2038);
INSERT INTO `sys_role_menu` VALUES (1, 2039);
INSERT INTO `sys_role_menu` VALUES (1, 2040);
INSERT INTO `sys_role_menu` VALUES (1, 2041);
INSERT INTO `sys_role_menu` VALUES (1, 2042);
INSERT INTO `sys_role_menu` VALUES (1, 2043);
INSERT INTO `sys_role_menu` VALUES (1, 2044);
INSERT INTO `sys_role_menu` VALUES (1, 2045);
INSERT INTO `sys_role_menu` VALUES (1, 2046);
INSERT INTO `sys_role_menu` VALUES (1, 2047);
INSERT INTO `sys_role_menu` VALUES (1, 2048);
INSERT INTO `sys_role_menu` VALUES (1, 2049);
INSERT INTO `sys_role_menu` VALUES (1, 2050);
INSERT INTO `sys_role_menu` VALUES (1, 2051);
INSERT INTO `sys_role_menu` VALUES (1, 2052);
INSERT INTO `sys_role_menu` VALUES (1, 2053);
INSERT INTO `sys_role_menu` VALUES (1, 2054);
INSERT INTO `sys_role_menu` VALUES (1, 2055);
INSERT INTO `sys_role_menu` VALUES (1, 2056);
INSERT INTO `sys_role_menu` VALUES (1, 2057);
INSERT INTO `sys_role_menu` VALUES (1, 2058);
INSERT INTO `sys_role_menu` VALUES (1, 2059);
INSERT INTO `sys_role_menu` VALUES (1, 2060);
INSERT INTO `sys_role_menu` VALUES (1, 2061);
INSERT INTO `sys_role_menu` VALUES (1, 2062);
INSERT INTO `sys_role_menu` VALUES (1, 2063);
INSERT INTO `sys_role_menu` VALUES (1, 2064);
INSERT INTO `sys_role_menu` VALUES (1, 2065);
INSERT INTO `sys_role_menu` VALUES (1, 2066);
INSERT INTO `sys_role_menu` VALUES (1, 2067);
INSERT INTO `sys_role_menu` VALUES (1, 2068);
INSERT INTO `sys_role_menu` VALUES (1, 2069);
INSERT INTO `sys_role_menu` VALUES (1, 2070);
INSERT INTO `sys_role_menu` VALUES (1, 2071);
INSERT INTO `sys_role_menu` VALUES (1, 2072);
INSERT INTO `sys_role_menu` VALUES (1, 2073);
INSERT INTO `sys_role_menu` VALUES (1, 2074);
INSERT INTO `sys_role_menu` VALUES (1, 2075);
INSERT INTO `sys_role_menu` VALUES (1, 2076);
INSERT INTO `sys_role_menu` VALUES (1, 2077);
INSERT INTO `sys_role_menu` VALUES (1, 2078);
INSERT INTO `sys_role_menu` VALUES (1, 2080);
INSERT INTO `sys_role_menu` VALUES (1, 2081);
INSERT INTO `sys_role_menu` VALUES (1, 2082);
INSERT INTO `sys_role_menu` VALUES (1, 2083);
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 2);
INSERT INTO `sys_role_menu` VALUES (2, 4);
INSERT INTO `sys_role_menu` VALUES (2, 5);
INSERT INTO `sys_role_menu` VALUES (2, 101);
INSERT INTO `sys_role_menu` VALUES (2, 102);
INSERT INTO `sys_role_menu` VALUES (2, 103);
INSERT INTO `sys_role_menu` VALUES (2, 104);
INSERT INTO `sys_role_menu` VALUES (2, 105);
INSERT INTO `sys_role_menu` VALUES (2, 110);
INSERT INTO `sys_role_menu` VALUES (2, 116);
INSERT INTO `sys_role_menu` VALUES (2, 2023);
INSERT INTO `sys_role_menu` VALUES (2, 2024);
INSERT INTO `sys_role_menu` VALUES (2, 2025);
INSERT INTO `sys_role_menu` VALUES (2, 2026);
INSERT INTO `sys_role_menu` VALUES (2, 2027);
INSERT INTO `sys_role_menu` VALUES (2, 2030);
INSERT INTO `sys_role_menu` VALUES (2, 2031);
INSERT INTO `sys_role_menu` VALUES (2, 2032);
INSERT INTO `sys_role_menu` VALUES (2, 2035);
INSERT INTO `sys_role_menu` VALUES (2, 2036);
INSERT INTO `sys_role_menu` VALUES (2, 2037);
INSERT INTO `sys_role_menu` VALUES (2, 2038);
INSERT INTO `sys_role_menu` VALUES (2, 2039);
INSERT INTO `sys_role_menu` VALUES (2, 2040);
INSERT INTO `sys_role_menu` VALUES (2, 2041);
INSERT INTO `sys_role_menu` VALUES (2, 2042);
INSERT INTO `sys_role_menu` VALUES (2, 2043);
INSERT INTO `sys_role_menu` VALUES (2, 2044);
INSERT INTO `sys_role_menu` VALUES (2, 2045);
INSERT INTO `sys_role_menu` VALUES (2, 2046);
INSERT INTO `sys_role_menu` VALUES (2, 2047);
INSERT INTO `sys_role_menu` VALUES (2, 2048);
INSERT INTO `sys_role_menu` VALUES (2, 2049);
INSERT INTO `sys_role_menu` VALUES (2, 2050);

SET FOREIGN_KEY_CHECKS = 1;
