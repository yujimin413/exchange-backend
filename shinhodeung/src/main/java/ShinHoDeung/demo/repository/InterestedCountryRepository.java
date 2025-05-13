package ShinHoDeung.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ShinHoDeung.demo.domain.InterestedCountry;

public interface InterestedCountryRepository extends JpaRepository<InterestedCountry, Integer>{
    
}
