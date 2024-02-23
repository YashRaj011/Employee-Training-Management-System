package com.example.Employee.Training.Management.System.service;

import com.example.Employee.Training.Management.System.Repository.UserRepository;
import com.example.Employee.Training.Management.System.model.Setting;
import com.example.Employee.Training.Management.System.model.SettingPersonal;
import com.example.Employee.Training.Management.System.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class SettingService {

    @Autowired
    private PasswordEncoder passwordEncoderUsingBcrypt;

    @Autowired
    private UserRepository userRepo;

    public User changeUserPass(Setting userSetting, User user) {
        String currPass = userSetting.getCurrPassword();
        User newUser;
        String newPass = userSetting.getNewPassword();
        if(!(currPass.equals(newPass))) {
            user.setPassword(passwordEncoderUsingBcrypt.encode(newPass));
            newUser = userRepo.save(user);
            return newUser;
        }
        return null;
    }

    public User updateUserDetails(SettingPersonal settingPersonal, User user) {
        User newUser = null;
        if(settingPersonal != null) {
            user.setEmail(settingPersonal.getUserEmail());
            user.setContactNumber(settingPersonal.getContactNumber());
            newUser = userRepo.save(user);
        }
        return newUser;
    }

}
