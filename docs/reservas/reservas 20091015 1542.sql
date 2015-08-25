-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.37


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema services
--

CREATE DATABASE IF NOT EXISTS services;
USE services;

--
-- Definition of table `jos_cities`
--

DROP TABLE IF EXISTS `jos_cities`;
CREATE TABLE `jos_cities` (
  `city_id` int(11) NOT NULL,
  `city` varchar(90) NOT NULL,
  PRIMARY KEY (`city_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jos_cities`
--

/*!40000 ALTER TABLE `jos_cities` DISABLE KEYS */;
INSERT INTO `jos_cities` (`city_id`,`city`) VALUES 
 (1,'Puerto Madryn');
/*!40000 ALTER TABLE `jos_cities` ENABLE KEYS */;


--
-- Definition of table `jos_events`
--

DROP TABLE IF EXISTS `jos_events`;
CREATE TABLE `jos_events` (
  `event_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`event_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jos_events`
--

/*!40000 ALTER TABLE `jos_events` DISABLE KEYS */;
/*!40000 ALTER TABLE `jos_events` ENABLE KEYS */;


--
-- Definition of table `jos_locations`
--

DROP TABLE IF EXISTS `jos_locations`;
CREATE TABLE `jos_locations` (
  `location_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  `street` varchar(90) NOT NULL,
  `number` int(11) DEFAULT NULL,
  `supplier_id` int(11) NOT NULL,
  PRIMARY KEY (`location_id`),
  KEY `fk_jos_locations1` (`supplier_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jos_locations`
--

/*!40000 ALTER TABLE `jos_locations` DISABLE KEYS */;
INSERT INTO `jos_locations` (`location_id`,`name`,`street`,`number`,`supplier_id`) VALUES 
 (1,'Restaurante Mediterraneo','Bv. Brown',256,1);
/*!40000 ALTER TABLE `jos_locations` ENABLE KEYS */;


--
-- Definition of table `jos_phones`
--

DROP TABLE IF EXISTS `jos_phones`;
CREATE TABLE `jos_phones` (
  `phone_id` int(11) NOT NULL AUTO_INCREMENT,
  `supplier_id` int(11) NOT NULL,
  `phone` varchar(20) NOT NULL,
  PRIMARY KEY (`phone_id`),
  UNIQUE KEY `uk_phone` (`supplier_id`,`phone`),
  KEY `fk_phones1` (`supplier_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jos_phones`
--

/*!40000 ALTER TABLE `jos_phones` DISABLE KEYS */;
/*!40000 ALTER TABLE `jos_phones` ENABLE KEYS */;


--
-- Definition of table `jos_professionals`
--

DROP TABLE IF EXISTS `jos_professionals`;
CREATE TABLE `jos_professionals` (
  `professional_id` int(11) NOT NULL AUTO_INCREMENT,
  `enrollment` varchar(45) DEFAULT NULL,
  `jos_users_id` int(11) NOT NULL,
  `resource_id` int(11) NOT NULL,
  PRIMARY KEY (`professional_id`),
  KEY `fk_jos_professionals1` (`resource_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jos_professionals`
--

/*!40000 ALTER TABLE `jos_professionals` DISABLE KEYS */;
/*!40000 ALTER TABLE `jos_professionals` ENABLE KEYS */;


--
-- Definition of table `jos_professionals_has_jos_specialties`
--

DROP TABLE IF EXISTS `jos_professionals_has_jos_specialties`;
CREATE TABLE `jos_professionals_has_jos_specialties` (
  `professional_id` int(11) NOT NULL,
  `id_specialty` int(11) NOT NULL,
  PRIMARY KEY (`professional_id`,`id_specialty`),
  KEY `fk_jos_professionals_has_jos_specialties1` (`professional_id`),
  KEY `fk_jos_professionals_has_jos_specialties2` (`id_specialty`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jos_professionals_has_jos_specialties`
--

/*!40000 ALTER TABLE `jos_professionals_has_jos_specialties` DISABLE KEYS */;
/*!40000 ALTER TABLE `jos_professionals_has_jos_specialties` ENABLE KEYS */;


--
-- Definition of table `jos_reserve`
--

DROP TABLE IF EXISTS `jos_reserve`;
CREATE TABLE `jos_reserve` (
  `reserve_id` int(11) NOT NULL AUTO_INCREMENT,
  `jos_users_id` int(11) NOT NULL,
  `datetime_reserve` datetime NOT NULL COMMENT 'Fecha y hora en la que se cargó la reserva.',
  `datetime_realization` datetime NOT NULL COMMENT 'Fecha y hora de la reserva propiamente dicha.',
  `duration` time NOT NULL,
  `expiry` datetime NOT NULL,
  PRIMARY KEY (`reserve_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jos_reserve`
--

/*!40000 ALTER TABLE `jos_reserve` DISABLE KEYS */;
INSERT INTO `jos_reserve` (`reserve_id`,`jos_users_id`,`datetime_reserve`,`datetime_realization`,`duration`,`expiry`) VALUES 
 (1,1,'2009-07-25 13:00:00','2009-08-01 18:00:00','04:00:00','2009-08-01 18:00:00');
/*!40000 ALTER TABLE `jos_reserve` ENABLE KEYS */;


--
-- Definition of table `jos_reserve_has_jos_resources`
--

DROP TABLE IF EXISTS `jos_reserve_has_jos_resources`;
CREATE TABLE `jos_reserve_has_jos_resources` (
  `reserve_id` int(11) NOT NULL,
  `resource_id` int(11) NOT NULL,
  PRIMARY KEY (`reserve_id`,`resource_id`),
  KEY `fk_jos_reserve_has_jos_resources1` (`reserve_id`),
  KEY `fk_jos_reserve_has_jos_resources2` (`resource_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jos_reserve_has_jos_resources`
--

/*!40000 ALTER TABLE `jos_reserve_has_jos_resources` DISABLE KEYS */;
INSERT INTO `jos_reserve_has_jos_resources` (`reserve_id`,`resource_id`) VALUES 
 (1,2),
 (1,3),
 (1,4);
/*!40000 ALTER TABLE `jos_reserve_has_jos_resources` ENABLE KEYS */;


--
-- Definition of table `jos_resource_types`
--

DROP TABLE IF EXISTS `jos_resource_types`;
CREATE TABLE `jos_resource_types` (
  `resource_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`resource_type_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jos_resource_types`
--

/*!40000 ALTER TABLE `jos_resource_types` DISABLE KEYS */;
INSERT INTO `jos_resource_types` (`resource_type_id`,`name`,`description`) VALUES 
 (1,'mesasResto','Mesas de restaurante'),
 (2,'butacasTeatro','Butacas de un teatro');
/*!40000 ALTER TABLE `jos_resource_types` ENABLE KEYS */;


--
-- Definition of table `jos_resources`
--

DROP TABLE IF EXISTS `jos_resources`;
CREATE TABLE `jos_resources` (
  `resource_id` int(11) NOT NULL AUTO_INCREMENT,
  `capacidad` tinyint(4) DEFAULT NULL,
  `name` varchar(45) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `group_id` int(11) NOT NULL,
  PRIMARY KEY (`resource_id`),
  KEY `fk_jos_resources1` (`group_id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jos_resources`
--

/*!40000 ALTER TABLE `jos_resources` DISABLE KEYS */;
INSERT INTO `jos_resources` (`resource_id`,`capacidad`,`name`,`description`,`group_id`) VALUES 
 (1,4,'Mesa 1','Mesa 1 del salon principal de restaurante mediterraneo',1),
 (2,4,'Mesa 2','Mesa 2 del salon principal de restaurante mediterraneo',1),
 (3,4,'Mesa 3','Mesa 3 del salon principal de restaurante mediterraneo',1),
 (4,4,'Mesa 4','Mesa 4 del salon principal de restaurante mediterraneo',1),
 (5,4,'Mesa 5','Mesa 5 del salon principal de restaurante mediterraneo',1),
 (6,4,'Mesa 6','Mesa 6 del salon principal de restaurante mediterraneo',1),
 (7,4,'Mesa 7','Mesa 7 del salon principal de restaurante mediterraneo',1);
/*!40000 ALTER TABLE `jos_resources` ENABLE KEYS */;


--
-- Definition of table `jos_resources_group`
--

DROP TABLE IF EXISTS `jos_resources_group`;
CREATE TABLE `jos_resources_group` (
  `group_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(90) NOT NULL,
  `visual_config` text,
  `location_id` int(11) NOT NULL,
  `resource_type_id` int(11) NOT NULL,
  `expiration` int(11) NOT NULL,
  PRIMARY KEY (`group_id`),
  KEY `fk_jos_resources_group1` (`location_id`),
  KEY `fk_jos_resources_group2` (`resource_type_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jos_resources_group`
--

/*!40000 ALTER TABLE `jos_resources_group` DISABLE KEYS */;
INSERT INTO `jos_resources_group` (`group_id`,`name`,`visual_config`,`location_id`,`resource_type_id`,`expiration`) VALUES 
 (1,'Salon principal','{\n    \"location\": {\n        \"CustomResource\": [\n            {   \"type\": \"POLYGON\",\n                \"points\": [ {\"x\": 0},{\"y\":190},\n                        {\"x\": 71},{\"y\":172},\n                        {\"x\": 110},{\"y\":178},\n                        {\"x\": 113},{\"y\":223},\n                        {\"x\": 0},{\"y\":266}\n                        ],\n                \"fill\": 0,\n                \"stroke\": 0,\n                \"nroRecurso\": 1,\n                \"onMouseClicked\": \"mousePressed\"\n            },\n            {   \"type\": \"POLYGON\",\n                \"points\": [ {\"x\": 128},{\"y\":161},\n                        {\"x\": 165},{\"y\":147},\n                        {\"x\": 213},{\"y\":151},\n                        {\"x\": 211},{\"y\":161},\n                        {\"x\": 171},{\"y\":172},\n                        {\"x\": 128},{\"y\":171}\n                        ],\n                \"fill\": 0,\n                \"stroke\": 0,\n                \"nroRecurso\": 2,\n                \"onMouseClicked\": \"mousePressed\"\n            },\n            {   \"type\": \"POLYGON\",\n                \"points\": [ {\"x\": 210},{\"y\":141},\n                        {\"x\": 246},{\"y\":131},\n                        {\"x\": 279},{\"y\":135},\n                        {\"x\": 246},{\"y\":151},\n                        {\"x\": 210},{\"y\":147}\n                        ],\n                \"fill\": 0,\n                \"stroke\": 0,\n                \"nroRecurso\": 3,\n                \"onMouseClicked\": \"mousePressed\"\n            },\n            {   \"type\": \"POLYGON\",\n                \"points\": [ {\"x\": 342},{\"y\":135},\n                        {\"x\": 355},{\"y\":130},\n                        {\"x\": 399},{\"y\":128},\n                        {\"x\": 138},{\"y\":394}\n                        ],\n                \"fill\": 0,\n                \"stroke\": 0,\n                \"nroRecurso\": 4,\n                \"onMouseClicked\": \"mousePressed\"\n            },\n            {   \"type\": \"POLYGON\",\n                \"points\": [ {\"x\": 271},{\"y\":127},\n                        {\"x\": 280},{\"y\":123},\n                        {\"x\": 310},{\"y\":124},\n                        {\"x\": 312},{\"y\":131},\n                        {\"x\": 299},{\"y\":135}\n                        ],\n                \"fill\": 0,\n                \"stroke\": 0,\n                \"nroRecurso\": 5,\n                \"onMouseClicked\": \"mousePressed\"\n            },\n            {   \"type\": \"POLYGON\",\n                \"points\": [ {\"x\": 450},{\"y\":137},\n                        {\"x\": 450},{\"y\":130},\n                        {\"x\": 507},{\"y\":128},\n                        {\"x\": 526},{\"y\":136}\n                        ],\n                \"fill\": 0,\n                \"stroke\": 0,\n                \"nroRecurso\": 6,\n                \"onMouseClicked\": \"mousePressed\"\n            },\n            {   \"type\": \"ELLIPSE\",\n                \"x\": 377,\n                \"y\": 172,\n                \"radX\": 90,\n                \"radY\": 20,\n                \"fill\": 0,\n                \"stroke\": 0,\n                \"nroRecurso\": 7,\n                \"onMouseClicked\": \"mousePressed\"\n            },\n        ]\n    }\n}\n',1,1,0);
/*!40000 ALTER TABLE `jos_resources_group` ENABLE KEYS */;


--
-- Definition of table `jos_slots`
--

DROP TABLE IF EXISTS `jos_slots`;
CREATE TABLE `jos_slots` (
  `slot_id` int(11) NOT NULL AUTO_INCREMENT,
  `day` varchar(20) NOT NULL,
  `hour_from` time NOT NULL,
  `hour_to` time NOT NULL,
  `max_duration` time NOT NULL,
  `min_duration` time NOT NULL,
  `steep` time NOT NULL,
  `tolerance` time NOT NULL,
  `alias` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`slot_id`)
) ENGINE=MyISAM AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jos_slots`
--

/*!40000 ALTER TABLE `jos_slots` DISABLE KEYS */;
INSERT INTO `jos_slots` (`slot_id`,`day`,`hour_from`,`hour_to`,`max_duration`,`min_duration`,`steep`,`tolerance`,`alias`) VALUES 
 (1,'lunes','21:00:00','02:00:00','05:00:00','05:00:00','00:00:00','00:00:00','cena'),
 (2,'domingo','07:00:00','11:00:00','04:00:00','04:00:00','00:00:00','00:00:00','desayuno'),
 (3,'miercoles','21:00:00','02:00:00','05:00:00','05:00:00','00:00:00','00:00:00','cena'),
 (4,'jueves','21:00:00','02:00:00','05:00:00','05:00:00','00:00:00','00:00:00','cena'),
 (5,'viernes','21:00:00','02:00:00','05:00:00','05:00:00','00:00:00','00:00:00','cena'),
 (6,'sabado','21:00:00','02:00:00','05:00:00','05:00:00','00:00:00','00:00:00','cena'),
 (7,'domingo','21:00:00','02:00:00','05:00:00','05:00:00','00:00:00','00:00:00','cena'),
 (8,'lunes','07:00:00','11:00:00','04:00:00','04:00:00','00:00:00','00:00:00','desayuno'),
 (13,'sabado','07:00:00','11:00:00','04:00:00','04:00:00','00:00:00','00:00:00','desayuno'),
 (10,'miercoles','07:00:00','11:00:00','04:00:00','04:00:00','00:00:00','00:00:00','desayuno'),
 (11,'jueves','07:00:00','11:00:00','04:00:00','04:00:00','00:00:00','00:00:00','desayuno'),
 (12,'viernes','07:00:00','11:00:00','04:00:00','04:00:00','00:00:00','00:00:00','desayuno'),
 (15,'lunes','12:00:00','16:00:00','04:00:00','04:00:00','00:00:00','00:00:00','almuerzo'),
 (16,'miercoles','12:00:00','16:00:00','04:00:00','04:00:00','00:00:00','00:00:00','almuerzo'),
 (17,'jueves','12:00:00','16:00:00','04:00:00','04:00:00','00:00:00','00:00:00','almuerzo'),
 (18,'viernes','12:00:00','16:00:00','04:00:00','04:00:00','00:00:00','00:00:00','almuerzo'),
 (14,'sabado','12:00:00','16:00:00','04:00:00','04:00:00','00:00:00','00:00:00','almuerzo'),
 (9,'domingo','12:00:00','16:00:00','04:00:00','04:00:00','00:00:00','00:00:00','almuerzo');
/*!40000 ALTER TABLE `jos_slots` ENABLE KEYS */;


--
-- Definition of table `jos_slots_has_jos_resources_group`
--

DROP TABLE IF EXISTS `jos_slots_has_jos_resources_group`;
CREATE TABLE `jos_slots_has_jos_resources_group` (
  `slot_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  `date_from` date NOT NULL,
  `date_to` date NOT NULL,
  `event_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`slot_id`,`group_id`),
  KEY `fk_jos_slots_has_jos_resources_group1` (`slot_id`),
  KEY `fk_jos_slots_has_jos_resources_group2` (`group_id`),
  KEY `fk_jos_slots_has_jos_resources_group3` (`event_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jos_slots_has_jos_resources_group`
--

/*!40000 ALTER TABLE `jos_slots_has_jos_resources_group` DISABLE KEYS */;
INSERT INTO `jos_slots_has_jos_resources_group` (`slot_id`,`group_id`,`date_from`,`date_to`,`event_id`) VALUES 
 (1,1,'2009-07-01','2010-08-01',0),
 (2,1,'2009-07-01','2010-08-01',0),
 (3,1,'2009-07-01','2010-08-01',0),
 (4,1,'2009-07-01','2010-08-01',0),
 (5,1,'2009-07-01','2010-08-01',0),
 (6,1,'2009-07-01','2010-08-01',0),
 (7,1,'2009-07-01','2010-08-01',0),
 (8,1,'2009-07-01','2010-08-01',0),
 (9,1,'2009-07-01','2010-08-01',0),
 (10,1,'2009-07-01','2009-07-02',0),
 (11,1,'2009-07-01','2010-08-01',0),
 (12,1,'2009-07-01','2010-08-01',0),
 (13,1,'2009-07-01','2010-08-01',0),
 (14,1,'2009-07-01','2010-08-01',0),
 (15,1,'2009-07-01','2010-08-01',0),
 (16,1,'2009-07-01','2010-08-01',0),
 (17,1,'2009-07-01','2010-08-01',0),
 (18,1,'2009-07-01','2010-08-01',0);
/*!40000 ALTER TABLE `jos_slots_has_jos_resources_group` ENABLE KEYS */;


--
-- Definition of table `jos_specialties`
--

DROP TABLE IF EXISTS `jos_specialties`;
CREATE TABLE `jos_specialties` (
  `specialty_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`specialty_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jos_specialties`
--

/*!40000 ALTER TABLE `jos_specialties` DISABLE KEYS */;
/*!40000 ALTER TABLE `jos_specialties` ENABLE KEYS */;


--
-- Definition of table `jos_suppliers`
--

DROP TABLE IF EXISTS `jos_suppliers`;
CREATE TABLE `jos_suppliers` (
  `supplier_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  `street` varchar(90) NOT NULL,
  `number` int(11) DEFAULT NULL,
  `city_id` int(11) NOT NULL,
  PRIMARY KEY (`supplier_id`),
  KEY `fk_jos_suppliers1` (`city_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jos_suppliers`
--

/*!40000 ALTER TABLE `jos_suppliers` DISABLE KEYS */;
INSERT INTO `jos_suppliers` (`supplier_id`,`name`,`street`,`number`,`city_id`) VALUES 
 (1,'Pepito Flores','Mitre',585,1);
/*!40000 ALTER TABLE `jos_suppliers` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
