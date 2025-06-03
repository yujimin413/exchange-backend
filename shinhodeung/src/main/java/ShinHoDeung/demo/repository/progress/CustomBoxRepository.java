package ShinHoDeung.demo.repository.progress;

import org.springframework.data.jpa.repository.JpaRepository;

import ShinHoDeung.demo.domain.progress.ContentType;
import ShinHoDeung.demo.domain.progress.CustomBox;
import java.util.List;
import ShinHoDeung.demo.domain.User;


public interface CustomBoxRepository extends JpaRepository<CustomBox, Integer>{
    List<CustomBox> findByUserAndContentType(User user, ContentType contentType);
}
