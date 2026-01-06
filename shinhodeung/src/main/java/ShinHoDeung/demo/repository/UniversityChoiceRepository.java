package ShinHoDeung.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ShinHoDeung.demo.domain.UniversityChoice;
import ShinHoDeung.demo.domain.User;

import java.util.Optional;


public interface UniversityChoiceRepository extends JpaRepository<UniversityChoice, Integer>{
    Optional<UniversityChoice> findByUser(User user);
}
