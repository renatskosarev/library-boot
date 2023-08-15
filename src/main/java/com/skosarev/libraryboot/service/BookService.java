package com.skosarev.libraryboot.service;

import com.skosarev.libraryboot.model.Book;
import com.skosarev.libraryboot.model.Person;
import com.skosarev.libraryboot.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {
    private static final int BOOKS_PER_PAGE = 5;
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllByPage(int page, Sort sort) {
        return bookRepository.findAll(PageRequest.of(page, BOOKS_PER_PAGE, sort)).getContent();
    }

    public int getTotalPages() {
        return bookRepository.findAll(PageRequest.of(1, BOOKS_PER_PAGE)).getTotalPages();
    }

    public Book get(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional
    public void create(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book book) {
        book.setId(id);
        bookRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void release(int id) {
        Book book = bookRepository.findById(id).get();
        book.setPerson(null);
        book.setTakenAt(null);
    }

    @Transactional
    public void assign(int id, Person person) {
        Book book = bookRepository.findById(id).get();
        book.setPerson(person);
        book.setTakenAt(new Date());
    }

    public List<Book> searchByTitle(String text) {
        return bookRepository.findByTitleContaining(text);
    }
}
