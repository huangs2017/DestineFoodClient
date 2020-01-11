USE orderfoods;


--1 `user`
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userName` varchar(20) character set utf8 NOT NULL,
  `userPassword` varchar(20) character set utf8 NOT NULL,
  PRIMARY KEY  (`userName`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `user` VALUES ('hs', 'hs');


--2 `food`
DROP TABLE IF EXISTS `food`;
CREATE TABLE `food` (
  `foodId` int(11) auto_increment NOT NULL,
  `foodName` varchar(20) character set utf8 default NULL,
  `price` float(11,0) default NULL,
  `shop` int(11) default NULL,
  `profile` varchar(100) character set utf8 default NULL,
  `image` varchar(20) default NULL,
  PRIMARY KEY  (`foodId`)
);

INSERT INTO `food` VALUES ('1', '鸭脖', '10', '0', '这个鸭脖不好吃', '0yabo.PNG');
INSERT INTO `food` VALUES ('2', '鸭肠', '20', '0', '这个鸭肠不好吃', '0yachang.PNG');
INSERT INTO `food` VALUES ('3', '鸭翅', '30', '0', '这个鸭翅不好吃', '0yachi.PNG');
INSERT INTO `food` VALUES ('4', '鸭舌', '40', '0', '这个鸭舌不好吃', '0yashe.PNG');
INSERT INTO `food` VALUES ('5', '鸭锁骨', '50', '0', '这个鸭锁骨不好吃', '0yasuogu.PNG');
INSERT INTO `food` VALUES ('6', '鸭掌', '40', '0', '这个鸭掌不好吃', '0yazhang.PNG');
INSERT INTO `food` VALUES ('7', '鸭胗', '50', '0', '这个鸭胗不好吃', '0yazhen.PNG');

INSERT INTO `food` VALUES ('8', '浣熊', '40', '1', '这是豆沙馅的包子', '1huanxiong.PNG');
INSERT INTO `food` VALUES ('9', '小黄鸭', '5', '1', '这是豆沙馅的包子', '1xiaohuangya.PNG');
INSERT INTO `food` VALUES ('10', '小猫', '5', '1', '这是豆沙馅的包子', '1xiaomao.PNG');
INSERT INTO `food` VALUES ('11', '小猪', '7', '1', '这是豆沙馅的包子', '1xiaozhu.PNG');
INSERT INTO `food` VALUES ('12', '小猫', '7', '1', '这是豆沙馅的包子', '1xiongmao.PNG');

INSERT INTO `food` VALUES ('13', '醋蒜', '4', '2', '有点淡！', '2cusuan.PNG');
INSERT INTO `food` VALUES ('14', '海白菜', '30', '2', '有点淡！', '2haibaicai.jpg');
INSERT INTO `food` VALUES ('15', '海带丝', '4', '2', '有点淡！', '2haidaisi.PNG');
INSERT INTO `food` VALUES ('16', '金针菇', '4', '2', '有点淡！', '2jinzhengu.jpg');
INSERT INTO `food` VALUES ('17', '辣白菜', '4', '2', '有点淡！', '2labaicai.PNG');
INSERT INTO `food` VALUES ('18', '木耳', '4', '2', '有点淡！', '2muer.PNG');
INSERT INTO `food` VALUES ('19', '泡椒', '4', '2', '有点淡！', '2paojiao.PNG');
INSERT INTO `food` VALUES ('20', '青萝卜干', '4', '2', '有点淡！', '2qingluobogan.jpg');
INSERT INTO `food` VALUES ('21', '酸豆角', '4', '2', '有点淡！', '2suandoujiao.jpg');
INSERT INTO `food` VALUES ('22', '咸菜疙瘩', '4', '2', '有点淡！', '2xiancaigeda.PNG');
INSERT INTO `food` VALUES ('23', '腌黄瓜', '4', '2', '有点淡！', '2yanhuanggua.jpg');


--3 `orders`
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `orderId` int(11) auto_increment NOT NULL,
  `userName` varchar(20) character set utf8 NOT NULL,
  `address` varchar(20) character set utf8 default NULL,
  `phone` varchar(20) character set utf8 default NULL,
  `remark` varchar(100) character set utf8 default NULL,
  PRIMARY KEY  (`orderId`),
  FOREIGN KEY (`userName`) REFERENCES `user` (`userName`)
)auto_increment=1 ENGINE=InnoDB DEFAULT CHARSET=utf8;


--4 `food_order`
DROP TABLE IF EXISTS `food_order`;
CREATE TABLE `food_order` (
  `foodId` int(11) NOT NULL,
  `orderId` int(11) NOT NULL,
  `quantity` int(11) default NULL,
  PRIMARY KEY  (`foodId`,`orderId`),
  FOREIGN KEY (`foodId`) REFERENCES `food` (`foodId`),
  FOREIGN KEY (`orderId`) REFERENCES `orders` (`orderId`)
);

