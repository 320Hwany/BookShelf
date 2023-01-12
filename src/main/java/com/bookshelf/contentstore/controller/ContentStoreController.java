package com.bookshelf.contentstore.controller;

import com.bookshelf.book.domain.Book;
import com.bookshelf.book.sevice.BookService;
import com.bookshelf.contentstore.dto.request.ContentSave;
import com.bookshelf.contentstore.service.ContentStoreService;
import com.bookshelf.member.dto.request.MemberSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class ContentStoreController {

    private final ContentStoreService contentStoreService;
    private final BookService bookService;

    @PostMapping("/book-content/{bookId}")
    public void save(MemberSession memberSession, @PathVariable Long bookId,
                     @RequestBody @Valid ContentSave contentSave) {
        Book book = bookService.getById(bookId);
        contentStoreService.save(book, contentSave);
    }
}
