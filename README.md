# sms-spring-boot-project
短信服务Spring Boot Starter ,目前只支持腾讯和阿里短信服务，腾讯短信已验证，阿里短信并未验证，期待各位小伙伴合作完善这个starter
# 前言
  短信服务在用户注册、登录、找回密码等相关操作中，可以让用户使用更加便捷，越来越多的公司都采用短信验证的方式让用户进行操作，从而提高用户的实用性。

#  Spring Boot Starter
   由于 Spring boot 的约定大于配置的理念，使得在使用Spring变得更加方便。Spring Boot 项目组提供了很多Starter ，让我们在使用 Spring 的时候变得非常容易。对于官方提供的Starter 采用 spring-boot-starter-xxx开头，对于非官方提供的Spring Boot Starter ,官方建议采用 xxxx-spring-boot-starter命名。
# 短信服务Starter

  ## 1. 开发工具及编译
> IntelliJ IDEA 2018.2.5
Maven 3.5
JDK 1.8
   >
  ## 2. 如何使用sms-spring-boot-starter
  ### (1). 在pom文件中引入
   ```
      <dependency>
            <groupId>com.github.jackieonway.sms</groupId>
            <artifactId>sms-spring-boot-starter</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
   ```
在pom.xml中配置maven中央仓库Snapshots地址
  ```
<repositories>
        <repository>
            <id>mavenRepoCenter</id>
            <name>Maven Development Snapshot Repository</name>
            <url>https://oss.sonatype.org/content/repositories/snapshots</url>
            <releases>
                <enabled>false</enabled>
            </releases>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
        </repository>
    </repositories>
```
### (2).在application.yml中加入
 ```
spring:
  jackieonway:
    sms:
      sms-type: tentcent  # 短信服务商 暂目前只有 腾讯和阿里的短信服务
      security-key: your security-key # 短信的私钥
      appid: your appid # 短信的应用id
      sign: your sign # 短信的签名
```
### (3). 在Springboot主程序中 加入 
 ```
@EnabledSmsAutoConfiguration
```
### (4). 创建发送短信程序

```
@RestController
public class HelloController {

    @Autowired
    private SmsService smsService;

    @GetMapping("/sayHello")
    public Object sayHello() {
        // 具体参数请参考各短信服务商
        // your template params
        String[] paramst = {"5678","5"};
        TencentSmsRequest tencentSmsRequest = new TencentSmsRequest();
        tencentSmsRequest.setPhoneNumber(new String[]{"your cellphone"});
        tencentSmsRequest.setParams(paramst);
        return smsService.sendTemplateSms("your template id", tencentSmsRequest);
    }
}

```
### (5). 发送
访问 localhost:8080/sayHello
![Screenshot_2019-05-17-22-54-13-510_com.android.mm.png](https://upload-images.jianshu.io/upload_images/12660257-e408bef0f9735a2a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
## 3. SmsService接口

```
    /**
     *  单个发送短信
     * @param params 根据对应的短信服务商所需信息填写
     */
    public Object sendSms(Integer type,Object params) throws SmsException;

    /**
     * 单个发送模板短信
     * @param tempalteId 短信模板id
     * @param params 根据对应的短信服务商所需信息填写
     */
    public Object sendTemplateSms(String tempalteId, Object params) throws SmsException;

    /**
     *  批量发送短信
     * @param params 根据对应的短信服务商所需信息填写
     */
    public Object sendBatchSms(int type,Object params) throws SmsException;

    /**
     * 批量发送模板短信
     * @param tempalteId 短信模板id
     * @param params 根据对应的短信服务商所需信息填写
     */
    public Object sendBatchTemplateSms(String tempalteId, Object params) throws SmsException;
```
该接口提供了单个和群发短信与模板短信，注意目前只提供了同步发送方法，异步发送方法，请结合线程池进行。
# 总结
只是针对腾讯短信服务进行了试验，阿里的短信服务并未真正验证，希望各位小伙伴能合作完成验证，共同完善该starter，觉得有用请starter该项目。如果只想使用而腾讯云短信业务的话，按照Demo使用即可。


github地址:**[sms-spring-boot-project](https://github.com/jackieonway/sms-spring-boot-project)**
