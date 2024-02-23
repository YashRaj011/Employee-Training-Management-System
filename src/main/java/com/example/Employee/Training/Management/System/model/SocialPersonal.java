package com.example.Employee.Training.Management.System.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SocialPersonal {
    @NotBlank(message = "facebook-url must not be blank")
    private String facebookUrl;
    @NotBlank(message = "instagram-url must not be blank")
    private String instagramUrl;
    @NotBlank(message = "linkedin-url must not be blank")
    private String linkedinUrl;
    @NotBlank(message = "twitter-url must not be blank")
    private String twitterUrl;
}
