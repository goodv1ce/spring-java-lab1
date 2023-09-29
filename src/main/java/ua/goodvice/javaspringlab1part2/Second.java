package ua.goodvice.javaspringlab1part2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class Second implements CommandLineRunner {
    @Override
    public void run(String... args) {
        System.out.println("Second");
    }
}
