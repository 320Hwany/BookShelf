package com.bookshelf.member.domain;

import com.bookshelf.member.dto.request.CreateAccessToken;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
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
}
