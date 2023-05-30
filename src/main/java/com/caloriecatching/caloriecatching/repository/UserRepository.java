package com.caloriecatching.caloriecatching.repository;

import com.caloriecatching.caloriecatching.entity.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserTable, Integer> {

    Optional<UserTable> findByLoginID(String loginID);

}
