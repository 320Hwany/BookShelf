package com.bookshelf.global.error;

import com.bookshelf.book.exception.BookNotFoundException;
import com.bookshelf.contentstore.exception.ContentStoreNotFoundException;
import com.bookshelf.global.error.dto.ErrorResponse;
import com.bookshelf.member.exception.MemberNotFoundException;
import com.bookshelf.member.exception.NameDuplicateException;
import com.bookshelf.member.exception.UnauthorizedException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestControllerAdvice
public class ControllerAdvice {

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> invalidRequest(MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청입니다")
                .build();

        for (FieldError fieldError : e.getFieldErrors()) {
            errorResponse.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ResponseBody
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ErrorResponse> MemberNotFound(MemberNotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(e.getStatusCode())
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(NOT_FOUND).body(errorResponse);
    }

    @ResponseBody
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> BookNotFound(BookNotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(e.getStatusCode())
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(NOT_FOUND).body(errorResponse);
    }

    @ResponseBody
    @ExceptionHandler(ContentStoreNotFoundException.class)
    public ResponseEntity<ErrorResponse> ContentStoreNotFound(ContentStoreNotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(e.getStatusCode())
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(NOT_FOUND).body(errorResponse);
    }

    @ResponseBody
    @ExceptionHandler(NameDuplicateException.class)
    public ResponseEntity<ErrorResponse> duplicateNameRequest(NameDuplicateException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(e.getStatusCode())
                .message(e.getMessage())
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> unauthorizedException(UnauthorizedException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(e.getStatusCode())
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(UNAUTHORIZED).body(errorResponse);
    }
}
