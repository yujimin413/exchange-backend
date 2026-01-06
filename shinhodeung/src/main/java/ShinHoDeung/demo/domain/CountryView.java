package ShinHoDeung.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "country_view")
@Getter
public class CountryView {

    @Id
    @Column(name = "country")
    private String country; 

    @Column(name = "university_count")
    private Integer universityCount;

    @Column(name = "region")
    private String region;
}
