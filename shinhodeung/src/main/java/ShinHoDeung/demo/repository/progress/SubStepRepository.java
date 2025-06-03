package ShinHoDeung.demo.repository.progress;

import org.springframework.data.jpa.repository.JpaRepository;

import ShinHoDeung.demo.domain.progress.MainStep;
import ShinHoDeung.demo.domain.progress.SubStep;

import java.util.List;


public interface SubStepRepository extends JpaRepository<SubStep, Integer>{
    List<SubStep> findByMainStep(MainStep mainStep);
}
