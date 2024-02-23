package com.example.Employee.Training.Management.System.Controller;

import com.example.Employee.Training.Management.System.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class ContactController {

    @RequestMapping("/contact")
    public String displayContactPage(Model model, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("loggedInUser");
        if(user != null) {
            model.addAttribute("username", user.getUserName());
            model.addAttribute("user", user);
        }
        return "Contact";
    }

}
