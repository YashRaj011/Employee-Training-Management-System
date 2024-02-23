package com.example.Employee.Training.Management.System.Controller;

import com.example.Employee.Training.Management.System.model.User;
import com.example.Employee.Training.Management.System.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.validation.Valid;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@Slf4j
public class  RegistrationController {
    private final RegisterService registerService;

    @Autowired
    public RegistrationController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @RequestMapping("/registration")
    public String displayRegistrationPage(Model model, @RequestParam(value = "error", required = false) String err) {
        model.addAttribute("register", new User());
        String dbErrorMessage = null;
        if(err != null) {
            dbErrorMessage = "Email already registered!!";
        }
        model.addAttribute("dbErrorMessage", dbErrorMessage);
        return "Registration";
    }

    @RequestMapping(value = "/RegisterUser", method = POST)
    public String RegisterNewUser(@Valid @ModelAttribute("register") User user, Errors errors, Model model,
                                    @RequestParam(required = false) boolean agreeTerm){
        if(errors.hasFieldErrors("password")){
            model.addAttribute("weakPassword", "true");
        }
        if (!errors.hasFieldErrors() && errors.hasErrors()) {
            model.addAttribute("passMatch", "false");
        }
        if(errors.hasErrors()){
            model.addAttribute("registered", "failed");
            log.error("Unable to Register user due to : " + errors);
            return "Registration";
        }
        if(!agreeTerm){
            model.addAttribute("registeredTerms", "failed");
            return "Registration";
        }
        boolean isSaved = registerService.registerUser(user);
        if(isSaved){
            return "redirect:/login?register=true";
        } else{
            return "redirect:/registration?error=true";
        }
    }

}
