package ShinHoDeung.demo.repository.progress;

import org.springframework.data.jpa.repository.JpaRepository;

import ShinHoDeung.demo.domain.progress.Detail;
import ShinHoDeung.demo.domain.progress.MetaComponent;
import java.util.List;


public interface MetaComponentRepository extends JpaRepository<MetaComponent, Integer>{
    List<MetaComponent> findByDetail(Detail detail);
}
