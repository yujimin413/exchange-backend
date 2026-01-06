package ShinHoDeung.demo.repository.progress;

import org.springframework.data.jpa.repository.JpaRepository;

import ShinHoDeung.demo.domain.User;
import ShinHoDeung.demo.domain.progress.Component;
import ShinHoDeung.demo.domain.progress.Detail;

import java.util.List;


public interface ComponentRepository extends JpaRepository<Component, Integer>{
    List<Component> findByUserAndDetail(User user, Detail detail);
    List<Component> findByUser(User user);
}
