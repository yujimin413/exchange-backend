package ShinHoDeung.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
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
import ShinHoDeung.demo.service.dto.UniversityOneReturnDto;
import ShinHoDeung.demo.service.dto.UniversitySpecification;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UniversityService {
    
    private final UniversityRepository universityRepository;
    private final InterestedUniversityRepository interestedUniversityRepository;
    private final ReportRepository reportRepository;

    private static final String S3_IMAGE_BASE_URL = "https://shinhodeung-exchangebucket.s3.ap-northeast-2.amazonaws.com/university-images/downloaded_images/";
    
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
            if(university.getNotes()!=null){
                String[] lines = university.getNotes().split("\\r?\\n");
                for(String line : lines){
                    String cleaned = line.replaceFirst("·\\s*", "").trim();
                    if (!cleaned.isEmpty()) {
                        notes.add(cleaned);
                    }
                }
            }

            // isFavorite 검사
            Boolean isFavorite = interestedUniversities.stream().anyMatch(u -> university.equals(u.getUniversity()));

            // tags 분리
            List<String> tags = null;
            if(university.getHashtag()!=null)
                tags = new ArrayList<>(Arrays.asList(university.getHashtag().split(" ")));

            String imageFileName = university.getEnglishName().replace(" ", "_") + ".jpg";
            String imageUrl = S3_IMAGE_BASE_URL + imageFileName;

            // dto build
            UniversityDto dto = UniversityDto.builder()
                                    .id(university.getId())
                                    .koreanName(university.getKoreanName())
                                    .englishName(university.getEnglishName())
                                    .region(university.getRegion())
                                    .country(university.getCountry())
                                    .notes(notes)
                                    .tags(tags)
                                    .image(imageUrl)
                                    .isFavorite(isFavorite)
                                    .latitude(university.getLatitude())
                                    .longitude(university.getLongitude())
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

        if(!result.isPresent())
            throw new NoSuchUniversityException();
        University university = result.get();
        
        // isFavorite
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Boolean isFavorite = interestedUniversityRepository.existsByUserAndUniversity(user, university);

        String imageFileName = university.getEnglishName().replace(" ", "_") + ".jpg";
        String imageUrl = S3_IMAGE_BASE_URL + imageFileName;

        // notes
        ArrayList<String> notes = new ArrayList<String>();
        String[] lines;
        if(university.getNotes()!=null){
            lines = university.getNotes().split("\\r?\\n");
            for(String line : lines){
                String cleaned = line.replaceFirst("·\\s*", "").trim();
                if (!cleaned.isEmpty()) {
                    notes.add(cleaned);
                }
            }
        }
        // specialNotes
        ArrayList<String> specialNotes = new ArrayList<String>();
        if(university.getSpecialNotes()!=null){
            lines = university.getSpecialNotes().split("\\r?\\n");
            for(String line : lines){
                String cleaned = line.replaceFirst("·\\s*", "").trim();
                if (!cleaned.isEmpty()) {
                    specialNotes.add(cleaned);
                }
            }
        }
        // languageRequirement
        ArrayList<String> languageRequirement = new ArrayList<String>();
        if(university.getLanguageRequirement()!=null){
            lines = university.getLanguageRequirement().split("\\r?\\n");
            for(String line : lines){
                String cleaned = line.trim();
                if (!cleaned.isEmpty()) {
                    languageRequirement.add(cleaned);
                }
            }
        }
        // availableCourses
        ArrayList<String> availableCourses = new ArrayList<String>();
        if(university.getAvailableCourses()!=null){
            lines = university.getAvailableCourses().split("\\r?\\n");
            for(String line : lines){
                String cleaned = line.replaceFirst("·\\s*", "").trim();
                if (!cleaned.isEmpty()) {
                    availableCourses.add(cleaned);
                }
            }
        }
        // reportNum
        List<Report> reports = reportRepository.findByUniversity(university.getEnglishName());
        int reportNum = reports.size();
        // reportNames
        List<String> reportNames = reports.stream().map(Report::getFileName).collect(Collectors.toList());
        // tags
        List<String> tags = null;
            if(university.getHashtag()!=null)
                tags = new ArrayList<>(Arrays.asList(university.getHashtag().split(" ")));
        // ai summary
        List<String> aiSummary = new ArrayList<>();
        aiSummary.add(university.getSummaryLocation());
        aiSummary.add(university.getSummaryWeather());
        aiSummary.add(university.getSummaryAcademic());
        aiSummary.add(university.getSummarySafety());

        return UniversityDetailReturnDto.builder()
                .koreanName(university.getKoreanName())
                .englishName(university.getEnglishName())
                .isFavorite(isFavorite)
                .image(imageUrl)
                .website(university.getWebsite())
                .tags(tags)
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
                .aiSummary(aiSummary)
                .build();
    }

    public UniversityFilterReturnDto getFilteredUniversity(UniversityFilterParamDto universityFilterParamDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        // filter find university
        List<University> universities = universityRepository.findAll(UniversitySpecification.filterBy(universityFilterParamDto));
        

        ArrayList<UniversityDto> dtos = new ArrayList<UniversityDto>();
        for(University university: universities){
            // notes 변환
            ArrayList<String> notes = null;
            if(university.getNotes()!=null){
                notes = new ArrayList<String>();
                String[] lines = university.getNotes().split("\\r?\\n");
                for(String line : lines){
                    String cleaned = line.replaceFirst("·\\s*", "").trim();
                    if (!cleaned.isEmpty()) {
                        notes.add(cleaned);
                    }
                }
            }

            // isFavorite 검사
            Boolean isFavorite = interestedUniversityRepository.existsByUserAndUniversity(user, university);

            String imageFileName = university.getEnglishName().replace(" ", "_") + ".jpg";
            String imageUrl = S3_IMAGE_BASE_URL + imageFileName;

            // tags 분리
            List<String> tags = null;
            if(university.getHashtag()!=null)
                tags = new ArrayList<>(Arrays.asList(university.getHashtag().split(" ")));

            // dto build
            UniversityDto dto = UniversityDto.builder()
                                    .id(university.getId())
                                    .koreanName(university.getKoreanName())
                                    .englishName(university.getEnglishName())
                                    .region(university.getRegion())
                                    .country(university.getCountry())
                                    .notes(notes)
                                    .tags(tags)
                                    .image(imageUrl)
                                    .isFavorite(isFavorite)
                                    .latitude(university.getLatitude())
                                    .longitude(university.getLongitude())
                                    .build();
            dtos.add(dto);
        }

        return UniversityFilterReturnDto.builder()
                .universities(dtos)
                .build();
    }

    @NotNull
    public UniversityOneReturnDto getOneUniversity(String universityId) throws NoSuchUniversityException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Optional<University> result = universityRepository.findById(universityId);
        List<InterestedUniversity> interestedUniversities = interestedUniversityRepository.findByUser(user);
        if(!result.isPresent())
            throw new NoSuchUniversityException();
        University university = result.get();

        ArrayList<UniversityDto> dtos = new ArrayList<UniversityDto>();
        
        // notes 변환
        ArrayList<String> notes = new ArrayList<String>();
        if(university.getNotes()!=null){
            String[] lines = university.getNotes().split("\\r?\\n");
            for(String line : lines){
                String cleaned = line.replaceFirst("·\\s*", "").trim();
                if (!cleaned.isEmpty()) {
                    notes.add(cleaned);
                }
            }
        }

        // isFavorite 검사
        Boolean isFavorite = interestedUniversities.stream().anyMatch(u -> university.equals(u.getUniversity()));
        // tags 분리
        List<String> tags = null;
        if(university.getHashtag()!=null)
            tags = new ArrayList<>(Arrays.asList(university.getHashtag().split(" ")));

        String imageFileName = university.getEnglishName().replace(" ", "_") + ".jpg";
        String imageUrl = S3_IMAGE_BASE_URL + imageFileName;

        // dto build
        UniversityDto dto = UniversityDto.builder()
                                .id(university.getId())
                                .koreanName(university.getKoreanName())
                                .englishName(university.getEnglishName())
                                .region(university.getRegion())
                                .country(university.getCountry())
                                .notes(notes)
                                .tags(tags)
                                .image(imageUrl)
                                .isFavorite(isFavorite)
                                .latitude(university.getLatitude())
                                .longitude(university.getLongitude())
                                .build();
        dtos.add(dto);

        return UniversityOneReturnDto.builder()
                .universities(dtos)
                .build();
    }
}
