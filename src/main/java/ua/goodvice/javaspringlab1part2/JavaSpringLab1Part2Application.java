package ua.goodvice.javaspringlab1part2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaSpringLab1Part2Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(JavaSpringLab1Part2Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello from Spring Boot!");
    }
}
