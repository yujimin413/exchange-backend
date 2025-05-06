package ShinHoDeung.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ShinHoDeung.demo.repository.UserRepository;

@RestController
public class BasicController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String hello(){
        
        return "드디어했읍니다....보고있나신준호......."+userRepository.findAll().size();
    }
}