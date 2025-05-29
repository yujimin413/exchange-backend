package ShinHoDeung.demo.repository.process;

import org.springframework.data.jpa.repository.JpaRepository;

import ShinHoDeung.demo.domain.process.UserResponse;

public interface UserResponseRepository extends JpaRepository<UserResponse, Integer>{
    
}
