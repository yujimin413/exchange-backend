package ShinHoDeung.demo.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "interested_university")
@Getter
@Setter
@NoArgsConstructor
public class InterestedUniversity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id", nullable = false)
    private University university;
}
