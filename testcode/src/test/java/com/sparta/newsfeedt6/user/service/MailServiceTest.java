package com.sparta.newsfeedt6.user.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.ExpectedCount.times;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {


    @InjectMocks
    private MailService mailService;
    @Mock
    private JavaMailSender javaMailSender;

    @Test
    @DisplayName("이메일 전송 성공 테스트")
    void sendEmail() {
        String email = "123@123.com";
        String title = "테스트";
        String text = "테스트 내용";

        // when
        mailService.sendEmail(email, title, text);

        //then
        verify(javaMailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    @DisplayName("이메일 전송 실패 테스트")
    void failedSendEmail() {
        String email = "123@123.com";
        String title = "테스트";
        String text = "테스트 내용";

        doThrow(new MailSendException("MailService.sendMail이 에러 유발"))
                .when(javaMailSender).send(any(SimpleMailMessage.class));

        // when then
        MailSendException exception = assertThrows(MailSendException.class,
                () -> mailService.sendEmail(email, title, text));


        //then
        assertEquals("이메일을 보낼 수 없음", exception.getMessage());
    }
}