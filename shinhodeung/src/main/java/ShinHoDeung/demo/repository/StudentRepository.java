package ShinHoDeung.demo.repository;

import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import ShinHoDeung.demo.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    @NotNull
    Optional<Student> findById(@NotNull Integer id);
    // @NotNull
    // List<Student> findAllByXnApiTokenIsNotNull();
}
