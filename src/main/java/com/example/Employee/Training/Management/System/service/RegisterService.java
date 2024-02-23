package com.example.Employee.Training.Management.System.service;

import com.example.Employee.Training.Management.System.Repository.RolesRepository;
import com.example.Employee.Training.Management.System.Repository.UserRepository;
import com.example.Employee.Training.Management.System.constants.EtmsConstants;
import com.example.Employee.Training.Management.System.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
// @Slf4j
public class RegisterService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoderUsingBcrypt;


    public boolean registerUser(User user){
        if(userRepo.getByEmail(user.getEmail()) != null) {
            return false;
        }
        boolean isSaved = false;
        Roles role = rolesRepository.getByRoleName(EtmsConstants.STUDENT_ROLE);
        user.setRoles(role);
        user.setPassword(passwordEncoderUsingBcrypt.encode(user.getPassword()));
        user = userRepo.save(user);
        if(null != user && user.getUserId()>=0) {
            isSaved = true;
        }
//        log.info(user.toString());
        return isSaved;
    }



}
