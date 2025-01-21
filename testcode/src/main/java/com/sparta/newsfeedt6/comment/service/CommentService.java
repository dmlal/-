package com.sparta.newsfeedt6.comment.service;

import com.sparta.newsfeedt6.comment.dto.CommentEditRequestDto;
import com.sparta.newsfeedt6.comment.dto.CommentRequestDto;
import com.sparta.newsfeedt6.comment.dto.CommentResponseDto;
import com.sparta.newsfeedt6.comment.entity.Comment;
import com.sparta.newsfeedt6.comment.repository.CommentRepository;
import com.sparta.newsfeedt6.post.entity.PostEntity;
import com.sparta.newsfeedt6.post.repository.PostJpaReqository;
import com.sparta.newsfeedt6.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.access.AccessDeniedException;



@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostJpaReqository postJpaReqository;

    public CommentResponseDto createComment(Long postId, CommentRequestDto requestDto, User user) {
        PostEntity post = postJpaReqository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디의 게시글이 존재하지 않습니다.")
        );

        Comment comment = new Comment(requestDto.getContent(), user, post);
        commentRepository.save(comment);
        System.out.println(requestDto.getContent());

        return new CommentResponseDto(comment);
    }

    public CommentResponseDto updateComment(Long commentId, CommentEditRequestDto requestDto, User user) {
        Comment comment = checkCommentId(commentId);

        checkUserPermission(user, comment);

        comment.update(requestDto.getContent());
        return new CommentResponseDto(comment);
    }

    public void deleteComment(Long commentId, User user) {
        Comment comment = checkCommentId(commentId);

        checkUserPermission(user, comment);
        commentRepository.delete(comment);
    }

    // 에러처리 1. 로그찍고  2. 에러를 다시 던진다.  3. trycatch 로 컨트롤러에서 받고  리스폰스엔티티를 활용하여 응답한다.  일괄처리도 알아보기
    private Comment checkCommentId(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글을 찾을 수 없습니다.")
        );
        return comment;
    }

    // 토큰의 유저정보(user)와  엔티티의 유저정보를 비교하여 동일인인지 검증
    private void checkUserPermission(User user, Comment comment) {
        if (!user.getId().equals(comment.getUser().getId())) {
            throw new AccessDeniedException("권한이 없습니다.");  // 403을 리턴하는 에러
        }
    }
}

// dto 작명 더좋은?

