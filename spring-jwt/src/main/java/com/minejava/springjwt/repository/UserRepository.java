package com.minejava.springjwt.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.minejava.springjwt.document.User;

public interface UserRepository extends MongoRepository<User, String>{
    // Optional 
    Optional<User> findByUsername(String username);
}
