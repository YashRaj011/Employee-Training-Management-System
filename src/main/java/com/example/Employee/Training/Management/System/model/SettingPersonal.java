package com.example.Employee.Training.Management.System.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class SettingPersonal {

    @Email(message = "Please provide a valid email address")
    private String userEmail;

    @Pattern(regexp="^$|[0-9]{10}", message = "Mobile number must be 10 digits")
    private String contactNumber;
}
