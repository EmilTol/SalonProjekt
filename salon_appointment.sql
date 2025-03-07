-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: salon
-- ------------------------------------------------------
-- Server version	9.2.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `appointment`
--

DROP TABLE IF EXISTS `appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer_name` varchar(100) NOT NULL,
  `customer_phone` varchar(20) NOT NULL,
  `customer_gender` enum('M','F') NOT NULL,
  `treatment_id` int NOT NULL,
  `appointment_datetime` datetime NOT NULL,
  `status` enum('open','closed','cancelled') DEFAULT 'open',
  `employee_id` int NOT NULL,
  `created_datetime` datetime DEFAULT CURRENT_TIMESTAMP,
  `extra_time` int DEFAULT '0',
  `extra_cost` decimal(10,2) DEFAULT '0.00',
  PRIMARY KEY (`id`),
  KEY `treatment_id` (`treatment_id`),
  KEY `employee_id` (`employee_id`),
  CONSTRAINT `appointment_ibfk_1` FOREIGN KEY (`treatment_id`) REFERENCES `treatment` (`id`),
  CONSTRAINT `appointment_ibfk_2` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment`
--

LOCK TABLES `appointment` WRITE;
/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
INSERT INTO `appointment` VALUES (1,'Test Person','12345678','M',1,'2025-03-01 14:00:00','open',1,'2025-02-24 16:45:10',0,0.00),(2,'Mads Jensen','12345678','M',1,'2025-03-01 10:00:00','open',1,'2025-02-27 01:00:26',0,0.00),(3,'Sofie Pedersen','23456789','F',2,'2025-03-01 11:00:00','closed',2,'2025-02-27 01:00:26',0,0.00),(4,'Nikolaj Larsen','34567890','M',3,'2025-03-01 12:00:00','cancelled',3,'2025-02-27 01:00:26',0,0.00),(5,'Camilla Sørensen','45678901','F',4,'2025-03-01 13:00:00','open',4,'2025-02-27 01:00:26',0,0.00),(6,'Lars Christiansen','56789012','M',1,'2025-03-02 09:30:00','cancelled',5,'2025-02-27 01:00:26',0,0.00),(7,'Katrine Andersen','67890123','F',2,'2025-03-02 10:30:00','closed',1,'2025-02-27 01:00:26',0,0.00),(8,'Mikkel Olesen','78901234','M',3,'2025-03-02 11:30:00','closed',2,'2025-02-27 01:00:26',0,0.00),(9,'Line Rasmussen','89012345','F',4,'2025-03-02 12:30:00','cancelled',3,'2025-02-27 01:00:26',0,0.00),(10,'Christian Holm','90123456','M',1,'2025-03-03 12:20:00','open',4,'2025-02-27 01:00:26',0,0.00),(11,'Emilie Jensen','01234567','F',2,'2025-03-03 11:15:00','closed',5,'2025-02-27 01:00:26',0,0.00),(12,'Frederik Nielsen','11223344','M',3,'2025-03-03 12:15:00','open',1,'2025-02-27 01:00:26',0,0.00),(13,'Maria Christensen','22334455','F',4,'2025-03-03 13:15:00','cancelled',2,'2025-02-27 01:00:26',0,0.00),(14,'Rasmus Mortensen','33445566','M',1,'2025-03-04 09:45:00','open',3,'2025-02-27 01:00:26',0,0.00),(15,'Anna Larsen','44556677','F',2,'2025-03-04 10:45:00','closed',4,'2025-02-27 01:00:26',0,0.00),(16,'Søren Jakobsen','55667788','M',3,'2025-03-04 11:45:00','open',5,'2025-02-27 01:00:26',0,0.00),(17,'Julie Petersen','66778899','F',4,'2025-03-04 12:45:00','cancelled',1,'2025-02-27 01:00:26',0,0.00),(18,'Morten Sørensen','77889900','M',1,'2025-03-05 10:00:00','closed',2,'2025-02-27 01:00:26',0,0.00),(19,'Ida Rasmussen','88990011','F',2,'2025-03-05 11:00:00','closed',3,'2025-02-27 01:00:26',0,0.00),(20,'Victor Andersen','99001122','M',3,'2025-03-05 12:00:00','open',4,'2025-02-27 01:00:26',0,0.00),(21,'Laura Holm','10111213','F',4,'2025-03-05 13:00:00','cancelled',5,'2025-02-27 01:00:26',0,0.00),(22,'Oliver Christiansen','11121314','M',1,'2025-03-06 09:30:00','open',1,'2025-02-27 01:00:26',0,0.00),(23,'Emma Madsen','12131415','F',2,'2025-03-06 10:30:00','closed',2,'2025-02-27 01:00:26',0,0.00),(24,'Noah Poulsen','13141516','M',3,'2025-03-06 11:30:00','open',3,'2025-02-27 01:00:26',0,0.00),(25,'Freja Jensen','14151617','F',4,'2025-03-06 12:30:00','cancelled',4,'2025-02-27 01:00:26',0,0.00),(26,'Victor Holm','15161718','M',1,'2025-03-07 10:15:00','open',5,'2025-02-27 01:00:26',0,0.00),(27,'Clara Nielsen','16171819','F',2,'2025-03-07 11:15:00','closed',1,'2025-02-27 01:00:26',0,0.00),(28,'Emil Andersen','17181920','M',3,'2025-03-07 12:15:00','open',2,'2025-02-27 01:00:26',0,0.00),(29,'Mathilde Sørensen','18192021','F',4,'2025-03-07 13:15:00','cancelled',3,'2025-02-27 01:00:26',0,0.00),(30,'Jakob Møller','19202122','M',1,'2025-03-08 09:45:00','open',4,'2025-02-27 01:00:26',0,0.00),(31,'Signe Rasmussen','20212223','F',2,'2025-03-08 10:45:00','closed',5,'2025-02-27 01:00:26',0,0.00),(32,'Frederik Bay','23232323','M',1,'2025-03-05 00:30:00','closed',1,'2025-02-28 00:44:46',0,200.00),(33,'Thomas Tom','23232323','M',3,'2025-03-05 14:00:00','open',3,'2025-02-28 00:59:13',20,150.00),(34,'Gustav','23232323','M',5,'2025-03-11 12:30:00','open',4,'2025-03-02 11:34:15',0,200.00),(35,'Jacob Ølsted','24242424','M',1,'2025-03-26 12:20:00','open',3,'2025-03-02 19:02:21',0,222.00),(36,'Jørgen Kræft','44444444','F',2,'2025-03-19 12:30:00','closed',2,'2025-03-02 19:58:43',0,999.00),(37,'Gert Has','44555555','M',1,'2025-03-05 23:30:00','closed',5,'2025-03-02 23:32:56',11,222.00),(38,'TestTest','23232323','M',1,'2025-03-01 10:50:00','open',1,'2025-03-03 00:03:16',0,0.00),(39,'test','12121212','M',3,'2025-03-08 13:10:00','closed',4,'2025-03-03 00:07:19',0,0.00),(40,'Test Clean','24242424','M',3,'2026-03-12 10:30:00','cancelled',1,'2025-03-03 21:18:47',69,259.00),(41,'TestPlz','25252525','M',2,'2025-03-11 13:30:00','cancelled',3,'2025-03-06 04:21:33',11,222.00),(42,'Test','23232323','F',2,'2025-03-03 10:50:00','open',4,'2025-03-06 05:11:33',0,0.00),(43,'wasdwasd wads','23232323','M',2,'2025-03-12 12:20:00','open',3,'2025-03-07 00:24:43',22,222.00),(44,'JANNA TEST','23232323','F',3,'2025-03-26 23:59:00','open',1,'2025-03-07 00:46:46',232,2323.00),(45,'Gert Tet','55555555','M',3,'2025-03-19 22:10:00','open',5,'2025-03-07 00:47:40',0,0.00);
/*!40000 ALTER TABLE `appointment` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-07  2:03:12
