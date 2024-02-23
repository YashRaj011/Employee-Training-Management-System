package com.example.Employee.Training.Management.System.Controller;

import com.example.Employee.Training.Management.System.Repository.CoursesRepository;
import com.example.Employee.Training.Management.System.model.Course;
import com.example.Employee.Training.Management.System.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class CourseController {

    @Autowired
    CoursesRepository coursesRepository;

    @RequestMapping("/courses")
    public String viewCoursesPage(Model model, HttpSession session){
        User user = (User) session.getAttribute("loggedInUser");
        if(user != null) {
            model.addAttribute("username", user.getUserName());
        }
        List<Course> coursesList = coursesRepository.findAll();
        model.addAttribute("courses", coursesList);
        model.addAttribute("NewCourse", new Course());
        return "Courses";
    }

}