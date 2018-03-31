package org.forkjoin.scrat.apikit.example;


import org.forkjoin.scrat.apikit.build.ApiBuilderMain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(
        scanBasePackages = "org.forkjoin.scrat.apikit.example"
)
public class ExampleApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplicationBuilder(ExampleApplication.class).build();
        application.setWebApplicationType(WebApplicationType.REACTIVE);
        application.run(args);
    }
}
