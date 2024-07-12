package org.example.ch17.persistence.mapper;

import org.apache.ibatis.annotations.*;
import org.example.ch17.persistence.entity.Book;

@Mapper
public interface BookMapper {
    @Select("select * from books where id = #{id}")
    Book getBookById(int id);

    @Insert("insert into books(title, author, book_concern, publish_date, price)" +
            " values (#{title}, #{author}, #{bookConcern}, #{publishDate}, #{price})")
    // 在插入資料後，獲取自動增長的主鍵值
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int saveBook(Book book);

    @Update("update books set price= #{price} where id = #{id}")
    int updateBook(Book book);

    @Delete("delete from books where id = #{id}")
    int deleteBook(int id);
}
