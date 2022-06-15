package com.example.dynamodbtest.controller;

import com.example.dynamodbtest.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TableController {
    
    private final TableService tableService;
    
    // user table 생성
    @PostMapping("/createTable")
    public void createTable(String tableName) {
        tableService.createTable(tableName);
    }
}
