package com.example.commonresult.controller;

import com.example.commonresult.result.PageRequest;
import com.example.commonresult.result.ResponseResult;
import com.example.commonresult.result.Result;
import com.example.commonresult.result.ResultException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
@ResponseResult
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/test")
    public Result test(Integer num){
        Map map  = new HashMap();
        int j = 10/num;
        map.put(1,1);
        return Result.success(map);
    }

    @GetMapping("/list")
    public Result list(Integer num){
        List<Integer> list = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            list.add(i);
        }
        return Result.success(list);
    }

    @GetMapping("/exception")
    public Result exception(Integer num){
        int j = 10/num;
        throw new ResultException("456","777");
    }


}
