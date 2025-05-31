package ShinHoDeung.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ShinHoDeung.demo.domain.progress.Component;
import ShinHoDeung.demo.domain.progress.Detail;
import ShinHoDeung.demo.domain.progress.MainStep;
import ShinHoDeung.demo.domain.progress.SubStep;
import ShinHoDeung.demo.repository.progress.ComponentRepository;
import ShinHoDeung.demo.repository.progress.DetailRepository;
import ShinHoDeung.demo.repository.progress.MainStepRepository;
import ShinHoDeung.demo.repository.progress.SubStepRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProgressTreeBuilder {
    private final MainStepRepository mainStepRepository;
    private final SubStepRepository subStepRepository;
    private final DetailRepository detailRepository;
    private final ComponentRepository componentRepository;

    public List<MainStep> build(){
        List<MainStep> mainSteps = mainStepRepository.findAll();

        for (MainStep mainStep : mainSteps) {
            List<SubStep> subSteps = subStepRepository.findByMainStep(mainStep);
            mainStep.setSubSteps(subSteps);

            for (SubStep subStep : subSteps) {
                List<Detail> details = detailRepository.findBySubStep(subStep);
                subStep.setDetails(details);

                for (Detail detail : details) {
                    List<Component> components = componentRepository.findByDetail(detail);
                    detail.setComponents(components);
                }
            }
        }
        return mainSteps;
    }
}
