package ShinHoDeung.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ShinHoDeung.demo.domain.University;
import java.util.Optional;


@Repository
public interface UniversityRepository extends JpaRepository<University, String>, JpaSpecificationExecutor<University> {
    // 지원방법이 여러가지인 대학의 경우 같은 대학이 중복검색됨. -> 상위 1개만 검색
    Optional<University> findTopByKoreanName(String koreanName);
}
