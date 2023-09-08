package com.leonardovaladao.spring.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leonardovaladao.spring.domains.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(String name);
}
