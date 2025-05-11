package ShinHoDeung.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ShinHoDeung.demo.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{
    
}
