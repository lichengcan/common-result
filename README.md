# 公共统一返回
直接返回String类型时，报错了

![](https://s2.loli.net/2023/12/26/n5EjAcL9sZ3Y1NT.png)
`    @Override
public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
Class<? extends HttpMessageConverter<?>> selectedConverterType,
ServerHttpRequest request, ServerHttpResponse response) {
// 提供一定的灵活度，如果body已经被包装了，就不进行包装
// 因为String不属于Result的子类，所以这里直接返回body
if (body instanceof Result||body instanceof String) {
return body;
}
return Result.success(body);
}`
特殊处理一下String类型
要么就这样返回
@GetMapping("/testString2")
public Result<String> testString2(){
String test = new String("李承灿大帅");
return Result.success(test);
}
