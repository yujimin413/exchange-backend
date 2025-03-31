package ShinHoDeung.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicController {
    @GetMapping("/")
    public String hello(){
        return "드디어했읍니다....보고있나신준호.......";
    }
}