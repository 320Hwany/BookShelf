package com.bookshelf.global.config;

import com.bookshelf.member.exception.UnauthorizedException;
import com.bookshelf.member.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class LoginCheckInterceptor implements HandlerInterceptor {

    private final SessionRepository sessionRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            throw new UnauthorizedException();
        }

        String accessToken = cookies[0].getValue();
        sessionRepository.findByAccessToken(accessToken)
                .orElseThrow(UnauthorizedException::new);

        return true;
    }
}
