package ShinHoDeung.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ShinHoDeung.demo.domain.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer>{
    
}
