package com.bookshelf.book.controller;

import com.bookshelf.book.dto.request.BookSave;
import com.bookshelf.book.sevice.BookService;
import com.bookshelf.member.controller.MemberController;
import com.bookshelf.member.domain.Member;
import com.bookshelf.member.dto.request.MemberSignup;
import com.bookshelf.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

@WebMvcTest({BookController.class,
        MemberController.class})
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.bookShelf.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
public class BookControllerDocTest {

    protected MockMvcRequestSpecification restDocs;

    @MockBean
    protected BookService bookService;

    @MockBean
    protected MemberService memberService;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        restDocs = RestAssuredMockMvc.given()
                .mockMvc(MockMvcBuilders.webAppContextSetup(webApplicationContext)
                        .apply(documentationConfiguration(restDocumentation)
                                .operationPreprocessors()
                                .withRequestDefaults(prettyPrint())
                                .withResponseDefaults(prettyPrint()))
                        .build())
                .log().all();
    }

    @Test
    @DisplayName("로그인하지 않으면 책 저장을 할 수 없습니다")
    void saveBookFail() {
        BookSave bookSave = new BookSave("제목입니다");
        restDocs
                .contentType(APPLICATION_JSON_VALUE)
                .body(bookSave)
                .when().post("/book")
                .then().log().all()
                .assertThat()
                .apply(document("book"))
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

//    @Test
//    @DisplayName("로그인을 하면 책을 저장할 수 있다.")
//    void saveBookSuccess() {
//        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
//        String header = mockHttpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
//
//        BookSave bookSave = new BookSave("제목입니다");
//        restDocs
//                .contentType(APPLICATION_JSON_VALUE)
//                .header("Authorization", header)
//                .body(bookSave)
//                .when().post("/book")
//                .then().log().all()
//                .assertThat()
//                .apply(document("book"))
//                .statusCode(HttpStatus.OK.value());
//    }

    @Test
    @DisplayName("회원 가입")
    void saveMember() {
        MemberSignup memberSignup = MemberSignup.builder()
                .username("회원이름3")
                .email("yhwjd993@naver.com")
                .password("432112")
                .age(22)
                .build();

        restDocs
                .contentType(APPLICATION_JSON_VALUE)
                .body(memberSignup)
                .when().post("/signup")
                .then().log().all()
                .assertThat()
                .apply(document("signup", responseFields(
                        fieldWithPath("username").description("이름"),
                        fieldWithPath("email").description("이메일"),
                        fieldWithPath("password").description("비밀번호"),
                        fieldWithPath("age").description("나이")
                )))
                .statusCode(HttpStatus.OK.value());
    }
}
