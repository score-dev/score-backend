package com.score.scoredev.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void join(User user) {
        userRepository.save(user);
    }

    public User findUserByUserKey(String userKey) {
        Optional<User> user = userRepository.findByUserId(userKey);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new IllegalArgumentException("존재하지 않는 회원.");
        }
    }
}
