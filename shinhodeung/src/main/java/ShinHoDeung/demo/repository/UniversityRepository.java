package ShinHoDeung.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ShinHoDeung.demo.domain.University;

@Repository
public interface UniversityRepository extends JpaRepository<University, String>{
    
}
