package com.bookshelf.member.service;

import com.bookshelf.member.domain.Member;
import com.bookshelf.member.domain.Session;
import com.bookshelf.member.dto.request.CreateAccessToken;
import com.bookshelf.member.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    @Transactional
    public Session makeSession(Member member, CreateAccessToken createAccessToken) {
        Session session = Session.builder()
                .member(member)
                .createAccessToken(createAccessToken)
                .build();
        sessionRepository.save(session);
        return session;
    }
}