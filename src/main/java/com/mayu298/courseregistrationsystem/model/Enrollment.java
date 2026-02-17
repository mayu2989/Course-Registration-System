package com.mayu298.courseregistrationsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mayu298.courseregistrationsystem.config.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "enrollment",uniqueConstraints = @UniqueConstraint(columnNames = {"student_id","course_id"}))
public class Enrollment extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    @JsonIgnore
    private Student student;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    @JsonIgnore
    private Course course;


}
