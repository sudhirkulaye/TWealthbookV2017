CREATE DATABASE  IF NOT EXISTS `twealthbookdev` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;
USE `twealthbookdev`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: twealthbookdev
-- ------------------------------------------------------
-- Server version	5.6.15-log

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
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES (1,2,'Sudhir','Ashok','Kulaye','1980-03-08',1,'2017-04-01',9819980951,'sudhir.kulaye@gmail.com','ANLPK32561',''),(1001,2,'Madhavi','Ashok','Kulaye','1985-11-16',2,'2017-04-01',9930174850,'madhavi.1611@gmail.com','BAMPK2831E',''),(1002,2,'Rohini','Ashok','Kulaye','1981-04-03',2,'2017-04-01',9987508953,'rohinikulaye@gmail.com','AXKPK3576G',''),(1003,2,'Yatin','Balkrishna','Gawde','1980-08-21',2,'2017-04-01',9819156287,'yatin.gawde@accenture.com','AIKPG4455H',''),(1004,2,'Yogesh','Ashok','Jadhav','1980-02-12',2,'2017-04-01',9967020963,'yjadhav226@gmail.com','AGCPJ2712N',''),(1005,2,'Pramod','Himatrao','Pawar','1970-08-30',2,'2017-04-01',9833539125,'pramodpawar@gmail.com','ADUPP0011B',''),(1006,2,'Rohan','Dhanaji','Rane','1985-03-17',2,'2017-04-01',9967835439,'rohandrane@gmail.com','AKUPR8082K',''),(1007,2,'Rahul','Vijayanand','Vaknalli','1995-03-18',1,'2017-04-01',9833539299,'anandkirtiv@gmail.com','ASIPV9082D',''),(1008,2,'Prashant','Govind','Parab','1965-12-19',2,'2017-04-01',9869483091,'prashant.gparab19@gmail.com','AEDPP5174M',''),(1009,2,'Ankush','Ramchandra ','Kulaye','1946-03-09',4,'2017-04-01',9969612144,'aravindkulaye@gmail.com','AAHPK4429N',''),(1010,2,'Sachin','Vasudev','Shengale','1980-06-28',2,'2017-04-01',9820518100,'sachinshengale@gmail.com','BATPS8667R',''),(1014,2,'Girish','Tamanna','Sadalge','1980-10-11',2,'2017-04-01',9820116406,'girishsadalge@gmail.com','BGIPS2699G',''),(1015,2,'Vijayanand','Bhaskar','Vaknalli','1969-09-10',2,'2017-04-01',9833539299,'anandkirtiv@gmail.com','AABPV8347G',''),(1016,2,'Aneeket','Keshav','Rane','1982-10-29',2,'2017-04-01',9821312266,'nishaaneeketrane@gmail.com','AMAPR1702L',''),(1017,2,'Jayesh',NULL,'Wadekar','1983-01-01',2,'2017-04-01',9823433623,'jayeshwadekar@gmail.com','',''),(1018,2,'Nikhil','Surendra','Phatak','1983-01-01',2,'2017-04-01',9819993419,'nikhilphatak29@gmail.com','','');
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `family_member`
--

