package com.bookshelf.member.domain;

import com.bookshelf.member.dto.request.CreateAccessToken;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseCookie;

import javax.persistence.*;

import java.time.Duration;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
public class Session {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "session_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String accessToken;

    @Builder
    public Session(Member member, CreateAccessToken createAccessToken) {
        this.member = member;
        this.accessToken = createAccessToken.getAccessToken();
    }

    public ResponseCookie setCookie() {
        ResponseCookie cookie = ResponseCookie.from("SESSION", accessToken)
                .domain("localhost")
                .path("/")
                .httpOnly(true)
                .secure(false)
                .maxAge(Duration.ofDays(30))
                .sameSite("Strict")
                .build();
        return cookie;
    }
}
