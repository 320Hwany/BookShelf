package com.bookshelf.book.repository;

import com.bookshelf.book.domain.Book;
import com.bookshelf.book.exception.BookNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class BookRepositoryImpl implements BookRepository {

    private final BookJpaRepository bookJpaRepository;

    @Override
    public void save(Book book) {
        bookJpaRepository.save(book);
    }

    @Override
    public Book getById(Long id) {
        Book book = bookJpaRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        return book;
    }

    @Override
    public void deleteAll() {
        bookJpaRepository.deleteAll();
    }

    @Override
    public Long count() {
        return bookJpaRepository.count();
    }
}
