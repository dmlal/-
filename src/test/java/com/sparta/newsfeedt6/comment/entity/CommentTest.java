package com.sparta.newsfeedt6.comment.entity;

import com.sparta.newsfeedt6.post.entity.PostEntity;
import com.sparta.newsfeedt6.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

class CommentTest {

    @MockBean
    private User user;
    PostEntity post;
    @Test
    @DisplayName("댓글 수정 테스트")
    public void test1(){

        String content = "수정";
        Comment comment = new Comment(content, user, post);


        String updateContent = "수정1";
        comment.update(updateContent);


        assertEquals(updateContent, comment.getContent());
    }

}