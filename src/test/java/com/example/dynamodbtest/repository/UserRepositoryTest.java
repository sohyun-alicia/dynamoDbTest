package com.example.dynamodbtest.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.example.dynamodbtest.DynamoDbTestApplication;
import com.example.dynamodbtest.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;


@SpringBootTest(classes = DynamoDbTestApplication.class)
@WebAppConfiguration
@TestPropertySource(properties = {
        "amazon.dynamodb.endpoint=http://localhost:8000/",
        "amazon.aws.accesskey=test1",
        "amazon.aws.secretkey=test231" })
class UserRepositoryTest {
    
    private DynamoDB dynamoDB;
    
    private DynamoDBMapper dynamoDBMapper;
    
    @Autowired
    private AmazonDynamoDB amazonDynamoDB;
    
    @Autowired
    UserRepository repository;
    
    
    @BeforeEach
    void setUp() {
        if (dynamoDB.getTable("User") == null) {
            dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
    
            CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(User.class);
            tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
            amazonDynamoDB.createTable(tableRequest);
        }
        
    }
    
    @AfterEach
    void after() {
        Table user = dynamoDB.getTable("User");
        user.delete();
    }
    
    @Test
    public void test() {
        User user = new User("1", "test", "test", "test@test.com", "USER");
        repository.save(user);
    
        Iterable<User> all = repository.findAll();
        for (User u : all) {
            String username = u.getUsername();
            String role = u.getRole();
            System.out.println("username = " + username);
            System.out.println("role = " + role);
        }
    }
}