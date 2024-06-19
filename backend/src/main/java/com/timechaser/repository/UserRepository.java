package com.timechaser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.timechaser.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
