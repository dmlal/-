package com.sparta.newsfeedt6.user.repository;

import com.sparta.newsfeedt6.user.entity.EmailVerification;
import com.sparta.newsfeedt6.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EmailVerificationRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailVerificationRepository emailVerificationRepository;

    @Test
    @DisplayName("이메일 DB 저장유무 확인")
    void findById() {
        // given
        User user = new User("username", "password", "email", "introduction");
        userRepository.save(user);

        EmailVerification emailVerification1 = new EmailVerification();
        emailVerification1.setEmail("123@123.com");
        emailVerificationRepository.save(emailVerification1);

        EmailVerification emailVerification2 = new EmailVerification();
        emailVerification2.setEmail("345@345.com");
        emailVerificationRepository.save(emailVerification2);

        // when
        Optional<EmailVerification> getEmail1 = emailVerificationRepository.findById(emailVerification1.getEmail());
        Optional<EmailVerification> getEmail2 = emailVerificationRepository.findById(emailVerification2.getEmail());

        // then
        assertEquals(emailVerification1.getEmail(), getEmail1.get().getEmail());
        assertEquals(emailVerification2.getEmail(), getEmail2.get().getEmail());


    }
}