package com.sparta.newsfeedt6.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.newsfeedt6.mvc.MockSpringSecurityFilter;
import com.sparta.newsfeedt6.post.dto.PostAddRequestDto;
import com.sparta.newsfeedt6.post.dto.PostResponseDto;
import com.sparta.newsfeedt6.post.entity.PostEntity;
import com.sparta.newsfeedt6.post.service.PostService;
import com.sparta.newsfeedt6.security.UserDetailsImpl;
import com.sparta.newsfeedt6.security.WebSecurityConfig;
import com.sparta.newsfeedt6.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = {PostController.class},
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = WebSecurityConfig.class
                )
        }
)
@MockBean(JpaMetamodelMappingContext.class)
class PostControllerTest {

    private MockMvc mvc;
    private Principal mockPrincipal;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

//    @Autowired
    @MockBean
    PostService postService;

//    @InjectMocks
//    PostController postController;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity(new MockSpringSecurityFilter()))
                .alwaysDo(print())
                .build();

        // 시큐리티에서 UserRoleEnum을 구현하지 않았기 떄문에 하드코딩
        String authority = "ROLE_USER";

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);


        User user = new User("컨트롤러테스트", "12341234", "123123123@gmail.com", "ROLE_USER");
        UserDetailsImpl testUserDetails = new UserDetailsImpl(user);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", authorities);


//        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
//                testUserDetails, testUserDetails.getPassword(), authorities));
        // @SpringBootTest 쓸거면 이걸 쓰는게 좋음
    }




    @Test
    @DisplayName(" 컨트롤러 게시글 작성")
    void addPost() throws Exception {
        // given
        PostAddRequestDto requestDto = new PostAddRequestDto("컨트롤러 테스트", "제발 성공");
        User user = new User("컨트롤러테스트", "12341234", "123123123@gmail.com", "ROLE_USER");
        PostEntity postEntity = new PostEntity(requestDto, user);

        String inputContent = new ObjectMapper().writeValueAsString(requestDto);
        PostResponseDto responseDto = new PostResponseDto(postEntity);
        given(postService.addPost(requestDto, user)).willReturn(responseDto);

        // when then
        mvc.perform(
                        post("/api/posts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(inputContent)
                                .principal(mockPrincipal)
                ).andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName(" 컨트롤러 단건 조회")
    void getPost() throws Exception {
        // given
        Long postId = 1L;
        User user = new User("컨트롤러테스트", "12341234", "123123123@gmail.com", "ROLE_USER");
        PostEntity postEntity = new PostEntity(new PostAddRequestDto("title","content"), user);
        PostResponseDto responseDto = new PostResponseDto(postEntity);

        given(postService.getPost(postId)).willReturn(responseDto);

        // when then
        mvc.perform(
                        get("/api/posts/" + postId)
                                .principal(mockPrincipal)
                ).andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(responseDto)))
                .andDo(print());

    }

    @Test
    @DisplayName("게시글 전체 조회")
    void getPosts() throws Exception{
        // given
        User user = new User("컨트롤러테스트", "12341234", "123123123@gmail.com", "ROLE_USER");
        PostEntity postEntity1 = new PostEntity(new PostAddRequestDto("title1","content1"), user);
        PostEntity postEntity2 = new PostEntity(new PostAddRequestDto("title2","content2"), user);
        PostEntity postEntity3 = new PostEntity(new PostAddRequestDto("title3","content3"), user);

        List<PostResponseDto> responseDtoList = new ArrayList<>();
        responseDtoList.add(new PostResponseDto(postEntity1));
        responseDtoList.add(new PostResponseDto(postEntity2));
        responseDtoList.add(new PostResponseDto(postEntity3));

        given(postService.getPosts()).willReturn(responseDtoList);

        // when then
        mvc.perform(
                        get("/api/posts")
                                .principal(mockPrincipal)
                ).andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(responseDtoList)))
                .andDo(print());
    }

    @Test
    void updatePost() {
    }

}