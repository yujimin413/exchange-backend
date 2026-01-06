package ShinHoDeung.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ShinHoDeung.demo.domain.CountryView;

public interface CountryViewRepository extends JpaRepository<CountryView, String>{
    
}
