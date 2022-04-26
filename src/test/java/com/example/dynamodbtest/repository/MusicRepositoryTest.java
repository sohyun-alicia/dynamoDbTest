package com.example.dynamodbtest.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.example.dynamodbtest.DynamoDbTestApplication;
import com.example.dynamodbtest.model.Music;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;


@SpringBootTest(classes = DynamoDbTestApplication.class)
@WebAppConfiguration
class MusicRepositoryTest {
    
    private DynamoDBMapper dynamoDBMapper;
    
    @Autowired
    private AmazonDynamoDB amazonDynamoDB;
    
    @Autowired
    private DynamoDB dynamoDB;
    
    @Autowired
    MusicRepository repository;
    
    Class<Music> tableClass = Music.class;
    
    Table table;
    
    String tableName;
    
    @BeforeEach
    public void setUp() {
        try {
            dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
            CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(tableClass);
            tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
    
            table = dynamoDB.createTable(tableRequest);
            tableName = table.getTableName();
            System.out.println(tableName + " 테이블을 생성중입니다. 다소 시간이 걸릴 수 있습니다.");
            table.waitForActive();
        }
        catch (InterruptedException e) {
            System.err.println(tableName + " 테이블 생성 요청에 실패했습니다.");
        }
    }
    
    @AfterEach
    void after() {
        try {
            Table table = dynamoDB.getTable(tableName);
            table.delete();
            System.out.println(tableName + " 테이블 삭제 요청중입니다. 다소 시간이 걸릴 수 있습니다.");
            table.waitForDelete();
        }
        catch (InterruptedException e) {
            System.err.println(tableName + " 테이블 삭제요청에 실패했습니다.");
        }
    }
    
    @Test
    public void 아이템저장() {
        Music music = new Music("Ariana Grande", "HoneyMoon Avenue");
        repository.save(music);
    
        Iterable<Music> all = repository.findAll();
        for (Music m : all) {
            String artist = m.getArtist();
            String songTitle = m.getSongTitle();
            System.out.println("artist = " + artist);
            System.out.println("songTitle = " + songTitle);
        }
    }
}