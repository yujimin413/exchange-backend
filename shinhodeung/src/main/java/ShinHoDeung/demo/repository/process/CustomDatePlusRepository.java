package ShinHoDeung.demo.repository.process;

import org.springframework.data.jpa.repository.JpaRepository;

import ShinHoDeung.demo.domain.process.CustomDatePlus;

public interface CustomDatePlusRepository extends JpaRepository<CustomDatePlus, Integer>{
    
}
