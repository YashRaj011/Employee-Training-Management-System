package com.example.Employee.Training.Management.System.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter
@Setter
@Entity
@Table(name = "user_personal_details")
public class UserPersonalDetails extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    private int uId;
    @NotBlank(message = "Description must not be blank")
    @Size(min = 100, max = 250,message = "Description must be at-least 100 and almost 250 characters long")
    private String userDescription;
    @NotBlank(message = "address must not be blank")
    @Size(min = 10,message = "address must be at-least 10 characters long")
    private String userAddress;

    @Column(name = "USER_YEARS_OF_EXPERIENCE")
    private int userYearsOfExperience;
    @NotBlank(message = "facebook-url must not be blank")
    private String facebookUrl;
    @NotBlank(message = "instagram-url must not be blank")
    private String instagramUrl;
    @NotBlank(message = "linkedin-url must not be blank")
    private String linkedinUrl;
    @NotBlank(message = "twitter-url must not be blank")
    private String twitterUrl;
}
