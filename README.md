#scrat-apikit


scrat-apikit 是一个根据spring mvc 注解和源代码生成 httprestfull 调用存根和ApiDoc 的工具项目



加入maven 插件
mvn scrat-apikit:api



```xml
<build>
    <plugins>
      <plugin>
        <groupId>sample.plugin</groupId>
        <artifactId>hello-maven-plugin</artifactId>
        <version>1.0-SNAPSHOT</version>
        <executions>
          <execution>
            <phase>compile</phase>
            <goals>
              <goal>sayhi</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
    </plugins>
</build>
```