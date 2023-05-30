package com.caloriecatching.caloriecatching.repository;

import com.caloriecatching.caloriecatching.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByLoginId(String loginID);

}
