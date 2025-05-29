package ShinHoDeung.demo.repository.process;

import org.springframework.data.jpa.repository.JpaRepository;

import ShinHoDeung.demo.domain.process.Deadline;

public interface DeadlineRepository extends JpaRepository<Deadline, Integer>{
    
}
