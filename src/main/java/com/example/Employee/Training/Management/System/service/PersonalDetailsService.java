package com.example.Employee.Training.Management.System.service;

import com.example.Employee.Training.Management.System.Repository.PersonalRepository;
import com.example.Employee.Training.Management.System.Repository.UserRepository;
import com.example.Employee.Training.Management.System.model.AboutPersonal;
import com.example.Employee.Training.Management.System.model.SocialPersonal;
import com.example.Employee.Training.Management.System.model.User;
import com.example.Employee.Training.Management.System.model.UserPersonalDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;

@Service
public class PersonalDetailsService {

    @Autowired
    private PersonalRepository personalRepository;
    @Autowired
    private UserRepository userRepository;

    public boolean updateUserAboutDetails(AboutPersonal aboutPersonal, HttpSession session){
        boolean isSaved = false;
        if(aboutPersonal != null) {
            User user = (User) session.getAttribute("loggedInUser");
            UserPersonalDetails userPersonalDetails = new UserPersonalDetails();
            if(user.getPersonalDetails() != null) {
                userPersonalDetails = user.getPersonalDetails();
            }
            userPersonalDetails.setUserDescription(aboutPersonal.getUserDescription());
            userPersonalDetails.setUserAddress(aboutPersonal.getUserAddress());
            userPersonalDetails.setUserYearsOfExperience(aboutPersonal.getUserYearsOfExperience());
            isSaved = isSaved(session, isSaved, userPersonalDetails);
        }
        return isSaved;
    }

    public boolean updateUserSocialDetails(SocialPersonal socialPersonal, HttpSession session){
        boolean isSaved = false;
        if(socialPersonal != null) {
            User user = (User) session.getAttribute("loggedInUser");
            UserPersonalDetails userPersonalDetails = new UserPersonalDetails();
            if(user.getPersonalDetails() != null) {
                userPersonalDetails = user.getPersonalDetails();
            }
            userPersonalDetails.setLinkedinUrl(socialPersonal.getLinkedinUrl());
            userPersonalDetails.setTwitterUrl(socialPersonal.getTwitterUrl());
            userPersonalDetails.setFacebookUrl(socialPersonal.getFacebookUrl());
            userPersonalDetails.setInstagramUrl(socialPersonal.getInstagramUrl());
            isSaved = isSaved(session, isSaved, userPersonalDetails);
        }
        return isSaved;
    }

    private boolean isSaved(HttpSession session, boolean isSaved, UserPersonalDetails userPersonalDetails) {
        UserPersonalDetails savedPersonalDetails = personalRepository.save(userPersonalDetails);
        User user = (User) session.getAttribute("loggedInUser");
        user.setPersonalDetails(savedPersonalDetails);
        user = userRepository.save(user);
        session.setAttribute("loggedInUser", user);
        if(null != savedPersonalDetails && savedPersonalDetails.getUId()>=0) {
            isSaved = true;
        }
        return isSaved;
    }
}
