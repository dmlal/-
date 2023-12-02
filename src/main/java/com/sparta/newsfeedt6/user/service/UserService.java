package com.sparta.newsfeedt6.user.service;

import com.sparta.newsfeedt6.security.jwt.JwtUtil;
import com.sparta.newsfeedt6.user.dto.LoginRequestDto;
import com.sparta.newsfeedt6.user.dto.SignupRequestDto;
import com.sparta.newsfeedt6.user.entity.EmailVerification;
import com.sparta.newsfeedt6.user.entity.User;
import com.sparta.newsfeedt6.user.repository.EmailVerificationRepository;
import com.sparta.newsfeedt6.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final EmailVerificationRepository emailVerificationRepository;
    private final JwtUtil jwtUtil;

    public User findByid(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String introduction = requestDto.getIntroduction();

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("이미 있는 username입니다.");
        }

        // 이메일 중복 확인
        String email = checkDuplicatedEmail(requestDto);

        User user = new User(username, password, email, introduction);
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
    }

    public void logout(HttpServletRequest req){
        System.out.println(jwtUtil.getTokenFromRequest(req));
    }

    public void sendCodeToEmail(String email) {
        String title = " 이메일 인증 번호";
        String authCode = String.valueOf((int) (Math.random() * 900) + 100);
        LocalDateTime expiredCodeTime = LocalDateTime.now().plusMinutes(30);

        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setEmail(email);
        emailVerification.setVerificationCode(authCode);
        emailVerification.setExpiredCodeTime(expiredCodeTime);

        emailVerificationRepository.save(emailVerification);

        mailService.sendEmail(email, title, authCode);
    }

    public boolean verifyCode(String email, String authCode) {
        EmailVerification emailVerification = emailVerificationRepository.findById(email)
                .orElseThrow(() -> new IllegalArgumentException("인증코드가 없습니다."));
        if (emailVerification.getExpiredCodeTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("인증 코드 시간 만료");
        }
        return authCode.equals(emailVerification.getVerificationCode());
    }

    // 이메일 중복 확인
    private String checkDuplicatedEmail(SignupRequestDto requestDto) {
        // 이메일 중복 확인
        String email = requestDto.getEmail();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }
        return email;
    }
}
