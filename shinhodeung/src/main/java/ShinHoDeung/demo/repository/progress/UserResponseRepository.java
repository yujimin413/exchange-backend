package ShinHoDeung.demo.repository.progress;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ShinHoDeung.demo.domain.User;
import ShinHoDeung.demo.domain.progress.UserResponse;

public interface UserResponseRepository extends JpaRepository<UserResponse, Integer>{
    List<UserResponse> findByUser(User user);
    Optional<UserResponse> findByUserAndComponentId(User user, Integer componentId);
}
