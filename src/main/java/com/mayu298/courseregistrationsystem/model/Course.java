package com.mayu298.courseregistrationsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mayu298.courseregistrationsystem.config.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
@Getter
@Setter
@Entity
@Table(name = "course")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@SQLDelete(sql = "UPDATE course SET deleted = true WHERE id=?")

@FilterDef(name = "deletedCourseFilter",
        parameters = @ParamDef(name = "isDeleted", type = Boolean.class))

@Filter(name = "deletedCourseFilter",
        condition = "deleted = :isDeleted")
public class Course extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(nullable = false)
    @NotBlank(message = "Title is required")
    String title;
    @Column(length = 1000)
    @NotBlank(message = "Description is required")
    String description;

}
