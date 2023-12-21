package com.example.commonresult.controller;

import com.example.commonresult.result.ResponseResult;
import com.example.commonresult.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: lichengcan
 * @date: 2023-09-07 15:23
 * @description
 **/
@RestController
@RequestMapping("/test")
@ResponseResult
public class TestController {

    @GetMapping("/test")
    public Object test(Integer num){
        Map map  = new HashMap();
        int j = 10/num;
        map.put(1,1);
        return Result.success(map);
    }

    @GetMapping("/list")
    public List<Integer> list(Integer num){
        List<Integer> list = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            list.add(i);
        }
        return list;
    }

    @GetMapping("/exception")
    public void exception(Integer num){
        int j = 10/num;
    }

    @GetMapping("/testString")
    public String testString(Integer num){
        return "李承灿大帅逼";
    }


    @GetMapping("/testInteger")
    public Integer testInteger(Integer num){
        return num;
    }
}
