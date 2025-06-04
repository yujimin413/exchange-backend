package ShinHoDeung.demo.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ShinHoDeung.demo.controller.dto.ComponentDto;
import ShinHoDeung.demo.controller.dto.MainStepDto;
import ShinHoDeung.demo.controller.dto.UnivChoiceDto;
import ShinHoDeung.demo.domain.UniversityChoice;
import ShinHoDeung.demo.domain.User;
import ShinHoDeung.demo.domain.progress.Component;
import ShinHoDeung.demo.domain.progress.MainStep;
import ShinHoDeung.demo.domain.progress.UserCurrent;
import ShinHoDeung.demo.domain.progress.UserResponse;
import ShinHoDeung.demo.repository.UniversityChoiceRepository;
import ShinHoDeung.demo.repository.progress.ComponentRepository;
import ShinHoDeung.demo.repository.progress.MainStepRepository;
import ShinHoDeung.demo.repository.progress.UserCurrentRepository;
import ShinHoDeung.demo.repository.progress.UserResponseRepository;
import ShinHoDeung.demo.service.dto.HomeReturnDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final UniversityChoiceRepository universityChoiceRepository;
    private final UserCurrentRepository userCurrentRepository;
    private final MainStepRepository mainStepRepository;
    private final ComponentRepository componentRepository;
    private final UserResponseRepository userResponseRepository;
    
    public HomeReturnDto getHome(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        
        // 지망대학교 리스트 불러오기
        List<UnivChoiceDto> univs = new ArrayList<>();
        Optional<UniversityChoice> result = universityChoiceRepository.findByUser(user);
        if(result.isPresent()){
            UniversityChoice universityChoice = result.get();
            if(universityChoice.getUniversity1()!=null)
                univs.add(UnivChoiceDto.builder().univId(universityChoice.getUniversity1().getId()).order(1).build());
            if(universityChoice.getUniversity2()!=null)
                univs.add(UnivChoiceDto.builder().univId(universityChoice.getUniversity2().getId()).order(2).build());
            if(universityChoice.getUniversity3()!=null)
                univs.add(UnivChoiceDto.builder().univId(universityChoice.getUniversity3().getId()).order(3).build());
        }
        
        // 진행상태 불러오기
        MainStepDto progress = MainStepDto.builder().build();
        Optional<UserCurrent> result2 = userCurrentRepository.findByUser(user);
        if(result2.isPresent()){
            UserCurrent userCurrent = result2.get();
            progress.setSortOrder(userCurrent.getMainStepOrder()); 

            MainStep mainstep = mainStepRepository.findBySortOrder(userCurrent.getMainStepOrder()).get();
            progress.setTitle(mainstep.getTitle());
        } else {
            progress.setSortOrder(1);
            progress.setTitle(mainStepRepository.findBySortOrder(1).get().getTitle()); 
        }

        // d-day있는 component 불러오기
        List<ComponentDto> componentDtos = null;
        List<Component> components = componentRepository.findByUser(user);
        if(components.size() != 0){
            // chekced component set 미리 확보
            List<UserResponse> userResponses = userResponseRepository.findByUser(user);
            Set<Component> checkedComponents = userResponses.stream()
                .filter(response->"checked".equals(response.getValue()))
                .map(response->response.getComponent())
                .collect(Collectors.toSet());

            List<Component> sortedComponents = components.stream()
                .filter(component -> component.getDueAt() != null)
                .filter(component -> !checkedComponents.contains(component))
                .sorted(Comparator.comparing(Component::getDueAt))
                .collect(Collectors.toList());
            
            componentDtos = sortedComponents.stream()
                .map((Component component) -> ComponentDto.builder()
                    .componentId(component.getId())
                    .contentType(component.getContentType())
                    .content(component.getContent())
                    .note(component.getNote())
                    .dueAt(component.getDueAt())
                    .checked(false)
                    .build())
                .collect(Collectors.toList());
        }

        return HomeReturnDto.builder()
            .univs(univs)
            .progress(progress)
            .components(componentDtos)
        .build();
    }
}
