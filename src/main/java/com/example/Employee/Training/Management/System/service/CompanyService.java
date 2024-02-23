package com.example.Employee.Training.Management.System.service;

import com.example.Employee.Training.Management.System.Repository.CompanyRepository;
import com.example.Employee.Training.Management.System.Repository.UserRepository;
import com.example.Employee.Training.Management.System.model.User;
import com.example.Employee.Training.Management.System.model.UserCompanyDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private UserRepository userRepository;

    public boolean registerUserCompanyDetails(UserCompanyDetails userCompanyDetails, HttpSession session){
        boolean isSaved = false;
        UserCompanyDetails savedCompanyDetails = companyRepository.save(userCompanyDetails);
        User user = (User) session.getAttribute("loggedInUser");
        user.setCompany(savedCompanyDetails);
        user = userRepository.save(user);
        session.setAttribute("loggedInUser", user);
        if(null != savedCompanyDetails && savedCompanyDetails.getCompanyId()>=0) {
            isSaved = true;
        }
        return isSaved;
    }
}
