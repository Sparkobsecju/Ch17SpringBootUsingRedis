CREATE DATABASE IF NOT EXISTS `sb_bookstore` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

USE `sb_bookstore`;

/* `category` 表的结构 */
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` smallint(6) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `root` tinyint(1) DEFAULT NULL,
  `parent_id` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `CATEGORY_PARENT_ID` (`parent_id`),
  CONSTRAINT `CATEGORY_PARENT_ID` FOREIGN KEY (`parent_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/* `books` 表的结构 */
DROP TABLE IF EXISTS `books`;
CREATE TABLE `books` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL,
  `author` varchar(50) NOT NULL,
  `book_concern` varchar(100)  NOT NULL COMMENT '出版社',
  `publish_date` date NOT NULL COMMENT '出版日期',
  `price` float(6,2) NOT NULL,
  `discount` float(3,2) DEFAULT NULL,
  `inventory` int(11) NOT NULL COMMENT '库存',
  `brief` varchar(500) DEFAULT NULL COMMENT '简介',
  `detail` text COMMENT '详情',
  `category_id` smallint(6) DEFAULT NULL COMMENT '图书分类，外键',
  `is_new` tinyint(1) DEFAULT NULL COMMENT '是否新书',
  `is_hot` tinyint(1) DEFAULT NULL COMMENT '是否热门书',
  `is_special_offer` tinyint(1) DEFAULT NULL COMMENT '是否特价',
  `img` varchar(250) DEFAULT NULL COMMENT '图书小图片',
  `img_big` varchar(250) DEFAULT NULL COMMENT '图书大图片',
  `slogan` varchar(300) DEFAULT NULL COMMENT '图书宣传语',
  PRIMARY KEY (`id`),
  KEY `FK_CATEGORY_ID` (`category_id`),
  KEY `INDEX_TITLE` (`title`),
  CONSTRAINT `FK_CATEGORY_ID` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


/* `comment`表的结构 */
DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(1000) NOT NULL,
  `comment_date` datetime NOT NULL,
  `book_id` int(11) DEFAULT NULL,
  `username` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_BOOK_ID` (`book_id`),
  CONSTRAINT `FK_BOOK_ID` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



/* `users`表的结构 */
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(512) NOT NULL,
  `mobile` varchar(20) NOT NULL,
  `enabled`     tinyint(1) not null default '1',
  `locked`      tinyint(1) not null default '0',
  `roles`       varchar(500),
  PRIMARY KEY (`id`),
  UNIQUE KEY `INDEX_USERNAME` (`username`),
  UNIQUE KEY `INDEX_MOBILE` (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;





