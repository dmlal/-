package com.sparta.backoffice.post.dto;

import com.sparta.backoffice.post.entity.Post;
import com.sparta.backoffice.user.constant.UserRoleEnum;
import com.sparta.backoffice.user.dto.UserSimpleDto;
import com.sparta.backoffice.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ChildPostResponseDto {
    Long id;
    String content;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;
    UserSimpleDto userSimpleDto;
    Integer replyCount;
    Long parentPostId;
    Integer likesCount;
    Boolean isDeleted;
    Boolean isPrivate;
    List<ChildPostResponseDto> childs;

    public ChildPostResponseDto(Post post, User loginUser, List<Long> followingIdList) {
        this.id = post.getId();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.replyCount = post.getChildPosts().size();
        if (post.getParentPost() != null) {
            this.parentPostId = post.getParentPost().getId();
            this.userSimpleDto = new UserSimpleDto(post.getUser());
        }
        this.likesCount = post.getLikes().size();
        this.isDeleted = post.isDeleted();

        if (loginUser == null) {
            //비회원 일 경우
            this.isPrivate = post.getUser().getIsPrivate();
        } else if (loginUser.getRole().equals(UserRoleEnum.ADMIN)) {
            this.isPrivate = false;//모든 게시물이 보인다.
        } else {
            if (post.getUser().getIsPrivate()) {
                if (post.getUser().equals(loginUser) || followingIdList.contains(post.getUser().getId())) {
                    this.isPrivate = false;
                } else {
                    this.isPrivate = true;
                }
            } else {
                this.isPrivate = false;
            }
        }

        if (!isDeleted && isPrivate) {
            this.content = "이 계정 소유자가 게시물을 볼 수 있는 사용자를 제한 하고 있어 이 게시물은 볼 수 없습니다.";
        } else {
            this.content = post.getContent();
        }

        this.childs = post.getChildPosts().stream().map(
                (post1) -> new ChildPostResponseDto(post1, loginUser, followingIdList)
        ).toList();
    }
}
