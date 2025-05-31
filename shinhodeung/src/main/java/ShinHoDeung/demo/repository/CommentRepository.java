package ShinHoDeung.demo.repository;

import ShinHoDeung.demo.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ShinHoDeung.demo.domain.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>{
    int countByPost(Post post);
//    List<Comment> findByPostOrderByCreateAtDesc(Post post);
    Page<Comment> findByPostOrderByCreateAtDesc(Post post, Pageable pageable);

}





