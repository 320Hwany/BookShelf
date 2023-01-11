package com.bookshelf.book.controller;

import com.bookshelf.book.domain.Book;
import com.bookshelf.book.dto.request.BookSave;
import com.bookshelf.book.dto.response.BookResponse;
import com.bookshelf.book.sevice.BookService;
import com.bookshelf.member.dto.request.MemberSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookService bookService;

    @PostMapping("/book")
    public void save(MemberSession memberSession, @RequestBody @Valid BookSave bookSave) {
        bookService.save(memberSession.getId(), bookSave);
    }

    @GetMapping("/book/{bookId}")
    public BookResponse getBook(@PathVariable Long bookId) {
        Book book = bookService.getById(bookId);
        return new BookResponse(book);
    }

    @GetMapping("/books-latest")
    public List<BookResponse> getBooksByLatest(@RequestParam Integer page) {
        return bookService.findBookResponsesByLatest(page);
    }
}
