package com.example.dynamodbtest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;


@Controller
public class MainController {
    
    @GetMapping("/")
    public String home(Principal principal,
                       HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse) {
        return "home";
    }
    
    @GetMapping("/test")
    public String test(Principal principal) {
        return "test";
    }
    
    @GetMapping("/logout1")
    public String logout(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse) {
        return "home";
    }
    
}
