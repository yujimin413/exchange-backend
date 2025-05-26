package ShinHoDeung.demo.repository;

import ShinHoDeung.demo.domain.Post;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;

import ShinHoDeung.demo.domain.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
//    Optional<Post> findById(@NotNull Integer postId);

}
