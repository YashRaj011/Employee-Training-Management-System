package com.example.Employee.Training.Management.System.Controller;

import com.example.Employee.Training.Management.System.model.Course;
import com.example.Employee.Training.Management.System.model.User;
import com.example.Employee.Training.Management.System.service.CourseDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
public class CourseDetailsController {

    private final CourseDetailsService courseDetailsService;

    @Autowired
    public CourseDetailsController(CourseDetailsService courseDetailsService){
        this.courseDetailsService = courseDetailsService;
    }

    @RequestMapping("/courseDetails")
    public String viewCourseDetailsPage(@RequestParam(value = "course_alias") String course_alias, @RequestParam(value = "enroll", required = false) String enrollInfo , Model model, HttpSession session){
        Course course = courseDetailsService.getCourseObject(course_alias);
        User user = (User) session.getAttribute("loggedInUser");
        if(user != null) {
            if(user.getEtmsCourses().contains(course)){
                model.addAttribute("userEnrolled", true);
            } else {
                model.addAttribute("userEnrolled", false);
            }
            model.addAttribute("username", user.getUserName());
        }
        String enrollErrorMessage;
        if(enrollInfo!=null) {
            if(enrollInfo.equals("failed")){
                enrollErrorMessage = "Failed to enroll into the course!!\nPlease try again after some time.";
                model.addAttribute("enrollError", enrollErrorMessage);
            }
        }
        model.addAttribute("CourseAlias", course_alias);
        model.addAttribute("Courses", course);
        return "CourseDetails";
    }



    @RequestMapping("/enrollStudentToCourse")
    public String EnrollUserToCourse(@RequestParam(value = "course_alias") String course_alias, HttpSession session){
        boolean isSaved = courseDetailsService.saveUserToCourse(course_alias, session);
        if(isSaved) {
            return "redirect:/courseDetails?course_alias=" + course_alias + "&enroll=success";
        }
        return "redirect:/courseDetails?course_alias=" + course_alias + "&enroll=failed.";
    }

}
