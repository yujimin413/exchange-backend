package ShinHoDeung.demo.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ShinHoDeung.demo.controller.dto.ComponentDto;
import ShinHoDeung.demo.controller.dto.DetailDto;
import ShinHoDeung.demo.controller.dto.MainStepDto;
import ShinHoDeung.demo.controller.dto.SubStepDto;
import ShinHoDeung.demo.domain.User;
import ShinHoDeung.demo.domain.progress.Component;
import ShinHoDeung.demo.domain.progress.ContentType;
import ShinHoDeung.demo.domain.progress.CustomBox;
import ShinHoDeung.demo.domain.progress.Detail;
import ShinHoDeung.demo.domain.progress.MainStep;
import ShinHoDeung.demo.domain.progress.SubStep;
import ShinHoDeung.demo.domain.progress.UserCurrent;
import ShinHoDeung.demo.domain.progress.UserResponse;
import ShinHoDeung.demo.repository.progress.ComponentRepository;
import ShinHoDeung.demo.repository.progress.CustomBoxRepository;
import ShinHoDeung.demo.repository.progress.UserCurrentRepository;
import ShinHoDeung.demo.repository.progress.UserResponseRepository;
import ShinHoDeung.demo.service.dto.ProgressCheckStatusParamDto;
import ShinHoDeung.demo.service.dto.ProgressFlowRetrunDto;
import ShinHoDeung.demo.service.dto.ProgressCustomBoxParamDto;
import ShinHoDeung.demo.service.dto.ProgressNewStatusParamDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProgressService {
    
    private final ProgressTreeBuilder progressTreeBuilder;
    private final ComponentRepository componentRepository;
    private final UserCurrentRepository userCurrentRepository;
    private final UserResponseRepository userResponseRepository;
    private final CustomBoxRepository customCheckPlusRepository;
    

    public ProgressFlowRetrunDto getProgressFlow(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        
        // user의 현재 진행상태 불러오기
        Optional<UserCurrent> result = userCurrentRepository.findByUser(user);
        UserCurrent userCurrent;
        if(!result.isPresent()){
            userCurrent = UserCurrent.builder()
                .user(user)
                .mainStepOrder(1)
                .subStepOrder(1)
                .build();
            userCurrentRepository.save(userCurrent);
        } else {
            userCurrent = result.get();
        }

        // user 진행상태 불러오기
        List<UserResponse> userResponses = userResponseRepository.findByUser(user);
        List<CustomBox> customCheckPlus = customCheckPlusRepository.findByUserAndContentType(user, ContentType.CHECK_BOX);
        List<CustomBox> customDatePlus = customCheckPlusRepository.findByUserAndContentType(user, ContentType.DATE);
        
        // json build
        List<MainStep> mainSteps = progressTreeBuilder.build();
        
        List<MainStepDto> mainStepDtos = new ArrayList<>();
        for (MainStep mainStep : mainSteps) {
            List<SubStepDto> subStepDtos = new ArrayList<>();
            List<SubStep> subSteps = mainStep.getSubSteps();
            subSteps.sort(Comparator.comparing(SubStep::getSortOrder));
            for (SubStep subStep : subSteps) {
                List<DetailDto> detailDtos = new ArrayList<>();
                List<Detail> details = subStep.getDetails();
                details.sort(Comparator.comparing(Detail::getSortOrder));
                for (Detail detail : details) {
                    List<ComponentDto> componentDtos = new ArrayList<>();
                    List<Component> components = detail.getComponents();
                    components.sort(Comparator.comparing(Component::getSortOrder));
                    for (Component component : detail.getComponents()) {
                        ComponentDto componentDto = ComponentDto.builder()
                            .contentType(component.getContentType())
                            .content(component.getContent())
                            .note(component.getNote())
                            .build();
                        
                        switch (component.getContentType()) {
                            case CHECK_BOX:
                                boolean exists = userResponses.stream()
                                    .anyMatch(r -> r.getComponent().equals(component)&& r.getValue().equals("checked"));
                                componentDto.setComponentId(component.getId());
                                componentDto.setChecked(exists);
                                break;
                            case CHECK_BOX_PLUS:
                                List<CustomBox> matches = customCheckPlus.stream()
                                    .filter(r -> r.getComponent().equals(component))
                                    .collect(Collectors.toList());
                                componentDto.setComponentId(component.getId());
                                
                                if(matches.size()!=0){
                                    for(CustomBox checkbox : matches){
                                        ComponentDto subDto = ComponentDto.builder()
                                            .customId(checkbox.getId())
                                            .contentType(ContentType.CHECK_BOX)
                                            .content(checkbox.getContent())
                                            .build();
                                        componentDtos.add(subDto);
                                    }
                                }
                                break;
                            case DATE_PLUS:
                                Optional<CustomBox> match = customDatePlus.stream()
                                .filter(r -> r.getComponent().equals(component))
                                .findFirst();
                            
                                if (match.isPresent()) {
                                    componentDto.setContentType(ContentType.DATE);
                                    componentDto.setContent(match.get().getContent());
                                } else {
                                    componentDto.setComponentId(component.getId());
                                }
                                break;
                            default:
                                break;
                        }
                        componentDtos.add(componentDto);
                    }
                    DetailDto detailDto = DetailDto.builder()
                        .title(detail.getTitle())
                        .componentDtos(componentDtos)
                        .build();
                    detailDtos.add(detailDto);
                }
                SubStepDto subStepDto = SubStepDto.builder()
                    .title(subStep.getTitle())
                    .sortOrder(subStep.getSortOrder())
                    .detailDtos(detailDtos)
                    .build();
                subStepDtos.add(subStepDto);
            }
            MainStepDto mainStepDto = MainStepDto.builder()
                .title(mainStep.getTitle())
                .sortOrder(mainStep.getSortOrder())
                .subStepDtos(subStepDtos)
                .build();
            mainStepDtos.add(mainStepDto);
        }

        return ProgressFlowRetrunDto.builder()
            .mainStepOrder(userCurrent.getMainStepOrder())
            .subStepOrder(userCurrent.getSubStepOrder())
            .mainStepDtos(mainStepDtos)
            .build();
    }

    public void updateCheckStatus(ProgressCheckStatusParamDto checkStatusParamDto) throws EntityNotFoundException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Optional<UserResponse> result = userResponseRepository.findByUserAndComponentId(user, checkStatusParamDto.getComponentId());
        UserResponse userResponse;

        if(checkStatusParamDto.getChecked()){
            if(result.isPresent()){
                // 이미 userResponse 존재시 checked 문자만 변경
                userResponse = result.get();
                if(userResponse.getValue().equals("checked")) return;
                else{
                    userResponse.setValue("checked");
                    userResponseRepository.save(userResponse);
                }
            } else {
                // userResponse 없을 시 추가
                Optional<Component> component = componentRepository.findById(checkStatusParamDto.getComponentId());
                if(!component.isPresent())
                    throw new EntityNotFoundException();
                userResponse = new UserResponse();
                userResponse.setComponent(component.get());
                userResponse.setUser(user);
                userResponse.setValue("checked");
                userResponseRepository.save(userResponse);
            }
        } else {
            if(result.isPresent()){
                userResponseRepository.delete(result.get());
            }
        }
    }

    public void updateNewStep(ProgressNewStatusParamDto progressNewStatusParamDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Optional<UserCurrent> result = userCurrentRepository.findByUser(user);
        UserCurrent userCurrent;
        if(result.isPresent()){
            userCurrent = result.get();
        } else {
            userCurrent = new UserCurrent();
            userCurrent.setUser(user);
        }
        userCurrent.setMainStepOrder(progressNewStatusParamDto.getMainStepOrder());
        userCurrent.setSubStepOrder(progressNewStatusParamDto.getSubStepOrder());
        userCurrentRepository.save(userCurrent);
    }

    public void addCustomBox(ProgressCustomBoxParamDto progressCustomBoxParamDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Optional<Component> result = componentRepository.findById(progressCustomBoxParamDto.getComponentId());
        if(!result.isPresent())
            throw new EntityNotFoundException();
        if(progressCustomBoxParamDto.getContentType().equals(ContentType.DATE)){
            // 날짜 처리 + 알람 설정
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 H:mm");
            try {
                LocalDateTime.parse(progressCustomBoxParamDto.getContent(), formatter);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("날짜 형식이 올바르지 않습니다. 예: 2025년 3월 25일 12:30");
            }
        } else if(progressCustomBoxParamDto.getContentType().equals(ContentType.CHECK_BOX)) {    
        } else {
            throw new IllegalArgumentException("contentType이 올바르지 않습니다.");
        }
        
        
        CustomBox customBox = CustomBox.builder()
            .component(result.get())
            .contentType(progressCustomBoxParamDto.getContentType())
            .user(user)
            .content(progressCustomBoxParamDto.getContent())
            .build();
        
        customCheckPlusRepository.save(customBox);
    }
}
