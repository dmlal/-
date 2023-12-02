# newsfeed-t6

## 사용 기술
### 프로그래밍 언어: 	![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)
### 웹 개발:  ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
### 데이터베이스: ![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)
### 개발 도구: ![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
### 의존성 관리: ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
<br>

## Version 
### JDK 17
### Spring Boot 3.1.5
<br>

## 핵심 구현 기능
#### 회원가입, 로그인 : 사용자는 회원가입, 로그인하여 사용권한을 얻을 수 있습니다.
#### 게시글 작성, 조회, 수정, 삭제    
#### 댓글 작성, 수정, 삭제
#### 
#### 특이사항 
<ul>
<li>회원가입시 이메일 정규식을 적용하여 네이버와 지메일만 허용</li>
<li><strong>회원가입시 이메일 인증 추가</strong></li>
<br>

## API 명세

```agsl
https://documenter.getpostman.com/view/30874094/2s9YeEarLn
```

</ul>
<br>


## 애플리케이션 실행
#### 1. git clone
```
git clone https://github.com/alstjd2627/newsfeed-t6.git
```

#### 2. 환경변수 설정

1. 인텔리제이 오른쪽 위 디버깅버튼 옆에 점 세개 클릭
   - 확인해야할것  NewsfeedT6Application  으로 설정되어있는지 확인
   - 버튼 클릭 후 Configration 아래 edit 클릭
   

2. Environment variables 에 아래에 적힌 것들을 입력하시면 됩니다.
   - Environment variables이 없다면?  Modify Option을 클릭 후<br>
      Environment variables를 찾아 클릭하시면 됩니다. (또는 alt + E)
```
port=???;db_name=???;username=???;password=???;jwt.secret.key=???;
```
3.  ???에 본인의 해당 값을 입력하고, 
    Apply 후 Run을 하여 정상적으로 실행되는지 확인하면 끝입니다.
- email 인증도 활용해보고 싶으시다면
 아래의 내용 또한 추가하면 됩니다. <br>
  (인증번호 발신 메일이 해당 메일 이름으로 전송됩니다.)<br>
- 다만 SMTP 적용한 계정과 앱 비밀번호를 입력하셔야합니다.
```
mail=???;mailpwd=???;  
```

#### 3. 
   



