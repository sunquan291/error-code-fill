# 错误码填充
## 1、引入对应的pom依赖则直接生效

```xml
 <groupId>com.zte.sdn.oscp</groupId>
 <artifactId>error-code-spring-boot-starter</artifactId>
 <version>2.4.4</version>
```

## 2、在REST调用地方，使用对应的封装方法：

```java
    @GetMapping(value = "/list")
    public MultiResponse<ATAMetricCO> test() {
      return  MultiResponse.buildFailure("00000001", "error xxx");
    }
```

## 3、并在classPath下定义错误码文件 xxxx-errorcode-en_US.json

```json
{
    "errcodes":[
        {"code":"00000001","level":"ERROR","label":"Error occurred in server."}
    ]
}
```

## 4、执行后的返回值，可以看到返回的errMessage已补充上在错误码文件配置的字串

```json
{"errCode":"00000001","errMessage":"Error occurred in server.","success":false}
```

