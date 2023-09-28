package com.score.scoredev.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.userKey = :userKey")
    Optional<User> findByUserId(@Param("userKey") String userKey);

    List<User> findAll();   // 전체 조회
}
