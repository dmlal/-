package com.sparta.newsfeedt6.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class EmailVerification {
    @Id
    private String email;

    @Column
    private String verificationCode;

    @Column
    private LocalDateTime expiredCodeTime;
}
