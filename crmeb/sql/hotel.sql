-- ----------------------------
-- Table structure for eb_store_hotel
-- ----------------------------
DROP TABLE IF EXISTS `eb_store_hotel`;
CREATE TABLE `eb_store_hotel` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '酒店ID',
  `product_id` int(10) unsigned NOT NULL COMMENT '关联商品ID',
  `name` varchar(128) NOT NULL COMMENT '酒店名称',
  `grade` tinyint(1) NOT NULL DEFAULT '0' COMMENT '酒店星级 1-5星',
  `address` varchar(255) NOT NULL COMMENT '酒店地址',
  `longitude` varchar(16) DEFAULT NULL COMMENT '经度',
  `latitude` varchar(16) DEFAULT NULL COMMENT '纬度', 
  `phone` varchar(32) DEFAULT NULL COMMENT '联系电话',
  `facilities` varchar(1000) DEFAULT NULL COMMENT '设施服务,JSON格式',
  `description` text COMMENT '酒店介绍',
  `check_in_time` varchar(32) DEFAULT NULL COMMENT '入住时间',
  `check_out_time` varchar(32) DEFAULT NULL COMMENT '退房时间',
  `is_show` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否显示',
  `is_del` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='酒店表';

-- ----------------------------
-- Table structure for eb_store_hotel_room
-- ----------------------------
DROP TABLE IF EXISTS `eb_store_hotel_room`;
CREATE TABLE `eb_store_hotel_room` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '房型ID',
  `hotel_id` int(10) unsigned NOT NULL COMMENT '酒店ID',
  `name` varchar(64) NOT NULL COMMENT '房型名称',
  `bed_type` varchar(32) NOT NULL COMMENT '床型',
  `area` int(10) unsigned DEFAULT NULL COMMENT '房间面积(平方米)',
  `window` tinyint(1) DEFAULT '0' COMMENT '是否有窗 0无 1有',
  `breakfast` tinyint(1) DEFAULT '0' COMMENT '含早餐数 0-4',
  `wifi` tinyint(1) DEFAULT '1' COMMENT '是否含wifi 0否 1是',
  `people_num` int(10) DEFAULT '2' COMMENT '可住人数',
  `images` varchar(2000) DEFAULT NULL COMMENT '房型图片',
  `facilities` varchar(1000) DEFAULT NULL COMMENT '房间设施,JSON格式',
  `description` text COMMENT '房型介绍',
  `price` decimal(8,2) NOT NULL COMMENT '房间价格',
  `is_show` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否显示',
  `is_del` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_hotel_id` (`hotel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='酒店房型表';

-- ----------------------------
-- 修改订单表，添加酒店相关字段
-- ----------------------------
ALTER TABLE `eb_store_order` 
ADD COLUMN `check_in_time` datetime DEFAULT NULL COMMENT '入住时间' AFTER `delivery_time`,
ADD COLUMN `check_out_time` datetime DEFAULT NULL COMMENT '离店时间' AFTER `check_in_time`,
ADD COLUMN `guest_name` varchar(32) DEFAULT NULL COMMENT '入住人姓名' AFTER `check_out_time`,
ADD COLUMN `guest_phone` varchar(32) DEFAULT NULL COMMENT '入住人电话' AFTER `guest_name`,
ADD COLUMN `guest_number` int(10) DEFAULT '1' COMMENT '入住人数' AFTER `guest_phone`;

-- ----------------------------
-- 修改商品表，添加商品类型
-- ----------------------------
ALTER TABLE `eb_store_product` 
MODIFY COLUMN `product_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '商品类型：0-普通商品，1-秒杀商品，2-砍价商品，3-拼团商品，4-酒店'; 