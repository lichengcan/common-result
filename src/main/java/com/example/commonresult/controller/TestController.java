package com.example.commonresult.controller;

import com.example.commonresult.result.ResponseResult;
import com.example.commonresult.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: lichengcan
 * @date: 2023-09-07 15:23
 * @description
 **/
@ResponseResult
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/test")
    public Object test(){
        Map map  = new HashMap();
//        int i = 0;
//        int j = 10/i;

        map.put(1,1);
        return Result.success(map);
    }
}
