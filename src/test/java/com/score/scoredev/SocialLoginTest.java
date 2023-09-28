package com.score.scoredev;

import com.score.scoredev.controller.UserController;
import com.score.scoredev.dto.UserDto;
import com.score.scoredev.entity.user.AbilityDegree;
import com.score.scoredev.entity.user.Gender;
import com.score.scoredev.entity.user.User;
import com.score.scoredev.entity.user.UsingPurpose;
import com.score.scoredev.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
@ContextConfiguration(classes = ScoreDevApplication.class)
public class SocialLoginTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserController userController;


    @Test
    @DisplayName("UserDto를 통한 새로운 회원 엔티티 생성, 닉네임으로 유저 검색")
    public void saveNewUserTest() {
        // given
        UserDto testUserDto1 = new UserDto(true, "test1", Gender.FEMALE, "oo초등학교", 4, AbilityDegree.SOSO, UsingPurpose.DIET, "abc", "qq");

        // when
        User testUser1 = testUserDto1.toEntityForTest();
        userService.saveUserInfo(testUser1);
        User foundUser1 = userService.findUserByNickname("test1");

        // then
        assertThat(foundUser1).isInstanceOf(User.class);
        assertThat(foundUser1.getNickname()).isEqualTo("test1");
        assertThat(foundUser1.getGrade()).isEqualTo(4);
        assertThat(foundUser1.getUserKey()).isEqualTo("abc");
        assertThat(foundUser1.getGender()).isEqualTo(Gender.FEMALE);
        assertThat(foundUser1.getSchool()).isEqualTo("oo초등학교");
        assertThat(foundUser1.getAbilityDegree()).isEqualTo(AbilityDegree.SOSO);
        assertThat(foundUser1.getUsingPurpose()).isEqualTo(UsingPurpose.DIET);
        assertThat(foundUser1.getRefreshToken()).isEqualTo("qq");
    }
    
    @Test
    @DisplayName("userKey로 회원 정보 검색")
    public void findByUserIdTest() {
        // given
        UserDto testUserDto2 = new UserDto(false, "test2", Gender.FEMALE,"ㄴㄴ중학교", 1, AbilityDegree.WEAK, UsingPurpose.EATING_HABIT);

        // when
        User testUser2 = testUserDto2.toEntity();
        userService.saveUserInfo(testUser2);
        User foundUser2 = userService.findUserByUserKey("0").get();

        // then
        assertThat(foundUser2).isEqualTo(testUser2);

    }

    // 이미 존재하는 회원에 대한 테스트 필요
    @Test
    @DisplayName("해당 회원 정보 존재 여부 확인")
    public void checkWhetherNewOrNotTest() {
        // given
//        UserDto previousUserDto = new UserDto(false, "previous", Gender.MALE, "ㄴㄴ중학교", 1, AbilityDegree.WEAK, UsingPurpose.EATING_HABIT, "test1", "ww");
        UserDto newUserDto = new UserDto(true, "new", Gender.MALE, "ㅋㅋ고등학교", 3, AbilityDegree.STRONG, UsingPurpose.ENTRANCE_EXAM, "test2", "ee");

//        HttpServletResponse responseForPreviousUser = new MockHttpServletResponse();
        HttpServletResponse responseForNewUser = new MockHttpServletResponse();

        // when
//        User previousUser = previousUserDto.toEntityForTest();
//        userService.saveUserInfo(previousUser);
//        HttpHeaders previousUserHeader = userController.checkWhetherNewOrNot("test1", responseForPreviousUser);
        User newUser = newUserDto.toEntityForTest();
        HttpHeaders newUserHeader = userController.checkWhetherNewOrNot("test2", responseForNewUser);

        // then
        URI location = newUserHeader.getLocation();
        assert location != null;
        assertThat(location.getPath()).isEqualTo("/user/score/join/1");

    }
}
