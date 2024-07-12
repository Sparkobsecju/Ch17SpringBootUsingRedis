package org.example.ch17.service;

import org.example.ch17.persistence.entity.Book;
import org.example.ch17.persistence.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "book")
public class BookService {
    @Autowired
    private BookMapper bookMapper;

    @Cacheable
    public Book getBookById(Integer id) {
        System.out.println("getBookById: " + id);
        return bookMapper.getBookById(id);
    }

    @CachePut(key = "#result.id")
    public Book saveBook(Book book) {
        System.out.println("saveBook: " + book);
        bookMapper.saveBook(book);
        return book;
    }

    @CachePut(key = "#result.id")
    public Book updateBook(Book book) {
        System.out.println("updateBook: " + book);
        bookMapper.updateBook(book);
        return book;
    }

    @CacheEvict(beforeInvocation = true)
    public void deleteBook(Integer id) {
        System.out.println("deleteBook: " + id);
        bookMapper.deleteBook(id);
    }
}
