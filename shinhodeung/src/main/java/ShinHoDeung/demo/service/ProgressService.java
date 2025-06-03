package ShinHoDeung.demo.service;

import java.time.LocalDate;
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
import ShinHoDeung.demo.domain.progress.Detail;
import ShinHoDeung.demo.domain.progress.MainStep;
import ShinHoDeung.demo.domain.progress.MetaComponent;
import ShinHoDeung.demo.domain.progress.SubStep;
import ShinHoDeung.demo.domain.progress.UserCurrent;
import ShinHoDeung.demo.domain.progress.UserResponse;
import ShinHoDeung.demo.repository.progress.ComponentRepository;
import ShinHoDeung.demo.repository.progress.DetailRepository;
import ShinHoDeung.demo.repository.progress.MetaComponentRepository;
import ShinHoDeung.demo.repository.progress.UserCurrentRepository;
import ShinHoDeung.demo.repository.progress.UserResponseRepository;
import ShinHoDeung.demo.service.dto.ProgressCheckStatusParamDto;
import ShinHoDeung.demo.service.dto.ProgressFlowRetrunDto;
import ShinHoDeung.demo.service.dto.ProgressUpdateComponentParamDto;
import ShinHoDeung.demo.service.dto.ProgressNewStatusParamDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProgressService {
    
    private final ProgressTreeBuilder progressTreeBuilder;
    private final DetailRepository detailRepository;
    private final MetaComponentRepository metaComponentRepository;
    private final ComponentRepository componentRepository;
    private final UserCurrentRepository userCurrentRepository;
    private final UserResponseRepository userResponseRepository;
    
    
    

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
            List<MetaComponent> metaComponents = metaComponentRepository.findAll();
            List<Component> components = metaComponents.stream()
            .map((MetaComponent meta)-> Component.builder()
                .contentType(meta.getContentType())
                .content(meta.getContent())
                .note(meta.getNote())
                .detail(meta.getDetail())
                .user(user)
                .build()
            ).collect(Collectors.toList());
            componentRepository.saveAll(components);
        } else {
            userCurrent = result.get();
        }

        // user 진행상태 불러오기
        List<UserResponse> userResponses = userResponseRepository.findByUser(user);
        
        // json build
        List<MainStep> mainSteps = progressTreeBuilder.build(user);
        
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
                    for (Component component : detail.getComponents()) {
                        ComponentDto componentDto = ComponentDto.builder()
                            .contentType(component.getContentType())
                            .content(component.getContent())
                            .note(component.getNote())
                            .build();
                        if(component.getContentType().equals(ContentType.CHECK_BOX)){
                            boolean exists = userResponses.stream()
                                    .anyMatch(r -> r.getComponent().equals(component)&& r.getValue().equals("checked"));
                            componentDto.setComponentId(component.getId());
                            componentDto.setChecked(exists);
                            componentDto.setDueAt(component.getDueAt());
                        } else if(component.getContentType().equals(ContentType.DATE_PLUS)){
                            componentDto.setComponentId(component.getId());
                        }
                        componentDtos.add(componentDto);
                    }
                    if(detail.getEditable()!=null && detail.getEditable()){
                        ComponentDto componentDto = ComponentDto.builder()
                            .contentType(ContentType.CHECK_BOX_PLUS)
                            .content("")
                            .build();
                        componentDtos.add(componentDto);
                    }
                    DetailDto detailDto = DetailDto.builder()
                        .title(detail.getTitle())
                        .componentDtos(componentDtos)
                        .detailId(detail.getId())
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

    public void updateComponent(ProgressUpdateComponentParamDto progressDatePlusParamDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Optional<Component> result = componentRepository.findById(progressDatePlusParamDto.getComponentId());
        if(!result.isPresent())
            throw new EntityNotFoundException();
        if(!result.get().getUser().equals(user))
            throw new EntityNotFoundException();

        if(progressDatePlusParamDto.getContentType().equals(ContentType.DATE)){
            // 날짜 처리 + 알람 설정
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일");
            try {
                LocalDate.parse(progressDatePlusParamDto.getContent(), formatter);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("날짜 형식이 올바르지 않습니다. 예: 2025년 3월 25일");
            }
            
            Component component = result.get();
            component.setContentType(ContentType.DATE);
            component.setContent(progressDatePlusParamDto.getContent());
            componentRepository.save(component);
        } else if(progressDatePlusParamDto.getContentType().equals(ContentType.CHECK_BOX)){
            Component component = result.get();
            if(progressDatePlusParamDto.getContent()!=null)
                component.setContent(progressDatePlusParamDto.getContent());
            if(progressDatePlusParamDto.getNote()!=null)
                component.setNote(progressDatePlusParamDto.getNote());
            if(progressDatePlusParamDto.getDueAt()!=null)
                component.setDueAt(progressDatePlusParamDto.getDueAt());
            componentRepository.save(component);
        }else {
            throw new IllegalArgumentException("contentType이 올바르지 않습니다.");
        }
        
    }

    public Integer addCheckBox(Integer detailId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Optional<Detail> result = detailRepository.findById(detailId);
        if(!result.isPresent())
            throw new EntityNotFoundException();

        Component component = Component.builder()
            .contentType(ContentType.CHECK_BOX)
            .user(user)
            .detail(result.get())
            .content("")
            .build();
        component = componentRepository.save(component);

        return component.getId();
    }
}
