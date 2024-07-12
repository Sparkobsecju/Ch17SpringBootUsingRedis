CREATE DATABASE  IF NOT EXISTS springboot;

USE springboot;

DROP TABLE IF EXISTS category;

CREATE TABLE category (
  id smallint(6) NOT NULL,
  name varchar(50) NOT NULL COMMENT '分类名称',
  root tinyint(1) DEFAULT NULL COMMENT '是否根分类',
  parent_id smallint(6) DEFAULT NULL COMMENT '父分类的ID',
  PRIMARY KEY (id),
  KEY CATEGORY_PARENT_ID (parent_id),
  CONSTRAINT CATEGORY_PARENT_ID FOREIGN KEY (parent_id) REFERENCES category (id)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS books;

CREATE TABLE books (
  id int(11) NOT NULL AUTO_INCREMENT,
  title varchar(50) NOT NULL COMMENT '书名',
  author varchar(50) NOT NULL COMMENT '作者',
  book_concern varchar(100) NOT NULL COMMENT '出版社',
  publish_date date NOT NULL COMMENT '出版日期',
  price float(6,2) NOT NULL COMMENT '价格',
  category_id smallint(6) DEFAULT NULL COMMENT '图书分类，外键',
  PRIMARY KEY (id),
  KEY FK_CATEGORY_ID (category_id),
  KEY INDEX_TITLE (title),
  CONSTRAINT FK_CATEGORY_ID FOREIGN KEY (category_id) REFERENCES category (id)
) ENGINE=InnoDB;



CREATE TABLE category_sequence (
   value INT NOT NULL
) ENGINE = InnoDB;

insert into category_sequence values(0);

CREATE TABLE id_sequence (
   table_name                     varchar(30),
   current_id                     int
) ENGINE = InnoDB;



