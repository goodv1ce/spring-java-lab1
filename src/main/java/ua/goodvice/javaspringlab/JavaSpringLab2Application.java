package ua.goodvice.javaspringlab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import ua.goodvice.javaspringlab.util.FakeDatabaseImplementation;

@SpringBootApplication
public class JavaSpringLab2Application {

    @Bean
    public FakeDatabaseImplementation fakeDatabaseImplementation() {
        return new FakeDatabaseImplementation();
    }

    public static void main(String[] args) {
        SpringApplication.run(JavaSpringLab2Application.class, args);
    }

}
