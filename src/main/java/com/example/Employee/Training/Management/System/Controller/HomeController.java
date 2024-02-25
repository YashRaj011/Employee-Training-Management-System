package com.example.Employee.Training.Management.System.Controller;

// import lombok.extern.slf4j.Slf4j;
import com.example.Employee.Training.Management.System.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

// @Slf4j
@Controller
public class HomeController {
    @RequestMapping(value = "/home")
    public String displayHomePage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if(user != null) {
            model.addAttribute("username", user.getUserName());
        }
        return "Index";
    }
}
