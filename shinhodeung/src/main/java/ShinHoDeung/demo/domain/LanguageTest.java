package ShinHoDeung.demo.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "language_test")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LanguageTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "test_name", nullable = false, length = 50)
    private String testName;

    @Column(name = "test_score", nullable = false, length = 20)
    private String testScore;
}
