package ShinHoDeung.demo.domain.process;

import ShinHoDeung.demo.domain.User;
import jakarta.persistence.*;

@Entity
@Table(name = "user_response")
public class UserResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "component_id", nullable = false)
    private Component component;

    @Column(name = "value", nullable = false, columnDefinition = "TEXT")
    private String value;
}
