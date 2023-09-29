package com.example.demo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.core.annotation.Order;

@SpringBootApplication
public class MySpringConsoleHelloWorldApplication implements CommandLineRunner {
    public static void main(String[] args) {
        System.out.print("Begin of main.");
        SpringApplication.run(MySpringConsoleHelloWorldApplication.class, args);
        System.out.print("End of main.");
    }

    @Component
    @Order(2)
    class HelloFromSpringBoot implements CommandLineRunner
    {
    @Override
    public void run(String... args) throws Exception 
    {
        System.out.println("Hello from Spring Boot!");
    }
    }
    
    @Component
    @Order(1) 
    class First implements CommandLineRunner {
        @Override
        public void run(String... args) throws Exception {
            System.out.println("First");
        }
    }

    @Component
    @Order(3) 
    class Second implements CommandLineRunner {
        @Override
        public void run(String... args) throws Exception {
            System.out.println("Second");
        }
    }

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
	}
}



