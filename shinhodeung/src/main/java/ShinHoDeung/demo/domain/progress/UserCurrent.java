package ShinHoDeung.demo.domain.progress;

import ShinHoDeung.demo.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_current")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCurrent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "main_step_order", nullable = false)
    private Integer mainStepOrder;

    @Column(name = "sub_step_order", nullable = false)
    private Integer subStepOrder;
}
