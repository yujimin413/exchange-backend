package ShinHoDeung.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ShinHoDeung.demo.domain.User;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    public List<User> findByStudentId(Integer studentId);
}
