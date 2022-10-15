package com.project.miqaatattendanceservice.users.domain;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UsersRepository extends MongoRepository<User, Integer> {

    Set<User> findByHofIts(Integer hofId);
}
