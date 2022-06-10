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
import org.springframework.test.context.web.WebAppConfiguration;


@SpringBootTest(classes = DynamoDbTestApplication.class)
@WebAppConfiguration
class UserRepositoryTest {
    
    @Autowired
    private DynamoDB dynamoDB;
    
    @Autowired
    private AmazonDynamoDB amazonDynamoDB;
    
    @Autowired
    private UserRepository repository;
    
    private String tableName = "com.example.dynamodbtest.model." + "User";
    
    @BeforeEach
    public void createTable() {
        try {
            DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
            Class<?> tableClass = Class.forName(tableName);
            CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(tableClass);
            tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
        
            Table table = dynamoDB.createTable(tableRequest);
            tableName = table.getTableName();
            System.out.println(tableName + " 테이블을 생성중입니다. 다소 시간이 걸릴 수 있습니다.");
            table.waitForActive();
        }
        catch (InterruptedException e) {
            System.err.println(tableName + " 테이블 생성 요청에 실패했습니다.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
  /*  @AfterEach
    void 테이블_삭제() {
        try {
            Table table = dynamoDB.getTable(tableName);
            table.delete();
            System.out.println(tableName + " 테이블 삭제 요청중입니다. 다소 시간이 걸릴 수 있습니다.");
            table.waitForDelete();
        }
        catch (InterruptedException e) {
            System.err.println(tableName + " 테이블 삭제요청에 실패했습니다.");
        }
    }*/

    @Test
    public void 유저_등록() {
        User user = new User();
        user.setUsername("test");
        user.setEmail("test@test.com");
        user.setPassword("test");
        user.setRole("USER");
        
        repository.save(user);
    
        Iterable<User> all = repository.findAll();
        for (User u : all) {
            String id = u.getId();
            String username = u.getUsername();
            String role = u.getRole();
            System.out.println("id = " + id);
            System.out.println("username = " + username);
            System.out.println("role = " + role);
        }
    }
}