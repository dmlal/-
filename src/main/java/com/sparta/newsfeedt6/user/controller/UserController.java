package com.sparta.newsfeedt6.user.controller;
import com.sparta.newsfeedt6.security.jwt.JwtUtil;
import com.sparta.newsfeedt6.user.dto.EmailRequestDto;
import com.sparta.newsfeedt6.user.dto.EmailVerificationRequestDto;
import com.sparta.newsfeedt6.user.dto.SignupRequestDto;
import com.sparta.newsfeedt6.user.service.UserService;
import com.sparta.newsfeedt6.user.dto.LoginRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/signup")
    public String signUp(){
        return "signupForm";
    }

    @PostMapping("/signup")
    public String signup(@Valid @RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);

        return "home";
    }

    @GetMapping("/login")
    public String login(){
        return "loginForm";
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse res) {
//
//        userService.login(requestDto, res);
//
//        HttpHeaders headers = new HttpHeaders();
//        res.setHeader(JwtUtil.AUTH_HEADER, jwtUtil.createToken(requestDto.getUsername()));
//        return ResponseEntity.ok().headers(headers).body("로그인에 성공하였습니다.");
//    }

    @PatchMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest req) {
        userService.logout(req);
        return ResponseEntity.ok("Logout successful");
    }


    // 이메일 인증 관련 메소드 2개
    @PostMapping("/emails/verification-requests")
    public ResponseEntity sendMessage(@Valid @RequestBody EmailRequestDto requestDto) {
        userService.sendCodeToEmail(requestDto.getEmail());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/emails/verifications")
    public ResponseEntity<String> verificationEmail(@Valid @RequestBody EmailVerificationRequestDto requestDto) {
        Boolean isVerified = userService.verifyCode(requestDto.getEmail(), requestDto.getAuthCode());

        if (isVerified) {
            return ResponseEntity.ok("이메일 인증에 성공하였습니다");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증 실패");
        }
    }
}
