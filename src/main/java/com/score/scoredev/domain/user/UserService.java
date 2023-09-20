package com.score.scoredev.domain.user;

import com.score.scoredev.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void saveUserInfo(User user) {
        userRepository.save(user);
    }

    public User join(boolean marketing, String nickname, Gender gender, String school, int grade, AbilityDegree abilityDegree, UsingPurpose usingPurpose) {
        saveUserInfo(new UserDto(marketing, nickname, gender, school, grade, abilityDegree, usingPurpose).toEntity());
        return findUserByNickname(nickname); // 저장된 User 엔티티를 리턴
    }

    public User findUserByNickname(String nickname) {
        Optional<User> user = userRepository.findByNickname(nickname);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new IllegalArgumentException("존재하지 않는 회원");
        }
    }

    public Optional<User> findUserByUserKey(String userKey) {
        return userRepository.findByUserId(userKey);
    }
}
