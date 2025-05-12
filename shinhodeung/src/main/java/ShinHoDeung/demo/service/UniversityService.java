package ShinHoDeung.demo.service;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import ShinHoDeung.demo.domain.University;
import ShinHoDeung.demo.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UniversityService {
    
    UniversityRepository universityRepository;

    @NotNull
    public List<University> getAllUniversity(){
        return universityRepository.findAll();
    }
}
