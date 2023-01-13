package com.bookshelf.member.repository;

import com.bookshelf.member.domain.Member;
import com.bookshelf.member.dto.request.MemberLogin;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository {

    void save(Member member);

    Optional<Member> findByUsername(String username);

    Optional<Member> findByEmail(String email);

    Member getById(Long id);

    Optional<Member> findById(Long id);

    Member getByEmailAndPassword(String email, String password);

    Boolean checkByMemberLogin(MemberLogin memberLogin);

    void deleteAll();

    Long count();

    void delete(Member member);
}
