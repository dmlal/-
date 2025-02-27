package com.sparta.backoffice.follow.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FollowRepositoryTest {

    @Autowired
    FollowRepository followRepository;

    @Test
    void findByFollowerIdAndFollowingId() {
        followRepository.findByFollowerIdAndFollowingId(1L, 2L);
    }
}