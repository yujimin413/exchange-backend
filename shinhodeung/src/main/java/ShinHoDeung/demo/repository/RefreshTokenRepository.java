package ShinHoDeung.demo.repository;

import org.springframework.data.repository.CrudRepository;

import ShinHoDeung.demo.domain.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
//    Optional<Post> findById(@NotNull Integer postId);

}
