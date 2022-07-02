package com.minejava.springjwt.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.minejava.springjwt.document.RefreshToken;

public interface TokenRefreshRepository extends MongoRepository<RefreshToken, String>{
    
}
