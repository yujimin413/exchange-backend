package ShinHoDeung.demo.repository.progress;

import org.springframework.data.jpa.repository.JpaRepository;

import ShinHoDeung.demo.domain.progress.CustomDatePlus;
import java.util.List;
import ShinHoDeung.demo.domain.User;


public interface CustomDatePlusRepository extends JpaRepository<CustomDatePlus, Integer>{
    List<CustomDatePlus> findByUser(User user);
}
