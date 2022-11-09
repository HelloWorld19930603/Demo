package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author chengcheng
 * @Date 2022-11-09
 **/
@RestController
public class DemoController {

    @RequestMapping("/test")
    public String test() {
        System.out.println("hello");
        return "success";
    }

}
