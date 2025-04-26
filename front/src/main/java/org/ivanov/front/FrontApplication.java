package org.ivanov.front;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class FrontApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(FrontApplication.class, args);
        System.out.println();
    }

}
