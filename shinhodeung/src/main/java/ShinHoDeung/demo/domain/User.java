package ShinHoDeung.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "student_id", nullable = false, unique = true)
    private Integer studentId;

    @Column(name = "user_name", nullable = false, length = 100)
    private String userName;

    @Column(nullable = false, length = 100)
    private String major;

    @Column(name = "profile_url", length = 2048)
    private String profileUrl;

    @Column(name = "credit_average")
    private Float creditAverage;

    @Column(name = "planned_grade")
    private Integer plannedGrade;

    @Column(name = "planned_semester")
    private Integer plannedSemester;
}
