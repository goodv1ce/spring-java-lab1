package ua.goodvice.javaspringlab1part2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;
@SpringBootApplication
@Order(2)
public class JavaSpringLab1Part2Application implements CommandLineRunner {

    public static void main(String[] args) {
        System.out.println("Begin of main.");
        SpringApplication.run(JavaSpringLab1Part2Application.class, args);
        System.out.println("End of main.");
    }

    @Override
    public void run(String... args) {
        System.out.println("Hello from Spring Boot!");
    }
}
