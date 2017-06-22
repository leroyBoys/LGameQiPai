-- MySQL dump 10.13  Distrib 5.6.26, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: user_center
-- ------------------------------------------------------
-- Server version	5.6.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `exchange_code`
--

DROP TABLE IF EXISTS `exchange_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exchange_code` (
  `exchangeCode` varchar(255) COLLATE utf8_bin NOT NULL,
  `psd` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `playerId` int(11) unsigned zerofill DEFAULT NULL,
  `reward_goods` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `cdkey_type` varchar(255) CHARACTER SET utf8 DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `game_notices`
--

DROP TABLE IF EXISTS `game_notices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `game_server`
--

DROP TABLE IF EXISTS `game_server`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `game_server` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `g_v_id` int(11) DEFAULT NULL,
  `key` varchar(50) DEFAULT NULL,
  `zone_name` varchar(20) DEFAULT NULL,
  `zone_icon` varchar(60) DEFAULT NULL,
  `zone_desc` varchar(100) DEFAULT NULL,
  `server_type` enum('server','gate') DEFAULT 'server' COMMENT '服务器类型（网关/游戏）',
  `zone_num` int(11) DEFAULT NULL COMMENT '分区号',
  `ip` varchar(20) DEFAULT NULL,
  `udp_port` int(11) DEFAULT NULL,
  `port` int(11) DEFAULT NULL,
  `max_count` int(11) DEFAULT NULL,
  `server_status` tinyint(4) DEFAULT '1' COMMENT '0停机维护,1:正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `game_version`
--

DROP TABLE IF EXISTS `game_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `servergroup`
--

DROP TABLE IF EXISTS `servergroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `servergroup` (
  `group` int(11) NOT NULL AUTO_INCREMENT,
  `sqlurl` varchar(500) DEFAULT NULL,
  `redisurl` varchar(500) DEFAULT NULL,
  `sql_user_name` varchar(20) DEFAULT NULL,
  `sql_pwd` varchar(100) DEFAULT NULL,
  `redis_user_name` varchar(20) DEFAULT NULL,
  `redis_pwd` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`group`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_attribute`
--

DROP TABLE IF EXISTS `user_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_attribute` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `email` varchar(100) COLLATE utf8_bin NOT NULL,
  `identity_card` varchar(20) COLLATE utf8_bin NOT NULL,
  `mobile` int(11) NOT NULL,
  `qq` int(11) DEFAULT NULL,
  `real_name` varchar(255) COLLATE utf8_bin NOT NULL,
  `sex` smallint(6) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_device`
--

DROP TABLE IF EXISTS `user_device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_from`
--

DROP TABLE IF EXISTS `user_from`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_from` (
  `id` int(11) NOT NULL COMMENT 'uid',
  `user_src` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `serial_num` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `info` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_info`
--

DROP TABLE IF EXISTS `user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` int(11) DEFAULT '0',
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
  `is_online` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0:等待登录,1:登录成功:：离线',
  PRIMARY KEY (`id`),
  UNIQUE KEY `userName` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


insert  into `game_server`(`id`,`g_v_id`,`key`,`zone_name`,`zone_icon`,`zone_desc`,`server_type`,`zone_num`,`ip`,`udp_port`,`port`,`max_count`,`server_status`) values (2,1,NULL,'本地','sdf','本地','gate',1,'127.0.0.1',4434,4433,3000,1),(5,1,NULL,'test','es','desc','server',4,'192.168.4.179',4434,4439,3000,1),(6,2,NULL,'测试','dfs','测试','server',1,'192.168.4.179',4466,4467,3000,1);
insert  into `game_version`(`id`,`src_id`,`game_id`,`version`,`test_ip`,`version_test`,`down_url`,`res_url`,`server_status`,`f_ids`) values (1,1,1,'1.1.1',NULL,'2.2.01','biggg','http://192.168.4.179:8811/Server/Config/ServerVersion.txt',1,'2211'),(2,2,2,'1.1',NULL,'2.2.01','sss','http://192.168.4.179:8811/Server/kxxy/Config/ServerVersion.txt',1,NULL);
insert  into `servergroup`(`group`,`sqlurl`,`redisurl`,`sql_user_name`,`sql_pwd`,`redis_user_name`,`redis_pwd`) values (1,'jdbc:mysql://127.0.0.1:3306/qpgame?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull','redis://0@119.254.166.136:6379','root','123456',NULL,NULL);

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_token`
--

DROP TABLE IF EXISTS `user_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_token` (
  `uid` int(11) NOT NULL,
  `key` varchar(100) DEFAULT NULL,
  `ip` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'user_center'
--
/*!50003 DROP PROCEDURE IF EXISTS `pr_static_all_table` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pr_static_all_table`()
BEGIN
-- ----------------------------------------------------------------------------------
--                          存储过程说明
-- 创建人:      吕晓辉
-- 创建时间:    20130821
-- 功能描述:    静态库查询所有表信息
-- 修改人:     
-- 修改时间:    
-- 修改描述:    
-- ----------------------------------------------------------------------------------
	select table_name,table_comment,table_rows from information_schema.TABLES where table_schema = 'user_center';
    END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `pr_static_get_table_comment` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pr_static_get_table_comment`(
IN       t_tablename     VARCHAR(30)    -- 表明
)
BEGIN
-- ----------------------------------------------------------------------------------
--                          存储过程说明
-- 创建人:      吕晓辉
-- 创建时间:    20130821
-- 功能描述:    静态库查询表结构信息
-- 修改人:     
-- 修改时间:    
-- 修改描述:    
-- ----------------------------------------------------------------------------------
	DECLARE l_sql VARCHAR(200);
	SET @sqlstr = CONCAT('SHOW FULL COLUMNS FROM ',t_tablename);
	PREPARE l_sql FROM @sqlstr;
	EXECUTE l_sql;
    END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `pr_static_get_table_result` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pr_static_get_table_result`(
IN       t_tablename     VARCHAR(30) ,   -- 表明
IN       t_comment     VARCHAR(30) ,   -- 字段名
IN       t_comment_value     VARCHAR(30) ,   -- 字段对应值
IN       t_type     VARCHAR(30)    -- 查询方式
)
BEGIN
-- ----------------------------------------------------------------------------------
--                          存储过程说明
-- 创建人:      吕晓辉
-- 创建时间:    20130821
-- 功能描述:    静态库查询表信息
-- 修改人:     
-- 修改时间:    
-- 修改描述:    
-- ----------------------------------------------------------------------------------
	DECLARE l_sql VARCHAR(500);
	IF LENGTH(t_comment) > 0 THEN
		IF (t_type IS NOT NULL AND t_type <> '' AND t_type = 0) THEN -- 精确查询
			SET @sqlstr = CONCAT('SELECT * FROM ',t_tablename, ' WHERE ',t_comment,' = \'',t_comment_value,'\'');
		ELSE -- 模糊查询
			SET @sqlstr = CONCAT('SELECT * FROM ',t_tablename, ' WHERE ',t_comment,' like   ',CONCAT('\'%',t_comment_value,'%\''));
		END IF;	
	ELSE 
		SET @sqlstr = CONCAT('SELECT * FROM ',t_tablename);
	END IF;
	
	PREPARE l_sql FROM @sqlstr;
	EXECUTE l_sql;
    END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-05-28 22:58:58
