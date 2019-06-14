package de.enmacc.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"de.enmacc"})
public class EnmaccApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnmaccApplication.class, args);
    }

}

