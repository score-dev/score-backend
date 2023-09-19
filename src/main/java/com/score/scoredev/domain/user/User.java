package com.score.scoredev.domain.user;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Getter
@Entity
@Table(name = "user")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")   // 사용자 id
    private Long id;

    @Column(name = "marketing")  // 마케팅 정보 수신 동의
    private boolean marketing;

    @Column(name = "nickname", length = 20) // 닉네임
    private String nickname;

    @Column(name = "gender")  // 성별

    private String gender;

    @Column(name = "school")  // 재학 중인 학교
    private String school;

    @Column(name = "grade")  // 학년
    private int grade;

    @Column(name = "ability_degree")  // 기초 체력
    private AbilityDegree abilityDegree;

    @Column(name = "using_purpose", unique = false) // 사용 목적
    private UsingPurpose usingPurpose;

    @Column(name = "join_date")
    private Date joinDate = new Date();

    @Column(name = "userKey")   // 소셜 로그인 id
    private String userKey;

    @Column(name = "refresh_token")
    private String refreshToken;
}
