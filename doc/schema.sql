
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `title` varchar(64) NOT NULL DEFAULT '',
    `price` double(10,0) NOT NULL DEFAULT '0',
    `description` varchar(500) NOT NULL DEFAULT '',
    `sales` int(11) NOT NULL DEFAULT '0',
    `img_url` varchar(255) NOT NULL DEFAULT '',
    `stock` int(11) NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment="商品表";

LOCK TABLES `item` WRITE;
INSERT INTO `item` VALUES (6,'iphone99',800,'最好用的苹果手机',139,'https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3974550569,4161544558&fm=27&gp=0.jpg',100),(7,'iphone8',600,'第二好用的苹果手机',88,'http://img5.imgtn.bdimg.com/it/u=2067197169,357050228&fm=26&gp=0.jpg',100);
UNLOCK TABLES;


DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
    `id` varchar(32) NOT NULL,
    `user_id` int(11) NOT NULL DEFAULT '0',
    `item_id` int(11) NOT NULL DEFAULT '0',
    `item_price` double NOT NULL DEFAULT '0',
    `amount` int(11) NOT NULL DEFAULT '0',
    `order_price` double NOT NULL DEFAULT '0',
    `promo_id` int(11) NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment="订单表";


DROP TABLE IF EXISTS `promo`;
CREATE TABLE `promo` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `promo_name` varchar(255) NOT NULL DEFAULT '',
    `start_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `item_id` int(11) NOT NULL DEFAULT '0',
    `promo_item_price` double NOT NULL DEFAULT '0',
    `end_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment="秒杀活动配置";


DROP TABLE IF EXISTS `stock_log`;
CREATE TABLE `stock_log` (
    `stock_log_id` varchar(64) NOT NULL,
    `item_id` int(11) NOT NULL DEFAULT '0',
    `amount` int(11) NOT NULL DEFAULT '0',
    `status` int(11) NOT NULL DEFAULT '0' COMMENT '//1表示初始状态，2表示下单扣减库存成功，3表示下单回滚',
    PRIMARY KEY (`stock_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment="库存日志";


DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(64) NOT NULL DEFAULT '',
    `gender` tinyint(4) NOT NULL DEFAULT '0' COMMENT '//1代表男性，2代表女性',
    `age` int(11) NOT NULL DEFAULT '0',
    `telphone` varchar(255) NOT NULL DEFAULT '',
    `encrpt_password` varchar(128) NOT NULL DEFAULT '',
    `register_mode` varchar(255) NOT NULL DEFAULT '' COMMENT '//byphone,bywechat,byalipay',
    `third_party_id` varchar(64) NOT NULL DEFAULT '',
    PRIMARY KEY (`id`),
    UNIQUE KEY `telphone_unique_index` (`telphone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment="用户信息";