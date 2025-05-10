package ShinHoDeung.demo.repository;

import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import ShinHoDeung.demo.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    @NotNull
    Optional<User> findByStudentId(@NotNull Integer studentId);
}
