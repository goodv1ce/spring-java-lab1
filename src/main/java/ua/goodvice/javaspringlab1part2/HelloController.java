package ua.goodvice.javaspringlab1part2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping("/")
    @ResponseBody
    public String helloWorld() {
        return "Hello, World!";
    }

    @GetMapping("/team")
    public String ourTeam() {
        return "our-team.html";
    }
}
