package ShinHoDeung.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "keyword_view")
@Getter
public class KeywordView {

    @Id
    @Column(name = "keyword")
    private String keyword;  // keyword는 유일하다는 전제하에 ID로 설정

    @Column(name = "type")
    private String type;
    
}
