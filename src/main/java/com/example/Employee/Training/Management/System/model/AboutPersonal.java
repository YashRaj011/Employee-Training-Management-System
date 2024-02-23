package com.example.Employee.Training.Management.System.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AboutPersonal {
    @NotBlank(message = "Description must not be blank")
    @Size(min = 100, max = 250,message = "Description must be at-least 100 and almost 250 characters long")
    private String userDescription;
    @NotBlank(message = "address must not be blank")
    @Size(min = 10,message = "address must be at-least 10 characters long")
    private String userAddress;
    @Column(name = "USER_YEARS_OF_EXPERIENCE")
    private int userYearsOfExperience;
}
