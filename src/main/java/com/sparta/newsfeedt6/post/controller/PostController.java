package com.sparta.newsfeedt6.post.controller;

import com.sparta.newsfeedt6.post.dto.PostAddRequestDto;
import com.sparta.newsfeedt6.post.dto.PostResponseDto;
import com.sparta.newsfeedt6.post.dto.PostUpdateRequestDto;
import com.sparta.newsfeedt6.post.service.PostService;
import com.sparta.newsfeedt6.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    // 게시글 등록 (POST)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PostResponseDto> addPost(
            @RequestBody PostAddRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        PostResponseDto responseDto = postService.addPost(requestDto, userDetails.getUser());
        return ResponseEntity.status(201).body(responseDto);
    }


    // 게시글 선택 조회 (GET) : by ID
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPost(
            @PathVariable Long postId
    ){
        PostResponseDto responseDto = postService.getPost(postId);
        return ResponseEntity.ok(responseDto);
    }

//    // 게시글 선택 조회 (GET) : by TITLE        URI에서 ID와 TITLE을 구분 할 수 없기 때문에 두 메소드가 동시에 매핑됨.
//    @GetMapping("/{title}")
//    public ResponseEntity<List<PostResponseDto>> getPostByTitle(@RequestParam String title) {
//        List<PostResponseDto> responseDtos =  postService.getPostsByTitle(title);
//        return ResponseEntity.ok(responseDtos);
//    }

    // 게시글 전체 조회
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getPosts(){
        List<PostResponseDto> responseDtos = postService.getPosts();
        return ResponseEntity.ok(responseDtos);
    }

    // 게시글 수정
    @PatchMapping("/{postId}")
    public  ResponseEntity<PostResponseDto> updatePost(
            @PathVariable Long postId,
            @RequestBody PostUpdateRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        PostResponseDto responseDto = postService.updatePost(postId, requestDto, userDetails.getUser());
        return ResponseEntity.ok(responseDto);
    }

    // 게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        postService.deletePost(postId, userDetails.getUser());
        return ResponseEntity.ok("삭제성공");
    }
}