LOCK TABLES `family_member` WRITE;
/*!40000 ALTER TABLE `family_member` DISABLE KEYS */;
INSERT INTO `family_member` VALUES (1,1,'9819980951','SELF'),(1001,1001,'9930174850','SELF'),(1002,1002,'9987508953','SELF'),(1003,1003,'9819156287','SELF'),(1004,1004,'9967020963','SELF'),(1005,1005,'9833539125','SELF'),(1007,1007,'9833539299','SELF'),(1008,1008,'9869483091','SELF'),(1009,1009,'9969612144','SELF'),(1010,1010,'9820518100','SELF'),(1014,1014,'9820116406','SELF'),(1015,1007,'9833539299','FATHER'),(1016,1016,'9821312266','SELF'),(1017,1017,'9823433623','SELF'),(1018,1002,'9987508953','SPOUSE');
/*!40000 ALTER TABLE `family_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `portfolio`
--

LOCK TABLES `portfolio` WRITE;
/*!40000 ALTER TABLE `portfolio` DISABLE KEYS */;
/*!40000 ALTER TABLE `portfolio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ADMIN'),(2,'END_USER'),(3,'RELATIONSHIP_MANAGER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sequence_next_hi_value`
--

LOCK TABLES `sequence_next_hi_value` WRITE;
/*!40000 ALTER TABLE `sequence_next_hi_value` DISABLE KEYS */;
INSERT INTO `sequence_next_hi_value` VALUES ('client_id',1019);
/*!40000 ALTER TABLE `sequence_next_hi_value` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'9819980951','QUALITY80',1,'Sudhir','Kulaye','2017-04-01','sudhir.kulaye@gmail.com','703 Diamond Park','Navghar Road','Mulund East','Mumbai','Maharashtra',400081,9819980951,0,'2017-04-01 00:00:00'),(1001,'9930174850','QUALITY80',1,'Madhavi','Kulaye','2017-04-01','madhavi.1611@gmail.com','703 Diamond Park','Navghar Road','Mulund East','Mumbai','Maharashtra',400081,9930174850,0,'2017-04-01 00:00:00'),(1002,'9987508953','QUALITY80',1,'Rohini','Kulaye','2017-04-01','rohinikulaye@gmail.com','703 Diamond Park','Navghar Road','Mulund East','Mumbai','Maharashtra',400081,9987508953,0,'2017-04-01 00:00:00'),(1003,'9819156287','QUALITY80',1,'Yatin','Gawde','2017-04-01','yatin.gawde@accenture.com','601 ANMOL HEIGHTS','Naupada','Thane West','Thane','Maharashtra',404001,9819156287,0,'2017-04-01 00:00:00'),(1004,'9967020963','QUALITY80',1,'Yogesh','Jadhav','2017-04-01','yjadhav226@gmail.com','201 Riddhi CHSL','Sector 7','Airoli','Navi Mumbai','Maharashtra',408001,9967020963,0,'2017-04-01 00:00:00'),(1005,'9833539125','QUALITY80',1,'Pramod','Pawar','2017-04-01','pramodpawar@gmail.com','704 Diamond Park','Navghar Road','Mulund East','Mumbai','Maharashtra',400081,9833539125,0,'2017-04-01 00:00:00'),(1007,'9833539299','QUALITY80',1,'Rahul','Vaknalli','2017-04-01','anandkirtiv@gmail.com','FLAT NO 2 GRANDSLAM SOCIETY RUTURAJ BLDG',' MHATRE WADI','Dahisar West','Mumbai','Maharashtra',400068,9833539299,0,'2017-04-01 00:00:00'),(1008,'9869483091','QUALITY80',1,'Prashant','Parab','2017-04-01','prashant.gparab19@gmail.com','33/234 ','ABHUDAYA NGR','KALACHOUKI','Mumbai','Maharashtra',400033,9869483091,0,'2017-04-01 00:00:00'),(1009,'9969612144','QUALITY80',1,'Ankush','Kulaye','2017-04-01','aravindkulaye@gmail.com','R00M NO 12 SURAJ BUILDING BAITHI CHAWL ','JAGANNATH BHATANK AR',' ELPHINSTONE RD','Mumbai','Maharashtra',400013,9969612144,0,'2017-04-01 00:00:00'),(1010,'9820518100','QUALITY80',1,'Sachin','Shengale','2017-04-01','sachinshengale@gmail.com','A 1-504 BHAVINI ENCLAVE','SANT RAMDAS MARG','Mulund East','Mumbai','Maharashtra',400081,9820518100,0,'2017-04-01 00:00:00'),(1014,'9820116406','QUALITY80',1,'Girish','Sadalge','2017-04-01','girishsadalge@gmail.com',NULL,NULL,'Kandivali','Mumbai','Maharashtra',NULL,9820116406,0,'2017-04-01 00:00:00'),(1016,'9821312266','QUALITY80',1,'Aneeket','Rane','2017-04-01','nishaaneeketrane@gmail.com','Rolex Apartment B-1-601','S V Road, Near Shantinaath Shopping Center ','Malad West','Mumbai','Maharashtra',400064,9821312266,0,'2017-04-01 00:00:00'),(1017,'9823433623','QUALITY80',1,'Jayesh','Wadekar','2017-04-01','jayeshwadekar@gmail.com','Akurdi','Akurdi','Pune','Pune','Maharashtra',NULL,9823433623,0,'2017-04-01 00:00:00');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,2),(1001,2),(1002,2),(1003,2),(1004,2),(1005,2),(1007,2),(1008,2),(1009,2),(1010,2),(1014,2),(1016,2),(1017,2);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-23 21:18:57
