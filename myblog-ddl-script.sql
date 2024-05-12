DROP TABLE IF EXISTS `instruments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
-- CREATE TABLE instruments (
--     id BIGINT AUTO_INCREMENT PRIMARY KEY,
--     title VARCHAR(255) NULL,
--     description VARCHAR(255) NOT NULL,
--     content VARCHAR(255) NULL,
--     name VARCHAR(255) NOT NULL,
--     brand VARCHAR(255) NULL,
--     user_id VARCHAR(255) NULL,
--     is_available BOOLEAN DEFAULT TRUE,
--     ratings DOUBLE ,
--     instrument_condition VARCHAR(255) NULL,
--     created_at DATETIME NULL,
--     tags VARCHAR(255) NULL,
--     category_id BIGINT,
--     FOREIGN KEY (category_id) REFERENCES category(id)
-- );


-- CREATE TABLE instrument (
--     id BIGINT AUTO_INCREMENT PRIMARY KEY,
--     title VARCHAR(255),
--     description VARCHAR(255) NOT NULL,
--     content VARCHAR(255),
--     name VARCHAR(255) NOT NULL,
--     brand VARCHAR(255),
--     user_id VARCHAR(255),
--     is_available BOOLEAN DEFAULT TRUE,
--     ratings DOUBLE DEFAULT 0.0,
--     condition VARCHAR(255),
--     created_at DATETIME,
--     tags VARCHAR(255),
--     available_from_date DATE,
--     available_to_date DATE,
--     category_id BIGINT,
--     FOREIGN KEY (category_id) REFERENCES category(id)
-- );



DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKh4c7lvsc298whoyd4w9ta25cr` (`_id`),
  CONSTRAINT `FKh4c7lvsc298whoyd4w9ta25cr` FOREIGN KEY (`category_id`) REFERENCES `instrument` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

