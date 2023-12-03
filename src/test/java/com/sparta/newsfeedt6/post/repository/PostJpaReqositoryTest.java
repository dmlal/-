package com.sparta.newsfeedt6.post.repository;

import com.sparta.newsfeedt6.post.dto.PostAddRequestDto;
import com.sparta.newsfeedt6.post.entity.PostEntity;
import com.sparta.newsfeedt6.user.entity.User;
import com.sparta.newsfeedt6.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PostJpaReqositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostJpaReqository postJpaReqository;

    @Test
    @Transactional
    @DisplayName("DB 저장값 조회")
    void findAllByOrderByCreatedAtDesc() {
        // given
        User user = new User("username", "password", "email", "introduction");
        userRepository.save(user);
        PostEntity postEntity1 = new PostEntity(new PostAddRequestDto("1", "1"), user);
        PostEntity postEntity2 = new PostEntity(new PostAddRequestDto("2", "2"), user);

        postJpaReqository.save(postEntity1);
        postJpaReqository.save(postEntity2);

        // when
        List<PostEntity> postEntityList = postJpaReqository.findAllByOrderByCreatedAtDesc();

        // then
        assertThat(postEntityList.get(1)).isEqualTo(postEntity1);
        assertThat(postEntityList.get(0)).isEqualTo(postEntity2);

    }

    @Test
    @DisplayName("제목으로 검색")
    void findByTitleContaining() {
        // given
        User user = new User("username", "password", "email", "introduction");
        userRepository.save(user);
        PostEntity postEntity1 = new PostEntity(new PostAddRequestDto("1", "1"), user);
        PostEntity postEntity2 = new PostEntity(new PostAddRequestDto("2", "2"), user);

        postJpaReqository.save(postEntity1);
        postJpaReqository.save(postEntity2);

        // when
        List<PostEntity> postEntityList = postJpaReqository.findByTitleContaining(postEntity1.getTitle());

        // then
        assertThat(postEntityList.get(0)).isEqualTo(postEntity1);
    }
}