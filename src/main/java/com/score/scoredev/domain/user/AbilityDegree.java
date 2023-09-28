package com.score.scoredev.domain.user;

public enum AbilityDegree {
    VERY_WEAK(0),
    WEAK(25),
    SOSO(50),
    STRONG(75),
    VERY_STRONG(100);

    // 인스턴스 필드 추가
    private final int abilityDegree;

    // 생성자 추가
    AbilityDegree(int abilityDegree) {
        this.abilityDegree = abilityDegree;
    }

    // 인스턴스 필드 get 메서드 추가
    public int getAbilityDegree() {return abilityDegree;}

}
