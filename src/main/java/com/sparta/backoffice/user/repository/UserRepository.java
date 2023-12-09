package com.sparta.backoffice.user.repository;

import com.sparta.backoffice.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByNickname(String newNickname);

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

	Optional<User> findByNaverId(String naverId);

	Optional<User> findByKakaoId(String kakaoId);
}
