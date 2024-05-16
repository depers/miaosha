
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item` (
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `title` varchar(64) NOT NULL DEFAULT '' COMMENT '商品名称',
    `price` double(10,0) NOT NULL DEFAULT '0' COMMENT '商品金额',
    `description` varchar(500) NOT NULL DEFAULT '' COMMENT '商品描述',
    `sale` int(11) NOT NULL DEFAULT '0' COMMENT '商品销量',
    `img_url` varchar(255) NOT NULL DEFAULT '' COMMENT '商品主图',
    `stock` int(11) NOT NULL DEFAULT '0' COMMENT '库存',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment="商品表";

LOCK TABLES `item` WRITE;
INSERT INTO `item` VALUES (6,'iphone99',800,'最好用的苹果手机',139,'https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3974550569,4161544558&fm=27&gp=0.jpg',100),(7,'iphone8',600,'第二好用的苹果手机',88,'http://img5.imgtn.bdimg.com/it/u=2067197169,357050228&fm=26&gp=0.jpg',100);
UNLOCK TABLES;


DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
    `id` varchar(32) NOT NULL COMMENT '主键',
    `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
    `item_id` int(11) NOT NULL DEFAULT '0' COMMENT '商品id',
    `item_price` double NOT NULL DEFAULT '0' COMMENT '商品金额',
    `amount` int(11) NOT NULL DEFAULT '0' COMMENT '购买数量',
    `order_price` double NOT NULL DEFAULT '0' COMMENT '订单金额',
    `promo_id` int(11) NOT NULL DEFAULT '0' COMMENT '活动id',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment="订单表";


DROP TABLE IF EXISTS `promo`;
CREATE TABLE `promo` (
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `promo_name` varchar(255) NOT NULL DEFAULT '' COMMENT '活动名称',
    `start_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '活动开始时间',
    `item_id` int(11) NOT NULL DEFAULT '0' COMMENT '商品id',
    `promo_item_price` double NOT NULL DEFAULT '0' COMMENT '活动商品金',
    `end_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '活动结束时间',
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment="秒杀活动配置";


DROP TABLE IF EXISTS `stock_log`;
CREATE TABLE `stock_log` (
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `item_id` int(11) NOT NULL DEFAULT '0' COMMENT '主键',
    `amount` int(11) NOT NULL DEFAULT '0' COMMENT '主键',
    `status` int(11) NOT NULL DEFAULT '0' COMMENT '//1表示初始状态，2表示下单扣减库存成功，3表示下单回滚',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment="库存日志";


DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name` varchar(64) NOT NULL DEFAULT '' COMMENT '姓名',
    `gender` tinyint(4) NOT NULL DEFAULT '0' COMMENT '性别，1代表男性，2代表女性',
    `age` int(11) NOT NULL DEFAULT '0' COMMENT '年龄',
    `phone` varchar(255) NOT NULL DEFAULT '' COMMENT '手机号',
    `password` varchar(128) NOT NULL DEFAULT '' COMMENT '密码',
    PRIMARY KEY (`id`),
    UNIQUE KEY `phone_unique_index` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment="用户信息";

DROP TABLE IF EXISTS `sequence`;
CREATE TABLE `sequence` (
    `name` varchar(64) NOT NULL DEFAULT '' COMMENT '序列号名称',
    `current_value` bigint not null default '0' comment '当前值',
    `step` bigint not null  default '0' comment '步长'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment="序列号表";

