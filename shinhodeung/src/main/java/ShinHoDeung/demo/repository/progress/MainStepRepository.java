package ShinHoDeung.demo.repository.progress;

import org.springframework.data.jpa.repository.JpaRepository;

import ShinHoDeung.demo.domain.progress.MainStep;

public interface MainStepRepository extends JpaRepository<MainStep, Integer>{
    
}
