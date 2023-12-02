package com.sparta.newsfeedt6.post.service;

import com.sparta.newsfeedt6.post.dto.PostAddRequestDto;
import com.sparta.newsfeedt6.post.dto.PostResponseDto;
import com.sparta.newsfeedt6.post.dto.PostUpdateRequestDto;
import com.sparta.newsfeedt6.post.entity.PostEntity;
import com.sparta.newsfeedt6.post.repository.PostJpaReqository;
import com.sparta.newsfeedt6.user.entity.User;
import com.sparta.newsfeedt6.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostJpaReqository postJpaReqository;

    private final UserService userService;

    // 게시글 등록 (POST)
    public PostResponseDto addPost(PostAddRequestDto requestDto, User user) {
        User findUser = userService.findByid(user.getId());
        PostEntity postEntity = new PostEntity(requestDto, findUser); // 유저디테일스가 들어갔기때문에 유저로 변경
        PostEntity savePost = postJpaReqository.save(postEntity);

        return new PostResponseDto(savePost);
    }

    // 게시글 선택 조회 (GET) : by ID
    public PostResponseDto getPost(Long postId) {
        PostEntity postEntity = getPostEntity(postId);

        return new PostResponseDto(postEntity);
    }

    // 게시글 선택 조회 (GET) : by TITLE
    public List<PostResponseDto> getPostsByTitle(String title) {
        List<PostEntity> posts= postJpaReqository.findByTitleContaining(title);
        return posts.stream().map(PostResponseDto::new)
                .collect(Collectors.toList());
    }
    // 게시글 전체 조회

    public List<PostResponseDto> getPosts(){
        return postJpaReqository.findAllByOrderByCreatedAtDesc().stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }
    // 게시글 수정
    @Transactional
    public PostResponseDto updatePost(Long postId, PostUpdateRequestDto requestDto, User user) {
        PostEntity postEntity = getPostEntity(postId);

        checkUserPermission(user, postEntity);

        postEntity.update(requestDto);

        return new PostResponseDto(postEntity);
    }
    // 게시글 삭제
    @Transactional
    public void deletePost(Long postId, User user) {
        PostEntity postEntity = getPostEntity(postId);
        checkUserPermission(user, postEntity);
        postJpaReqository.delete(postEntity);
    }
    // 게시글 Id 찾기

    private PostEntity getPostEntity(Long postId){
        return postJpaReqository.findById(postId)
                .orElseThrow(() -> new NullPointerException("해당 게시글을 찾을 수 없습니다."));
    }

    // 토큰의 유저정보(user)와  엔티티의 유저정보를 비교하여 동일인인지 검증
    private void checkUserPermission(User user, PostEntity postEntity) {
        if (!user.getId().equals(postEntity.getUser().getId())) {
            throw new AccessDeniedException("권한이 없습니다.");  // 403을 리턴하는 에러
        }
    }
}


