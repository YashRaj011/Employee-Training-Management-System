package com.example.Employee.Training.Management.System.Controller;

import com.example.Employee.Training.Management.System.Repository.CoursesRepository;
import com.example.Employee.Training.Management.System.Repository.UserRepository;
import com.example.Employee.Training.Management.System.model.Course;
import com.example.Employee.Training.Management.System.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final CoursesRepository coursesRepository;
    private final UserRepository userRepository;

    @Autowired
    public AdminController(CoursesRepository coursesRepository,
                           UserRepository userRepository) {
        this.coursesRepository = coursesRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/addNewCourse")
    public ModelAndView addNewCourse(@ModelAttribute("NewCourse")Course course) {
        ModelAndView mv = new ModelAndView();
        coursesRepository.save(course);
        mv.setViewName("redirect:/courses");
        return mv;
    }

    @RequestMapping("/viewEnrolledStudents")
    public ModelAndView viewEnrolledUsers(@RequestParam(name = "id") int id, Model model, HttpSession session,
                                            @RequestParam(required = false)String error,
                                                @RequestParam(required = false)String admin) {
        String errorMessage;
        String adminMessage;
        User user = (User) session.getAttribute("loggedInUser");
        if(user != null) {
            model.addAttribute("username", user.getUserName());
        }
        ModelAndView mv = new ModelAndView("CourseStudents");
        Optional<Course> courseEntity = coursesRepository.findById(id);
        session.setAttribute("courses", courseEntity.get());
        mv.addObject("course", courseEntity.get());
        int personsLength = courseEntity.get().getUsers().size();
        String isEmpty = "false";
        if(personsLength < 1) {
            isEmpty = "true";
        }
        model.addAttribute("isEmpty", isEmpty);
        mv.addObject("user", new User());
        if(error != null) {
            errorMessage = "Invalid Email entered!!\nCannot find student with the mentioned email.";
            mv.addObject("errorMessage", errorMessage);
        }
        if(admin != null) {
            adminMessage = "Admins cannot be added to the course!!";
            mv.addObject("adminMessage", adminMessage);
        }
        return mv;
    }

    @PostMapping("/addNewStudent")
    public ModelAndView addNewStudent(@ModelAttribute("user") User userEnt, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        Course currCourse = (Course) session.getAttribute("courses");
        User userEntity = userRepository.getByEmail(userEnt.getEmail());
        if(null == userEntity || !(userEntity.getUserId() > 0)) {
            mv.setViewName("redirect:/admin/viewEnrolledStudents?id=" + currCourse.getCourseId() + "&error=true");
            return mv;
        }
        if(userEntity.getRoles().getRoleName().equals("ADMIN")) {
            mv.setViewName("redirect:/admin/viewEnrolledStudents?id=" + currCourse.getCourseId() + "&admin=true");
            return mv;
        }
        userEntity.getEtmsCourses().add(currCourse);
        currCourse.getUsers().add(userEntity);
        userRepository.save(userEntity);
        mv.setViewName("redirect:/admin/viewEnrolledStudents?id=" + currCourse.getCourseId());
        session.setAttribute("courses", currCourse);
        return mv;
    }

    @GetMapping("/deleteStudent")
    public ModelAndView deleteStudentFromCourse(@RequestParam int userId, HttpSession session) {
        Course course = (Course) session.getAttribute("courses");
        Optional<User> user = userRepository.findById(userId);
        user.get().getEtmsCourses().remove(course);
        course.getUsers().remove(user);
        userRepository.save(user.get());
        session.setAttribute("courses", course);
        return new ModelAndView("redirect:/admin/viewEnrolledStudents?id="+course.getCourseId());
    }

}
