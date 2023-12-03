package com.sparta.newsfeedt6.post.service;

import com.sparta.newsfeedt6.post.dto.PostAddRequestDto;
import com.sparta.newsfeedt6.post.dto.PostResponseDto;
import com.sparta.newsfeedt6.post.entity.PostEntity;
import com.sparta.newsfeedt6.post.repository.PostJpaReqository;
import com.sparta.newsfeedt6.user.entity.User;
import com.sparta.newsfeedt6.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.parameters.P;
import org.springframework.test.util.ReflectionTestUtils;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostJpaReqository postJpaReqository;

    @Mock
    private UserService userService;

    @BeforeEach
    void clean() {
        postJpaReqository.deleteAll();
    }

    @Test
    @DisplayName("게시글 작성")
    void addPost() {
        // given
        PostAddRequestDto requestDto = new PostAddRequestDto();
        User user = new User("username", "password", "email", "introduction");
        ReflectionTestUtils.setField(user, "id", 1L);
        PostEntity postEntity = new PostEntity(requestDto, user);

        given(userService.findByid(user.getId())).willReturn(user);
        given(postJpaReqository.save(any(PostEntity.class))).willReturn(postEntity);

        // when
        PostResponseDto responseDto = postService.addPost(requestDto, user);
        String title = responseDto.title();
        String content = responseDto.content();

        // then
        assertEquals(postEntity.getTitle(), title);
        assertEquals(postEntity.getContent(), content);

    }

    @Test
    void getPost() {

    }

    @Test
    void getPosts() {
    }

    @Test
    void updatePost() {
    }

    @Test
    void deletePost() {
    }
}