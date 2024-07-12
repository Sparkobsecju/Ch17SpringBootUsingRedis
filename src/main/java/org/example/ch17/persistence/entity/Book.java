package org.example.ch17.persistence.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
public class Book implements Serializable {
    private static final long serialVersionUID = -3683048489314021339L;
    private Long id; // 主鍵
    private String title; // 書名
    private String author; // 作者
    private String bookConcern; // 出版社
    private Date publishDate; // 出版日期
    private Double price; // 價格
}
