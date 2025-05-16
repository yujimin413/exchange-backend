package ShinHoDeung.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ShinHoDeung.demo.domain.InterestedUniversity;
import ShinHoDeung.demo.domain.University;
import ShinHoDeung.demo.domain.User;

public interface InterestedUniversityRepository extends JpaRepository<InterestedUniversity, Integer>{
    boolean existsByUserAndUniversity(User user, University university);
    Optional<InterestedUniversity> findByUserAndUniversity(User user, University university);
    List<InterestedUniversity> findByUser(User user);
}
