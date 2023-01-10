package com.bookshelf.member.repository;

import com.bookshelf.member.domain.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionJpaRepository extends JpaRepository<Session, Long> {
}
