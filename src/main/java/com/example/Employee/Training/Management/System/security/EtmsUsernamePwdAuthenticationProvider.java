package com.example.Employee.Training.Management.System.security;

import com.example.Employee.Training.Management.System.Repository.UserRepository;
import com.example.Employee.Training.Management.System.model.Roles;
import com.example.Employee.Training.Management.System.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class EtmsUsernamePwdAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoderUsingBcrypt;

    @Override
    public Authentication authenticate(Authentication authentication){
        String email = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        User user = userRepository.getByEmail(email);
        if(null != user && user.getUserId()>0 &&
                passwordEncoderUsingBcrypt.matches(pwd, user.getPassword())){
            return new UsernamePasswordAuthenticationToken(
                    email, pwd, getGrantedAuthorities(user.getRoles()));
        }
        throw new BadCredentialsException("Invalid credentials");
    }

    private List<GrantedAuthority> getGrantedAuthorities(Roles roles){
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+roles.getRoleName()));
        return grantedAuthorities;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
