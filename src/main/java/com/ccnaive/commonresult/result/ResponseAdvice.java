package com.ccnaive.commonresult.result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 定义全局性的控制器
 * basePackages 作用范围
 * @author lichengcan
 */
@RestControllerAdvice(basePackages = "com.ccnaive.commonresult")
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    private static final Logger LOG = LoggerFactory.getLogger(ResponseAdvice.class);

    /**
     * 环境变量
     */
    @Value("${spring.profiles.active}")
    private String env;

    /**
     * 是否需要进行统一返回封装
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        //定义到类级别：如果类上有这个注解，那就封装
        if (returnType.getDeclaringClass().getAnnotation(ResponseResult.class)!=null) {
            return true;
        }
        //也可以定义到方法级
//        if (returnType.getMethodAnnotation(ResponseResult.class)!=null) {
//            return true;
//        }
        return false;
    }


    /**
     * 此方法才是真正的统一返回封装逻辑
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        // 提供一定的灵活度，如果body已经被包装了，就不进行包装
        // 因为String不属于Result的子类，所以这里直接返回body
        if (body instanceof Result||body instanceof String) {
            return body;
        }
        return Result.success(body);
    }


    /**
     * 处理参数校验失败异常
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        String defaultMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
        return buildErrorResponse(ResultEnum.SYSTEM_EXCEPTION.getCode(), defaultMessage);
    }

    /**
     * 处理 Request Valid 异常
     */
    @ResponseBody
    @ExceptionHandler(BindException.class)
    public Result handleBindException(BindException ex) {
        String defaultMessage = ex.getAllErrors().get(0).getDefaultMessage();
        return buildErrorResponse(ResultEnum.SYSTEM_EXCEPTION.getCode(), defaultMessage);
    }

    /**
     * 处理 BeanInstantiationException 异常
     */
    @ResponseBody
    @ExceptionHandler(BeanInstantiationException.class)
    public Result handleBeanInstantiationException(BeanInstantiationException ex) {
        Throwable cause = ex.getCause();
        return buildErrorResponse(ResultEnum.SYSTEM_EXCEPTION.getCode(), cause.getMessage());
    }

    /**
     * 处理所有其他异常
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception ex) {
        LOG.error("系统异常", ex);
        String message;
        if (ex instanceof HttpMessageNotReadableException) {
            message = "请求参数格式错误";
        } else if (ex instanceof HttpRequestMethodNotSupportedException) {
            message = "不支持的请求方式";
        } else {
            message = "系统繁忙，请稍后再试";
        }
        return buildErrorResponse(ResultEnum.SYSTEM_EXCEPTION.getCode(), message);
    }

    /**
     * 构建错误响应
     */
    private Result buildErrorResponse(String code, String message) {
        if ("dev".equals(env)) {
            return Result.failed(code, message);
        } else {
            return Result.failed(code, null);
        }
    }
}
