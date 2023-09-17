package com.score.scoredev.domain.user;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Entity
@Table(name = "user")
@Getter
public class User {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "school")
    private String school;

    @Column(name = "grade")
    private int grade;

    @Column(name = "ability_degree")
    private int abilityDegree;

    @Column(name = "join_date")
    private Date joinDate = new Date();

    @Column(name = "userKey")
    private String userKey;

    @Column(name = "refresh_token")
    private String refreshToken;
}
