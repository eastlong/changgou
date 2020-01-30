/*
Navicat MySQL Data Transfer

Source Server         : changgou
Source Server Version : 50726
Source Host           : 192.168.200.128:3306
Source Database       : changgou_order

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-10-08 17:12:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_category_report
-- ----------------------------
DROP TABLE IF EXISTS `tb_category_report`;
CREATE TABLE `tb_category_report` (
  `category_id1` int(11) NOT NULL COMMENT '1级分类',
  `category_id2` int(11) NOT NULL COMMENT '2级分类',
  `category_id3` int(11) NOT NULL COMMENT '3级分类',
  `count_date` date NOT NULL COMMENT '统计日期',
  `num` int(11) DEFAULT NULL COMMENT '销售数量',
  `money` int(11) DEFAULT NULL COMMENT '销售额',
  PRIMARY KEY (`category_id1`,`category_id2`,`category_id3`,`count_date`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=FIXED;

-- ----------------------------
-- Records of tb_category_report
-- ----------------------------
INSERT INTO `tb_category_report` VALUES ('1', '4', '5', '2019-01-26', '1', '300');
INSERT INTO `tb_category_report` VALUES ('74', '7', '8', '2019-01-26', '5', '900');

-- ----------------------------
-- Table structure for tb_order
-- ----------------------------
DROP TABLE IF EXISTS `tb_order`;
CREATE TABLE `tb_order` (
  `id` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '订单id',
  `total_num` int(11) DEFAULT NULL COMMENT '数量合计',
  `total_money` int(11) DEFAULT NULL COMMENT '金额合计',
  `pre_money` int(11) DEFAULT NULL COMMENT '优惠金额',
  `post_fee` int(11) DEFAULT NULL COMMENT '邮费',
  `pay_money` int(11) DEFAULT NULL COMMENT '实付金额',
  `pay_type` varchar(1) COLLATE utf8_bin DEFAULT NULL COMMENT '支付类型，1、在线支付、0 货到付款',
  `create_time` datetime DEFAULT NULL COMMENT '订单创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '订单更新时间',
  `pay_time` datetime DEFAULT NULL COMMENT '付款时间',
  `consign_time` datetime DEFAULT NULL COMMENT '发货时间',
  `end_time` datetime DEFAULT NULL COMMENT '交易完成时间',
  `close_time` datetime DEFAULT NULL COMMENT '交易关闭时间',
  `shipping_name` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '物流名称',
  `shipping_code` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '物流单号',
  `username` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '用户名称',
  `buyer_message` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '买家留言',
  `buyer_rate` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '是否评价',
  `receiver_contact` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '收货人',
  `receiver_mobile` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '收货人手机',
  `receiver_address` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '收货人地址',
  `source_type` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '订单来源：1:web，2：app，3：微信公众号，4：微信小程序  5 H5手机页面',
  `transaction_id` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '交易流水号',
  `order_status` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '订单状态 ',
  `pay_status` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '支付状态 0:未支付 1:已支付',
  `consign_status` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '发货状态 0:未发货 1:已发货 2:已送达',
  `is_delete` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `status` (`order_status`) USING BTREE,
  KEY `payment_type` (`pay_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_order
-- ----------------------------
INSERT INTO `tb_order` VALUES ('1181493940860358656', '5', '5', null, null, '5', '1', '2019-10-08 08:58:01', '2019-10-08 08:58:01', null, null, null, null, null, null, 'heima', null, '0', null, null, null, '1', null, '0', '0', '0', null);

-- ----------------------------
-- Table structure for tb_order_config
-- ----------------------------
DROP TABLE IF EXISTS `tb_order_config`;
CREATE TABLE `tb_order_config` (
  `id` int(11) NOT NULL COMMENT 'ID',
  `order_timeout` int(11) DEFAULT NULL COMMENT '正常订单超时时间（分）',
  `seckill_timeout` int(11) DEFAULT NULL COMMENT '秒杀订单超时时间（分）',
  `take_timeout` int(11) DEFAULT NULL COMMENT '自动收货（天）',
  `service_timeout` int(11) DEFAULT NULL COMMENT '售后期限',
  `comment_timeout` int(11) DEFAULT NULL COMMENT '自动五星好评',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_order_config
-- ----------------------------
INSERT INTO `tb_order_config` VALUES ('1', '60', '10', '15', '7', '7');

-- ----------------------------
-- Table structure for tb_order_item
-- ----------------------------
DROP TABLE IF EXISTS `tb_order_item`;
CREATE TABLE `tb_order_item` (
  `id` varchar(200) COLLATE utf8_bin NOT NULL COMMENT 'ID',
  `category_id1` int(11) DEFAULT NULL COMMENT '1级分类',
  `category_id2` int(11) DEFAULT NULL COMMENT '2级分类',
  `category_id3` int(11) DEFAULT NULL COMMENT '3级分类',
  `spu_id` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT 'SPU_ID',
  `sku_id` varchar(200) COLLATE utf8_bin NOT NULL COMMENT 'SKU_ID',
  `order_id` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '订单ID',
  `name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '商品名称',
  `price` int(20) DEFAULT NULL COMMENT '单价',
  `num` int(10) DEFAULT NULL COMMENT '数量',
  `money` int(20) DEFAULT NULL COMMENT '总金额',
  `pay_money` int(11) DEFAULT NULL COMMENT '实付金额',
  `image` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '图片地址',
  `weight` int(11) DEFAULT NULL COMMENT '重量',
  `post_fee` int(11) DEFAULT NULL COMMENT '运费',
  `is_return` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '是否退货',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `item_id` (`sku_id`) USING BTREE,
  KEY `order_id` (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_order_item
-- ----------------------------
INSERT INTO `tb_order_item` VALUES ('1181493940990382080', '1099', '1117', '1124', '10000000616300', '100000006163', '1181493940860358656', '巴布豆(BOBDOG)柔薄悦动婴儿拉拉裤XXL码80片(15kg以上)', '1', '5', '5', '5', 'https://m.360buyimg.com/mobilecms/s720x720_jfs/t23998/350/2363990466/222391/a6e9581d/5b7cba5bN0c18fb4f.jpg!q70.jpg.webp', '50', null, '0');

-- ----------------------------
-- Table structure for tb_order_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_order_log`;
CREATE TABLE `tb_order_log` (
  `id` varchar(20) NOT NULL COMMENT 'ID',
  `operater` varchar(50) DEFAULT NULL COMMENT '操作员',
  `operate_time` datetime DEFAULT NULL COMMENT '操作时间',
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单ID',
  `order_status` char(1) DEFAULT NULL COMMENT '订单状态',
  `pay_status` char(1) DEFAULT NULL COMMENT '付款状态',
  `consign_status` char(1) DEFAULT NULL COMMENT '发货状态',
  `remarks` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_order_log
-- ----------------------------

-- ----------------------------
-- Table structure for tb_preferential
-- ----------------------------
DROP TABLE IF EXISTS `tb_preferential`;
CREATE TABLE `tb_preferential` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `buy_money` int(11) DEFAULT NULL COMMENT '消费金额',
  `pre_money` int(11) DEFAULT NULL COMMENT '优惠金额',
  `category_id` int(20) DEFAULT NULL COMMENT '品类ID',
  `start_time` date DEFAULT NULL COMMENT '活动开始日期',
  `end_time` date DEFAULT NULL COMMENT '活动截至日期',
  `state` varchar(1) DEFAULT NULL COMMENT '状态',
  `type` varchar(1) DEFAULT NULL COMMENT '类型1不翻倍 2翻倍',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_preferential
-- ----------------------------
INSERT INTO `tb_preferential` VALUES ('1', '10000', '3000', '757', '2019-07-01', '2020-01-18', '1', '1');
INSERT INTO `tb_preferential` VALUES ('2', '30000', '10000', '757', '2019-07-01', '2020-01-18', '1', '1');
INSERT INTO `tb_preferential` VALUES ('3', '60000', '30000', '757', '2019-07-01', '2020-01-18', '1', '1');
INSERT INTO `tb_preferential` VALUES ('4', '10000', '4000', '76', '2019-07-01', '2020-03-23', '1', '2');

-- ----------------------------
-- Table structure for tb_return_cause
-- ----------------------------
DROP TABLE IF EXISTS `tb_return_cause`;
CREATE TABLE `tb_return_cause` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cause` varchar(100) DEFAULT NULL COMMENT '原因',
  `seq` int(11) DEFAULT '1' COMMENT '排序',
  `status` char(1) DEFAULT NULL COMMENT '是否启用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_return_cause
-- ----------------------------

-- ----------------------------
-- Table structure for tb_return_order
-- ----------------------------
DROP TABLE IF EXISTS `tb_return_order`;
CREATE TABLE `tb_return_order` (
  `id` varchar(20) NOT NULL COMMENT '服务单号',
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单号',
  `apply_time` datetime DEFAULT NULL COMMENT '申请时间',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `user_account` varchar(11) DEFAULT NULL COMMENT '用户账号',
  `linkman` varchar(20) DEFAULT NULL COMMENT '联系人',
  `linkman_mobile` varchar(11) DEFAULT NULL COMMENT '联系人手机',
  `type` char(1) DEFAULT NULL COMMENT '类型',
  `return_money` int(11) DEFAULT NULL COMMENT '退款金额',
  `is_return_freight` char(1) DEFAULT NULL COMMENT '是否退运费',
  `status` char(1) DEFAULT NULL COMMENT '申请状态',
  `dispose_time` datetime DEFAULT NULL COMMENT '处理时间',
  `return_cause` int(11) DEFAULT NULL COMMENT '退货退款原因',
  `evidence` varchar(1000) DEFAULT NULL COMMENT '凭证图片',
  `description` varchar(1000) DEFAULT NULL COMMENT '问题描述',
  `remark` varchar(1000) DEFAULT NULL COMMENT '处理备注',
  `admin_id` int(11) DEFAULT NULL COMMENT '管理员id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_return_order
-- ----------------------------

-- ----------------------------
-- Table structure for tb_return_order_item
-- ----------------------------
DROP TABLE IF EXISTS `tb_return_order_item`;
CREATE TABLE `tb_return_order_item` (
  `id` varchar(20) COLLATE utf8_bin NOT NULL COMMENT 'ID',
  `category_id` int(20) DEFAULT NULL COMMENT '分类ID',
  `spu_id` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT 'SPU_ID',
  `sku_id` varchar(20) COLLATE utf8_bin NOT NULL COMMENT 'SKU_ID',
  `order_id` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '订单ID',
  `order_item_id` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '订单明细ID',
  `return_order_id` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '退货订单ID',
  `title` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '标题',
  `price` int(20) DEFAULT NULL COMMENT '单价',
  `num` int(10) DEFAULT NULL COMMENT '数量',
  `money` int(20) DEFAULT NULL COMMENT '总金额',
  `pay_money` int(20) DEFAULT NULL COMMENT '支付金额',
  `image` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '图片地址',
  `weight` int(11) DEFAULT NULL COMMENT '重量',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `item_id` (`sku_id`) USING BTREE,
  KEY `order_id` (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_return_order_item
-- ----------------------------

-- ----------------------------
-- Table structure for tb_task
-- ----------------------------
DROP TABLE IF EXISTS `tb_task`;
CREATE TABLE `tb_task` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `task_type` varchar(32) DEFAULT NULL COMMENT '任务类型',
  `mq_exchange` varchar(64) DEFAULT NULL COMMENT '交换机名称',
  `mq_routingkey` varchar(64) DEFAULT NULL COMMENT 'routingkey',
  `request_body` varchar(512) DEFAULT NULL COMMENT '任务请求的内容',
  `status` varchar(32) DEFAULT NULL COMMENT '任务状态',
  `errormsg` varchar(512) DEFAULT NULL COMMENT '任务错误信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_task
-- ----------------------------

-- ----------------------------
-- Table structure for tb_task_his
-- ----------------------------
DROP TABLE IF EXISTS `tb_task_his`;
CREATE TABLE `tb_task_his` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `task_type` varchar(32) DEFAULT NULL COMMENT '任务类型',
  `mq_exchange` varchar(64) DEFAULT NULL COMMENT '交换机名称',
  `mq_routingkey` varchar(64) DEFAULT NULL COMMENT 'routingkey',
  `request_body` varchar(512) DEFAULT NULL COMMENT '任务请求的内容',
  `status` varchar(32) DEFAULT NULL COMMENT '任务状态',
  `errormsg` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_task_his
-- ----------------------------

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime DEFAULT NULL,
  `log_modified` datetime DEFAULT NULL,
  `ext` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_unionkey` (`xid`,`branch_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of undo_log
-- ----------------------------
