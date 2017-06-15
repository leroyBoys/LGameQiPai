
insert  into `game_server`(`id`,`g_v_id`,`key`,`zone_name`,`zone_icon`,`zone_desc`,`server_type`,`zone_num`,`ip`,`udp_port`,`port`,`max_count`,`server_status`) values (2,1,NULL,'本地','sdf','本地','gate',1,'127.0.0.1',4434,4433,3000,1),(5,1,NULL,'test','es','desc','server',4,'192.168.4.179',4434,4439,3000,1),(6,2,NULL,'测试','dfs','测试','server',1,'192.168.4.179',4466,4467,3000,1);
insert  into `game_version`(`id`,`src_id`,`game_id`,`version`,`test_ip`,`version_test`,`down_url`,`res_url`,`server_status`,`f_ids`) values (1,1,1,'1.1.1',NULL,'2.2.01','biggg','http://192.168.4.179:8811/Server/Config/ServerVersion.txt',1,'2211'),(2,2,2,'1.1',NULL,'2.2.01','sss','http://192.168.4.179:8811/Server/kxxy/Config/ServerVersion.txt',1,NULL);
insert  into `servergroup`(`group`,`sqlurl`,`redisurl`,`sql_user_name`,`sql_pwd`,`redis_user_name`,`redis_pwd`) values (1,'jdbc:mysql://127.0.0.1:3306/qpgame?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull','redis://0@119.254.166.136:6379','root','123456',NULL,NULL);



DROP PROCEDURE autoInsertUser;
DELIMITER //
CREATE PROCEDURE autoInsertUser()
BEGIN
DECLARE num INT;
SET num=1;
WHILE num < 5000 DO
 INSERT INTO `user_info`(user_name, user_pwd,role) VALUES(CONCAT("test", num), "",1); SET num=num+1;
END WHILE;
 END//

CALL autoInsertUser()
DROP PROCEDURE autoInsertUser;