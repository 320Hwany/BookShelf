package com.bookshelf.member.repository;

import com.bookshelf.member.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository {

    void save(Member member);

    Optional<Member> findByName(String name);

    Member getByEmailAndPassword(String email, String password);

    void deleteAll();
}
