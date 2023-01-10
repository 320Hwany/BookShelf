package com.bookshelf.member.service;

import com.bookshelf.member.domain.Member;
import com.bookshelf.member.domain.Session;
import com.bookshelf.member.dto.request.CreateAccessToken;
import com.bookshelf.member.dto.request.MemberLogin;
import com.bookshelf.member.dto.request.MemberSignup;
import com.bookshelf.member.dto.response.MemberResponse;
import com.bookshelf.member.exception.NameDuplicateException;
import com.bookshelf.member.repository.MemberRepository;
import com.bookshelf.member.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final SessionRepository sessionRepository;
    private final CreateAccessToken createAccessToken;

    public boolean validateDuplication(MemberSignup memberSignup) {
        Optional<Member> optionalMember = memberRepository.findByName(memberSignup.getName());
        return optionalMember.isPresent();
    }

    @Transactional
    public void signup(MemberSignup memberSignup) {
        if (validateDuplication(memberSignup)) {
            throw new NameDuplicateException();
        }
        Member member = new Member(memberSignup);
        memberRepository.save(member);
    }

    @Transactional
    public MemberResponse login(MemberLogin memberLogin) {
        Member member = memberRepository.getByEmailAndPassword(memberLogin.getEmail(), memberLogin.getPassword());
        Session session = Session.builder()
                .member(member)
                .createAccessToken(createAccessToken)
                .build();
        sessionRepository.save(session);
        return new MemberResponse(member);
    }
}
