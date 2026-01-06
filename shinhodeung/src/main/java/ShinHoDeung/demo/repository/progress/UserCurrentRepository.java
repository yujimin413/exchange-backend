package ShinHoDeung.demo.repository.progress;

import org.springframework.data.jpa.repository.JpaRepository;

import ShinHoDeung.demo.domain.progress.UserCurrent;
import java.util.Optional;

import ShinHoDeung.demo.domain.User;


public interface UserCurrentRepository extends JpaRepository<UserCurrent, Integer>{
    Optional<UserCurrent> findByUser(User user);
}
