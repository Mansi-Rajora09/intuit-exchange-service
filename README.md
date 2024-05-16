# The source code of this project is upgraded to Spring Boot 3.2.1 and jwt 0.12.3



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

ALTER TABLE category AUTO_INCREMENT = 1;


INSERT INTO category (id,description, name, created_at, updated_at) 
VALUES 
(1,'String instrument with four strings played with a bow', 'Violin', NOW(), NOW()),
(2,'Percussion instrument consisting of a skin stretched over a wooden or metal shell', 'Drums', NOW(), NOW()),
(3,'String instrument played by plucking its strings with the fingers or a plectrum', 'Bass Guitar', NOW(), NOW()),
(4,'Brass instrument with a cup-shaped mouthpiece and a flared bell', 'Trumpet', NOW(), NOW()),
(5,'Woodwind instrument with a reed mouthpiece and a straight conical tube', 'Clarinet', NOW(), NOW()),
(6,'String instrument played with a bow, with a lower pitch than a violin', 'Cello', NOW(), NOW()),
(7,'Percussion instrument consisting of a series of metal bars struck with mallets', 'Xylophone', NOW(), NOW()),
(8,'String instrument with a pear-shaped body and a fretted neck', 'Mandolin', NOW(), NOW());

ALTER TABLE instruments AUTO_INCREMENT = 1;


INSERT INTO instruments (id,description, name, created_at, updated_at, category_id, user_id, ratings, brand, content, instrument_condition, is_available, tags, title, available_from_date, available_to_date, limit_value, instrument_image, instrument_video) 
VALUES 

(1,'Professional violin by Stradivarius', 'Stradivarius Violin', NOW(), NOW(), 1, 1, 4.5, 'Stradivarius', 'Wood', 'New', 1, 'string, violin, classical', 'Professional Violin', NOW(), NOW(), 10, NULL, NULL),
(2,'Student-level violin suitable for beginners', 'Student Violin', NOW(), NOW(), 1, 1, 3.8, 'Student', 'Wood', 'Used', 1, 'string, violin, student', 'Beginner Violin', NOW(), NOW(), 5, NULL, NULL),
(3,'Electric violin with built-in pickups for amplification', 'Electric Violin', NOW(), NOW(), 1, 1, 4.2, 'Yamaha', 'Wood, Electric components', 'Like New', 1, 'string, violin, electric', 'Electric Violin', NOW(), NOW(), 8, NULL, NULL),


(4,'Full drum kit including bass drum, snare drum, tom-toms, and cymbals', 'Full Drum Kit', NOW(), NOW(), 2, 1, 4.0, 'Pearl', 'Wood, Metal', 'Used', 1, 'percussion, drum kit, full set', 'Full Drum Kit', NOW(), NOW(), 5, NULL, NULL),
(5,'Portable drum set suitable for small venues or practice sessions', 'Compact Drum Set', NOW(), NOW(), 2, 1, 4.3, 'Ludwig', 'Wood, Metal', 'Like New', 1, 'percussion, drum kit, portable', 'Compact Drum Set', NOW(), NOW(), 8, NULL, NULL),
(6,'Electronic drum kit with programmable drum sounds and built-in metronome', 'Electronic Drum Set', NOW(), NOW(), 2, 1, 4.5, 'Roland', 'Plastic, Metal', 'New', 1, 'percussion, drum kit, electronic', 'Electronic Drum Set', NOW(), NOW(), 3, NULL, NULL),


(7,'4-string bass guitar suitable for rock and pop music', '4-String Bass Guitar', NOW(), NOW(), 3, 1, 4.2, 'Fender', 'Wood, Metal', 'Used', 1, 'bass, guitar, 4-string', '4-String Bass Guitar', NOW(), NOW(), 7, NULL, NULL),
(8,'5-string bass guitar with extended low-end range', '5-String Bass Guitar', NOW(), NOW(), 3, 1, 4.3, 'Ibanez', 'Wood, Metal', 'Like New', 1, 'bass, guitar, 5-string', '5-String Bass Guitar', NOW(), NOW(), 5, NULL, NULL),
(9,'Fretless bass guitar for smooth, sliding bass lines', 'Fretless Bass Guitar', NOW(), NOW(), 3, 1, 4.0, 'Music Man', 'Wood, Metal', 'Used', 1, 'bass, guitar, fretless', 'Fretless Bass Guitar', NOW(), NOW(), 3, NULL, NULL),

(10,'Bb trumpet with a bright, powerful sound', 'Bb Trumpet', NOW(), NOW(), 4, 1, 4.5, 'Yamaha', 'Brass', 'New', 1, 'trumpet, brass, Bb', 'Bb Trumpet', NOW(), NOW(), 6, NULL, NULL),
(11,'C trumpet with a slightly higher pitch than Bb trumpet', 'C Trumpet', NOW(), NOW(), 4, 1, 4.3, 'Bach', 'Brass', 'Used', 1, 'trumpet, brass, C', 'C Trumpet', NOW(), NOW(), 4, NULL, NULL),
(12,'Pocket trumpet for portability and unique sound', 'Pocket Trumpet', NOW(), NOW(), 4, 1, 4.1, 'Mendini', 'Brass', 'Like New', 1, 'trumpet, brass, pocket', 'Pocket Trumpet', NOW(), NOW(), 3, NULL, NULL),
 
(13,'Standard Bb clarinet suitable for beginners and intermediate players', 'Bb Clarinet', NOW(), NOW(), 5, 1, 4.2, 'Buffet Crampon', 'Wood, Plastic', 'Used', 1, 'clarinet, woodwind, Bb', 'Bb Clarinet', NOW(), NOW(), 8, NULL, NULL),
(14,'Professional-level A clarinet with improved intonation and tone quality', 'A Clarinet', NOW(), NOW(), 5, 1, 4.6, 'Yamaha', 'Wood', 'New', 1, 'clarinet, woodwind, A', 'A Clarinet', NOW(), NOW(), 5, NULL, NULL),
(15,'Eb clarinet for orchestral and chamber music performance', 'Eb Clarinet', NOW(), NOW(), 5, 1, 4.4, 'Selmer', 'Wood', 'Like New', 1, 'clarinet, woodwind, Eb', 'Eb Clarinet', NOW(), NOW(), 4, NULL, NULL);




