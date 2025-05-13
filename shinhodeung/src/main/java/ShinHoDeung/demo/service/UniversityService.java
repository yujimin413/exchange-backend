package ShinHoDeung.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ShinHoDeung.demo.controller.dto.UniversityDto;
import ShinHoDeung.demo.domain.InterestedUniversity;
import ShinHoDeung.demo.domain.University;
import ShinHoDeung.demo.domain.User;
import ShinHoDeung.demo.repository.InterestedUniversityRepository;
import ShinHoDeung.demo.repository.UniversityRepository;
import ShinHoDeung.demo.service.dto.UniversityAllReturnDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UniversityService {
    
    private final UniversityRepository universityRepository;
    private final InterestedUniversityRepository interestedUniversityRepository;
    
    @NotNull
    public UniversityAllReturnDto getAllUniversity(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        List<University> universities = universityRepository.findAll();
        List<InterestedUniversity> interestedUniversities = interestedUniversityRepository.findByUser(user);

        ArrayList<UniversityDto> dtos = new ArrayList<UniversityDto>();
        for(University university: universities){
            // notes 변환
            ArrayList<String> notes = new ArrayList<String>();
            String[] lines = university.getNotes().split("\\r?\\n");
            for(String line : lines){
                String cleaned = line.replaceFirst("·\\s*", "").trim();
                if (!cleaned.isEmpty()) {
                    notes.add(cleaned);
                }
            }

            // isFavorite 검사
            Boolean isFavorite = interestedUniversities.stream().anyMatch(u -> university.equals(u.getUniversity()));

            // dto build
            UniversityDto dto = UniversityDto.builder()
                                    .id(university.getId())
                                    .korean_name(university.getKoreanName())
                                    .english_name(university.getEnglishName())
                                    .notes(notes)
                                    .tags(null)
                                    .image(null)
                                    .isFavorite(isFavorite)
                                    .build();
            dtos.add(dto);
        }

        return UniversityAllReturnDto.builder()
                .universities(dtos)
                .build();
    }

    public void saveInterestedUniversity(String universityId) throws Exception{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Optional<University> university = universityRepository.findById(universityId);
        System.out.println(universityId);
        if(!university.isPresent())
            throw new Exception();

        InterestedUniversity interestedUniversity = new InterestedUniversity();
        interestedUniversity.setUser(user);
        interestedUniversity.setUniversity(university.get());
        interestedUniversityRepository.save(interestedUniversity);
    }
}
