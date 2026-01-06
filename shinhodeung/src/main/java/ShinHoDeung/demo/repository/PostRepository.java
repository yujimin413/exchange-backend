package ShinHoDeung.demo.repository;

import ShinHoDeung.demo.domain.Post;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
    @NotNull
    Page<Post> findByCategory(@NotNull String category, @NotNull Pageable pageable);
}
