package com.example.Employee.Training.Management.System.Controller;

import com.example.Employee.Training.Management.System.Repository.UserRepository;
import com.example.Employee.Training.Management.System.model.Setting;
import com.example.Employee.Training.Management.System.model.SettingPersonal;
import com.example.Employee.Training.Management.System.model.User;
import com.example.Employee.Training.Management.System.service.SettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@Slf4j
public class SettingController {

    @Autowired
    private PasswordEncoder passwordEncoderUsingBcrypt;

    private final SettingService settingService;

    @Autowired
    public SettingController(SettingService settingService) {
        this.settingService = settingService;
    }

    @RequestMapping("/settings")
    public String displaySetting(Model model, HttpSession session,
                                 @RequestParam(value = "password_error", required = false) String passwordError,
                                  @RequestParam(value = "password_change", required = false) String passwordStatus,
                                   @RequestParam(value = "existing_password_error", required = false) String existingPasswordStatus,
                                    @RequestParam(value = "value_error", required = false) String valueErrorStatus,
                                     @RequestParam(value = "field_error", required = false) String fieldErrorStatus,
                                      @RequestParam(value = "password_match_error", required = false) String passwordMatchErrorStatus,
                                        @RequestParam(value = "details_update_error", required = false) String detailsUpdateStatus) {
        User user = (User) session.getAttribute("loggedInUser");
        SettingPersonal settingPersonal = new SettingPersonal();
        if(user != null) {
            model.addAttribute("user", user);
            model.addAttribute("username", user.getUserName());
            model.addAttribute("userEmail", user.getEmail());
            model.addAttribute("userContact", user.getContactNumber());
            settingPersonal.setUserEmail(user.getEmail());
            settingPersonal.setContactNumber(user.getContactNumber());
            model.addAttribute("settingPersonal", settingPersonal);
        }
        model.addAttribute("passSetting", new Setting());
        String passwordErrorMessage = null;
        String passwordStatusMessage = null;
        String existingPasswordStatusMessage = null;
        String valueStatusMessage = null;
        String fieldErrorStatusMessage = null;
        String passwordMatchErrorStatusMessage = null;
        String detailsUpdateStatusMessage = null;
        if(passwordError != null) {
            passwordErrorMessage = "Please enter a different password to change!";
            model.addAttribute("passwordStatus", "failed");
            model.addAttribute("passError", passwordErrorMessage);
        } else if(existingPasswordStatus != null){
            existingPasswordStatusMessage = "Invalid current password!!";
            model.addAttribute("passwordStatus", "false");
            model.addAttribute("existingPasswordStatusMessage", existingPasswordStatusMessage);
        } else if(passwordStatus != null) {
            passwordStatusMessage = "Password changed successfully!";
            model.addAttribute("passwordStatus", "success");
            model.addAttribute("passwordStatusMessage", passwordStatusMessage);
        } else if(valueErrorStatus != null) {
            valueStatusMessage = "Please enter a valid input!";
            model.addAttribute("ValueError", "true");
            model.addAttribute("valueStatusMessage", valueStatusMessage);
        } else if(fieldErrorStatus != null) {
            fieldErrorStatusMessage = "Please choose a strong password!";
            model.addAttribute("weakPassword", "true");
            model.addAttribute("fieldErrorStatusMessage", fieldErrorStatusMessage);
        } else if(passwordMatchErrorStatus != null) {
            passwordMatchErrorStatusMessage = "Passwords do not match!";
            model.addAttribute("passMatch", "false");
            model.addAttribute("passwordMatchErrorStatusMessage", passwordMatchErrorStatusMessage);
        } else if(detailsUpdateStatus != null) {
            detailsUpdateStatusMessage = "Error Occurred while updating details!";
            model.addAttribute("updateError", "false");
            model.addAttribute("detailsUpdateStatusMessage", detailsUpdateStatusMessage);
        }


        return "Setting";
    }

    @RequestMapping(value = "/changeUserPassword", method = RequestMethod.POST)
    public String changeUserPassword(@Valid @ModelAttribute("passSetting") Setting userPassDetails,Errors errors,
                                     Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if(user != null) {
            model.addAttribute("user", user);
        }
        if(errors.hasErrors()){
            if(!errors.hasFieldErrors()) {
                log.error("Unable to change the password of user due to : " + errors);
                return "redirect:/settings?password_match_error=true";
            } else {
                log.error("Unable to change the password of user due to : " + errors);
                return "redirect:/settings?value_error=true";
            }
        }
        if(errors.hasFieldErrors()){
            log.error("Unable to change the password of user due to : " + errors);
            return "redirect:/settings?field_error=true";
        }

        User existingUser = (User) session.getAttribute("loggedInUser");
        if(passwordEncoderUsingBcrypt.matches(userPassDetails.getCurrPassword(), existingUser.getPassword())) {
            User newUser = settingService.changeUserPass(userPassDetails, existingUser);
            if(newUser == null) {
                return "redirect:/settings?password_error=true";
            }
            session.setAttribute("loggedInUser", newUser);
            return "redirect:/settings?password_change=true";
        }
        return "redirect:/settings?existing_password_error=true";
    }

    @RequestMapping(value = "/savePersonalDetails", method = RequestMethod.POST)
    public String changeUserContactDetails(@Valid @ModelAttribute("settingPersonal") SettingPersonal settingPersonal,
                                           Model model, HttpSession httpSession,
                                           HttpServletRequest req, HttpServletResponse res) {
        User user = (User) httpSession.getAttribute("loggedInUser");
        User savedUser = settingService.updateUserDetails(settingPersonal, user);
        if(savedUser != null) {
            httpSession.setAttribute("loggedInUser", savedUser);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if(auth != null) {
                new SecurityContextLogoutHandler().logout(req, res, auth);
            }
            return "redirect:/login?details_update=true";
        }
        return "redirect:/settings?details_update_error=true";
    }

}
