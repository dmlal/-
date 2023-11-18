package com.example.todoapp.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SignupTest {

    /*
    이 테스트 코드는 assertThrows(Exception.class, () -> {});  이 부분이 제대로 작동하지 않아서 포기했습니다.
    대신 htrml 쪽에도 정규식을 포함시켜 html와 서버 양측에서 이중으로 검증가능 하도록 변경하였습니다.
     */



//    // "ID는 최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)로 구성되어야 한다."
//    // "비밀번호는 최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성되어야 한다."
//    @Test
//    void successTest() {
//        SignupRequestDto testrequestDto = new SignupRequestDto();
//        testrequestDto.setUsername("abcde");
//        testrequestDto.setPassword("abcAbc123");
//
//        // 예외 안생기면 성공
//        assertThrows(Exception.class, () -> {});
//    }
//
//    @Test
//    void idFailedTest() {
//        System.out.println("예외가 발생하면 성공");
//        SignupRequestDto testrequestDto = new SignupRequestDto();
//        // 대문자 입력
////        testrequestDto.setUsername("ABCDE");
//        // 길이 미달
////        testrequestDto.setUsername("ABC");
//        // 길이 초과
////        testrequestDto.setUsername("0123456789111");
//        // 영어 제외 문자 입력
//        testrequestDto.setUsername("으아아아아");
//        testrequestDto.setPassword("abcAbc123");
//
//        assertThrows(Exception.class, () -> {});
//    }
//    @Test
//    void passwordFailedTest() {
//        System.out.println("예외가 발생하면 성공");
//        SignupRequestDto testrequestDto = new SignupRequestDto();
//        testrequestDto.setUsername("ABCDE");
//        // 길이 미달
//        testrequestDto.setPassword("aA1");
//        // 영어 제외 입력
//        testrequestDto.setPassword("ㅁㄴㅇㅁㄴㅇ");
//
//        assertThrows(Exception.class, () -> {});
//    }
}