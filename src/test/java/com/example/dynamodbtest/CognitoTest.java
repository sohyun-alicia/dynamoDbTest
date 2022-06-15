package com.example.dynamodbtest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;


@SpringBootTest(classes = DynamoDbTestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CognitoTest {
    
    @Autowired
    TestRestTemplate restTemplate;
    
    @Test
    public void revokeEndpoint() {
        String url = "http://localhost:8080/oauth2/revoke";
        restTemplate.getRestTemplate().setInterceptors(
                Collections.singletonList(((request, body, execution) -> {
                    request.getHeaders()
                            .add("Authorization", "JSESSIONID=D8AA33C8C5D3B43FDEB4F3F11B57840D");
                    return execution.execute(request, body);
                }))
        );
        String object = restTemplate.getForObject(url, String.class);
        System.out.println("object = " + object);
        
    }
    
    @Test
    public void getUser() {
        String url = "http://localhost:8080/";
        restTemplate.getRestTemplate().setInterceptors(
                Collections.singletonList(((request, body, execution) -> {
                    request.getHeaders()
                            .add("Cookie", "JSESSIONID=D8AA33C8C5D3B43FDEB4F3F11B57840D");
                
                    return execution.execute(request, body);
                }))
        );
        String object = restTemplate.getForObject(url, String.class);
        System.out.println("object = " + object);
    }
    
    @Test
    public void tokenEndpoint() {
        String url = "http://localhost:8080/oauth2/token";
        
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        header.setBasicAuth("M2djMHJsYjR2Z2xsYm9ucW5pZjVpMDU3NjQ6bzZ0NnY3ajBvZWJ0M2Vsa2FsdDFsOXFkNThyMzNhMnNoamc5MnQydmpqaXZyNmlqOGZn");
        header.set("Cookie", "JSESSIONID=D8AA33C8C5D3B43FDEB4F3F11B57840D");
    
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type","client_credentials&");
        map.add("client_id","3gc0rlb4vgllbonqnif5i05764");
        map.add("scope","openid");
        map.add("redirect_uri","http://localhost:8080/login/oauth2/code/cognito");
        map.add("code","AUTHORIZATION_CODE&");
    
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, header);
    
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        System.out.println("exchange = " + exchange);
    }
    
    @Test
    public void test() {
        String url = "http://localhost:8080/test";
        
        restTemplate.getRestTemplate().setInterceptors(
                Collections.singletonList(((request, body, execution) -> {
                    request.getHeaders()
                            .add("Cookie", "JSESSIONID=284EF21EE1575C0C243D5F54E52692AD");
                    
                    return execution.execute(request, body);
                }))
        );
    
        String object = restTemplate.getForObject(url, String.class);
        System.out.println("object = " + object);
    
    }
}
