package com.bookshelf.member.service;

import com.bookshelf.member.domain.Member;
import com.bookshelf.member.domain.Session;
import com.bookshelf.member.dto.request.MemberLogin;
import com.bookshelf.member.dto.request.MemberSignup;
import com.bookshelf.member.dto.request.MemberUpdate;
import com.bookshelf.member.dto.response.MemberResponse;
import com.bookshelf.member.exception.NameDuplicateException;
import com.bookshelf.member.repository.MemberRepository;
import com.bookshelf.member.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final SessionService sessionService;

    @Transactional
    public void signup(MemberSignup memberSignup) {
        if (validateDuplication(memberSignup)) {
            throw new NameDuplicateException();
        }
        Member member = new Member(memberSignup);
        memberRepository.save(member);
    }

    public boolean validateDuplication(MemberSignup memberSignup) {
        Optional<Member> optionalMember = memberRepository.findByName(memberSignup.getName());
        return optionalMember.isPresent();
    }

    public Member getByMemberLogin(MemberLogin memberLogin) {
        Member member = memberRepository.getByEmailAndPassword(memberLogin.getEmail(), memberLogin.getPassword());
        return member;
    }

    public Member getById(Long memberId) {
        Member member = memberRepository.getById(memberId);
        return member;
    }
    @Transactional
    public MemberResponse update(Long id, MemberUpdate memberUpdate) {
        Member member = memberRepository.getById(id);
        member.update(memberUpdate);
        return new MemberResponse(member);
    }

    @Transactional
    public void delete(Long id) {
        Member member = memberRepository.getById(id);
        deleteSessionsAboutMember(member);
        memberRepository.delete(member);
    }

    @Transactional
    public void deleteSessionsAboutMember(Member member) {
        List<Session> sessionList = sessionService.findAllByMember(member);
        sessionService.deleteSessionList(sessionList);
    }
}
