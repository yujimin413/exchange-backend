package ShinHoDeung.demo.repository;

import ShinHoDeung.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ShinHoDeung.demo.domain.LanguageTest;

import java.util.List;

@Repository
public interface LanguageTestRepository extends JpaRepository<LanguageTest, Integer>{
    // 사용자 기준으로 언어시험 점수 목록 조회
    List<LanguageTest> findByUser(User user);
}
