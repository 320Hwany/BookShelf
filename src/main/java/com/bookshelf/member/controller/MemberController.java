package com.bookshelf.member.controller;

import com.bookshelf.member.dto.request.MemberLogin;
import com.bookshelf.member.dto.request.MemberSignup;
import com.bookshelf.member.dto.response.MemberResponse;
import com.bookshelf.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public void signup(@RequestBody @Valid MemberSignup memberSignup) {
        memberService.signup(memberSignup);
    }

    @PostMapping("/login")
    public ResponseEntity<MemberResponse> login(@RequestBody @Valid MemberLogin memberLogin) {
        MemberResponse memberResponse = memberService.login(memberLogin);
        return ResponseEntity.ok(memberResponse);
    }
}
