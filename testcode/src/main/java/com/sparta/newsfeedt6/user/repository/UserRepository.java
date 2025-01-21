package com.sparta.newsfeedt6.user.repository;

import com.sparta.newsfeedt6.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//DB 연결준비 완료
public interface UserRepository extends JpaRepository<User, Long> {
    //일단은 optional로 받기
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

}

