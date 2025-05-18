package com.restApiB.controller;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.HashMap;
import java.util.Map;


@RestController
@EnableWebMvc
public class TestController {
    @RequestMapping(path = "/test", method = RequestMethod.GET)
    public Map<String, String> ping() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello from Test API B!");
        return response;
    }
}
