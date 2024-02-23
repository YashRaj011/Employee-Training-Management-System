package com.example.Employee.Training.Management.System.Controller;

import com.example.Employee.Training.Management.System.Repository.UserRepository;
import com.example.Employee.Training.Management.System.model.User;
import com.example.Employee.Training.Management.System.service.LoginService;
 import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
 import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Objects;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

 @Slf4j
@Controller
public class LoginController {
     private final LoginService logservice;
     private UserRepository userRepository;

    @Autowired
    public LoginController(LoginService logservice, UserRepository userRepository) {
        this.logservice = logservice;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/login", method = {RequestMethod.GET, POST})
    public String displayLoginPage(@RequestParam(value = "error", required = false) String error,
                                        @RequestParam(value = "logout", required = false) String logout,
                                            @RequestParam(value="register", required= false) String register,
                                                @RequestParam(value = "details_update", required = false) String details_update, Model model) {
        String errorMessage = null;
        String logoutMessage = null;
        String registerMessage = null;
        String detailsUpdateMessage = null;
        if(error != null) {
            errorMessage = "Invalid username or password!!";
        }
        if(logout != null) {
            logoutMessage = "You have been successfully logged out!";
        }
        if(register != null) {
            registerMessage = "Registration successful . Please enter the registered credentials to login";
        }
        if(details_update != null) {
            detailsUpdateMessage = "Update Successful, Please login through new credentials!";
        }
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("logoutMessage", logoutMessage);
        model.addAttribute("registerMessage", registerMessage);
        model.addAttribute("detailsUpdateMessage", detailsUpdateMessage);
        return "Login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest req, HttpServletResponse res) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null) {
            new SecurityContextLogoutHandler().logout(req, res, auth);
        }
        return "redirect:/login?logout=true";
    }

//    @RequestMapping(value = {"/checkMsg"}, method = POST)
//    public String validateUser(@Valid @ModelAttribute("login_user")LogUser loguser, Errors errors, Model model) {
//        if(errors.hasErrors()){
//            model.addAttribute("userDetail", "failed");
//            log.error("User login failed due to : " + errors.toString());
//            return "Login.html";
//        }
//        return "/CompanyDetailsRegistration";
//    }



}
