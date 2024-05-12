
DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
 CREATE TABLE `category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `exchange_info`;
CREATE TABLE `exchange_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `borrow_user_id` bigint(20) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `from_date` datetime(6) DEFAULT NULL,
  `instrument_id` bigint(20) DEFAULT NULL,
  `renewals` int(11) DEFAULT NULL,
  `return_date` datetime(6) DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `exchange_type` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `exchange_info_history`;
CREATE TABLE `exchange_info_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `borrow_user_id` bigint(20) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `exchange_type` varchar(255) DEFAULT NULL,
  `from_date` datetime(6) DEFAULT NULL,
  `instrument_id` bigint(20) DEFAULT NULL,
  `renewals` int(11) DEFAULT NULL,
  `return_date` datetime(6) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ;

DROP TABLE IF EXISTS `instruments`;
CREATE TABLE `instruments` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `brand` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) NOT NULL,
  `instrument_condition` varchar(255) DEFAULT NULL,
  `is_available` bit(1) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `ratings` double DEFAULT NULL,
  `tags` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  `available_from_date` datetime(6) DEFAULT NULL,
  `available_to_date` datetime(6) DEFAULT NULL,
  `limit_value` int(11) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK8p3uvltus38e24d331s4ffu46` (`title`),
  KEY `FK58afiainejeaj35kaibjgkjrn` (`category_id`),
  CONSTRAINT `FK58afiainejeaj35kaibjgkjrn` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci




