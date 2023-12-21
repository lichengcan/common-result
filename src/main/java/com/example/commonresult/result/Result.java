package com.example.commonresult.result;

import lombok.Data;

/**
 * 统一返回数据结构
 *
 * @param <T>
 */
@Data
public class Result<T> {
    private String code;
    private String message;
    private T data;

    public Result() {
    }

    public Result(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), data);
    }

    public static Result<?> failed(String code, String message) {
        return new Result<>(code, message);
    }
}
