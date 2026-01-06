package ShinHoDeung.demo.repository.progress;

import org.springframework.data.jpa.repository.JpaRepository;

import ShinHoDeung.demo.domain.progress.Detail;
import ShinHoDeung.demo.domain.progress.SubStep;

import java.util.List;


public interface DetailRepository extends JpaRepository<Detail, Integer>{
    List<Detail> findBySubStep(SubStep subStep);
}
