package com.sparta.newsfeedt6.security.jwt;

import com.sparta.newsfeedt6.security.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static com.sparta.newsfeedt6.security.jwt.JwtUtil.REFRESH_HEADER;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
            throws ServletException, IOException {

        String tokenValue = jwtUtil.getTokenFromRequest(req);

        if (StringUtils.hasText(tokenValue)) {
            log.info(tokenValue);
            tokenValue = jwtUtil.substringToken(tokenValue);

            try {
                if (jwtUtil.validateToken(tokenValue)) {
                    Claims info = jwtUtil.getUserInfoFromToken(tokenValue);
                    setAuthentication(info.getSubject());
                }
            } catch (ExpiredJwtException e) {  // 액세스토큰 만료시 에러 출력
                String refreshToken = getRefreshTokenFromRequest(req);
                if (jwtUtil.validateToken(refreshToken)) {   // 리프레시토큰 검증 후 유효시 액세스토큰 재발급
                    Claims info = jwtUtil.getUserInfoFromToken(refreshToken);
                    String newAccessToken = jwtUtil.createToken(info.getSubject());
                    res.addHeader("Authorization", "Bearer " + newAccessToken);
                    setAuthentication(info.getSubject());
                } else {
                    res.sendRedirect("/login");
                    return;
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                return;
            }
        }
        filterChain.doFilter(req, res);

    }

    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    private String getRefreshTokenFromRequest(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println("Cookie name: " + cookie.getName());
                if (cookie.getName().equals(REFRESH_HEADER)) {
                    try {
                        return URLDecoder.decode(cookie.getValue(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        return null;
                    }
                }
            }
        }
        return null;
    }
}

//   리프레시토큰 에러나면 되돌릴 코드
//    @Override
//    protected void doFilterInternal (HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
//            throws ServletException, IOException {
//
//        String tokenValue = jwtUtil.getTokenFromRequest(req);
//
//        if (StringUtils.hasText(tokenValue)) {
//            tokenValue = jwtUtil.substringToken(tokenValue);
//            log.info(tokenValue);
//
//            if (!jwtUtil.validateToken(tokenValue)) {
//                log.error("token error");
//                return;
//            }
//            Claims info = jwtUtil.getUserInfoFromToken(tokenValue);
//            try {
//                setAuthentication(info.getSubject());
//            } catch (Exception e) {
//                log.error(e.getMessage());
//                return;
//            }
//        }
//    }
