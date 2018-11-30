-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: forum
-- ------------------------------------------------------
-- Server version	5.7.22-log

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
-- Table structure for table `f_authority`
--

DROP TABLE IF EXISTS `f_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `f_authority` (
  `authority_id` int(11) NOT NULL COMMENT '主键',
  `authority_name` varchar(64) DEFAULT '' COMMENT '权限名称',
  `icon` varchar(255) DEFAULT '' COMMENT '图标',
  `uri` varchar(255) DEFAULT '' COMMENT '请求uri',
  `permission` varchar(1000) DEFAULT '',
  PRIMARY KEY (`authority_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `f_authority`
--

LOCK TABLES `f_authority` WRITE;
/*!40000 ALTER TABLE `f_authority` DISABLE KEYS */;
INSERT INTO `f_authority` VALUES (1,'查询用户列表','','/user/list','roles[admin,普通用户]'),(2,'查询角色列表','','/r','roles[admin]');
/*!40000 ALTER TABLE `f_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `f_role`
--

DROP TABLE IF EXISTS `f_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `f_role` (
  `role_id` int(11) NOT NULL COMMENT '主键',
  `role_name` varchar(64) CHARACTER SET utf8 DEFAULT '' COMMENT '角色名称',
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role_name_UNIQUE` (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `f_role`
--

LOCK TABLES `f_role` WRITE;
/*!40000 ALTER TABLE `f_role` DISABLE KEYS */;
INSERT INTO `f_role` VALUES (1,'admin'),(2,'普通用户');
/*!40000 ALTER TABLE `f_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `f_role_authority`
--

DROP TABLE IF EXISTS `f_role_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `f_role_authority` (
  `role_id` int(11) NOT NULL DEFAULT '0' COMMENT '角色id',
  `authority_id` int(11) NOT NULL DEFAULT '0' COMMENT '权限id',
  PRIMARY KEY (`role_id`,`authority_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `f_role_authority`
--

LOCK TABLES `f_role_authority` WRITE;
/*!40000 ALTER TABLE `f_role_authority` DISABLE KEYS */;
INSERT INTO `f_role_authority` VALUES (1,1),(1,2),(2,1);
/*!40000 ALTER TABLE `f_role_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `f_user`
--

DROP TABLE IF EXISTS `f_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `f_user` (
  `sid` varchar(20) NOT NULL COMMENT '主键',
  `username` varchar(32) DEFAULT '' COMMENT '用户名',
  `password` varchar(255) DEFAULT '' COMMENT '密码',
  `role_id` int(11) DEFAULT '0' COMMENT '角色id',
  `enable` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`sid`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `f_user`
--

LOCK TABLES `f_user` WRITE;
/*!40000 ALTER TABLE `f_user` DISABLE KEYS */;
INSERT INTO `f_user` VALUES ('1068310406818304000','admin','81dc9bdb52d04dc20036dbd8313ed055',1,1),('1068310406881218560','guest','81dc9bdb52d04dc20036dbd8313ed055',2,1);
/*!40000 ALTER TABLE `f_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `sid` varchar(45) NOT NULL,
  `username` varchar(45) DEFAULT NULL,
  `psw` varchar(100) DEFAULT NULL,
  `enable` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('1067343445464584192','admin','81dc9bdb52d04dc20036dbd8313ed055',1),('1067343661345411072','admin1','admin1',1),('1067344808634355712','admin1','admin1',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-11-30 17:07:55
