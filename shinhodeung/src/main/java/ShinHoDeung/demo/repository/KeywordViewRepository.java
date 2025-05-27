package ShinHoDeung.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ShinHoDeung.demo.domain.KeywordView;

public interface KeywordViewRepository extends JpaRepository<KeywordView, String>{
    // keyword LIKE :keyword%  (시작하는 단어)
    List<KeywordView> findByKeywordStartingWith(String keyword);
    // keyword LIKE :%keyword%  (중간에 들어가는 단어)
    List<KeywordView> findByKeywordContaining(String keyword);
}
