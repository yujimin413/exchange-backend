package ShinHoDeung.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ShinHoDeung.demo.domain.LanguageTest;

@Repository
public interface LanguageTestRepository extends JpaRepository<LanguageTest, Integer>{
    
}
