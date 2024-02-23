package com.example.Employee.Training.Management.System.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().and()
                .authorizeRequests()
                .mvcMatchers("/home").permitAll()
                .mvcMatchers("/dashboard").authenticated()
                .mvcMatchers("/profile").authenticated()
                .mvcMatchers("/favCourses").authenticated()
                .mvcMatchers("/displayProfile").authenticated()
                .mvcMatchers("/courseDetails/**").authenticated()
                .mvcMatchers("/additionalCompanyDetails").authenticated()
                .mvcMatchers("/enrollUserToCourse/**").authenticated()
                .mvcMatchers("/settings").authenticated()
                .mvcMatchers("/registration").permitAll()
                .mvcMatchers("/courses").permitAll()
                .mvcMatchers("/RegisterUser").permitAll()
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                .mvcMatchers("/login/**").permitAll()
                .and()
                .formLogin().loginPage("/login")
                .defaultSuccessUrl("/dashboard", true).failureUrl("/login?error=true").permitAll()
                .and().logout().logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true).permitAll()
                .and().httpBasic();

        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoderUsingBcrypt() {
        return new BCryptPasswordEncoder();
    }



}
