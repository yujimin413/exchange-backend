package ShinHoDeung.demo.domain.progress;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "sub_step")
@Getter
@Setter
public class SubStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_step_id", nullable = false)
    private MainStep mainStep;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "subStep", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Detail> details;
}
