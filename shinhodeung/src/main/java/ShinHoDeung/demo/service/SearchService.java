package ShinHoDeung.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ShinHoDeung.demo.controller.dto.Detail;
import ShinHoDeung.demo.controller.dto.Keyword;
import ShinHoDeung.demo.domain.CountryView;
import ShinHoDeung.demo.domain.KeywordView;
import ShinHoDeung.demo.domain.University;
import ShinHoDeung.demo.repository.CountryViewRepository;
import ShinHoDeung.demo.repository.KeywordViewRepository;
import ShinHoDeung.demo.repository.UniversityRepository;
import ShinHoDeung.demo.service.dto.SearchReturnParamDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchService {
    
    private final KeywordViewRepository keywordViewRepository;
    private final CountryViewRepository countryViewRepository;
    private final UniversityRepository universityRepository;

    public SearchReturnParamDto getKeywords(String search){
        List<Keyword> keywords = new ArrayList<Keyword>();
        
        List<KeywordView> keywordViews = keywordViewRepository.findByKeywordStartingWith(search);
        for(KeywordView keywordView : keywordViews){
            Detail detail;
            if(keywordView.getType().equals("country")){
                CountryView country = countryViewRepository.findById(keywordView.getKeyword()).get();
                detail = Detail.builder()
                                .region(country.getRegion())
                                .university_count(country.getUniversityCount())
                                .build();
            } else if(keywordView.getType().equals("university")){
                University university = universityRepository.findByKoreanName(keywordView.getKeyword()).get();
                detail = Detail.builder()
                        .region(university.getRegion())
                        .english_name(university.getEnglishName())
                        .tags(null)
                        .build();
            } else {
                detail = null;
            }
            Keyword keyword = Keyword.builder()
                .type(keywordView.getType())
                .name(keywordView.getKeyword())
                .detail(detail)
                .build();

            keywords.add(keyword);
        }

        return SearchReturnParamDto.builder()
                .keywords(keywords)
                .build();
    }
}
