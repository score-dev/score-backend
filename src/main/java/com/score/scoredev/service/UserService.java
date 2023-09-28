package com.score.scoredev.service;

import com.score.scoredev.entity.user.User;
import com.score.scoredev.repository.UserRepository;
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
