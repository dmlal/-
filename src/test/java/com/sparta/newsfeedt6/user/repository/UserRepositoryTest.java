package com.sparta.newsfeedt6.user.repository;

import com.sparta.newsfeedt6.user.dto.SignupRequestDto;
import com.sparta.newsfeedt6.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName(" username 찾기 성공")
    void findByUsername() {
        // given
        String username = "테스트1";
        User user = new User(username, "password", "email", "introduction");
        userRepository.save(user);

        // when
        Optional<User> getUserFromDb = userRepository.findByUsername(username);

        // then
        assertTrue(getUserFromDb.isPresent(), "저장된 사용자 찾지못함");
        assertEquals(username, getUserFromDb.get().getUsername());
    }

    @Test
    @DisplayName(" username 찾기 실패")
    void failedFindByUsername() {
        // given
        String username = "테스트1";
        User user = new User(username, "password", "email", "introduction");
        userRepository.save(user);

        String username2 = "테스트2";

        // when
        Optional<User> getUserFromDb = userRepository.findByUsername(username2);

        // then
        assertFalse(getUserFromDb.isPresent(), "저장된 사용자 찾지못함");
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