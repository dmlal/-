package com.sparta.newsfeedt6.user.service;

import com.sparta.newsfeedt6.security.jwt.JwtUtil;
import com.sparta.newsfeedt6.user.entity.EmailVerification;
import com.sparta.newsfeedt6.user.repository.EmailVerificationRepository;
import com.sparta.newsfeedt6.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@Component
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    MailService mailService;
    @Mock
    EmailVerificationRepository emailVerificationRepository;
    @Mock
    JwtUtil jwtUtil;



    @Test
    @DisplayName("이메일 인증코드 유효시 검증")
    void verifyCode() {
        // given
        String email ="test@test.com";
        String authCode = "999";
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setVerificationCode(authCode);
        emailVerification.setExpiredCodeTime(LocalDateTime.now().plusDays(30));

        given(emailVerificationRepository.findById(email)).willReturn(Optional.of(emailVerification));
        // when
        boolean checkCode = userService.verifyCode(email, authCode);

        //then
        assertTrue(checkCode);
    }

    @Test
    @DisplayName("이메일 인증코드 만료시 에러처리")
    void expiredCode() {
        // given
        String email ="test@test.com";
        String authCode = "999";
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setVerificationCode(authCode);
        emailVerification.setExpiredCodeTime(LocalDateTime.now().minusDays(30));

        given(emailVerificationRepository.findById(email)).willReturn(Optional.of(emailVerification));

        // when then
        assertThrows(IllegalArgumentException.class, () -> userService.verifyCode(email, authCode));
    }
}