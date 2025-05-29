package ShinHoDeung.demo.domain.process;

import ShinHoDeung.demo.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_current")
@Getter
@Setter
@NoArgsConstructor
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
