package com.example.dynamodbtest.service;

import org.springframework.stereotype.Service;

@Service
public interface TableService {
    void createTable(String tableName);
}
