package com.example.Employee.Training.Management.System.Controller;

import com.example.Employee.Training.Management.System.Repository.UserRepository;
import com.example.Employee.Training.Management.System.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpSession;

@Controller
 @Slf4j
public class DashboardController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping("/dashboard")
    public String displayDashboardPage(Model model, Authentication authentication, HttpSession session) {
        User user = userRepository.getByEmail(authentication.getName());
        model.addAttribute("username", user.getUserName());
        model.addAttribute("role", authentication.getAuthorities().toString());
        session.setAttribute("loggedInUser", user);
        return "Dashboard";
    }



}
