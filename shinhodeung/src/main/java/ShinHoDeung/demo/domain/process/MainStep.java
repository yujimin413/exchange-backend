package ShinHoDeung.demo.domain.process;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Table(name = "main_step")
@Getter
public class MainStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "mainStep", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubStep> subSteps;
}