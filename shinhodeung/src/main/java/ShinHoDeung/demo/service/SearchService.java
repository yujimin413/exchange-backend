package ShinHoDeung.demo.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        
        List<KeywordView> keywordViews = new ArrayList<>();
        List<KeywordView> startingWords = keywordViewRepository.findByKeywordStartingWith(search);
        List<KeywordView> containWords = keywordViewRepository.findByKeywordContaining(search);
        List<KeywordView> rest = containWords.stream()
        .filter(e -> !startingWords.contains(e))
        .collect(Collectors.toList());
        rest.sort(Comparator.comparing(KeywordView::getKeyword));
        keywordViews.addAll(startingWords);
        keywordViews.addAll(rest);

        for(KeywordView keywordView : keywordViews){
            Detail detail;
            if(keywordView.getType().equals("country")){
                CountryView country = countryViewRepository.findById(keywordView.getKeyword()).get();
                detail = Detail.builder()
                                .region(country.getRegion())
                                .university_count(country.getUniversityCount())
                                .build();
            } else if(keywordView.getType().equals("university")){
                University university = universityRepository.findTopByKoreanName(keywordView.getKeyword()).get();
                detail = Detail.builder()
                        .region(university.getRegion())
                        .country(university.getCountry())
                        .university_id(university.getId())
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
