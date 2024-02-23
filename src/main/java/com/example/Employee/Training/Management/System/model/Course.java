package com.example.Employee.Training.Management.System.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "courses")
public class Course extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    @Column(name = "COURSE_ID")
    private int courseId;
    @NotBlank(message = "Course Name must not be blank")
    @Size(min = 3,message = "Course Name must be at-least 3 characters long")
    @Column(name="COURSE_NAME")
    private String courseName;

    @NotBlank(message = "Course Alias must not be blank")
    @Size(min = 3,message = "Course Alias must be at-least 3 characters long")
    @Column(name = "COURSE_ALIAS")
    private String courseAlias;
    @NotBlank(message = "Course Description must not be blank")
    @Size(min = 50, max = 500, message = "Course Description must be at-least 50 characters long and almost 500 characters")
    @Column(name="COURSE_DESCRIPTION")
    private String courseDescription;
    @NotBlank(message = "Course duration must not be blank")
    @Size(min = 5,message = "Course duration must be at-least 5 characters long")
    @Column(name="COURSE_DURATION")
    private String courseDuration;
    @NotBlank(message = "Course Prerequisites must not be blank")
    @Size(min = 50,message = "Course Prerequisites must be at-least 50 characters long")
    @Column(name="COURSE_PREREQUISITES")
    private String coursePrerequisites;
    @NotBlank(message = "Course Achievement must not be blank")
    @Size(min = 10,message = "Course Achievement must be at-least 10 characters long")
    @Column(name="COURSE_ACHIEVEMENT_GAIN")
    private String courseAchievementGain;
    @NotBlank(message = "Course image path must not be blank")
    @Column(name="COURSE_IMAGE_PATH")
    private String courseImagePath;

    @ManyToMany(mappedBy = "etmsCourses", fetch = FetchType.EAGER)
    private Set<User> users = new HashSet<>();

}
