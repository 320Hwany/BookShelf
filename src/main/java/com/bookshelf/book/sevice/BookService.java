package com.bookshelf.book.sevice;

import com.bookshelf.book.domain.Book;
import com.bookshelf.book.dto.request.BookSave;
import com.bookshelf.book.dto.request.CreateLikesAndBookmark;
import com.bookshelf.book.repository.BookRepository;
import com.bookshelf.member.domain.Member;
import com.bookshelf.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    public Book getById(Long id) {
        Book book = bookRepository.getById(id);
        return book;
    }

    @Transactional
    public void save(Long memberId, BookSave bookSave) {
        Member member = memberRepository.getById(memberId);
        Book book = new Book(member, bookSave, new CreateLikesAndBookmark());
        bookRepository.save(book);
    }
}
