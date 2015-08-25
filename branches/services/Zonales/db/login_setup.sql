CREATE DATABASE IF NOT EXISTS zonales;
USE zonales;

ALTER TABLE `zonales`.`jos_users`
	ADD COLUMN `email2` VARCHAR(100) AFTER `email`,
	ADD COLUMN `birthdate` date  NOT NULL AFTER `params`,
	ADD COLUMN `sex` char(1)  NOT NULL AFTER `birthdate`;

DROP TABLE IF EXISTS `zonales`.`jos_protocol_types`;
CREATE TABLE  `zonales`.`jos_protocol_types` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(45) NOT NULL,
  `function` varchar(255) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `zonales`.`jos_providers`;
CREATE TABLE  `zonales`.`jos_providers` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(45) NOT NULL,
  `discovery_url` varchar(255) default NULL,
  `parameters` varchar(255) default NULL,
  `protocol_type_id` int(11) NOT NULL,
  `description` varchar(45) default NULL,
  `observation` varchar(45) default NULL,
  `icon_url` varchar(255) NOT NULL,
  `access` int(11) NOT NULL,
  `prefix` varchar(50) NOT NULL,
  `suffix` varchar(50) NOT NULL,
  `required_input` varchar(255),
  PRIMARY KEY  (`id`),
unique (`name`),
  KEY `fk_jos_providers_jos_protocol_type` (`protocol_type_id`),
  CONSTRAINT `fk_jos_providers_jos_protocol_type` FOREIGN KEY (`protocol_type_id`) REFERENCES `jos_protocol_types` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Definition of table `zonales`.`jos_alias`
--

DROP TABLE IF EXISTS `zonales`.`jos_alias`;
CREATE TABLE  `zonales`.`jos_alias` (
  `user_id` int(11) NOT NULL,
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(255) NOT NULL,
  `provider_id` int(11) NOT NULL,
  `association_date` date NOT NULL,
  `block` tinyint(1) NOT NULL,
  `activation` varchar(100) NOT NULL,
unique (`name`),
  PRIMARY KEY  USING BTREE (`id`),
  KEY `fk_jos_aliases_jos_providers` (`provider_id`),
  CONSTRAINT `fk_jos_aliases_jos_users` FOREIGN KEY (`user_id`) REFERENCES `jos_users`(`id`);
  CONSTRAINT `fk_jos_aliases_jos_providers` FOREIGN KEY (`provider_id`) REFERENCES `jos_providers` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `jos_protocol_types` WRITE;
INSERT INTO `zonales`.`jos_protocol_types`(name,functionname) VALUES  ('OpenID',''),
 ('Twitter OAuth',''),
 ('Facebook Connect',''),
 ('Microsoft Passport',''),
 ('Email',''),
 ('Tradicional','');
UNLOCK TABLES;



LOCK TABLES `jos_groups` WRITE;
INSERT INTO `zonales`.`jos_groups` VALUES (3,'Guest');
UNLOCK TABLES;


LOCK TABLES `jos_providers` WRITE;
INSERT INTO `zonales`.`jos_providers` VALUES('name','discovery_url','parameters','protocol_type_id','description','observation','icon_url','access','prefix','suffix','required_input')
('Google','https://www.google.com/accounts/o8/id',NULL,1,NULL,NULL,'images/login/google.png',0,'','','::'),
 ('Yahoo','me.yahoo.com',NULL,1,NULL,NULL,'images/login/yahoo.png',0,'','','::'),
 ('OpenID',NULL,NULL,1,'','','images/login/openid.png',0,'','','text:username:ZONALES_PROVIDER_ENTER_ID'),
 ('Zonales',NULL,NULL,6,NULL,'','images/login/zonales.png',3,'','','text:username:ZONALES_PROVIDER_ENTER_USERNAME;password:password:ZONALES_PROVIDER_ENTER_PASSWORD'),
 ('ClaimID',NULL,NULL,1,NULL,NULL,'images/login/claimid.png',0,'http://claimid.com/','','text:username:ZONALES_PROVIDER_ENTER_USERNAME'),
 ('MyOpenID',NULL,NULL,1,NULL,NULL,'images/login/myopenid.png',0,'','.myopenid.com','text:username:ZONALES_PROVIDER_ENTER_USERNAME'),
 ('LiveJournal',NULL,NULL,1,NULL,NULL,'images/login/livejournal.png',0,'','.livejournal.com','text:username:ZONALES_PROVIDER_ENTER_USERNAME'),
 ('Flickr',NULL,NULL,1,NULL,NULL,'images/login/flickr.png',0,'www.flickr.com/','','text:username:ZONALES_PROVIDER_ENTER_USERNAME'),
 ('MySpace',NULL,'',1,NULL,NULL,'images/login/myspace.jpg',0,'www.myspace.com/','','text:username:ZONALES_PROVIDER_ENTER_PROFILE_NAME'),
 ('Aol',NULL,NULL,1,NULL,NULL,'images/login/aol.png',0,'openid.aol.com/','','text:username:ZONALES_PROVIDER_ENTER_USERNAME'),
 ('Orange','orange.fr',NULL,1,NULL,NULL,'images/login/orange.png',0,'','','::'),
('Wordpress',NULL,NULL,1,NULL,NULL,'images/login/wordpress.png',0,'','.wordpress.com','text:username:ZONALES_PROVIDER_ENTER_USERNAME'),
 ('Verisign',NULL,NULL,1,NULL,NULL,'images/login/verisign.png',0,'','.pip.verisignlabs.com','text:username:ZONALES_PROVIDER_ENTER_USERNAME');
UNLOCK TABLES;
