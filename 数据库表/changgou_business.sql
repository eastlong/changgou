/*
Navicat MySQL Data Transfer

Source Server         : changgou
Source Server Version : 50726
Source Host           : 192.168.200.128:3306
Source Database       : changgou_business

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-10-08 17:12:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_activity
-- ----------------------------
DROP TABLE IF EXISTS `tb_activity`;
CREATE TABLE `tb_activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(200) DEFAULT NULL COMMENT '活动标题',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `status` char(1) DEFAULT NULL COMMENT '状态',
  `content` text COMMENT '活动内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_activity
-- ----------------------------

-- ----------------------------
-- Table structure for tb_ad
-- ----------------------------
DROP TABLE IF EXISTS `tb_ad`;
CREATE TABLE `tb_ad` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(50) DEFAULT NULL COMMENT '广告名称',
  `position` varchar(50) DEFAULT NULL COMMENT '广告位置',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '到期时间',
  `status` char(1) DEFAULT NULL COMMENT '状态',
  `image` varchar(100) DEFAULT NULL COMMENT '图片地址',
  `url` varchar(100) DEFAULT NULL COMMENT 'URL',
  `remarks` varchar(1000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_ad
-- ----------------------------
INSERT INTO `tb_ad` VALUES ('1', '777777q6', 'web_index_lb', '2019-06-28 09:01:43', '2019-12-06 07:29:59', '1', 'img/banner1.jpg', '://www.badu.com', null);
INSERT INTO `tb_ad` VALUES ('2', '444444asd', 'web_index_lb', '2019-06-28 09:01:43', '2019-12-06 07:29:59', '1', 'img/banner2.jpg', '://www.badu.com', null);
INSERT INTO `tb_ad` VALUES ('3', 'sfsfsfsqqq', 'web_index_lb', '2019-02-06 15:29:59', '2019-12-15 15:30:02', '1', 'img/banner3.jpg', 'img/banner3.jpg', null);
INSERT INTO `tb_ad` VALUES ('6', 'wwwww', 'web_index_lb', '2019-06-28 09:01:43', '2019-12-06 07:29:59', '1', 'img/banner4.jpg', '://www.badu.com', null);
INSERT INTO `tb_ad` VALUES ('7', 'qqqwwweee', 'web_index_lb', '2019-06-28 09:01:43', '2019-12-06 07:29:59', '1', 'img/ad1.jpg', '://www.badu.com', null);
