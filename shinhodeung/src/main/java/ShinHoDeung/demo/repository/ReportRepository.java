package ShinHoDeung.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ShinHoDeung.demo.domain.Report;

import java.util.List;


@Repository
public interface ReportRepository extends JpaRepository<Report, String>{
    List<Report> findByUniversity(String university);
}
