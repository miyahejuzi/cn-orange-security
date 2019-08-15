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

#### 


















