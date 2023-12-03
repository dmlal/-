package com.sparta.newsfeedt6.user.repository;

import com.sparta.newsfeedt6.user.dto.SignupRequestDto;
import com.sparta.newsfeedt6.user.entity.EmailVerification;
import com.sparta.newsfeedt6.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void findByUsername() {
    }

    @Test
    @DisplayName("유저 레포지토리  이메일 가져오기")
    void findByEmail() {
        // given
        SignupRequestDto requestDto = new SignupRequestDto();
        requestDto.setUsername("테스트");
        requestDto.setPassword("123123");
        requestDto.setEmail("123123@gmail.com");

        User user = new User(requestDto.getUsername(), requestDto.getPassword(), requestDto.getEmail(), "introduction");
        userRepository.save(user);

        // when
        Optional<User> getEmail = userRepository.findByEmail(requestDto.getEmail());

        // then
        assertEquals(requestDto.getEmail(), getEmail.get().getEmail());
    }
}