package ShinHoDeung.demo.repository.process;

import org.springframework.data.jpa.repository.JpaRepository;

import ShinHoDeung.demo.domain.process.UserCurrent;

public interface UserCurrentRepository extends JpaRepository<UserCurrent, Integer>{
    
}
