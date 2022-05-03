package com.example.dynamodbtest.controller;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.example.dynamodbtest.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    
    private final AmazonDynamoDB amazonDynamoDB;
    
    @PostMapping("/createUser")
    public void createUser() {
        DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
        CreateTableRequest request = dynamoDBMapper.generateCreateTableRequest(User.class);
        
    
    }
}
