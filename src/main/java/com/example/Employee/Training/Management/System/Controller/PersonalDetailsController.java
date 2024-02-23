package com.example.Employee.Training.Management.System.Controller;

import com.example.Employee.Training.Management.System.model.AboutPersonal;
import com.example.Employee.Training.Management.System.model.SocialPersonal;
import com.example.Employee.Training.Management.System.model.User;
import com.example.Employee.Training.Management.System.model.UserPersonalDetails;
import com.example.Employee.Training.Management.System.service.PersonalDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@Slf4j
public class PersonalDetailsController {

    private final PersonalDetailsService personalDetailsService;

    public PersonalDetailsController(PersonalDetailsService personalDetailsService) {
        this.personalDetailsService = personalDetailsService;
    }

    @RequestMapping("/PersonalDetails")
    public String displayPersonalDetailsRegistrationPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        UserPersonalDetails personalDetails = new UserPersonalDetails();
        model.addAttribute("username", user.getUserName());
        model.addAttribute("registerPersonal", personalDetails);
        return "PersonalDetailsRegistration";
    }

    @PostMapping(value = "/saveAboutDetails")
    public String SaveAboutDetails(@Valid @ModelAttribute("aboutPersonalDetails") AboutPersonal aboutPersonal, Errors errors, Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        model.addAttribute("username", user.getUserName());
        model.addAttribute("userDetailsInfo", "failed");
        if(errors.hasErrors()){
            log.error("Unable to Register user due to : ");
            return "Profile";
        }
        if(personalDetailsService.updateUserAboutDetails(aboutPersonal, session)){
            model.addAttribute("registeredPersonalAdditionalDetails", "success");
            return "redirect:/displayProfile";
        }
        return "redirect:/displayProfile?error=true";
    }

    @PostMapping(value = "/saveSocialDetails")
    public String SaveSocialDetails(@Valid @ModelAttribute("socialPersonalDetails") SocialPersonal socialPersonal, Errors errors, Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        model.addAttribute("username", user.getUserName());
        model.addAttribute("userDetailsInfo", "failed");
        if(errors.hasErrors()){
            log.error("Unable to Register user due to : ");
            return "Profile";
        }
        if(personalDetailsService.updateUserSocialDetails(socialPersonal, session)){
            model.addAttribute("registeredPersonalAdditionalDetails", "success");
            return "redirect:/displayProfile";
        }
        return "redirect:/displayProfile?error=true";
    }

}
