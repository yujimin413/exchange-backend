package ShinHoDeung.demo.repository.progress;

import org.springframework.data.jpa.repository.JpaRepository;

import ShinHoDeung.demo.domain.progress.MainStep;
import java.util.Optional;


public interface MainStepRepository extends JpaRepository<MainStep, Integer>{
    Optional<MainStep> findBySortOrder(Integer sortOrder);
}
