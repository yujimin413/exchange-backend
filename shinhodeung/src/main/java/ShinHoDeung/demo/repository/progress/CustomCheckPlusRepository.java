package ShinHoDeung.demo.repository.progress;

import org.springframework.data.jpa.repository.JpaRepository;

import ShinHoDeung.demo.domain.progress.CustomCheckPlus;
import java.util.List;
import ShinHoDeung.demo.domain.User;


public interface CustomCheckPlusRepository extends JpaRepository<CustomCheckPlus, Integer>{
    List<CustomCheckPlus> findByUser(User user);
}
