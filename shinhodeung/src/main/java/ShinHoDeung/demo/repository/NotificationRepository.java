package ShinHoDeung.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ShinHoDeung.demo.domain.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer>{
    
}
