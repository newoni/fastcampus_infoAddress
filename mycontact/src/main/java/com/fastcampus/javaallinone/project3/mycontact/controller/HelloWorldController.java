package com.fastcampus.javaallinone.project3.mycontact.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//@Controller
//@ResponseBody
@RestController //controller, responsebody는 restController를 포함함
public class HelloWorldController {

    @PostMapping(value="/api/helloWorld")
    public String helloWorld(){
        return "HELLO WORLD";
    }
}
