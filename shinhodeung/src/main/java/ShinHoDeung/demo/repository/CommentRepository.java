package ShinHoDeung.demo.repository;

import ShinHoDeung.demo.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ShinHoDeung.demo.domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>{
    int countByPost(Post post);
}
