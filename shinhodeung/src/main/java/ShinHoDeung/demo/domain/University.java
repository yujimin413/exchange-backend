package ShinHoDeung.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "university")
@Getter
@Setter
@NoArgsConstructor
public class University {

    @Id
    @Column(length = 50)
    private String id;

    @Column(name = "program_type", nullable = false, length = 50)
    private String programType;

    @Column(nullable = false, length = 50)
    private String institution;

    @Column(nullable = false, length = 50)
    private String region;

    @Column(nullable = false, length = 50)
    private String country;

    @Column(name = "english_name", nullable = false, length = 255)
    private String englishName;

    @Column(name = "korean_name", nullable = false, length = 255)
    private String koreanName;

    @Column(name = "credit_requirement", length = 255)
    private String creditRequirement;

    @Column(name = "language_requirement", nullable = false, length = 255)
    private String languageRequirement;

    @Column(name = "special_notes", length = 2048)
    private String specialNotes;

    @Column(nullable = false, length = 2048)
    private String notes;

    @Column(name = "available_courses", nullable = false, length = 2048)
    private String availableCourses;

    @Column(nullable = false, length = 2048)
    private String website;

    @Column(name = "language_region", nullable = false, length = 255)
    private String languageRegion;

    @Column(name = "available_majors", nullable = false, length = 255)
    private String availableMajors;

    @Column(name = "summary_location", length = 2048)
    private String summaryLocation;

    @Column(name = "summary_weather", length = 2048)
    private String summaryWeather;

    @Column(name = "summary_academic", length = 2048)
    private String summaryAcademic;

    @Column(name = "summary_safety", length = 2048)
    private String summarySafety;

    @Column(length = 1024)
    private String hashtag;
}
