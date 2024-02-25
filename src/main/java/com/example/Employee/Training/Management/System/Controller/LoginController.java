package com.example.Employee.Training.Management.System.Controller;

 import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
 import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@Controller
public class LoginController {

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

}
