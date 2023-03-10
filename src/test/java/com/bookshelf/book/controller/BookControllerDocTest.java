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
    @DisplayName("??????????????? ????????? ??? ????????? ??? ??? ????????????")
    void saveBookFail() {
        BookSave bookSave = new BookSave("???????????????");
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
//    @DisplayName("???????????? ?????? ?????? ????????? ??? ??????.")
//    void saveBookSuccess() {
//        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
//        String header = mockHttpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
//
//        BookSave bookSave = new BookSave("???????????????");
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
    @DisplayName("?????? ??????")
    void saveMember() {
        MemberSignup memberSignup = MemberSignup.builder()
                .username("????????????3")
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
                        fieldWithPath("username").description("??????"),
                        fieldWithPath("email").description("?????????"),
                        fieldWithPath("password").description("????????????"),
                        fieldWithPath("age").description("??????")
                )))
                .statusCode(HttpStatus.OK.value());
    }
}
