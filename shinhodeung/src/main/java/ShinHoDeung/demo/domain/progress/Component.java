package ShinHoDeung.demo.domain.progress;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "component")
@Getter
public class Component {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "detail_id", nullable = false)
    private Detail detail;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_type", nullable = false)
    private ContentType contentType;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "TEXT")
    private String note;
}
