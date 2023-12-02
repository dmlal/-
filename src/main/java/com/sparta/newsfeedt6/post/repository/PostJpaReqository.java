package com.sparta.newsfeedt6.post.repository;

import com.sparta.newsfeedt6.post.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostJpaReqository extends JpaRepository<PostEntity, Long> {
    List<PostEntity> findAllByOrderByCreatedAtDesc();

    List<PostEntity> findByTitleContaining(String title);

}