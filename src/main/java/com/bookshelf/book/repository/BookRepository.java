package com.bookshelf.book.repository;

import com.bookshelf.book.domain.Book;
import com.bookshelf.book.dto.request.BookSearch;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository {

    void save(Book book);

    Book getById(Long id);

    List<Book> findForMember(Long memberId, BookSearch bookSearch);

    List<Book> findByLatest(BookSearch bookSearch);
    List<Book> findByLikes(BookSearch bookSearch);

    List<Book> findBookmarkedBooks(Long memberId, BookSearch bookSearch);

    void deleteAll();

    Long count();

    void saveAll(List<Book> bookList);
}
