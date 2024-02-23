package com.example.Employee.Training.Management.System.service;

import com.example.Employee.Training.Management.System.Repository.CoursesRepository;
import com.example.Employee.Training.Management.System.Repository.UserRepository;
import com.example.Employee.Training.Management.System.model.Course;
import com.example.Employee.Training.Management.System.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@Slf4j
public class CourseDetailsService {
    private final UserRepository userRepository;

    private final CoursesRepository coursesRepository;

    @Autowired
    public CourseDetailsService(CoursesRepository courseRepository,
                                UserRepository userRepository){
        this.coursesRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public Course getCourseObject(String CourseAlias){
        return coursesRepository.findByCourseAlias(CourseAlias);
    }


    public boolean saveUserToCourse(String courseAlias, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        Course course = coursesRepository.findByCourseAlias(courseAlias);
        course.getUsers().add(user);
        user.getEtmsCourses().add(course);
        User savedUser = userRepository.save(user);
        session.setAttribute("loggedInUser", savedUser);
        return null != savedUser;
    }
}
