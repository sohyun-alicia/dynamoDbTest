package com.example.dynamodbtest.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@PropertySource("classpath:aws.properties")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    
    @Value("${cognito.clientId}")
    private String clientId;
    @Value("${cognito.logoutUrl}")
    private String logoutUrl;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .and()
                .authorizeRequests(authz -> authz.mvcMatchers("/")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .oauth2Login()
                .and()
                .logout()
                .logoutSuccessHandler(new CognitoOidcLogoutSuccessHandler(logoutUrl, clientId));
    }
    
    
}
