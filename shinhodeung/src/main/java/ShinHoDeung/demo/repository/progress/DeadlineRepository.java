package ShinHoDeung.demo.repository.progress;

import org.springframework.data.jpa.repository.JpaRepository;

import ShinHoDeung.demo.domain.progress.Deadline;

public interface DeadlineRepository extends JpaRepository<Deadline, Integer>{
    
}
