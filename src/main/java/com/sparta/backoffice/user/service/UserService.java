package com.sparta.backoffice.user.service;

import com.sparta.backoffice.global.exception.ApiException;
import com.sparta.backoffice.post.entity.Post;
import com.sparta.backoffice.post.service.PostService;
import com.sparta.backoffice.user.constant.UserRoleEnum;
import com.sparta.backoffice.user.dto.UserInfoDto;
import com.sparta.backoffice.user.dto.request.PasswordUpdateRequestDto;
import com.sparta.backoffice.user.dto.request.ProfileUpdateRequestDto;
import com.sparta.backoffice.user.dto.response.ProfileUpdateResponseDto;
import com.sparta.backoffice.user.entity.PasswordHistory;
import com.sparta.backoffice.user.entity.User;
import com.sparta.backoffice.user.repository.PasswordHistoryRepository;
import com.sparta.backoffice.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.sparta.backoffice.global.constant.ErrorCode.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordHistoryRepository passwordHistoryRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PostService postService;

    @Transactional
    public ProfileUpdateResponseDto updateProfile(Long userId, ProfileUpdateRequestDto requestDto, User authUser) {
        User user = foundUser(userId);

        if (!authUser.getRole().equals(UserRoleEnum.ADMIN)) {
            checkUserPermission(user, authUser);
        }

        String newNickname = requestDto.getNickname();
        if (!newNickname.equals(user.getNickname()) && userRepository.existsByNickname(newNickname)) {  // 기존닉네임과 같은지 , 닉네임이 중복인지
            throw new ApiException(CAN_NOT_CHANGE_NICKNAME);
        }

        User newProfile = user.updateProfile(requestDto);

        ProfileUpdateResponseDto responseDto = new ProfileUpdateResponseDto(newProfile);

        return responseDto;
    }

    @Transactional
    public void updatePassword(Long userId, PasswordUpdateRequestDto requestDto, User authUser) {
        User user = foundUser(userId);

        checkUserPermission(user, authUser);

        Pageable recentPasswords = PageRequest.of(0, 3);
        List<PasswordHistory> recentThreePasswords = passwordHistoryRepository.findTop3ByUserIdOrderByModifiedAtDesc(userId, recentPasswords);

        String encodedNewPassword = passwordEncoder.encode(requestDto.getNewPassword());

        for (PasswordHistory passwordHistory : recentThreePasswords) {
            if (passwordEncoder.matches(requestDto.getPassword(), passwordHistory.getPassword())) {
                throw new ApiException(RECENTLY_USED_PASSWORD);
            }
        }

        user.updatePassword(encodedNewPassword);
        userRepository.save(user);

        PasswordHistory updatedPasswordHistory = new PasswordHistory(user, encodedNewPassword);
        passwordHistoryRepository.save(updatedPasswordHistory);
    }


    public List<UserInfoDto> getAllUsers(Integer cursor, Integer size, String dir) {
        Sort sort = Sort.by(dir.equalsIgnoreCase("desc") ?
                Sort.Direction.DESC : Sort.Direction.ASC, "createdAt");

        Pageable pageable = PageRequest.of(cursor, size, sort);

        Page<User> users = userRepository.findAll(pageable);

        return users.stream().map(UserInfoDto::new).toList();
    }

    @Transactional
    public void deleteUser(Long userId, User adminUser) {
        User user = foundUser(userId);

        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            throw new ApiException(NOT_ALLOW_ADMIN_DELETE);
        }

        //유저가 쓴 게시글-> 유저 정보 지우고 / 삭제 상태로 변경(흔적은 남겨두기)
        List<Post> posts = user.getPostList();
        for (Post post : posts) {
            postService.deletePost(post.getId(), adminUser);
            post.changeStateIsDeletedAndRemoveWriter();
        }

        userRepository.delete(user);
    }

    @Transactional
    public void blockUser(Long userId) {
        User user = foundUser(userId);

        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            throw new ApiException(NOT_ALLOW_ADMIN_BLOCK);
        } else if (user.getRole().equals(UserRoleEnum.BLOCK)) {
            throw new ApiException(ALREADY_BLOCK_USER);
        } else {
            user.block();
        }
    }

    @Transactional
    public void unblockUser(Long userId) {
        User user = foundUser(userId);

        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            throw new ApiException(NOT_ALLOW_ADMIN_BLOCK);
        } else if (user.getRole().equals(UserRoleEnum.USER)) {
            throw new ApiException(NOT_BLOCKED_USER);
        } else {
            user.unblock();
        }
    }

    public UserInfoDto getUserInfo(Long userId, User loginUser) {
        User findUser = foundUser(userId);
        return new UserInfoDto(findUser);
    }

    public User foundUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new ApiException(NOT_FOUND_USER_ERROR));
    }

    public void checkUserPermission(User user, User authUser) {
        if (!user.getUsername().equals(authUser.getUsername())) {
            throw new ApiException(DENIED_AUTHORITY);
        }
    }

}
