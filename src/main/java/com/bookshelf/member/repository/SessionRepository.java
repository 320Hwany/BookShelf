package com.bookshelf.member.repository;

import com.bookshelf.member.domain.Member;
import com.bookshelf.member.domain.Session;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository {

    void save(Session session);

    void deleteAll();

    Long count();

    Optional<Session> findByAccessToken(String accessToken);

    List<Session> findAllByMemberId(Long memberId);

    void deleteList(List<Session> sessionList);
}
