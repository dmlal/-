package com.example.todoapp.service;

import com.example.todoapp.controller.exception.DuplicatedUsernameException;
import com.example.todoapp.dto.LoginRequestDto;
import com.example.todoapp.dto.SignupRequestDto;
import com.example.todoapp.entity.User;
import com.example.todoapp.jwt.JwtUtil;
import com.example.todoapp.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signUp(@Valid SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        if (userRepository.existsByUsername(username)) {
            throw new DuplicatedUsernameException();
        }
        User user = new User(username, password);
        userRepository.save(user);
    }

    public void login(LoginRequestDto requestDto, HttpServletResponse res) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtUtil.createToken(user.getUsername());
        jwtUtil.addAcceccTokenToCookie(accessToken, res);

        String refToken = jwtUtil.createRefreshToken(user.getUsername());
        jwtUtil.addRefreshTokenToCookie(refToken, res);


    }
}
