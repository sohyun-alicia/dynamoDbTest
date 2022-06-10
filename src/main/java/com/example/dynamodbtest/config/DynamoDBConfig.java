package com.example.dynamodbtest.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.example.dynamodbtest.service.TableService;
import com.example.dynamodbtest.serviceImpl.TableServiceImpl;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:aws.properties")
@EnableDynamoDBRepositories(basePackages = "com.example.dynamodbtest.repository")
public class DynamoDBConfig {
    @Value("${aws.dynamodb.endpoint}")
    private String amazonDynamoDBEndpoint;
    
    @Value("${aws.accessKey}")
    private String amazonAWSAccessKey;
    
    @Value("${aws.secretKey}")
    private String amazonAWSSecretKey;
    
    @Value("${region}")
    private String region;
    
    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        AwsClientBuilder.EndpointConfiguration endpointConfiguration =
                new AwsClientBuilder.EndpointConfiguration(amazonDynamoDBEndpoint, region);
    
        return AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(amazonAWSCredentials()))
                .withEndpointConfiguration(endpointConfiguration)
                .build();
    }
    
    @Bean
    public DynamoDB dynamoDB() {
        return new DynamoDB(amazonDynamoDB());
    }
    
    @Bean
    public AWSCredentials amazonAWSCredentials() {
        return new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey);
    }
    
    @Bean
    public TableService tableService() {
        return new TableServiceImpl(dynamoDB(), amazonDynamoDB());
    }
}
