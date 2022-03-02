package com.zbl.zh.mis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class Test {

    @GetMapping("hello")
    public String hello(){
        return "Hello World!";
    }
    
}
