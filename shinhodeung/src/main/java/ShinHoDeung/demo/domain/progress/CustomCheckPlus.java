package ShinHoDeung.demo.domain.progress;

import ShinHoDeung.demo.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "custom_check_plus")
@Getter
@Setter
@NoArgsConstructor
public class CustomCheckPlus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "component_id", nullable = false)
    private Component component;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
}
