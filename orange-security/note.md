# Spring Security开发安全的REST服务
> [Spring Security开发安全的REST服务  av50683258](https://www.bilibili.com/video/av50683258)

#### MAVEN聚合多个子模块项目版本号修改
> 在版本迭代过程中需要修改对应的版本号，还是需要顶级pom的版本号，及每个子模块内部parent的版本号。
```xml
   <parent>
        <groupId>com.maven.multily.module</groupId>
        <artifactId>parent</artifactId>
        <!--版本升级需要修改每个子模块 parent.version的值-->
        <version>1.1.0-SNAPSHOT</version>
    </parent>
```

> 这种手工修改方式极容易遗漏，导致项目内部模块版本依赖存在问题。
  我们可以通过maven的插件方式来升级整个项目的版本号。方案如下：

- 在项目顶层pom中添加version插件
```xml
    <plugin>
      <groupId>org.codehaus.mojo</groupId>
      <artifactId>versions-maven-plugin</artifactId>
      <version>2.3</version>
      <configuration>
          <generateBackupPoms>false</generateBackupPoms>
      </configuration>
    </plugin>
```
> 在项目根目录下执行以下命令修改版本号1
```
// 设置新的版本号未1.2.0-SNAPSHOT
mvn versions:set -DnewVersion=1.2.0-SNAPSHOT
```
> 以上命令会将maven-multily-module/pom.xml版本修改为1.2.0-SNAPSHOT，且会修改所有子模块内 parent的version为1.2.0-SNAPSHOT。所以建议子模块不设置version，自动从parent继承version即可

#### 使用 maven 构建 jar 项目
> 直接运行 `mvn clean package` 只能得到一个不包含依赖其他模块的单独 jar, 在 demo 的 pom.xml 里加上:   
```xml
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>1.3.3.RELEASE</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
        <finalName>demo</finalName>
    </build>
```
> 则可以得到一个 ${finalName}.jar 文件, 而原来的则后缀为 ${finalName}.jar.original

#### 使用 Spring mvc 开发 RESTful API
- 用 url 描述资源, 用 http 方法描述行为, 用状态码标识不同的结果
- 查询 GET, 创建 POST, 修改 PUT, 删除 DELETE
- 使用 json 交换数据
- RESTful 只是一种风格, 不是强制的标准

#### 乱七的备注
- url 路径参数可以使用正则 
- JsonView 自定义注解可以控制返回的 Json 串含有哪些属性. @JsonView(被注解在 bean 属性上的注解.class). 只标注显示的.
- 返回的时间最好是时间戳, 而不是有具体格式的值
- hibernate validator bean 的内容校验, @NotBlank(message = "不能为空") 标记不为空. 例如在 json to object 的时候, 加上 @Valid 才可以使用.
- BandingResult 在 validator 出错时, 不打回请求, 而是可以进入方法里面
```
    public List<User> createUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().stream().forEach((e) -> System.out.println(e.getDefaultMessage()));
        }
    }
```
- handler处理器，总归的来说 就是一段与正常顺序不同的程序码，该段程序码可以是一个程序区块、函式、异质系统或是任何型式，只要能处理引发的肇因的都叫处理器
- 执行顺序 : filter - interceptor - controllerAdvice - aspect - controller.method
- multipart/from-data   setContentType("application/x-download") response.setHeader("Content-Disposition", "attachment;filename=test.txt");
- Test.class.getResource() 得到的是当前类FileTest.class文件的URI目录。不包括自己


#### 测试
> MockMvc：

- 路径请求
```
    mockMvc.perform（MockMvcRequestBuilders.请求方式（“url / {path}”，参数值）
```
- 表单请求
```
    mockMvc.perform（MockMvcRequestBuilders 
            .请求方式（“url”）.param（“键”，“值”）.contentType（MediaType.APPLICATION_FORM_URLENCODED）
```
- JSON请求
```
    MvcResult mvcResult= mvc.perform(
            MockMvcRequestBuilders.post("http://127.0.0.1:8080/index").
                    content(jsonObject.toString()).
                    contentType(MediaType.APPLICATION_JSON)).
            andExpect(MockMvcResultMatchers.status().isOk()).
            andDo(MockMvcResultHandlers.print()).
            andReturn();
```

#### spring mvc 异步处理 rest 请求, Callable & DeferredResult
[浅谈响应式编程（Reactive Programming）](https://www.jianshu.com/p/1765f658200a)
- 响应式编程（Reactive Programming）是一种通过异步和数据流来构建事务关系的编程模型
- spring 对响应式编程的支持是 webflux
- java 的响应式编程也有 Vert.x [Vert.x(vertx) 简明介绍](https://blog.csdn.net/king_kgh/article/details/80772657) [利用Vertx构建简单的API 服务、分布式服务](https://www.jianshu.com/p/fbe0430959e8?from=groupmessage)

[高性能关键技术之---体验Spring MVC的异步模式（Callable、WebAsyncTask、DeferredResult） 基础使用篇](https://blog.csdn.net/f641385712/article/details/88692534)
> Tomcat等应用服务器的连接线程池实际上是有限制的；每一个连接请求都会耗掉线程池的一个连接数；如果某些耗时很长的操作，如对大量数据的查询操作、调用外部系统提供的服务以及一些IO密集型操作等，会占用连接很长时间，这个时候这个连接就无法被释放而被其它请求重用。如果连接占用过多，服务器就很可能无法及时响应每个请求；极端情况下如果将线程池中的所有连接耗尽，服务器将长时间无法向外提供服务！

> Spring MVC3.2 之后支持异步请求，能够在 controller 中返回一个 Callable 或者 DeferredResult。
> 由于 Spring MVC 的良好封装，异步功能使用起来出奇的简单。

> 注意：异步模式对前端来说，是无感知的，这是后端的一种技术。
  所以这个和我们自己开启一个线程处理，立马返回给前端是有非常大的不同的，需要注意~
  
原理简介:对于一次请求，比如front/test

1. spring mvc开启副线程处理业务(将Callable 提交到 TaskExecutor)
2. DispatcherServlet 和所有的 Filter 退出 web 容器的线程，但是 response 保持打开状态
3. 同时 interceptor 只执行了 preHandler. 直接退出 Interceptor
4. Callable 返回结果，SpringMVC 将请求 front/test 重新派发给容器(再重新请求一次front/test)，恢复之前的处理；
5. 根据 Callable 返回结果，继续处理（比如参数绑定、视图解析等等就和之前一样了）
6. DispatcherServlet 重新被调用，将结果返回给用户

> 实际使用中，并不建议直接使用Callable ，而是使用Spring提供的WebAsyncTask 代替，它包装了Callable，功能更强大些

- 在这种异步的方式下, filter 没有影响, 但是 interceptor 会有问题, preHandler 会执行两次, 可以使用 AsyncHandlerInterceptor

DeferredResult 需要自己写一个线程处理处理结果;
> 更多参考 java 的并发编程模型

#### 使用 swagger 自动生成文档
- 使用 
1. @EnableSwagger2
2. @ApiOperation @ApiParam 等..
3. 访问 /swagger-ui.html

#### 使用 WireMock 伪造 REST 服务
```
    1. 自己在本地启动服务, 然后编写程序访问
    ......
    2. 在程序里启动和访问
    new WireMockServer(wireMockConfig().port(8062)).start();
    configureFor(8062);
    removeAllMappings();
    stubFor(get(urlEqualTo("/api/add")).willReturn(aResponse().withStatus(200).withBody(
        ToJson.of("username username, password password"))));
```
[WireMock实战-1](https://www.jianshu.com/p/dba612b2172c)
> 集成至 spring boot 中使用 略...

#### 使用 security 开发封装一个安全模块 browser && app
- core




















