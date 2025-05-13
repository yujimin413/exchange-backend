package ShinHoDeung.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import ShinHoDeung.demo.domain.InterestedUniversity;
import ShinHoDeung.demo.domain.University;
import ShinHoDeung.demo.domain.User;
import ShinHoDeung.demo.repository.InterestedUniversityRepository;
import ShinHoDeung.demo.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UniversityService {
    
    UniversityRepository universityRepository;
    InterestedUniversityRepository interestedUniversityRepository;
    
    @NotNull
    public List<University> getAllUniversity(){
        return universityRepository.findAll();
    }

    @NotNull
    public List<University> getInterestedUniversity(User user){
        List<InterestedUniversity> interestedUniversities = interestedUniversityRepository.findByUser(user);
        return interestedUniversities.stream().map(InterestedUniversity::getUniversity)
                                .collect(Collectors.toList());
    }
}
