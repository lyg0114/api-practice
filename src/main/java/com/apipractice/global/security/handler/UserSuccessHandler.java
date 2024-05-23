package com.apipractice.global.security.handler;

import static com.apipractice.global.security.type.RoleType.USER;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.apipractice.domain.member.entity.Member;
import com.apipractice.domain.member.application.repository.MemberRepository;
import com.apipractice.global.security.service.CustomUserDetails;
import com.apipractice.global.security.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.global.security.handler
 * @since : 20.05.24
 */
@RequiredArgsConstructor
@Component
public class UserSuccessHandler implements AuthenticationSuccessHandler {

    private final MemberRepository memberRepository;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    @Transactional
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        try {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Member member = memberRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("가입된 이메일이 존재하지 않습니다."));

            // accessToken , refreshToken 생성
            String accessToken = jwtService.createAccessToken(member.getEmail(), USER, member.getId());
            String refreshToken = jwtService.createRefreshToken(member.getEmail());
            member.updateRefreshToken(refreshToken);
            response.setContentType(APPLICATION_JSON_VALUE);
            objectMapper.writeValue(response.getWriter(), new TokenResponseDto(accessToken, refreshToken));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Getter
    @AllArgsConstructor
    private static class TokenResponseDto {
        private String accessToken;
        private String refreshToken;
    }
}
