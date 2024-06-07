package com.dqv5.screw.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author duq
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ScrewServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScrewServerApplication.class, args);
    }

}
