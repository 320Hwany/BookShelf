package com.bookshelf.book.sevice;

import com.bookshelf.book.domain.Book;
import com.bookshelf.book.dto.request.BookSave;
import com.bookshelf.book.dto.request.CreateLikesAndBookmark;
import com.bookshelf.book.repository.BookRepository;
import com.bookshelf.member.domain.Member;
import com.bookshelf.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("아이디로부터 책 찾기 테스트 - 성공")
    @Transactional
    void getById() {
        // given
        Member member = new Member();
        memberRepository.save(member);

        Book book = Book.builder()
                .title("책 제목")
                .member(member)
                .createLikesAndBookmark(new CreateLikesAndBookmark())
                .build();

        bookRepository.save(book);

        // when
        Book getBook = bookService.getById(book.getId());

        // then
        assertThat(getBook).isEqualTo(book);
    }

    @Test
    @DisplayName("아이디로부터 책 찾기 테스트 - 성공")
    @Transactional
    void save() {
        // given
        Member member = new Member();
        memberRepository.save(member);

        BookSave bookSave = BookSave.builder()
                .title("책 제목")
                .build();

        // when
        bookService.save(member.getId(), bookSave);

        // then
        assertThat(bookRepository.count()).isEqualTo(1);
    }
}