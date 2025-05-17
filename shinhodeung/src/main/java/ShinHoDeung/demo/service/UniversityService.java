package ShinHoDeung.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ShinHoDeung.demo.controller.dto.UniversityDto;
import ShinHoDeung.demo.domain.InterestedUniversity;
import ShinHoDeung.demo.domain.Report;
import ShinHoDeung.demo.domain.University;
import ShinHoDeung.demo.domain.User;
import ShinHoDeung.demo.exception.NoSuchUniversityException;
import ShinHoDeung.demo.repository.InterestedUniversityRepository;
import ShinHoDeung.demo.repository.ReportRepository;
import ShinHoDeung.demo.repository.UniversityRepository;
import ShinHoDeung.demo.service.dto.UniversityAllReturnDto;
import ShinHoDeung.demo.service.dto.UniversityDetailReturnDto;
import ShinHoDeung.demo.service.dto.UniversityFilterParamDto;
import ShinHoDeung.demo.service.dto.UniversityFilterReturnDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UniversityService {
    
    private final UniversityRepository universityRepository;
    private final InterestedUniversityRepository interestedUniversityRepository;
    private final ReportRepository reportRepository;
    
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
                                    .koreanName(university.getKoreanName())
                                    .englishName(university.getEnglishName())
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

    public void saveInterestedUniversity(String universityId) throws NoSuchUniversityException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Optional<University> university = universityRepository.findById(universityId);
        System.out.println(universityId);
        if(!university.isPresent())
            throw new NoSuchUniversityException();

        InterestedUniversity interestedUniversity = new InterestedUniversity();
        interestedUniversity.setUser(user);
        interestedUniversity.setUniversity(university.get());
        interestedUniversityRepository.save(interestedUniversity);
    }

    public void deleteInterestedUniversity(String universityId) throws NoSuchUniversityException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Optional<University> university = universityRepository.findById(universityId);
        
        if(!university.isPresent())
            throw new NoSuchUniversityException();
        
        Optional<InterestedUniversity> interestedUniversity = interestedUniversityRepository.findByUserAndUniversity(user, university.get());
        if(interestedUniversity.isPresent())
            interestedUniversityRepository.delete(interestedUniversity.get());
    }

    public UniversityDetailReturnDto getUnviersityDetail(String universityId) throws NoSuchUniversityException{
        Optional<University> result = universityRepository.findById(universityId);

        System.out.println(universityId);
        System.out.println(result.isPresent());
        System.out.println(result.get());
        if(!result.isPresent())
            throw new NoSuchUniversityException();
        University university = result.get();
        
        // isFavorite
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Boolean isFavorite = interestedUniversityRepository.existsByUserAndUniversity(user, university);
        // notes
        ArrayList<String> notes = new ArrayList<String>();
        String[] lines = university.getNotes().split("\\r?\\n");
        for(String line : lines){
            String cleaned = line.replaceFirst("·\\s*", "").trim();
            if (!cleaned.isEmpty()) {
                notes.add(cleaned);
            }
        }
        // specialNotes
        ArrayList<String> specialNotes = new ArrayList<String>();
        lines = university.getSpecialNotes().split("\\r?\\n");
        for(String line : lines){
            String cleaned = line.replaceFirst("·\\s*", "").trim();
            if (!cleaned.isEmpty()) {
                specialNotes.add(cleaned);
            }
        }
        // languageRequirement
        ArrayList<String> languageRequirement = new ArrayList<String>();
        lines = university.getSpecialNotes().split("\\r?\\n");
        for(String line : lines){
            String cleaned = line.trim();
            if (!cleaned.isEmpty()) {
                languageRequirement.add(cleaned);
            }
        }
        // availableCourses
        ArrayList<String> availableCourses = new ArrayList<String>();
        lines = university.getSpecialNotes().split("\\r?\\n");
        for(String line : lines){
            String cleaned = line.replaceFirst("·\\s*", "").trim();
            if (!cleaned.isEmpty()) {
                availableCourses.add(cleaned);
            }
        }
        // reportNum
        List<Report> reports = reportRepository.findByUniversity(university.getEnglishName());
        int reportNum = reports.size();
        // reportNames
        List<String> reportNames = reports.stream().map(Report::getFileName).collect(Collectors.toList());

        return UniversityDetailReturnDto.builder()
                .koreanName(university.getKoreanName())
                .englishName(university.getEnglishName())
                .isFavorite(isFavorite)
                .image(university.getImage())
                .website(university.getWebsite())
                // .tags(tags)
                .tags(null)
                .programType(university.getProgramType())
                .institution(university.getInstitution())
                .id(university.getId())
                .creditRequirement(university.getCreditRequirement())
                .languageRequirement(languageRequirement)
                .region(university.getRegion())
                .country(university.getCountry())
                .languageRegion(university.getLanguageRegion())
                .specialNotes(specialNotes)
                .notes(notes)
                .availableCourses(availableCourses)
                .reportNum(reportNum)
                .reportNames(reportNames)
                // .aiSummary(aiSummary)
                .aiSummary(null)
                .build();
    }

    public UniversityFilterReturnDto getFilteredUniversity(UniversityFilterParamDto universityFilterParamDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        // filter find university
        List<University> universities = universityRepository.findAll();

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
            Boolean isFavorite = interestedUniversityRepository.existsByUserAndUniversity(user, university);

            // dto build
            UniversityDto dto = UniversityDto.builder()
                                    .id(university.getId())
                                    .koreanName(university.getKoreanName())
                                    .englishName(university.getEnglishName())
                                    .notes(notes)
                                    .tags(null)
                                    .image(null)
                                    .isFavorite(isFavorite)
                                    .build();
            dtos.add(dto);
        }

        return UniversityFilterReturnDto.builder()
                .universities(null)
                .build();
    }
}
