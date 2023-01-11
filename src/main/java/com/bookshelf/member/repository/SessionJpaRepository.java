package com.bookshelf.member.repository;

import com.bookshelf.member.domain.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessionJpaRepository extends JpaRepository<Session, Long> {

    Optional<Session> findByAccessToken(String accessToken);

    List<Session> findAllByMemberId(Long memberId);
}
