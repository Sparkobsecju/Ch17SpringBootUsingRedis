package org.example.ch17.service;

import org.example.ch17.persistence.entity.Book;
import org.example.ch17.persistence.mapper.BookMapper;
import org.example.ch17.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService2 {
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private RedisUtil redisUtil;

    private static final String KEY_PREFIX = "book::";

    public Book getBookById(Integer id) {
        if (redisUtil.hasKey(KEY_PREFIX + id)) {
            return (Book) redisUtil.get(KEY_PREFIX + id);
        } else {
            Book book = bookMapper.getBookById(id);
            redisUtil.set(KEY_PREFIX + id, book);
            return book;
        }
    }

    public Book saveBook(Book book) {
        bookMapper.saveBook(book);
        redisUtil.set(KEY_PREFIX + book.getId(), book);
        return book;
    }

    public Book updateBook(Book book) {
        bookMapper.updateBook(book);
        redisUtil.set(KEY_PREFIX + book.getId(), book);
        return book;
    }

    public void deleteBook(Integer id) {
        redisUtil.remove(KEY_PREFIX + id);
        bookMapper.deleteBook(id);
    }
}













