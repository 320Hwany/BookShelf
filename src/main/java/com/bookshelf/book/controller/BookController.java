package com.bookshelf.book.controller;

import com.bookshelf.book.domain.Book;
import com.bookshelf.book.dto.request.BookSave;
import com.bookshelf.book.dto.response.BookResponse;
import com.bookshelf.book.sevice.BookService;
import com.bookshelf.member.domain.Member;
import com.bookshelf.member.dto.request.MemberSession;
import com.bookshelf.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookService bookService;

    @PostMapping("/book")
    public void save(@RequestBody @Valid BookSave bookSave, MemberSession memberSession) {
        bookService.save(memberSession.getId(), bookSave);
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<BookResponse> getBook(@PathVariable Long bookId) {
        Book book = bookService.getById(bookId);
        return ResponseEntity.ok(new BookResponse(book));
    }

    @GetMapping("/books-orderBy-latest")
    public ResponseEntity<List<BookResponse>> getBooksByLatest(@RequestParam Integer page) {
        List<BookResponse> bookResponsesByLatest = bookService.findBookResponsesByLatest(page);
        return ResponseEntity.ok(bookResponsesByLatest);
    }

    @GetMapping("/books-orderBy-likes")
    public ResponseEntity<List<BookResponse>> getBooksByLikes(@RequestParam Integer page) {
        List<BookResponse> bookResponsesByLikes = bookService.findBookResponsesByLikes(page);
        return ResponseEntity.ok(bookResponsesByLikes);
    }

    @GetMapping("/bookmarked")
    public ResponseEntity<List<BookResponse>> getBookmarked(@RequestParam Integer page, MemberSession memberSession) {
        List<BookResponse> bookmarkedBooks = bookService.findBookmarkedBooks(memberSession.getId(), page);
        return ResponseEntity.ok(bookmarkedBooks);
    }

    @PostMapping("/like/{bookId}")
    public void thumbsUp(@PathVariable Long bookId, MemberSession memberSession) {
        Book book = bookService.getById(bookId);
        bookService.thumbsUp(book);
    }
}
