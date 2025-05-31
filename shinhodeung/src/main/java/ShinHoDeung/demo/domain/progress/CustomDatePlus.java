package ShinHoDeung.demo.domain.progress;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import ShinHoDeung.demo.domain.User;

@Entity
@Table(name = "custom_date_plus")
@Getter
@Setter
@NoArgsConstructor
public class CustomDatePlus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "component_id", nullable = false)
    private Component component;

    @Column(name = "due_at", nullable = false)
    private LocalDateTime dueAt;
}
