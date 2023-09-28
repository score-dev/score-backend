package com.score.scoredev.dto;

import com.score.scoredev.entity.user.AbilityDegree;
import com.score.scoredev.entity.user.Gender;
import com.score.scoredev.entity.user.User;
import com.score.scoredev.entity.user.UsingPurpose;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class UserDto {
    private boolean marketing;
    private String nickname;
    private Gender gender;
    private String school;
    private int grade;
    private AbilityDegree abilityDegree;
    private UsingPurpose usingPurpose;
    private String userKey;
    private String refreshToken;

    public UserDto(boolean marketing, String nickname, Gender gender, String school, int grade, AbilityDegree abilityDegree, UsingPurpose usingPurpose) {
        this.marketing = marketing;
        this.nickname = nickname;
        this.gender = gender;
        this.school = school;
        this.grade = grade;
        this.abilityDegree = abilityDegree;
        this.usingPurpose = usingPurpose;
    }

    public UserDto(boolean marketing, String nickname, Gender gender, String school, int grade, AbilityDegree abilityDegree, UsingPurpose usingPurpose, String userKey, String refreshToken) {
        this.marketing = marketing;
        this.nickname = nickname;
        this.gender = gender;
        this.school = school;
        this.grade = grade;
        this.abilityDegree = abilityDegree;
        this.usingPurpose = usingPurpose;
        this.userKey = userKey;
        this.refreshToken = refreshToken;
    }

    public User toEntity() {
        return User.builder()
                .marketing(marketing)
                .nickname(nickname)
                .gender(gender)
                .school(school)
                .grade(grade)
                .abilityDegree(abilityDegree)
                .usingPurpose(usingPurpose)
                .build();
    }

    public User toEntityForTest() {
        return User.builder()
                .marketing(marketing)
                .nickname(nickname)
                .gender(gender)
                .school(school)
                .grade(grade)
                .abilityDegree(abilityDegree)
                .usingPurpose(usingPurpose)
                .userKey(userKey)
                .refreshToken(refreshToken)
                .build();
    }
}
