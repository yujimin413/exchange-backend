package ShinHoDeung.demo.repository.process;

import org.springframework.data.jpa.repository.JpaRepository;

import ShinHoDeung.demo.domain.process.Component;

public interface ComponentRepository extends JpaRepository<Component, Integer>{
    
}
