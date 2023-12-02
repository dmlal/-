package com.sparta.newsfeedt6.security;

import com.sparta.newsfeedt6.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserDetailsImpl implements UserDetails {

    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }



    public User getUser() {
        return user;
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 인터페이스라서 반드시 구현해야합니다.  하지만 jwt액세스토큰으로 인증, 인가를 하기 때문에 보안상 비밀번호를 받아올 필요가 없습니다.
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // 인터페이스라서 반드시 구현해야합니다.  하지만 프로젝트 구현사항에서 사용자 관리자 권한을 구분하지 않았기 때문에 모두의 권한을 통일할 예정입니다.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        String authority = "ROLE_USER";

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @Override
    public boolean isEnabled(){
        return true;
    }

}
