/*
SQLyog Ultimate v8.71 
MySQL - 5.6.26 : Database - user_center
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`user_center` /*!40100 DEFAULT CHARACTER SET gbk */;

USE `user_center`;

/*Table structure for table `exchange_code` */

DROP TABLE IF EXISTS `exchange_code`;

CREATE TABLE `exchange_code` (
  `exchangeCode` varchar(255) COLLATE utf8_bin NOT NULL,
  `psd` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `playerId` int(11) unsigned zerofill DEFAULT NULL,
  `reward_goods` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `cdkey_type` varchar(255) CHARACTER SET utf8 DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `exchange_code` */

/*Table structure for table `game_notices` */

DROP TABLE IF EXISTS `game_notices`;

CREATE TABLE `game_notices` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` tinyint(4) DEFAULT NULL COMMENT '1.停机公告。2更新公告\r\n',
  `title_name` varchar(20) DEFAULT NULL,
  `title_desc` varchar(100) DEFAULT NULL,
  `begin_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `ver` varchar(10) DEFAULT NULL COMMENT '公告版本，每次更新都改变即可\r\n',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `game_notices` */

/*Table structure for table `game_server` */

DROP TABLE IF EXISTS `game_server`;

CREATE TABLE `game_server` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `g_v_id` int(11) DEFAULT NULL,
  `zone_name` varchar(20) DEFAULT NULL,
  `zone_icon` varchar(60) DEFAULT NULL,
  `zone_desc` varchar(100) DEFAULT NULL,
  `server_type` enum('server','gate') DEFAULT 'server' COMMENT '服务器类型（网关/游戏）',
  `group_num` int(11) DEFAULT NULL COMMENT '分区号',
  `ip` varchar(20) DEFAULT NULL,
  `udp_port` int(11) DEFAULT NULL,
  `port` int(11) DEFAULT NULL,
  `max_count` int(11) DEFAULT NULL,
  `server_status` tinyint(4) DEFAULT '1' COMMENT '0停机维护,1:正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=gbk;

/*Data for the table `game_server` */

insert  into `game_server`(`id`,`g_v_id`,`zone_name`,`zone_icon`,`zone_desc`,`server_type`,`group_num`,`ip`,`udp_port`,`port`,`max_count`,`server_status`) values (2,1,'本地','sdf','本地','server',1,'192.168.4.231',4434,4433,3000,1),(5,1,'test','es','desc','server',1,'192.168.4.179',4434,4439,3000,1),(6,2,'测试','dfs','测试','server',1,'192.168.4.179',4466,4467,3000,1);

/*Table structure for table `game_version` */

DROP TABLE IF EXISTS `game_version`;

CREATE TABLE `game_version` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `src_id` int(11) DEFAULT NULL COMMENT '渠道(int)',
  `game_id` int(11) DEFAULT NULL COMMENT '游戏id（int）',
  `version` varchar(15) DEFAULT NULL COMMENT '主版本号（M）.主版本发布序列（N）.资源版本（R）',
  `test_ip` varchar(15) DEFAULT NULL COMMENT '审核版本跳转地址',
  `version_test` varchar(15) DEFAULT NULL COMMENT '审核版本信息',
  `down_url` varchar(100) DEFAULT NULL COMMENT '主版本下载地址',
  `res_url` varchar(100) DEFAULT NULL COMMENT '主版本资源地址',
  `server_status` tinyint(11) DEFAULT '0' COMMENT '0停机维护,1:正常',
  `f_ids` varchar(100) DEFAULT NULL COMMENT '公告idStr(修改即改变)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=gbk;

/*Data for the table `game_version` */

insert  into `game_version`(`id`,`src_id`,`game_id`,`version`,`test_ip`,`version_test`,`down_url`,`res_url`,`server_status`,`f_ids`) values (1,2,1,'0.1.1',NULL,'2.2.01','biggg','http://192.168.4.179:8811/Server/Config/ServerVersion.txt',1,'2211'),(2,2,2,'1.1',NULL,'2.2.01','sss','http://192.168.4.179:8811/Server/kxxy/Config/ServerVersion.txt',1,NULL);

/*Table structure for table `servergroup` */

DROP TABLE IF EXISTS `servergroup`;

CREATE TABLE `servergroup` (
  `group` int(11) NOT NULL AUTO_INCREMENT,
  `sqlurl` varchar(40) DEFAULT NULL,
  `redisurl` varchar(40) DEFAULT NULL,
  `sql_user_name` varchar(20) DEFAULT NULL,
  `sql_pwd` varchar(100) DEFAULT NULL,
  `redis_user_name` varchar(20) DEFAULT NULL,
  `redis_pwd` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`group`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `servergroup` */

/*Table structure for table `user_attribute` */

DROP TABLE IF EXISTS `user_attribute`;

CREATE TABLE `user_attribute` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `email` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `identity_card` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `mobile` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `qq` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `real_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `sex` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `user_attribute` */

/*Table structure for table `user_device` */

DROP TABLE IF EXISTS `user_device`;

CREATE TABLE `user_device` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime DEFAULT NULL,
  `device_info` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `device_name` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `os_id` int(11) NOT NULL,
  `device_mac` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `udid` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `user_device` */

insert  into `user_device`(`id`,`create_date`,`device_info`,`device_name`,`os_id`,`device_mac`,`udid`) values (1,'2015-11-25 15:28:17','phone','plt',0,'maoo-11o-sdf','ooooooooooos'),(2,'2015-11-25 15:31:52','phone','plt',0,'maoo-11o-sdf','ooooooooooos'),(3,'2016-04-13 16:57:53',NULL,'1',0,'B0D59D49EC10',NULL),(4,'2016-04-13 17:08:04','Soars\'PC','1',0,'B0D59D49EC10','2'),(5,'2016-04-27 11:21:04','Soars\'PC','test',0,'B0D59D49EC10',''),(6,'2016-05-13 20:42:18','Soars\'PC','test',0,'test','353efdc8a23e301c4d9206f3bb456164'),(7,'2016-05-13 20:51:47','Soars\'PC','test',0,'test','d9c33962e01136bb3a11d0f493ef389e'),(8,'2016-05-14 13:09:37','Soars\'PC','test',0,'test','7775d0c334081484cf52675aa848cf3a'),(9,'2016-05-14 21:05:43','Soars\'PC','test',0,'test','c4dd1680cb4cf7a4fafe054ef1a3cf53'),(10,'2016-05-16 11:07:34','Soars\'PC','test',0,'test','8491eb3d2c9fdbf324a7eec3af891414'),(11,'2016-06-16 08:43:22','Soars\'PC','test',0,'B2959D49EC10',''),(12,'2016-06-16 08:54:38','Soars\'PC','test',0,'test','db9b4c9ec1a5376dfdbdf789ba359a3c'),(13,'2016-07-13 15:25:08','Soars\'PC','test',0,'FCAA14094F7F',''),(14,'2016-08-01 15:14:37','Soars\'PC','test',0,'test','6ab79c87875ec3a61f21ab8a51440a7b'),(15,'2016-08-01 15:36:16','Soars\'PC','test',0,'test','2b7db25580feb5524c6bb72582105acf'),(16,'2016-08-02 14:34:01','Soars\'PC','test',0,'test','593eaf646d1605a9f9a424739718ac7f'),(17,'2016-08-12 10:36:55','Soars\'PC','test',0,'test','47b76f5d9b14c0f4e1298df45485359d'),(18,'2016-08-18 18:14:13','Soars\'PC','test',0,'test','31141fe93242b0b8a0ff75413bef07dc');

/*Table structure for table `user_from` */

DROP TABLE IF EXISTS `user_from`;

CREATE TABLE `user_from` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_src` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `serial_num` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `info` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `user_from` */

/*Table structure for table `user_info` */

DROP TABLE IF EXISTS `user_info`;

CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` int(11) DEFAULT NULL,
  `user_from_type` tinyint(4) DEFAULT '0',
  `user_from_id` int(100) DEFAULT '0',
  `user_name` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `user_pwd` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `role` tinyint(4) DEFAULT '0' COMMENT '(0游客，1：正常，2：机器人，3：GM)',
  `invite_code` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `user_status` int(11) NOT NULL DEFAULT '0' COMMENT '0正常，1禁言，2禁止登陆',
  `status_endtime` timestamp NULL DEFAULT NULL,
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `login_off_time` timestamp NULL DEFAULT NULL,
  `login_time` timestamp NULL DEFAULT NULL,
  `is_online` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `user_info` */

insert  into `user_info`(`id`,`device_id`,`user_from_type`,`user_from_id`,`user_name`,`user_pwd`,`role`,`invite_code`,`user_status`,`status_endtime`,`create_date`,`login_off_time`,`login_time`,`is_online`) values (1,1,0,0,'tome','tome',1,'sdfsx',0,NULL,'2015-10-20 16:57:08','2016-04-19 18:42:10','2016-04-23 12:49:11',1),(2,5,0,0,'test','1',1,'hAky43oe',0,NULL,'2016-04-13 17:22:20','2016-05-03 14:08:35','2016-05-03 14:11:14',1),(3,5,0,0,'soars','1',1,'zmm7wbo8',0,NULL,'2016-05-03 14:41:12',NULL,NULL,0),(4,5,0,0,'soars1','1',1,'3Dz2iloa',0,NULL,'2016-05-03 14:42:15',NULL,NULL,0),(5,5,0,0,'abc123','1',1,'3SfTdlos',0,NULL,'2016-05-03 14:45:14','2016-05-03 14:55:08','2016-05-03 14:55:05',0),(6,5,0,0,'abc124','1',1,'NtD3qMo2',0,NULL,'2016-05-03 14:56:48','2016-05-03 14:57:54','2016-05-03 14:57:50',0),(7,5,0,0,'abc125','1',1,'&45z3Rod',0,NULL,'2016-05-03 15:01:31',NULL,NULL,0),(8,11,0,0,'abc126','1',1,'hXSS@Doz',0,NULL,'2016-05-03 15:12:18','2016-06-16 11:55:07','2016-06-16 11:54:32',0),(9,8,0,0,'admin','a',1,'qC8P77ox',0,NULL,'2016-05-14 13:09:37','2016-05-14 14:52:16','2016-05-14 14:49:11',0),(10,9,0,0,'qwqw','123',1,'bXXxHno9',0,NULL,'2016-05-14 21:05:44',NULL,NULL,0),(11,10,0,0,'112233','112233',1,'yXhBxWoc',0,NULL,'2016-05-16 11:07:34',NULL,NULL,0),(12,12,0,0,'ztf123','1',1,'EPB7ifo7',0,NULL,'2016-06-16 08:54:38','2016-06-16 08:57:49','2016-06-16 08:56:11',0),(13,11,0,0,'abc127','1',1,'gqyHpNop',0,NULL,'2016-06-16 11:55:31','2016-07-11 16:35:07','2016-07-11 16:18:40',0),(14,12,0,0,'ssssag','a',1,'yYsu3fo5',0,NULL,'2016-06-16 12:35:36','2016-07-08 09:58:55','2016-07-08 09:53:54',0),(15,13,0,0,'tststs1','1',1,'&EZ7hJoi',0,NULL,'2016-07-13 15:25:08','2016-07-14 09:53:20','2016-07-14 09:52:31',0),(16,13,0,0,'asasas','0',1,'6TuzzYok',0,NULL,'2016-07-14 09:57:48','2016-07-14 10:22:01','2016-07-14 10:21:42',0),(17,13,0,0,'as','0',1,'e69qv9o3',0,NULL,'2016-07-14 10:22:19','2016-07-25 19:12:40','2016-07-25 19:12:02',0),(18,13,0,0,'abc456','1',1,'rXY@eNom',0,NULL,'2016-07-25 19:16:54','2016-07-29 20:07:31','2016-07-29 20:07:01',0),(19,13,0,0,'a','0',1,'3Redxboj',0,NULL,'2016-07-29 20:16:34','2016-08-29 10:18:05','2016-08-29 10:18:03',0),(20,15,0,0,'zhd','zhd',1,'N6xH3Vou',0,NULL,'2016-08-01 15:36:16','2016-08-03 17:01:19','2016-08-03 16:25:17',0),(21,15,0,0,'zzz','zzz',1,'l29xiWof',0,NULL,'2016-08-02 17:43:05','2016-08-02 17:42:52','2016-08-02 17:40:49',0),(22,15,0,0,'z','z',1,'JTJT2ior',0,NULL,'2016-08-03 08:56:39','2016-08-03 08:59:07','2016-08-03 08:58:39',0),(23,13,0,0,'s','s',1,'75dNA7o4',0,NULL,'2016-08-11 16:14:30','2016-08-29 19:00:41','2016-08-29 19:00:28',0),(24,17,0,0,'lilongdi','123456',1,'BDQrJpov',0,NULL,'2016-08-12 10:36:55','2016-08-12 10:47:18','2016-08-12 10:45:35',0),(25,15,0,0,'mmm','m',1,'jD@ViGoy',0,NULL,'2016-08-16 10:32:49','2016-08-16 12:37:48','2016-08-16 12:37:08',0),(26,13,0,0,'1','s',1,'QAkqRWol',0,NULL,'2016-08-16 12:41:49','2016-08-16 12:46:41','2016-08-16 12:45:42',0),(27,15,0,0,'q','q',1,'BZvj5rot',0,NULL,'2016-08-16 12:47:35','2016-08-16 12:50:49','2016-08-16 12:47:38',0),(28,15,0,0,'2','2',1,'VMEWvvon',0,NULL,'2016-08-16 12:51:35','2016-08-16 15:09:21','2016-08-16 15:09:12',0),(29,13,0,0,'sa','sa',1,'CiqVXho6',0,NULL,'2016-08-16 13:50:47','2016-08-31 14:15:52','2016-08-31 14:16:03',1),(30,15,0,0,'qqq','s',1,'PrJjqfob',0,NULL,'2016-08-17 13:51:03','2016-08-18 15:14:13','2016-08-18 15:09:18',0),(31,15,0,0,'999','9',1,'xYxZj5og',0,NULL,'2016-08-17 14:22:10','2016-08-17 14:23:05','2016-08-17 14:22:16',0),(32,12,0,0,'kjhdk','a',1,'ePG6M8oh',0,NULL,'2016-08-17 17:23:52','2016-08-18 18:12:22','2016-08-18 18:09:11',0),(33,18,0,0,'sb','s',1,'et5Nt6oA',0,NULL,'2016-08-18 18:14:14','2016-08-18 18:26:45','2016-08-18 18:19:37',0),(34,18,0,0,'123456','1',1,'2eFYDxoB',0,NULL,'2016-08-19 13:51:58','2016-08-19 14:18:55','2016-08-19 14:07:08',0);

/*Table structure for table `user_token` */

DROP TABLE IF EXISTS `user_token`;

CREATE TABLE `user_token` (
  `uid` int(11) NOT NULL,
  `key` varchar(100) DEFAULT NULL,
  `ip` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `user_token` */

insert  into `user_token`(`uid`,`key`,`ip`) values (1,'037D20','localhost:4433'),(2,'A4Cdl1',''),(5,'q7v6Hq',''),(6,'A7jJGE',''),(8,'EMl529',''),(9,'j337I8',''),(10,'xiW98g',''),(11,'28YEc9',''),(12,'696915',''),(13,'R9XM1n',''),(14,'6RdOUb',''),(15,'8GEq3x',''),(16,'6345s3',''),(17,'662n3f',''),(18,'PWLsr2',''),(19,'A54482',''),(20,'O521V2',''),(21,'dD901I',''),(22,'v090T3',''),(23,'82242g',''),(24,'S09w9f',''),(25,'444Y62',''),(26,'3m570W',''),(27,'Ng260A',''),(28,'B65376',''),(29,'3722C2',''),(30,'qfc425',''),(31,'A680O0',''),(32,'19z9gh',''),(33,'8Y3l4H',''),(34,'5Af265','');

/* Procedure structure for procedure `GET_DEV` */

/*!50003 DROP PROCEDURE IF EXISTS  `GET_DEV` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `GET_DEV`(
	p_device_mac  VARCHAR(100),
	p_udid  VARCHAR(100)
	)
BEGIN
	select id from user_device where  device_mac = p_device_mac and udid = p_udid;
    END */$$
DELIMITER ;

/* Procedure structure for procedure `GET_SERVER_VERSION` */

/*!50003 DROP PROCEDURE IF EXISTS  `GET_SERVER_VERSION` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `GET_SERVER_VERSION`(
	p_src_id INT,
	p_game_id INT)
BEGIN
	select * from game_version where src_id = p_src_id and game_id = p_game_id;
    END */$$
DELIMITER ;

/* Procedure structure for procedure `GET_USERINFO_ID` */

/*!50003 DROP PROCEDURE IF EXISTS  `GET_USERINFO_ID` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `GET_USERINFO_ID`(
	p_id int,
	p_from_id int,
	p_dev_id int
	)
BEGIN
	 IF p_id > 0 THEN
		SELECT * FROM user_info WHERE id = p_id;
	elseif p_from_id > 0 then
		SELECT * FROM user_info WHERE user_from_id = p_from_id;
	ELSE
		SELECT * FROM user_info WHERE device_id = p_dev_id;
	END IF;
	
    END */$$
DELIMITER ;

/* Procedure structure for procedure `GET_USERINFO_NAME` */

/*!50003 DROP PROCEDURE IF EXISTS  `GET_USERINFO_NAME` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `GET_USERINFO_NAME`(
		p_user_name VARCHAR(25)
		)
BEGIN
	SELECT * FROM user_info WHERE  user_name = p_user_name;
    END */$$
DELIMITER ;

/* Procedure structure for procedure `GET_USERINFO_UP` */

/*!50003 DROP PROCEDURE IF EXISTS  `GET_USERINFO_UP` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `GET_USERINFO_UP`(
	p_user_name VARCHAR(25),
	p_user_pwd VARCHAR(25))
BEGIN
	select * from user_info where user_pwd = p_user_pwd and user_name = p_user_name;
    END */$$
DELIMITER ;

/* Procedure structure for procedure `GET_USERTOKEN` */

/*!50003 DROP PROCEDURE IF EXISTS  `GET_USERTOKEN` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `GET_USERTOKEN`(p_uid int)
BEGIN
	select uid,`key`,ip from user_token where uid = p_uid;
    END */$$
DELIMITER ;

/* Procedure structure for procedure `GET_ZONE` */

/*!50003 DROP PROCEDURE IF EXISTS  `GET_ZONE` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `GET_ZONE`(
	p_id INT
	)
BEGIN
	select * from game_zone where id = p_id;
    END */$$
DELIMITER ;

/* Procedure structure for procedure `GET_ZONE_GVID` */

/*!50003 DROP PROCEDURE IF EXISTS  `GET_ZONE_GVID` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `GET_ZONE_GVID`(
	p_game_version_id INT
	)
BEGIN
	select * from game_zone where g_v_id = p_game_version_id;
    END */$$
DELIMITER ;

/* Procedure structure for procedure `IN_DEVICE` */

/*!50003 DROP PROCEDURE IF EXISTS  `IN_DEVICE` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `IN_DEVICE`(
	p_device_info  VARCHAR(100),
	p_device_name  VARCHAR(25),
	p_os_id int,
	p_device_mac  VARCHAR(100),
	p_udid  VARCHAR(100),
	p_create_date datetime)
BEGIN
	INSERT INTO user_device(device_info,device_name,os_id,device_mac,udid,create_date)
			VALUES
		     (p_device_info,p_device_name,p_os_id,p_device_mac,p_udid,p_create_date);
    END */$$
DELIMITER ;

/* Procedure structure for procedure `IN_USERFROM` */

/*!50003 DROP PROCEDURE IF EXISTS  `IN_USERFROM` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `IN_USERFROM`(
	p_user_src varchar(25),
	p_serial_num VARCHAR(25),
	p_info VARCHAR(25),
	p_create_date datetime)
BEGIN
	INSERT INTO user_from(user_src,serial_num,info,create_date)
			VALUES
		     (p_user_src,p_serial_num,p_info,p_create_date);
    END */$$
DELIMITER ;

/* Procedure structure for procedure `IN_USERINFO` */

/*!50003 DROP PROCEDURE IF EXISTS  `IN_USERINFO` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `IN_USERINFO`(
	p_device_id int,
	p_user_from_id int,
	p_user_from_type TINYINT,
	p_user_name VARCHAR(25),
	p_user_pwd VARCHAR(25),
	p_role tinyint,
	p_invite_code VARCHAR(25),
	p_user_status tinyint,
	p_status_endtime DATETIME,
	p_create_date DATETIME)
BEGIN
	INSERT INTO user_info(device_id,user_from_type,user_from_id,user_name,user_pwd,role,invite_code,user_status,status_endtime,create_date)
			VALUES
		     (p_device_id,p_user_from_type,p_user_from_id,p_user_name,p_user_pwd,p_role,p_invite_code,p_user_status,p_status_endtime,p_create_date);
    END */$$
DELIMITER ;

/* Procedure structure for procedure `SET_TOKEN` */

/*!50003 DROP PROCEDURE IF EXISTS  `SET_TOKEN` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `SET_TOKEN`(
	p_key VARCHAR(15),
	p_uid int,
	p_ip varchar(25)
	)
BEGIN
	IF NOT EXISTS (SELECT 1 FROM user_token WHERE uid = p_uid) THEN
		INSERT INTO user_token (uid,`key`,ip) VALUES (p_uid,p_key,p_ip);
	ELSE
		UPDATE user_token SET `key` =p_key ,ip=p_ip WHERE uid = p_uid;
	END IF;
    END */$$
DELIMITER ;

/* Procedure structure for procedure `SET_USERINFO_AGAIN` */

/*!50003 DROP PROCEDURE IF EXISTS  `SET_USERINFO_AGAIN` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `SET_USERINFO_AGAIN`(
		p_id int,
	p_user_name VARCHAR(25),
	p_user_pwd VARCHAR(25),
	p_invite_code VARCHAR(25)
	)
BEGIN
	UPDATE user_info SET user_name = p_user_name,user_pwd = p_user_pwd,invite_code = p_invite_code  WHERE id = p_id;
    END */$$
DELIMITER ;

/* Procedure structure for procedure `SET_USERINFO_DEV` */

/*!50003 DROP PROCEDURE IF EXISTS  `SET_USERINFO_DEV` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `SET_USERINFO_DEV`(
	p_id int,
	p_dev_id int
	)
BEGIN
	UPDATE user_info SET device_id = p_dev_id  WHERE id = p_id;
    END */$$
DELIMITER ;

/* Procedure structure for procedure `SET_USERINFO_ONLINE` */

/*!50003 DROP PROCEDURE IF EXISTS  `SET_USERINFO_ONLINE` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `SET_USERINFO_ONLINE`(
		p_id int,
		p_isonline bool,
		p_updateTime datetime
	)
BEGIN
          IF p_isonline THEN
		UPDATE user_info SET is_online = p_isonline,login_time = p_updateTime WHERE id = p_id;
	ELSE
		UPDATE user_info SET is_online = p_isonline,login_off_time = p_updateTime WHERE id = p_id;
	END IF;
    END */$$
DELIMITER ;

/* Procedure structure for procedure `SET_USERINFO_PWD` */

/*!50003 DROP PROCEDURE IF EXISTS  `SET_USERINFO_PWD` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `SET_USERINFO_PWD`(
	p_id int,
	p_old varchar(20),
	p_pwd VARCHAR(20)
 )
BEGIN
	update user_info set user_pwd = p_pwd where id = p_id and user_pwd = p_old;
    END */$$
DELIMITER ;

/* Procedure structure for procedure `SET_USERINFO_STATUS` */

/*!50003 DROP PROCEDURE IF EXISTS  `SET_USERINFO_STATUS` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `SET_USERINFO_STATUS`(
		p_id int,
		p_status int,
		p_endTime datetime
	)
BEGIN
	update user_info set user_status = p_status,status_endtime = p_endTime where id = p_id;
    END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
