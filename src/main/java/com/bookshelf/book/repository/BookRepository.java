package com.bookshelf.book.repository;

import com.bookshelf.book.domain.Book;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository {

    void save(Book book);

    Book getById(Long id);

    void deleteAll();

    Long count();
}
