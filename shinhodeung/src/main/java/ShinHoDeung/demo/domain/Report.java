package ShinHoDeung.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "report")
@Getter
@Setter
@Immutable
public class Report {

    @Column(name = "student_id")
    private String studentId; 

    @Column(name = "before_departure", columnDefinition = "TEXT")
    private String beforeDeparture;

    @Column(name = "after_arrival", columnDefinition = "TEXT")
    private String afterArrival;

    @Column(name = "campus_life", columnDefinition = "TEXT")
    private String campusLife;

    @Column(name = "university_intro", columnDefinition = "TEXT")
    private String universityIntro;

    @Column(name = "impression", columnDefinition = "TEXT")
    private String impression;

    @Id
    @Column(name = "file_name", columnDefinition = "TEXT")
    private String fileName;

    @Column(name = "department", columnDefinition = "TEXT")
    private String department;

    @Column(name = "year")
    private Double year;

    @Column(name = "name", columnDefinition = "TEXT")
    private String name;

    @Column(name = "university", columnDefinition = "TEXT")
    private String university;

    @Column(name = "program_type", columnDefinition = "TEXT")
    private String programType;

    @Column(name = "study_period", columnDefinition = "TEXT")
    private String studyPeriod;

}

