package com.example.todoapp.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
//@Setter   // 테스트코드 사용을 위해 등록 -> 실패했기 때문에 주석처리
public class SignupRequestDto {
    @Pattern(regexp = "^[a-z0-9]{4,10}$",
            message = "ID는 최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)로 구성되어야 한다.")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$",
            message = "최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성되어야 한다." )
    private String password;
}


    /*
    회원가입 테스트코드가 assertThrows(Exception.class, () -> {});  이 부분이 제대로 작동하지 않아서 포기했습니다.
    대신 htrml 쪽에도 정규식을 포함시켜 html와 서버 양측에서 이중으로 검증가능 하도록 변경하였습니다.
    */
