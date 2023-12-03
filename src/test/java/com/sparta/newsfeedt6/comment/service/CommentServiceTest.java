package com.sparta.newsfeedt6.comment.service;

import com.sparta.newsfeedt6.comment.dto.CommentEditRequestDto;
import com.sparta.newsfeedt6.comment.dto.CommentRequestDto;
import com.sparta.newsfeedt6.comment.dto.CommentResponseDto;
import com.sparta.newsfeedt6.comment.entity.Comment;
import com.sparta.newsfeedt6.comment.repository.CommentRepository;
import com.sparta.newsfeedt6.post.dto.PostAddRequestDto;
import com.sparta.newsfeedt6.post.entity.PostEntity;
import com.sparta.newsfeedt6.post.repository.PostJpaReqository;
import com.sparta.newsfeedt6.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    CommentService commentService;
    @Mock
    CommentRepository commentRepository;
    @Mock
    PostJpaReqository postJpaReqository;

    @BeforeEach
    void clean() {
        commentRepository.deleteAll();
    }


    @Test
    @DisplayName("댓글 생성 성공")
    void createComment() {
        // given
        Long postId = 1L;
        CommentRequestDto requestDto = new CommentRequestDto();
        requestDto.setContent("댓글 작성");
        User user = new User("username", "password", "email", "introduction");
        ReflectionTestUtils.setField(user, "id", 1L);
        PostEntity postEntity = new PostEntity(new PostAddRequestDto(), user);

        given(postJpaReqository.findById(postId)).willReturn(Optional.of(postEntity));

        // when
        CommentResponseDto responseDto = commentService.createComment(postId, requestDto, user);

        // then
        assertEquals(responseDto.getContent(), responseDto.getContent());
    }

    @Test
    @DisplayName("댓글 생성 실패")
    void failedCreateComment() {
        // given
        Long postId = 1L;
        CommentRequestDto requestDto = new CommentRequestDto();
        requestDto.setContent("댓글 작성");
        User user = new User("username", "password", "email", "introduction");
        ReflectionTestUtils.setField(user, "id", 1L);

        given(postJpaReqository.findById(postId)).willReturn(Optional.empty());

        // when  then
        assertThrows(IllegalArgumentException.class, () -> {
            commentService.createComment(postId, requestDto, user);
        });
    }

    @Test
    @DisplayName("댓글 수정 성공")
    @Transactional
    void updateComment() {
        // given
        Long commentId = 1L;
        CommentEditRequestDto requestDto = new CommentEditRequestDto();
        requestDto.setContent("댓글 수정");
        User user = new User("username", "password", "email", "introduction");
        ReflectionTestUtils.setField(user, "id", 1L);
        PostEntity postEntity = new PostEntity(new PostAddRequestDto(), user);
        Comment comment = new Comment(requestDto.getContent(), user, postEntity);

        given(commentRepository.findById(commentId)).willReturn(Optional.of(comment));

        // when
        CommentResponseDto responseDto = commentService.updateComment(commentId, requestDto, user);

        // then
        assertEquals(requestDto.getContent(), responseDto.getContent());
    }

    @Test
    void deleteComment() {
    }
}