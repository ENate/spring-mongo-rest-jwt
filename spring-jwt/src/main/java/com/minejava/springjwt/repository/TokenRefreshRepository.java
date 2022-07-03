package com.minejava.springjwt.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.minejava.springjwt.document.RefreshToken;

public interface TokenRefreshRepository extends MongoRepository<RefreshToken, String>{
    void deleteByOwner_Id(ObjectId id);
    default void deleteByOwner_Id(String id) {
        deleteByOwner_Id(new ObjectId(id));
    };
}
