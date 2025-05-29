package ShinHoDeung.demo.repository.process;

import org.springframework.data.jpa.repository.JpaRepository;

import ShinHoDeung.demo.domain.process.CustomCheckPlus;

public interface CustomCheckPlusRepository extends JpaRepository<CustomCheckPlus, Integer>{
    
}
