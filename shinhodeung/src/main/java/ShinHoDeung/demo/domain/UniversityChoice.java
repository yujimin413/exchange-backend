package ShinHoDeung.demo.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "university_choice")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UniversityChoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id_1", nullable = false)
    private University university1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id_2")
    private University university2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id_3")
    private University university3;
}