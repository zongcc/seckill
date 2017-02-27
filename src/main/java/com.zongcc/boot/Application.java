package com.zongcc.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;

/**
 * Created by zongcc on 2017/1/7.
 */
@Controller
@EnableAutoConfiguration
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class);
    }
}
