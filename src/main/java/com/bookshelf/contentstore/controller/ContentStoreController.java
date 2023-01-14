package com.bookshelf.contentstore.controller;

import com.bookshelf.book.domain.Book;
import com.bookshelf.book.sevice.BookService;
import com.bookshelf.contentstore.dto.request.ContentUpdate;
import com.bookshelf.contentstore.dto.response.ContentStoreResponse;
import com.bookshelf.contentstore.dto.request.ContentSave;
import com.bookshelf.contentstore.service.ContentStoreService;
import com.bookshelf.global.config.annotation.Login;
import com.bookshelf.member.dto.request.MemberSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ContentStoreController {

    private final ContentStoreService contentStoreService;
    private final BookService bookService;

    @PostMapping("/book-content/{bookId}")
    public void save(@Login MemberSession memberSession, @PathVariable Long bookId,
                     @RequestBody @Valid ContentSave contentSave) {
        Book book = bookService.getById(bookId);
        contentStoreService.save(book, contentSave);
    }

    @GetMapping("/book-content/{bookId}")
    public ResponseEntity<List<ContentStoreResponse>> getBookContent(@Login MemberSession memberSession,
                                                                     @PathVariable Long bookId) {
        List<ContentStoreResponse> contentStoreOfBook = contentStoreService.findContentStoreOfBook(bookId);
        return ResponseEntity.ok(contentStoreOfBook);
    }

    @PatchMapping("/book-content/{bookContentId}")
    public void updateBookContent(@Login MemberSession memberSession,
                                  @PathVariable Long bookContentId,
                                  @RequestBody @Valid ContentUpdate contentUpdate) {
        contentStoreService.update(bookContentId, contentUpdate);
    }

    @DeleteMapping("/book-content/{bookContentId}")
    public void deleteBookContent(@Login MemberSession memberSession,
                                  @PathVariable Long bookContentId) {
        contentStoreService.delete(bookContentId);
    }
}
