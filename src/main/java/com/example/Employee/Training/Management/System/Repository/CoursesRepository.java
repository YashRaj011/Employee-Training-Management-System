package com.example.Employee.Training.Management.System.Repository;

import com.example.Employee.Training.Management.System.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursesRepository extends JpaRepository<Course, Integer> {
    Course findByCourseAlias(String courseAlias);
}
