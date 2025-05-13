package ShinHoDeung.demo.controller;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ShinHoDeung.demo.common.CommonResponse;
import ShinHoDeung.demo.domain.University;
import ShinHoDeung.demo.domain.User;
import ShinHoDeung.demo.repository.UserRepository;
import ShinHoDeung.demo.service.UniversityService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/university")
@RequiredArgsConstructor
public class UniversityController {

    UniversityService universityService;
    UserRepository userRepository;

    @NotNull
    @GetMapping("/all")
    public ResponseEntity<List<University>> getUniversityAll(){
        return ResponseEntity.ok(universityService.getAllUniversity());
    }
    
    @GetMapping("/like")
    public ResponseEntity<List<University>> getUniversityLike(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(universityService.getInterestedUniversity(user));
    }
}
