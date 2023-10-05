package org.online.lms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class E1i4Application {

    public static void main(String[] args) {
        SpringApplication.run(E1i4Application.class, args);
    }

}
