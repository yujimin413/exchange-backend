package ShinHoDeung.demo.controller;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ShinHoDeung.demo.domain.University;
import ShinHoDeung.demo.domain.User;
import ShinHoDeung.demo.repository.UserRepository;
import ShinHoDeung.demo.service.InterestedUniversityService;
import ShinHoDeung.demo.service.UniversityService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/map")
@RequiredArgsConstructor
public class MapController {

    UniversityService universityService;
    InterestedUniversityService interestedUniversityService;
    UserRepository userRepository;

    @NotNull
    @GetMapping("/university/all")
    public List<University> getUniversityAll(){
        return universityService.getAllUniversity();
    }
    
    @GetMapping("/university/like")
    public List<University> getUniversityLike(@RequestParam Integer studentId){
        User user = userRepository.findByStudentId(studentId).get();
        return interestedUniversityService.getInterestedUniversity(user);
    }
}
