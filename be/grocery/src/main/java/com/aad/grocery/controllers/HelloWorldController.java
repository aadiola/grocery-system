package com.aad.grocery.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello, World!";
    }
    
    @GetMapping("/goodbye")
    public String goodbyeWorld() {
        return "goodybe, World!";
    }
}
