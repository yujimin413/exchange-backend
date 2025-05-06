package ShinHoDeung.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ShinHoDeung.demo.domain.Announcement;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Integer>{
    
}
