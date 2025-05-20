package ShinHoDeung.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ShinHoDeung.demo.domain.University;
import java.util.Optional;


@Repository
public interface UniversityRepository extends JpaRepository<University, String>, JpaSpecificationExecutor<University> {
    Optional<University> findByKoreanName(String koreanName);
}
