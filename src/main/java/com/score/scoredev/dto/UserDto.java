package com.score.scoredev.dto;

import com.score.scoredev.domain.user.AbilityDegree;
import com.score.scoredev.domain.user.Gender;
import com.score.scoredev.domain.user.User;
import com.score.scoredev.domain.user.UsingPurpose;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDto {
    private boolean marketing;
    private String nickname;
    private Gender gender;
    private String school;
    private int grade;
    private AbilityDegree abilityDegree;
    private UsingPurpose usingPurpose;

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
}
