package com.example.commonresult.result;

/**
 * 常用结果的枚举
 */
public enum ResultEnum {
    SUCCESS("00000", "请求成功"),
    VALIDATE_FAILED("2002", "参数校验失败"),
    COMMON_FAILED("2003", "接口调用失败"),
    FORBIDDEN("2004", "没有权限访问资源"),
    USER_EXCEPTION("A0001", "用户端错误"),
    SYSTEM_EXCEPTION("B0001", "系统执行出错"),
    THIRD_PARTY_SERVICE_INVOKE_EXCEPTION("C0001", "调用第三方服务出错");

    private String code;
    private String message;

    // 构造方法
    ResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }


}
