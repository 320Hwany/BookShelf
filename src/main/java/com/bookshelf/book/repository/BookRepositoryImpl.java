package com.bookshelf.book.repository;

import com.bookshelf.book.domain.Book;
import com.bookshelf.book.domain.QBook;
import com.bookshelf.book.dto.request.BookSearch;
import com.bookshelf.book.exception.BookNotFoundException;
import com.bookshelf.member.domain.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.bookshelf.book.domain.QBook.book;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BookRepositoryImpl implements BookRepository {

    private final BookJpaRepository bookJpaRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public void save(Book book) {
        bookJpaRepository.save(book);
    }

    @Override
    public Book getById(Long id) {
        Book book = jpaQueryFactory.selectFrom(QBook.book)
                .leftJoin(QBook.book.member, QMember.member)
                .fetchJoin()
                .where(QBook.book.id.eq(id))
                .fetchOne();

        if (book == null) {
            throw new BookNotFoundException();
        }
        return book;
    }

    @Override
    public List<Book> findByLatest(BookSearch bookSearch) {
        return jpaQueryFactory.selectFrom(book)
                .leftJoin(book.member, QMember.member)
                .fetchJoin()
                .limit(bookSearch.getLimit())
                .offset(bookSearch.getOffset())
                .orderBy(book.id.desc())
                .fetch();
    }

    @Override
    public List<Book> findByLikes(BookSearch bookSearch) {
        return jpaQueryFactory.selectFrom(book)
                .leftJoin(book.member, QMember.member)
                .fetchJoin()
                .limit(bookSearch.getLimit())
                .offset(bookSearch.getOffset())
                .orderBy(book.likes.desc())
                .fetch();
    }

    @Override
    public List<Book> findBookmarkedBooks(Long id, BookSearch bookSearch) {
        return jpaQueryFactory.selectFrom(book)
                .where(book.bookMark.eq(true))
                .where(book.member.id.eq(id))
                .leftJoin(book.member, QMember.member)
                .fetchJoin()
                .limit(bookSearch.getLimit())
                .offset(bookSearch.getOffset())
                .orderBy(book.id.desc())
                .fetch();
    }

    @Override
    public void deleteAll() {
        bookJpaRepository.deleteAll();
    }

    @Override
    public Long count() {
        return bookJpaRepository.count();
    }

    @Override
    public void saveAll(List<Book> bookList) {
        bookJpaRepository.saveAll(bookList);
    }
}
