package ShinHoDeung.demo.service.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.domain.Specification;

import ShinHoDeung.demo.controller.dto.LangaugeSelection;

import ShinHoDeung.demo.domain.University;
import jakarta.persistence.criteria.Predicate;

public class UniversitySpecification {

    private static final Map<String, List<String>> map = Map.of(
        "영어(비유럽)", List.of("A-1", "A-2", "A-3", "A-4", "A-5"),
        "영어(유럽)", List.of("A-1", "B-1", "B-2", "C-1", "C-2"),
        "중국어", List.of("B-1", "B-2", "B-3"),
        "일본어", List.of("C-1", "C-2"),
        "프랑스어", List.of("D-1", "D-2", "D-3"),
        "독일어", List.of("E-1", "E-2", "E-3")
    );

    public static Specification<University> filterBy(UniversityFilterParamDto request) throws IllegalArgumentException{
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getRegion() != null && !request.getRegion().isEmpty()) {
                predicates.add(cb.equal(root.get("region"), request.getRegion()));
            }

            if (request.getCountries() != null && !request.getCountries().isEmpty()) {
                predicates.add(root.get("country").in(request.getCountries()));
            }

            if (request.getProgramTypes() != null && !request.getProgramTypes().isEmpty()) {
                predicates.add(root.get("programType").in(request.getProgramTypes()));
            }

            if (request.getAvailableMajors() != null && !request.getAvailableMajors().isEmpty()) {
                List<Predicate> majorPredicates = new ArrayList<>();
                for (String major : request.getAvailableMajors()) {
                    // '공학/기술' 이 포함된 문자열 찾기
                    majorPredicates.add(
                        cb.like(root.get("availableMajors"), "%" + major + "%")
                    );
                }
                // 여러 major 중 하나라도 포함되면 통과
                predicates.add(cb.or(majorPredicates.toArray(new Predicate[0])));
            }

            if (request.getLanguageSelections() != null && !request.getLanguageSelections().isEmpty()) {
                for(LangaugeSelection lang : request.getLanguageSelections()){
                    List<String> tests = map.get(lang.getLanguageRegion());
                    if(tests==null) 
                        throw new IllegalArgumentException();
                    if(!tests.contains(lang.getTest())) 
                        throw new IllegalArgumentException();
                }

                List<Predicate> langPredicates = new ArrayList<>();
                for (LangaugeSelection lang : request.getLanguageSelections()){
                    langPredicates.add(
                        cb.like(root.get("languageRegion"), "%"+lang.getLanguageRegion()+"%")
                    );
                }
                predicates.add(cb.or(langPredicates.toArray(new Predicate[0])));                

                List<Predicate> testPredicates = new ArrayList<>();
                for (LangaugeSelection lang : request.getLanguageSelections()){
                    List<String> list = map.get(lang.getLanguageRegion());
                    List<String> sliced = list.subList(list.indexOf(lang.getTest()), list.size());
                    List<Predicate> rangePredicates = new ArrayList<>();
                    for(String slice : sliced){
                        rangePredicates.add(
                            cb.like(root.get("languageRequirement"), "%"+slice+"%")
                        );
                    }
                    testPredicates.add(cb.or(rangePredicates.toArray(new Predicate[0])));
                }
                predicates.add(cb.or(testPredicates.toArray(new Predicate[0])));                
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
