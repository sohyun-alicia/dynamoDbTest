package com.example.dynamodbtest.repository;

import com.example.dynamodbtest.model.User;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@EnableScan
@Component
public interface UserRepository extends CrudRepository<User, Long> {
}
