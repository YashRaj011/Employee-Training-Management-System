package com.example.Employee.Training.Management.System.Controller;

import com.example.Employee.Training.Management.System.Repository.UserRepository;
import com.example.Employee.Training.Management.System.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.example.Employee.Training.Management.System.FilesSystem.FileUploadUtil;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class ProfileController {

    private final UserRepository userRepository;

    @Autowired
    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping("/displayProfile")
    public String displayProfile(Model model, HttpSession session){
        User user = (User) session.getAttribute("loggedInUser");
        AboutPersonal ap = new AboutPersonal();
        SocialPersonal sp = new SocialPersonal();
        if(user != null) {
            model.addAttribute("username", user.getUserName());
            model.addAttribute("user", user);
            if(user.getCompany() != null) {
                UserCompanyDetails userCompanyDetails = user.getCompany();
                if(userCompanyDetails != null && userCompanyDetails.getCompanyId() > 0) {
                    model.addAttribute("userCompanyDetails", userCompanyDetails);
                    model.addAttribute("userCompanyDetail", "notnull");
                }
            } else {
                model.addAttribute("userCompanyDetail", "null");
            }
            if(user.getPersonalDetails() != null) {
                UserPersonalDetails userPersonalDetails = user.getPersonalDetails();
                ap.setUserDescription(userPersonalDetails.getUserDescription());
                ap.setUserAddress(userPersonalDetails.getUserAddress());
                ap.setUserYearsOfExperience(userPersonalDetails.getUserYearsOfExperience());
                sp.setFacebookUrl(userPersonalDetails.getFacebookUrl());
                sp.setInstagramUrl(userPersonalDetails.getInstagramUrl());
                sp.setLinkedinUrl(userPersonalDetails.getLinkedinUrl());
                sp.setTwitterUrl(userPersonalDetails.getTwitterUrl());
                model.addAttribute("userPersonalDetail", "notnull");
            } else {
                model.addAttribute("userPersonalDetail", "null");
            }
            model.addAttribute("aboutPersonalDetails", ap);
            model.addAttribute("socialPersonalDetails", sp);
        }
        return "Profile";
    }

    @PostMapping("/addProfImage")
    public ModelAndView saveUser(@RequestParam("image") MultipartFile multipartFile, HttpSession session) throws IOException {
        User user = (User) session.getAttribute("loggedInUser");
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        user.setUserPhotosName(fileName);
        User savedUser = userRepository.save(user);
        String uploadDir = "user-photos/" + savedUser.getUserId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        return new ModelAndView("redirect:/displayProfile");
    }

}
