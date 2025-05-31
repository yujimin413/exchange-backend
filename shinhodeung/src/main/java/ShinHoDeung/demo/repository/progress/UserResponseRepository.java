package ShinHoDeung.demo.repository.progress;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ShinHoDeung.demo.domain.User;
import ShinHoDeung.demo.domain.progress.UserResponse;

public interface UserResponseRepository extends JpaRepository<UserResponse, Integer>{
    List<UserResponse> findByUser(User user);
}
