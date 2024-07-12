package org.example.ch17.controller;

import org.example.ch17.persistence.entity.Book;
import org.example.ch17.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping
    public String saveBook(@RequestBody Book book) {
        Book resultBook = bookService.saveBook(book);
        return resultBook.toString();
    }

    @GetMapping("/{id}")
    public String getBookById(@PathVariable Integer id) {
        Book resultBook = bookService.getBookById(id);
        return resultBook.toString();
    }

    @PutMapping
    public String updateBook(@RequestBody Book book) {
        Book resultBook = bookService.updateBook(book);
        return resultBook.toString();
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Integer id) {
        bookService.deleteBook(id);
        return "刪除成功";
    }
}
