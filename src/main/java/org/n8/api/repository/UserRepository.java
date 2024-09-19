package org.n8.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.n8.api.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

}