

# 一、Spring Boot入门

## 1、Spring Boot简介

> 简化Spring的一个框架；
>
> 是整个Spring技术栈的一个整合；
>
> 是简化Spring技术栈的快速开发脚手架。

生效
开启 

## 2、Spring Boot入门案例

> 通过创建一个maven项目，改造成一个简单的Spring Boot项目。

官网指引：https://spring.io/guides/gs/spring-boot/

### （1）新建一个普通的maven项目

![image-20201214213246257](https://gitee.com/yj1109/cloud-image/raw/master/img/image-20201214213246257.png)

### （2）导入父项目依赖以及配置

```xml
<modelVersion>4.0.0</modelVersion>
<!-- 导入父项目 -->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.6.6</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>

<!-- 导入简单的依赖 -->
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>

<!-- 可以通过maven进行jar包生成 -->
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
</plugin>
```

### （3）创建一个启动类，进行配置

==注意文件的位置：保证它在controller、mapper包的同级==

```java
@SpringBootApplication
public class QuickStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuickStartApplication.class, args);
    }
}
```

### （4）创建一个HelloController

```java
@Controller
public class HelloController {

    @RequestMapping(path = "/hello")
    @ResponseBody
    public String sayhello() {
        System.out.println("Hello World!");
        return "Hello";
    }

}
```

### （5）启动项目，进行测试

> 运行QuickStartApplication的main方法，成功启动后访问url：http://localhost:8080/hello



## 3、使用Spring Initializr快速创建Spring Boot项目

默认生成的Spring Boot项目：

* 基本框架已经搭好，主程序也已经编好，我们只需要写自己的逻辑。
* resources文件夹的结构
	* static：保存所有的静态资源（js / css / images）
	* templates：保存所有的模板页面；（Spring Boot默认jar包使用嵌入式的tomcat，默认不支持jsp页面）；可以使用模板引擎（framemarker，thymeleaf）
	* application.yml：配置文件，可以修改一些默认设置

## 4、SpringBoot的特点：

### 1.1 依赖管理

* 父项目的依赖管理

```xml
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.6.6</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
又有一个父项目：
  <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-dependencies</artifactId>
    <version>2.6.6</version>
  </parent>
spring-boot-dependencies这个项目里有一个properties的标签，里面定义了差不多我们开发所需要的所有依赖版本号。自动版本仲裁。

```

* 修改版本号

```xml
首先从spring-boot-dependencies里面查看我们引入的jar包默认配置的版本，如果不合适取出配置的key，在自己的pom中重新配置
<properties>
    <java.version>11</java.version>
    <mysql.version>5.1.43</mysql.version>
</properties>
```

* 开发中的各种场景启动器starter

  `https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.build-systems.starters`

  ```xml
  1. spring-boot-starter-* : 这种命名的依赖一般是官方提供的，引入后这个场景所需要的依赖会自动导入
  2. *-spring-boot-starter :第三方提供的简化开发的启动器
  3. 所有场景启动器最底层的依赖
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter</artifactId>
      <version>2.6.6</version>
      <scope>compile</scope>
  </dependency>
  也是springboot自动配置的核心依赖
  ```

* 有默认版本号，自动版本仲裁

  ```xml
  1. 引入依赖默认可以不写版本号（spring-boot-dependencies指定好的话）
  2. spring-boot-dependencies没有指定的话，需要自己写版本号
  ```

### 1.2 自动配置

* 自动配置好了Tomcat

  * 引入Tomcat的依赖（依赖管理 web -> tomcat）

    ```xml
    <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-tomcat</artifactId>
          <version>2.6.6</version>
          <scope>compile</scope>
        </dependency>
    ```

  * 配置tomcat(自动配置将分析)

* 自动配好了SpringMVC

  * 引入了SpringMVC的全套组件
  * 自动配置好了SpringMVC的常用组件 

* 自动配置好了web的常见功能：如字符编码等

  * SpringBoot帮我们配置好了所有Web开发常见的场景

* 默认的包结构

  * @SpringBootApplication，默认扫描主程序所在的包及其子包里的所有组件
  * 若想改变扫描路径，添加属性即可@SpringBootApplication(scanBasePackages = {"com.ityj"})

* 各种配置拥有默认值

  * 默认配置最终都是映射到XxxProperties的类上：ServerProperties、WebMvcProperties、Knife4jProperties
  * 配置文件最终都会绑定到每个类上，这个类会在容器中创建对象。

* 按需加载所有的配置项

  * 非常多的starter
  * 以后纳入了相关场景，这些自动配置才会起作用
  * SpringBoot的所有自动配置供能都在spring-boot-auto-configure包里



## 5、容器功能

### 1.1 组件添加

#### （1）@Configuration

* 基本使用
* Full模式和Lite模式
  * 最佳实战
    * 配置类组件之间无依赖关系用Lite模式加速容器启动过程，减少判断（Lite模式是真实的方法）
    * 配置类组件之间有依赖关系，方法会被调用得到之前单实例组件，用Full模式（代理方法，多次调用getBean()也是同一个方法，会进行判断）

#### （2）@Bean, @Component, @Controller, @Service, @repository

#### （3）@ComponentScan, @Import

#### （4）@Conditional

> 条件装配，满足conditional的某种条件时，才进行组件的注入
>
> * @ConditionOnBean(name="Dog")  --> 当组件中有Dog时，才会对下面的组件进行注入

![image-20220407223434913](https://gitee.com/yj1109/cloud-image/raw/master/img/image-20220407223434913.png)

### 1.2 原生配置文件引入

#### （1） @ImportResource

可以将配置文件中的组件注入到容器中：@ImportResource("classpath:bean-pet.xml")

### 1.3 配置绑定

####  @ConfigurationProperties

(1) ConfigurationProperties + Component将自己类Person和配置文件中的属性绑定在一起，并注入到容器中

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component  //注册bean到容器中
/*
    @ConfigurationProperties 作用：
    将配置文件中配置的每一个属性的值，映射到这个组件中；
    告诉SpringBoot将本类中的所有属性和配置文件中相关的配置进行绑定
    参数 prefix = “person” : 将配置文件中的person下面的所有属性一一对应
*/
@ConfigurationProperties(value = "person")
@Validated         //JSR303数据校验
public class Person implements Serializable {

    private String id;
    private String name;
    private String gender;
    private int age;
    private List<String> allPets;
    private Set<String> set;
    private List<String> list;
    private String[] stringArr;
    private Map<String,Object> map;
    private Date date;
    private boolean status;

    @Email(message = "邮箱格式错误！")
    @NotNull
    private String email;

}

public class Pet implements Serializable{
    private String id;
    private String name;
}

```

```yml
person:
  id: s001${random.uuid}
  name: Jack
  gender: male
  age: 24
  allPets:
    - dog
    - pig
    - cat
  set:
    - a
    - b
    - c
    - c
    - b
  list:
    - 1
    - 2
    - 3
    - 4
  stringArr:
    - banana
    - apple
    - orange
  map: {k1: v1,k2: v2}
  date: 2020/09/19
  status: false
  email: ayinjun1109@163.com
```

（2）通过一个配置类开启配置绑定

```java
@EnableConfigurationProperties(Car.class)  // 第二种注入容器中的方式（ConfigurationProperties）
// 作用 1: 开启Car配置绑定功能  2: 把Car组件自动注入到容器中
public class MyConfig {}
```

```java
@Data
@ConfigurationProperties(prefix = "mycar")
public class Car {
    private String brand;
    private double price;
}
```



## 6、配置绑定

### 1.1 引导加载配置类

```java
@SpringBootApplication

↓↓↓↓
    
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = { @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
		@Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
public @interface SpringBootApplication {}

```

#### (1) SpringBootConfiguration

其内部就是一个@Configuration，表明是一个配置类

#### (2) @ComponentScan

指明扫描哪些包

#### (3) @EnableAutoConfiguration

```java
@AutoConfigurationPackage
@Import(AutoConfigurationImportSelector.class)
public @interface EnableAutoConfiguration {}
```

##### a. @AutoConfigurationPackage

```java
@Import(AutoConfigurationPackages.Registrar.class)
public @interface AutoConfigurationPackage {}

// 通过@Import给容器中导入了一个组件。（通过一个Registrar.calss批量注册）
// 就是将指定包下的所有组件导入到容器中。Main程序所在的包下:com.ityj.boot
```

![image-20220409184403270](https://gitee.com/yj1109/cloud-image/raw/master/img/image-20220409184403270.png)

##### b. @Import(AutoConfigurationImportSelector.class)

```java
1. AutoConfigurationImportSelector.selectImports中的getAutoConfigurationEntry(annotationMetadata)批量获取所有的组件
2. List<String> configurations = getCandidateConfigurations(annotationMetadata, attributes);
3. 最终通过工厂架子啊loadSpringFactories(classLoaderToUse)得到所有组件
4. Enumeration<URL> urls = classLoader.getResources("META-INF/spring.factories");
默认扫描我们系统中所有目录下的META-INF\spring.factories
主要是spring-boot-autoconfigure-2.6.6.jar!\META-INF\spring.factories下org.springframework.boot.autoconfigure.EnableAutoConfiguration属性，其中2.6.6有134个，在程序中又引入了@EnableKnife4j，所以一共有加载了135个组件配置类
```

![image-20220409191717912](https://gitee.com/yj1109/cloud-image/raw/master/img/image-20220409191717912.png)

![image-20220409192151919](https://gitee.com/yj1109/cloud-image/raw/master/img/image-20220409192151919.png)



### 1.2 按需加载配置项

```xml
虽然我们133个默认场景的自动配置项启动的时候全部加载。
最终是按照条件装配规则@Conditional，按需装配的。
```

可以通过几个案例来查看最终效果：

1. AopAutoConfiguration是进行注册并使用的了。可以通过run.getBean(AopAutoConfiguration.class);进行确认

   ```java
   @Configuration(proxyBeanMethods = false)
   @ConditionalOnProperty(prefix = "spring.aop", name = "auto", havingValue = "true", matchIfMissing = true)
   // 表示如果配置了spring.aop.auto=true, 会进行注册。如果没有配置也会注册。怎样都会注册
   public class AopAutoConfiguration {}
   
   里面的类org.springframework.boot.autoconfigure.aop.AopAutoConfiguration$ClassProxyingConfiguration也进行了注册。
       @Configuration(proxyBeanMethods = false)
       @ConditionalOnMissingClass("org.aspectj.weaver.Advice")  // 如果没有引入Advice这个类，确实没有导入这个包
       @ConditionalOnProperty(prefix = "spring.aop", name = "proxy-target-class", havingValue = "true",
                              matchIfMissing = true)  // 怎样都满足
       static class ClassProxyingConfiguration {}     // 所以也进行了注册。
   
       
   ```

2. CacheAutoConfiguration没有生效

   ```java
   @Configuration(proxyBeanMethods = false)
   @ConditionalOnClass(CacheManager.class)
   @ConditionalOnBean(CacheAspectSupport.class)   // 不满足条件，所以CacheAutoConfiguration没有注册
   @ConditionalOnMissingBean(value = CacheManager.class, name = "cacheResolver")
   @EnableConfigurationProperties(CacheProperties.class)
   @AutoConfigureAfter({ CouchbaseDataAutoConfiguration.class, HazelcastAutoConfiguration.class,
   		HibernateJpaAutoConfiguration.class, RedisAutoConfiguration.class })
   @Import({ CacheConfigurationImportSelector.class, CacheManagerEntityManagerFactoryDependsOnPostProcessor.class })
   public class CacheAutoConfiguration {}
   ```

3. DispatcherServletAutoConfiguration生效的

1 DispatcherServletAutoConfiguration

```java
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = Type.SERVLET)
@ConditionalOnClass(DispatcherServlet.class)
@AutoConfigureAfter(ServletWebServerFactoryAutoConfiguration.class)
public class DispatcherServletAutoConfiguration {}
```

1.1 内部类DispatcherServletConfiguration

```java
@Configuration(proxyBeanMethods = false)
@Conditional(DefaultDispatcherServletCondition.class)   // 当满足这个类里面代码逻辑给定的条件时，为true
@ConditionalOnClass(ServletRegistration.class)			// 有这个类时
@EnableConfigurationProperties(WebMvcProperties.class)	
// 1.开启WebMvcProperties这个类和对应配置文件spring.mvc的配置绑定功能;配置文件里的所有spring.mvc.xxx都会被WebMvcProperties封装。
// 2. 把WebMvcProperties放到容器中
protected static class DispatcherServletConfiguration {}
```

1.1.1方法dispatcherServlet

```java
@Bean(name = "dispatcherServlet")
public DispatcherServlet dispatcherServlet(WebMvcProperties webMvcProperties) {  // 这个webMvcProperties是从容器中拿的。webMvcProperties又是通过@EnableConfigurationProperties(WebMvcProperties.class)注入到容器中的
   DispatcherServlet dispatcherServlet = new DispatcherServlet();
   dispatcherServlet.setDispatchOptionsRequest(webMvcProperties.isDispatchOptionsRequest());
   dispatcherServlet.setDispatchTraceRequest(webMvcProperties.isDispatchTraceRequest());
   dispatcherServlet.setThrowExceptionIfNoHandlerFound(webMvcProperties.isThrowExceptionIfNoHandlerFound());
   dispatcherServlet.setPublishEvents(webMvcProperties.isPublishRequestHandledEvents());
   dispatcherServlet.setEnableLoggingRequestDetails(webMvcProperties.isLogRequestDetails());
   return dispatcherServlet;
}
```

1.1.2方法multipartResolver，文件上传解析器

```java
@Bean
@ConditionalOnBean(MultipartResolver.class)   // 容器中有了MultipartResolver类型的bean
@ConditionalOnMissingBean(name = "multipartResolver")  // 容器中没有multipartResolver名字的bean
public MultipartResolver multipartResolver(MultipartResolver resolver) {  
   // MultipartResolver resolver这个对象作为参数传入到@Bean标注的配置里，则resolver这个值就是从容器中获取。
   // 直接将容器中MultipartResolver类型的bean返回，这个名字设置为multipartResolver，防止用户配置的文件上传解析器不符合规范。（名字必须是multipartResolver）
   return resolver;
}
```

4. HttpEncodingAutoConfiguration

目前发现SpringBoot前后端交互没有出现中文乱码现象，主要是自动配置了HttpEncodingAutoConfiguration.characterEncodingFilter

```java
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ServerProperties.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass(CharacterEncodingFilter.class)
@ConditionalOnProperty(prefix = "server.servlet.encoding", value = "enabled", matchIfMissing = true)
public class HttpEncodingAutoConfiguration {}
```

```java
private final Encoding properties;

public HttpEncodingAutoConfiguration(ServerProperties properties) {
   this.properties = properties.getServlet().getEncoding();
}
```

```java
@Bean
@ConditionalOnMissingBean
public CharacterEncodingFilter characterEncodingFilter() {
   CharacterEncodingFilter filter = new OrderedCharacterEncodingFilter();
   filter.setEncoding(this.properties.getCharset().name());
   filter.setForceRequestEncoding(this.properties.shouldForce(Encoding.Type.REQUEST));
   filter.setForceResponseEncoding(this.properties.shouldForce(Encoding.Type.RESPONSE));
   return filter;
}
```

==总结：==

* SpringBoot首先加载所有的自动配置类XxxAutoConfiguration
* 每个自动配置类按照条件(conditional)判断是否生效，默认都会绑定配置文件指定的值.XxxProperties.class里面。XxxProperties和配置文件中的属性又是一一对应的。
* 生效的配置类就会给容器汇总装配非常多的组件。
* 只要容器中有这些组件，就相当于有了这些功能。
* 定制化配置
  * 用户直接自己@Bean替换底层的组件
  * 用户去看这个组件对应的配置文件，通过yml进行修改

XxxAutoConfiguration  --> 组件   ->  去XxxProperties的Bean中进行取值   --> 可通过application.yml进行修改



## 7、最佳实战

### 1.1 引入场景依赖

* Spring官方的：https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.build-systems.starters
* 第三方的：http://www.mybatis.cn/archives/861.html

### 1.2 查看自动配置了哪些（选做）

* 自己分析：根据XxxAutoConfiguration类上以及方法上的注解分析是否生效
* 配置文件中debug=true开启自动配置报告，可以看到positive/negative的组件，以及满足与否的原因

### 1.3 配置是否需要修改

* 参考文档修改配置项

  https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#appendix.application-properties

* 自己根据XxxProperties的属性进行分析

* 自定义加入或修改配置型:@Bean...

* 自定义器: XxxCustomizer

* ......



## 8、开发小技巧

### 1.1 Lombok

> 在IDE中下载lombok插件再引入依赖即可，springboot已经对版本进行了控制

```xml
<dependency>
   <groupId>org.projectlombok</groupId>
   <artifactId>lombok</artifactId>
</dependency>
```

### 1.2 devtools

修改代码后，IDE手动编译一次，会进行重启。Automatic Restart

> https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.devtools

```xml
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-devtools</artifactId>
   <optional>true</optional>
</dependency>
```

### 1.3 Spring Initializr

> https://start.spring.io/





# 二、Spring Boot2 核心技术

## 1、配置文件

### 1.1 配置文件

Spring Boot使用一个全局的配置文件，其配置文件名是固定的。

* application.properties
* application.yml

配置文件的作用：修改Spring Boot自动配置的默认值。

标记语言：

​	以前的配置文件，大多是使用xxx.xml的方式，比较繁琐。

XML：

```xml
<server>
	<port>9090</port>
</server>
```



​    YMAL是以数据为中心，比json、xml等更加适合做配置文件。

​	eg：

```yml
server:
  port: 9090
```



### 1.2 YMAL语法

#### （1）基本语法

k: (空格)v：表示一对键值对（空格必须有）

以空格的缩进来控制层级关系，只要是左对齐的一列数据，都是一个层级的。

```yml
server:
  port: 9090
  servlet:
    context-path: /sb
```

属性跟值大小写敏感

#### （2）值的写法

（1）字面量： 普通的值（数字，字符串，布尔值）

k: v   字面量直接来写。

字符串默认不需要加双引号。

如果加了需要跟单引号做好区分：

双引号：写的如果是换行(\n)类的字符，最终会进行换行输出。

单引号：写的如果是换行(\n)类的字符，最终会把输入的值原封不动输出。

（2）对象，Map（属性和值）（键值对）

```yml
friend: 
	lastName: san
	age: 20
```

行内写法：`friend: {lastName: san,age: 20}`

（3）数组（List、Set）

用 - 值来表示数组中的一个元素

```yml
pets: 
	- cat
	- pig
	- dog
```

行内写法：`pets: [cat, pig, dog]`



如果想要通过@Value获取到数组或集合，可以这样写(逗号隔开)：

```yml
data:
  list: Jack,Rose,Tom
```

```java
@Value("${data.list}")
private List<String> list;  // Spring默认情况下会以','进行分割，转换成对应的数组或List。
@Value("${data.list}")
private String[] arr;

@Value("#{'${data.list}'.split(',')}")   // 数组或list接收都可以
private List<String> list2;
@Value("#{'${data.list}'.split(',')}")
private String[] arr2;
```

### 1.3 Profile 文件

（1）通过yml文件的spring:  profiles:  active:   指明

```yml
server:
  port: 9090
  servlet:
    context-path: /sb
spring:
  profiles:
    active: prd



---
server:
  port: 8888
spring:
  profiles: dat

---

server:
  port: 9999
spring:
  profiles: prd
```

（2）通过application-{profile}.properties实现动态切换。

application.properties

```properties
server.port=7777
# 如果没有指定spring.profiles.active，默认是application.properties对应的值
spring.profiles.active=prd
```

application-dev.properties

```properties
server.port=6666
```

application-prd.properties

```properties
server.port=5555
```

​    （3）使用命令行，启动jar包，指定对应的配置文件

```shell
java -jar springboot-review1214-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```

​	（4）也可以使用`spring.config.location`指定文件位置

```
java -jar springboot-review1214-0.0.1-SNAPSHOT.jar --spring.config.location=./application.yml
最后指定的端口号是外部location的端口：优先，并且和其他内部配置文件互补。
---------------------------------------------------------
springboot-review1214-0.0.1-SNAPSHOT.jar和外部配置文件application.yml所在同一个文件夹下，其实直接
`java -jar springboot-review1214-0.0.1-SNAPSHOT.jar`就会默认先读取外部的端口为1111的application.yml配置文件。
```

### 1.4 SpringBoot默认日志（slf4j --> logback）

如果想要使用，直接引入logback.xml或者logback-spring.xml即可。

* SpringBoot如何整合使用更加优秀的log4j2的日志框架呢？

（1）排除spring的spring-boot-starter-logging框架，再引入spring-boot-starter-log4j2依赖

```xml
<!--要想使用log4j2的日志框架，需要排除掉原始的-->
<exclusions>
    <exclusion>
        <artifactId>spring-boot-starter-logging</artifactId>
        <groupId>org.springframework.boot</groupId>
    </exclusion>
</exclusions>
<dependency> <!-- 引入log4j2依赖 -->
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-log4j2</artifactId>
</dependency>
```

==logback-demo==

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration debug="false">
    <!--定义日志文件的存储地址 勿在 LogBack 的配置中使用相对路径-->
    <property name="LOG_HOME" value="C:home" />

    <!-- 控制台输出 -->
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度%msg：日志消息，%n是换行符-->
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
        </encoder>
    </appender>
    <!-- 按照每天生成日志文件 + 单个文件大小为10M  +  保留7天 -->
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!--日志文件输出的文件名-->
            <FileNamePattern>${LOG_HOME}/spring-logback-druid.log.%d{yyyy-MM-dd}.log</FileNamePattern>
            <!--日志文件保留天数-->
            <MaxHistory>7</MaxHistory>
        </rollingPolicy>
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度%msg：日志消息，%n是换行符-->
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
        </encoder>
        <!--日志文件最大的大小-->
        <triggeringPolicy class="ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy">
            <MaxFileSize>10MB</MaxFileSize>
        </triggeringPolicy>
    </appender>

    <!-- 日志输出级别 -->
    <root level="info">
        <appender-ref ref="STDOUT"/>
        <appender-ref ref="FILE"/>
    </root>
</configuration>
```

==log4j2.xml==

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN">

    <!--全局参数-->
    <Properties>
        <Property name="pattern">%d{yyyy-MM-dd HH:mm:ss,SSS} %5p %c{1}:%L - %m%n</Property>
        <Property name="logDir">/data/logs/dust-server</Property>
    </Properties>

    <Loggers>
        <Root level="INFO">
            <AppenderRef ref="console"/>
            <AppenderRef ref="rolling_file"/>
        </Root>
    </Loggers>

    <Appenders>
        <!-- 定义输出到控制台 -->
        <Console name="console" target="SYSTEM_OUT" follow="true">
            <!--控制台只输出level及以上级别的信息-->
            <ThresholdFilter level="INFO" onMatch="ACCEPT" onMismatch="DENY"/>
            <PatternLayout>
                <Pattern>${pattern}</Pattern>
            </PatternLayout>
        </Console>
        <!-- 同一来源的Appender可以定义多个RollingFile，定义按天存储日志 -->
        <RollingFile name="rolling_file"
                     fileName="${logDir}/dust-server.log"
                     filePattern="${logDir}/dust-server_%d{yyyy-MM-dd}.log">
            <ThresholdFilter level="INFO" onMatch="ACCEPT" onMismatch="DENY"/>
            <PatternLayout>
                <Pattern>${pattern}</Pattern>
            </PatternLayout>
            <Policies>
                <TimeBasedTriggeringPolicy interval="1"/>
            </Policies>
            <!-- 日志保留策略，配置只保留七天 -->
            <DefaultRolloverStrategy>
                <Delete basePath="${logDir}/" maxDepth="1">
                    <IfFileName glob="dust-server_*.log" />
                    <IfLastModified age="7d" />
                </Delete>
            </DefaultRolloverStrategy>
        </RollingFile>
    </Appenders>
</Configuration>

```



## 2、Web开发

### 1.1 简单功能分析

#### （1）静态资源访问

> https://docs.spring.io/spring-boot/docs/current/reference/html/web.html#web.servlet.spring-mvc.static-content

* **静态资源默认路径**

By default, Spring Boot serves static content from a directory called `/static` (or `/public` or `/resources` or `/META-INF/resources`) in the classpath.

只要静态资源放在上面四个目录下，可以直接访问：项目根目录/ + 资源名称（http://localhost:8080/img1.jpg）

* **静态资源访问前缀：默认没有**

By default, resources are mapped on `/**`, but you can tune that with the `spring.mvc.static-path-pattern` property. For instance, relocating all resources to `/res/**` can be achieved as follows:

```yml
spring:
  mvc:
    static-path-pattern: /res/**
```

http://localhost:8080/res/img1.jpg才能正常访问。

* **修改资源默认文件夹**

  ```yml
  spring:
    web:
      resources:
        static-locations: classpath:/aa/   # 修改默认静态资源的文件夹
  ```

#### （2）欢迎页支持

* 静态资源下添加index.html
  * 如果自定义了静态资源路径和访问前缀，可能会出问题。访问前缀不能开启
  * 静态资源路径可以开启。
* controller能处理/index请求

#### （3）自定义Favicon

> 只要在静态目录下放入一个favicon.ico图片即可
>
> static-path-pattern也会导致favicon失效。

#### （4）静态资源原理 - 源码解析

MVC相关功能的自动配置类最终来自WebMvcAutoConfiguration

通过分析注解，可以看到WebMvcAutoConfiguration是处于开启状态。

关于资源映射，最终来到了内部类WebMvcAutoConfigurationAdapter，这里有一个带参的内部类，这里的参数都是来自于容器。

```java
public WebMvcAutoConfigurationAdapter(WebProperties webProperties, WebMvcProperties mvcProperties,
      ListableBeanFactory beanFactory, ObjectProvider<HttpMessageConverters> messageConvertersProvider,
      ObjectProvider<ResourceHandlerRegistrationCustomizer> resourceHandlerRegistrationCustomizerProvider,
      ObjectProvider<DispatcherServletPath> dispatcherServletPath,
      ObjectProvider<ServletRegistrationBean<?>> servletRegistrations) {
   this.resourceProperties = webProperties.getResources();
   this.mvcProperties = mvcProperties;
   this.beanFactory = beanFactory;
   this.messageConvertersProvider = messageConvertersProvider;
   this.resourceHandlerRegistrationCustomizer = resourceHandlerRegistrationCustomizerProvider.getIfAvailable();
   this.dispatcherServletPath = dispatcherServletPath;
   this.servletRegistrations = servletRegistrations;
   this.mvcProperties.checkConfiguration();
}
```

* **静态资源映射源码**

  * 可以看到访问/webjars/abc时，会自动映射到/META-INF/resources/webjars/abc
  * StaticPathPattern和StaticLocations如果没有配置时，访问/**默认会映射到classpath:[/META-INF/resources/, /resources/, /static/, /public/].

  ```java
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
      if (!this.resourceProperties.isAddMappings()) {
          logger.debug("Default resource handling disabled");
          return;
      }
      addResourceHandler(registry, "/webjars/**", "classpath:/META-INF/resources/webjars/");
      addResourceHandler(registry, this.mvcProperties.getStaticPathPattern(), (registration) -> {
          registration.addResourceLocations(this.resourceProperties.getStaticLocations());
          if (this.servletContext != null) {
              ServletContextResource resource = new ServletContextResource(this.servletContext, SERVLET_LOCATION);
              registration.addResourceLocations(resource);
          }
      });
  }
  ```

* **欢迎页配置源码**

  * 通过源码可以看出，welcomePage != null && "/**".equals(staticPathPattern)，也就是说staticPathPattern没有修改时，index页才会生效

  ```java
  @Bean
  public WelcomePageHandlerMapping welcomePageHandlerMapping(ApplicationContext applicationContext,
                                                             FormattingConversionService mvcConversionService, ResourceUrlProvider mvcResourceUrlProvider) {
      WelcomePageHandlerMapping welcomePageHandlerMapping = new WelcomePageHandlerMapping(
          new TemplateAvailabilityProviders(applicationContext), applicationContext, getWelcomePage(),
          this.mvcProperties.getStaticPathPattern());
      welcomePageHandlerMapping.setInterceptors(getInterceptors(mvcConversionService, mvcResourceUrlProvider));
      welcomePageHandlerMapping.setCorsConfigurations(getCorsConfigurations());
      return welcomePageHandlerMapping;
  }
  
  WelcomePageHandlerMapping(TemplateAvailabilityProviders templateAvailabilityProviders,
  			ApplicationContext applicationContext, Resource welcomePage, String staticPathPattern) {
      if (welcomePage != null && "/**".equals(staticPathPattern)) {
          logger.info("Adding welcome page: " + welcomePage);
          setRootViewName("forward:index.html");
      }
      else if (welcomeTemplateExists(templateAvailabilityProviders, applicationContext)) {
          logger.info("Adding welcome page template: index");
          setRootViewName("index");
      }
  }
  ```





### 1.2 请求参数处理

```java
DispatcherServlet
getHandler(request)
getHandlerAdapter(handler)
ha.handle(..., handler)
    handleInternal()
    	invokeHandlerMethod()
    		argumentResolvers
    		returnValueHandlers
    		invocableMethod.invokeAndHandle(webRequest, mavContainer);
				invokeForRequest(request,xxx,providedArgs);
                    getMethodArgumentValues()
                        resolvers.resolveArgument()
                        // resolveArgument()是一个接口,ModelAttributeMethodProcessor处理自定义参数Cat
                returnValueHandlers.handleReturnValue(returnValue,type,...)
                        selectHandler(value, type); //RequestResponseBodyMethodProcessor处理自定义类型参数Cat
						handler.handleReturnValue(value, type, mavContainer, webRequest);
			getModelAndView(mavContainer, modelFactory, webRequest);
```



#### （1）请求映射

##### 1.1 REST使用与原理

==OrderedHiddenHttpMethodFilter==

> 前端form表单实现PUT/DELETE/PATCH请求

```java
@GetMapping(path = "/user")
public String getMethod() {
    return "GET";
}
@PostMapping(path = "/user")
public String postMethod() {
    return "POST";
}
@PutMapping(path = "/user")
public String putMethod() {
    return "PUT";
}
@DeleteMapping(path = "/user")
public String deleteMethod() {
    return "DELETE";
}
@PatchMapping(path = "/user")
public String patchMethod() {
    return "PATCH";
}
```

```html
测试页面REST请求：
<form action="/user" method="get">
    <input value="GET请求" type="submit"/>
</form>

<form action="/user" method="post">
    <input value="POST请求" type="submit"/>
</form>

<form action="/user" method="post">
    <input hidden="hidden" name="_method" value="put"/>
    <input value="PUT请求" type="submit">
</form>

<form action="/user" method="post">
    <input hidden="hidden" name="_method" value="delete"/>
    <input value="DELETE请求" type="submit">
</form>

<form action="/user" method="post">
    <input hidden="hidden" name="_method" value="patch"/>
    <input value="PATCH请求" type="submit">
</form>
```

```yml
spring:  
  mvc:
    hiddenmethod:
      filter:
        enabled: true
```

form表单默认只支持GET和POST请求，若想要发送PUT请求，需要通过过滤器将request的method进行重新设置来实现。

SpringBoot中的OrderedHiddenHttpMethodFilter就可以实现这个功能。

```java
@Bean
@ConditionalOnMissingBean(HiddenHttpMethodFilter.class)
@ConditionalOnProperty(prefix = "spring.mvc.hiddenmethod.filter", name = "enabled", matchIfMissing = false)
public OrderedHiddenHttpMethodFilter hiddenHttpMethodFilter() {
   return new OrderedHiddenHttpMethodFilter();
}
```

默认这个配置不会加载，只有添加了spring.mvc.hiddenmethod.filter.enable=true才能注册。

其最终的实现原理是HiddenHttpMethodFilter

```java
@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

   HttpServletRequest requestToUse = request;

   if ("POST".equals(request.getMethod()) && request.getAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE) == null) {
      String paramValue = request.getParameter(this.methodParam);
      if (StringUtils.hasLength(paramValue)) {
         String method = paramValue.toUpperCase(Locale.ENGLISH);
         if (ALLOWED_METHODS.contains(method)) {
            requestToUse = new HttpMethodRequestWrapper(request, method);
         }
      }
   }

   filterChain.doFilter(requestToUse, response);
}
```

* 默认的this.methodParam=_method， 提供了set方法，可以修改

  * ```java
    @Bean
    public OrderedHiddenHttpMethodFilter hiddenHttpMethodFilter() {
        OrderedHiddenHttpMethodFilter orderedHiddenHttpMethodFilter = new OrderedHiddenHttpMethodFilter();
        orderedHiddenHttpMethodFilter.setMethodParam("_hide_method");
        return orderedHiddenHttpMethodFilter;
    }
    ```

* ALLOWED_METHODS=[PUT,DELETE,PATCH]，仅支持这三种请求

* 原请求必须是POST类型

* new HttpMethodRequestWrapper(request, method) 实现将_method=xx的请求设置进原始request, 达到xx请求效果

==当时用客户端工具如POSTMAN时，不会走这个过滤，因为过来的请求直接就是PUT或者其他的类型了==



##### 1.2 请求映射原理(DispatcherServlet)

> 所有的请求都会走org.springframework.web.servlet.DispatcherServlet#doDispatch方法

![image-20220413220001944](https://gitee.com/yj1109/cloud-image/raw/master/img/image-20220413220001944.png)

SpringMVC功能都从org.springframework.web.servlet.DispatcherServlet#doDispatch方法开始分析。

==解析doDispatch()方法==

```java
// Determine handler for the current request.
// 找到当前请求是使用哪个Handler(Controller的方法)处理
mappedHandler = getHandler(processedRequest);

```

Handler是通过遍历HandlerMapping处理器映射中的值来判断并获取的

![image-20220413221849239](https://gitee.com/yj1109/cloud-image/raw/master/img/image-20220413221849239.png)



可以看到访问的GET请求http://localhost:8080/user是在RequestMappingHandlerMapping中的

![image-20220413222135849](https://gitee.com/yj1109/cloud-image/raw/master/img/image-20220413222135849.png)

mappingRegistory中有着请求以及对应的Handler方法具体映射。

![image-20220413222120503](https://gitee.com/yj1109/cloud-image/raw/master/img/image-20220413222120503.png)



**所有的映射都是在HandlerMapping中：**

* SpringBoot自动配置了欢迎页的WelcomePageHandlerMapping。访问/默认找静态资源目录下的index.html文件

  ```java
  // We need to care for the default handler directly, since we need to
  // expose the PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE for it as well.
  org.springframework.web.servlet.handler.AbstractUrlHandlerMapping#getHandlerInternal，这里的WelcomePageHandlerMapping最终将/映射到ParameterizableViewController，forward:index.html
  Object rawHandler = null;
  if (StringUtils.matchesCharacter(lookupPath, '/')) {
      rawHandler = getRootHandler();
  }
  ```

  ![image-20220413225730459](https://gitee.com/yj1109/cloud-image/raw/master/img/image-20220413225730459.png)

* SpringBoot自动配置了默认的RequestMappingHandlerMapping
* 请求进来挨个尝试所有的HandlerMapping看是否有请求信息
  * 如果有，就找到这个请求对应的Handler
  * 如果没有，就从下一个HandlerMapping中找 



#### （2）普通参数与基本注解

##### 1.1 注解

* **@PathVariable/@RequestParam/@RequestHeader/@CookieValue**

```java
@GetMapping("/person/{id}/{name}")
public Map<String, Object> getRequest(@PathVariable("id") String id,
                                      @PathVariable("name") String personName,
                                      @PathVariable Map<String, Object> map,
                                      @RequestParam("age") Integer age,
                                      @RequestHeader("User-Agent") String userAgent,
                                      @CookieValue("Idea-7e7a18c1") String cookieIde,
                                      @CookieValue("Idea-7e7a18c1") Cookie cookie) {
    Map<String, Object> result = new HashMap<>();
    result.put("id", id);
    result.put("personName", personName);
    result.put("age", age);
    result.put("userAgent", userAgent);
    result.put("cookieIde", cookieIde);

    log.info("map = {}", map.toString());
    log.info("cookie.key = {}; cookie.value = {}", cookie.getName(), cookie.getValue());

    return result;
}
```

> test: http://localhost:8080/person/1/hello?age=21



* **@RequestBody**

```java
@PostMapping(path = "/saveUserInfo", produces = "application/json; charset=utf-8")
public Map<String, Object> saveUserInfo(@RequestBody String content) throws UnsupportedEncodingException {
    Map<String, Object> result = new HashMap<>();
    result.put("content", URLDecoder.decode(content, StandardCharsets.UTF_8));

    return result;
}
```

```html
<form action="/saveUserInfo" method="post">
    <h2>测试@RequestBody获取数据</h2>
    用户名：<input name="userName"/> <br/>
    邮箱：<input name="email"/> <br/>
    <input type="submit" value="提交">
</form>
```



* **@RequestAttribute**

```java
@Controller
public class RequestController {

    @GetMapping("/goto")
    public String gotoPage(HttpServletRequest request) {
        request.setAttribute("msg", "信息");
        return "forward:/success";    // 请求转发到 /success请求， 服务期间， 地址不变，一次请求一次相应
    }

    @GetMapping("/success")
    @ResponseBody
    public Map<String, Object> success(@RequestAttribute("msg") String message,
                                       HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        result.put("anno_message", message);
        result.put("request_message", request.getAttribute("msg"));
        return result;
    }

}
```



##### 1.2 Servlet API

```java
WebRequest/ServletRequest...ZoneId
```

> 对于HttpServletRequest request这种参数，也是通过参数解析器来进行处理的。
>
> ServletRequest对应的是ServletRequestMethodArgumentResolver

```java
@GetMapping("/goto")
public String gotoPage(HttpServletRequest request) {
    request.setAttribute("msg", "信息");
    return "forward:/success";    // 请求转发到 /success请求， 服务期间， 地址不变，一次请求一次相应
}
```

```java
@Override
public boolean supportsParameter(MethodParameter parameter) {
   Class<?> paramType = parameter.getParameterType();
   return (WebRequest.class.isAssignableFrom(paramType) ||
         ServletRequest.class.isAssignableFrom(paramType) ||
         MultipartRequest.class.isAssignableFrom(paramType) ||
         HttpSession.class.isAssignableFrom(paramType) ||
         (pushBuilder != null && pushBuilder.isAssignableFrom(paramType)) ||
         (Principal.class.isAssignableFrom(paramType) && !parameter.hasParameterAnnotations()) ||
         InputStream.class.isAssignableFrom(paramType) ||
         Reader.class.isAssignableFrom(paramType) ||
         HttpMethod.class == paramType ||
         Locale.class == paramType ||
         TimeZone.class == paramType ||
         ZoneId.class == paramType);
}
```



##### 1.3 复杂参数

```java
Map,Model,
RedirectAttributes,ServletResponse,
Errors/BindingResult,SessionStatus,UriComponentsBuilder,ServletUriComponentBuilder
```

==Map,Model里的参数最终会被放到request请求域中，使用map.put(x, v)相当于request.setAttribute(x, v)==

```java
@GetMapping("/params")
public String params(Map<String, Object> map,
                     Model model,
                     RedirectAttributes attribute,
                     HttpServletRequest request,
                     HttpServletResponse response) {
    map.put("map", "HelloMap");
    model.addAttribute("model", "HelloModel");
    attribute.addAttribute("redirectAttributes", "HelloRedirectAttributes");
    request.setAttribute("request", "HelloRequest");
    response.addCookie(new Cookie("k", "v-"));
    return "forward:/success";    // 请求转发到 /success请求， 服务期间， 地址不变，一次请求一次相应
}

@GetMapping("/success")
@ResponseBody
public Map<String, Object> success(@RequestAttribute(value = "msg", required = false) String message,
                                   HttpServletRequest request) {
    Map<String, Object> result = new HashMap<>();
    result.put("anno_message", message);
    result.put("request_message", request.getAttribute("msg"));
    result.put("map", request.getAttribute("map"));
    result.put("model", request.getAttribute("model"));
    result.put("redirectAttributes", request.getAttribute("redirectAttributes"));
    result.put("request", request.getAttribute("request"));
    return result;
}
```



是在doDispatch()的最后一步  --> processDispatchResult()   --> render(mv, request, response);进行视图渲染赋值

![image-20220417193042987](https://gitee.com/yj1109/cloud-image/raw/master/img/image-20220417193042987.png)

```java
protected void exposeModelAsRequestAttributes(Map<String, Object> model,
      HttpServletRequest request) throws Exception {

   model.forEach((name, value) -> {
      if (value != null) {
         request.setAttribute(name, value);   // 将Map和Model中的值放入request请求域中
      }
      else {
         request.removeAttribute(name);
      }
   });
}
```

##### 1.4 自定义参数

> 最终走的也是DispatcherServlet，在参数解析resolveArgument时，走了ModelAttributeMethodProcessor解析器。
>
> 内部通过反射以及一系列的converter实现了数据的绑定

```java
@PostMapping(path = "/saveCarInfo")
public Car saveCarInfo(Car car) {
    return car;
}
```

```html
<form action="/saveCarInfo" method="post">
    <h2>测试自定义参数是如何解析的</h2>
    品牌：<input name="brand"/> <br/>
    价格：<input name="price"/> <br/>
    <input type="submit" value="提交">
</form>
```

==**ModelAttributeMethodProcessor.resolveArgument**==

![image-20220419222318781](https://gitee.com/yj1109/cloud-image/raw/master/img/image-20220419222318781.png)

![image-20220419222408240](https://gitee.com/yj1109/cloud-image/raw/master/img/image-20220419222408240.png)

> bindRequestParameters解析request中的参数，将值绑定到binder的target对象中，到此参数获取完毕

![image-20220419225652253](C:/Users/ayinj/AppData/Roaming/Typora/typora-user-images/image-20220419225652253.png)



> convertForProperty()方法会进行类型转换
>
> org.springframework.validation.DataBinder#doBind

![image-20220419234902454](https://gitee.com/yj1109/cloud-image/raw/master/img/image-20220419234902454.png)

![image-20220419234135679](https://gitee.com/yj1109/cloud-image/raw/master/img/image-20220419234135679.png)

#### （3）请求参数处理原理

* HandlerMapping中找到能处理请求的Handler（Controller.method()）

![image-20220416184100611](https://gitee.com/yj1109/cloud-image/raw/master/img/image-20220416184100611.png)

* 为当前Handler找到一个适配器Adapter

![image-20220416184236446](https://gitee.com/yj1109/cloud-image/raw/master/img/image-20220416184236446.png)





##### 1.1 HandlerAdapter

> 根据请求的类型，确认对应的适配器Adapter

 ![image-20220416184527041](https://gitee.com/yj1109/cloud-image/raw/master/img/image-20220416184527041.png)

0 - 支持方法上标注@RequestMapping

1- 支持函数式编程

...

##### 1.2 执行目标方法

> 根据Handler和Adapter执行目标方法

```java
// Actually invoke the handler.  DispatcherServlet.doDispatch()
mv = ha.handle(processedRequest, response, mappedHandler.getHandler());
```

```java
// No synchronization on session demanded at all... 执行目标方法RequestMappingHandlerAdapter
mav = invokeHandlerMethod(request, response, handlerMethod);
invocableMethod.invokeAndHandle(webRequest, mavContainer);

// ServletInvocableHandlerMethod  执行方法
Object returnValue = invokeForRequest(webRequest, mavContainer, providedArgs);

// 获取方法参数值 InvocableHandlerMethod
Object[] args = getMethodArgumentValues(request, mavContainer, providedArgs);

```

##### 1.3 参数解析器

> 确定将要执行目标方法的每一个参数是什么。argumentResolvers

SpringMVC目标方法能支持多少种参数类型，取决于参数解析器。

![image-20220416191249903](https://gitee.com/yj1109/cloud-image/raw/master/img/image-20220416191249903.png)

参数解析器接口

* 首先判断是否支持解析这种参数supportsParameter()
* 支持的话执行resolveArgument()方法

![image-20220416191815891](https://gitee.com/yj1109/cloud-image/raw/master/img/image-20220416191815891.png)



##### 1.4 如何确定目标方法的每一个值

> InvocableHandlerMethod，获取到所有参数及其对应的值

```java
protected Object[] getMethodArgumentValues(NativeWebRequest request, @Nullable ModelAndViewContainer mavContainer,
      Object... providedArgs) throws Exception {

   // 获取到参数的所有详细信息：参数标注的注解(以及name，isRequired等信息)，参数的类型，参数的名称等信息 
   MethodParameter[] parameters = getMethodParameters();
   if (ObjectUtils.isEmpty(parameters)) {
      return EMPTY_ARGS;
   }

   Object[] args = new Object[parameters.length];
   for (int i = 0; i < parameters.length; i++) {
      MethodParameter parameter = parameters[i];
      parameter.initParameterNameDiscovery(this.parameterNameDiscoverer);
      args[i] = findProvidedArgument(parameter, providedArgs);
      if (args[i] != null) {
         continue;
      }
      // 判断解析器是否支持当前的参数类型
      if (!this.resolvers.supportsParameter(parameter)) {
         throw new IllegalStateException(formatArgumentError(parameter, "No suitable resolver"));
      }
      try {
          // 真正的获取参数值方法
         args[i] = this.resolvers.resolveArgument(parameter, mavContainer, request, this.dataBinderFactory);
      }
      catch (Exception ex) {
         // Leave stack trace for later, exception may actually be resolved and handled...
         if (logger.isDebugEnabled()) {
            String exMsg = ex.getMessage();
            if (exMsg != null && !exMsg.contains(parameter.getExecutable().toGenericString())) {
               logger.debug(formatArgumentError(parameter, exMsg));
            }
         }
         throw ex;
      }
   }
   return args;
}
```



###### 1.4.1 挨个判断哪个解析器执行这个参数类型

```java
@Override
public boolean supportsParameter(MethodParameter parameter) {
   return getArgumentResolver(parameter) != null;
}

@Nullable
private HandlerMethodArgumentResolver getArgumentResolver(MethodParameter parameter) {
    HandlerMethodArgumentResolver result = this.argumentResolverCache.get(parameter);
    if (result == null) {
        for (HandlerMethodArgumentResolver resolver : this.argumentResolvers) {
            if (resolver.supportsParameter(parameter)) {
                result = resolver;
                // 这里会将参数类型解析器resolver放入到缓存argumentResolverCache中。
                // 所以项目启动后，同一个请求第一次执行会慢于后续的
                this.argumentResolverCache.put(parameter, result);
                break;
            }
        }
    }
    return result;
}
```

###### 1.4.2 获取参数值

```java
return resolver.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
```



##### 1.5 返回值处理器

![image-20220416192100478](https://gitee.com/yj1109/cloud-image/raw/master/img/image-20220416192100478.png)



#### （4）请求响应与内容协商

##### 1.1 响应JSON

###### 1.1.1 @ResponseBody + jackson.jar

> 将结果转换成JSON格式

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

web场景会自动引入json
↓↓↓
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-json</artifactId>
    <version>2.4.4</version>
    <scope>compile</scope>
</dependency>

json场景主要用的是jackson
↓↓↓
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.11.4</version>
    <scope>compile</scope>
</dependency>
<dependency>
    <groupId>com.fasterxml.jackson.datatype</groupId>
    <artifactId>jackson-datatype-jdk8</artifactId>
    <version>2.11.4</version>
    <scope>compile</scope>
</dependency>
<dependency>
    <groupId>com.fasterxml.jackson.datatype</groupId>
    <artifactId>jackson-datatype-jsr310</artifactId>
    <version>2.11.4</version>
    <scope>compile</scope>
</dependency>
<dependency>
    <groupId>com.fasterxml.jackson.module</groupId>
    <artifactId>jackson-module-parameter-names</artifactId>
    <version>2.11.4</version>
    <scope>compile</scope>
</dependency>
```

```java
@GetMapping("/person")
@ResponseBody
public Person getPerson() {
    Person person = new Person();
    person.setAge(11);
    person.setName("杰克");
    return person;
}
```

###### 1.1.2 返回参数解析原理

（1）DispatcherServlet在处理完request后会收到一个返回值returnValue

```java
Object returnValue = invokeForRequest(webRequest, mavContainer, providedArgs);
```

![image-20220423200537134](https://gitee.com/yj1109/cloud-image/raw/master/img/image-20220423200537134.png)

（2）然后执行handleReturnValue()方法

```java
this.returnValueHandlers.handleReturnValue(
      returnValue, getReturnValueType(returnValue), mavContainer, webRequest);
```

（3）然后通过selectHandler()方法获取到处理当前返回参数的处理器returnValueHandlers

![image-20220423200752959](https://gitee.com/yj1109/cloud-image/raw/master/img/image-20220423200752959.png)

ReturnValueHandler一共有15种：

![image-20220423202001786](https://gitee.com/yj1109/cloud-image/raw/master/img/image-20220423202001786.png)

这里可以看到对于自定义的参数类型Person，对应的ValueHandler是**RequestResponseBodyMethodProcessor**，因为满足标注了**@ResponseBody**注解

```java
@Override
public boolean supportsReturnType(MethodParameter returnType) {
   return (AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), ResponseBody.class) ||
         returnType.hasMethodAnnotation(ResponseBody.class));
}
```

（4）最后对参数进行处理

通过内容协商writeWithMessageConverters处理

* 利用messageConverters，将person对象写为JSON
* MediaType内容协商：浏览器默认会以请求头的方式告诉服务器它能就收什么类型的数据。(Accept)
* 服务器最终根据自己自身的能力，决定自己能生产出(product)什么类型的数据
* SpringMVC挨个遍历容器底层的HttpMessageConverter，找到能够处理的converter
  * 最终MappingJackson2HttpMessageConverter可以将对象处理成JSON，并利用其转换成JSON

```java
handler.handleReturnValue(returnValue, returnType, mavContainer, webRequest);

// 使用消息转换器进行写出操作
writeWithMessageConverters(returnValue, returnType, inputMessage, outputMessage);
// 找到对应的MessageConverter
genericConverter.write(body, targetType, selectedMediaType, outputMessage);
// 针对Person->JSON 是利用AbstractJackson2HttpMessageConverter中的ObjectWriter进行转化
writeInternal(t, type, outputMessage);
objectWriter.writeValue(generator, value);
```



###### 1.1.3 HttpMessageConverter原理

![image-20220423235349607](https://gitee.com/yj1109/cloud-image/raw/master/img/image-20220423235349607.png)

HttpMessageConverter：看能否将此Class类型的对象，转化成MediaType类型的数据。

即：能否将Person对象的数据转换为JSON.（write）

或将JSON类型数据转换成Person对象.（read）



>  这里MappingJackson2HttpMessageConverter能够实现对Person转化为JSON的处理。
>
> 利用jackson底层的objectMapper转换的。

MessageConverters对数据进行处理，转换成json类型，一共9种

![image-20220423202146522](https://gitee.com/yj1109/cloud-image/raw/master/img/image-20220423202146522.png)

```java
supports(clazz);
0 --> byte[].class
1 --> String.class
2 --> String.class
3 --> Resource.class
4 --> not exists == true
5 --> DOMSource.class/SAXSource.class/StAXSource.class/StreamSource.class/Source.class
6 --> not exists == true
7 --> not exists == true
8 --> not exists == true
```



##### 1.2 内容协商

浏览器可以接受的数据类型Accept以及服务器可以product(提供)的类型

浏览器支持的类型：

q是指权重，越大越优先

![image-20220423231742234](https://gitee.com/yj1109/cloud-image/raw/master/img/image-20220423231742234.png)

![image-20220423231850843](https://gitee.com/yj1109/cloud-image/raw/master/img/image-20220423231850843.png)

服务器可以提供的类型

![image-20220423232017255](https://gitee.com/yj1109/cloud-image/raw/master/img/image-20220423232017255.png)

通过遍历发现服务器可提供的四种类型（有重复），浏览器都能够支持

![image-20220423233252663](https://gitee.com/yj1109/cloud-image/raw/master/img/image-20220423233252663.png)

最后通过选择，得到application/json;q=0.8的返回类型

![image-20220423234034189](https://gitee.com/yj1109/cloud-image/raw/master/img/image-20220423234034189.png)