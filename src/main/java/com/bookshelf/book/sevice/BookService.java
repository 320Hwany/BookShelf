package com.bookshelf.book.sevice;

import com.bookshelf.book.domain.Book;
import com.bookshelf.book.dto.request.BookSave;
import com.bookshelf.book.dto.request.BookSearch;
import com.bookshelf.book.dto.request.BooksLimit;
import com.bookshelf.book.dto.request.CreateLikesAndBookmark;
import com.bookshelf.book.dto.response.BookResponse;
import com.bookshelf.book.repository.BookRepository;
import com.bookshelf.member.domain.Member;
import com.bookshelf.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final CreateLikesAndBookmark createLikesAndBookmark;

    public Book getById(Long id) {
        Book book = bookRepository.getById(id);
        return book;
    }

    public List<BookResponse> findBookResponsesByLatest(Integer page) {
        BookSearch bookSearch = BookSearch.builder()
                .page(page)
                .booksLimit(new BooksLimit())
                .build();

        return bookRepository.findByLatest(bookSearch).stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }

    public List<BookResponse> findBookResponsesByLikes(Integer page) {
        BookSearch bookSearch = BookSearch.builder()
                .page(page)
                .booksLimit(new BooksLimit())
                .build();

        return bookRepository.findByLikes(bookSearch).stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }

    public List<BookResponse> findBookmarkedBooks(Long id, Integer page) {
        BookSearch bookSearch = BookSearch.builder()
                .page(page)
                .booksLimit(new BooksLimit())
                .build();

        return bookRepository.findBookmarkedBooks(id, bookSearch).stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void save(Long memberId, BookSave bookSave) {
        Member member = memberRepository.getById(memberId);
        Book book = new Book(member, bookSave, createLikesAndBookmark);
        bookRepository.save(book);
    }

    @Transactional
    public void thumbsUp(Book book) {
        book.thumbsUp();
    }
}
