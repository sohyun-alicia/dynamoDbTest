package com.example.dynamodbtest.serviceImpl;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.example.dynamodbtest.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {
    
    private final DynamoDB dynamoDB;
    private final AmazonDynamoDB amazonDynamoDB;
    
    @Override
    public void createTable(String tableName) {
        try {
            DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
            Class<?> tableClass = Class.forName("com.example.dynamodbtest." + tableName);
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
}
