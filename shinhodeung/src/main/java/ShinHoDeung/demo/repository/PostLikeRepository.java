package ShinHoDeung.demo.repository;

import ShinHoDeung.demo.domain.Post;
import ShinHoDeung.demo.domain.PostLike;
import ShinHoDeung.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Integer> {
    int countByPost(Post post);
    boolean existsByPostAndUser(Post post, User user);

    Optional<PostLike> findByPostAndUser(Post post, User user);

}

