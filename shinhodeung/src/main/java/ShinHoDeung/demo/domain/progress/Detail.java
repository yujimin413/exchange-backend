package ShinHoDeung.demo.domain.progress;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "detail")
@Getter
@Setter
public class Detail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_step_id", nullable = false)
    private SubStep subStep;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "detail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Component> components;
}