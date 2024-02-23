package com.example.Employee.Training.Management.System.Controller;

import com.example.Employee.Training.Management.System.model.User;
import com.example.Employee.Training.Management.System.model.UserCompanyDetails;
import com.example.Employee.Training.Management.System.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@Slf4j
public class CompanyDetailsController {

    private final CompanyService companyService;

    public CompanyDetailsController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @RequestMapping("/CompanyDetails")
    public String displayCompanyDetailsRegistrationPage(Model model, HttpSession session) {
        model.addAttribute("registerCompany", new UserCompanyDetails());
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        String username = loggedInUser.getUserName();
        model.addAttribute("username", username);
        return "CompanyDetailsRegistration";
    }

    @RequestMapping(value="/additionalCompanyDetails", method= POST)
    public String storeAdditionalCompanyDetails(@Valid @ModelAttribute("registerCompany") UserCompanyDetails userCompanyDetails, Errors errors, Model model, Authentication authentication, HttpSession session){
        if(errors.hasErrors()){
            model.addAttribute("username", authentication.getName());
            model.addAttribute("userCompanyDetailsInfo", "failed");
            log.error("Unable to Register user due to : ");
            return "CompanyDetailsRegistration";
        }
        if(companyService.registerUserCompanyDetails(userCompanyDetails, session)){
            model.addAttribute("registeredCompanyAdditionalDetails", "success");
            return "redirect:/PersonalDetails";
        }
        return "redirect:/CompanyDetails";
    }
}
