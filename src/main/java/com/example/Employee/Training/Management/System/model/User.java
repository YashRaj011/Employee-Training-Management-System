package com.example.Employee.Training.Management.System.model;

import com.example.Employee.Training.Management.System.annotation.FieldsValueMatch;
import com.example.Employee.Training.Management.System.annotation.PasswordValidator;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name="user_details")
@Getter
@Setter
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "password",
                fieldMatch = "confirmPassword",
                message = "Passwords do not match!"
        )
})
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="USER_ID")
    private int userId;
    @NotBlank(message = "Name must not be blank")
    @Size(min = 3,message = "Name must be at-least 3 characters long")
    @Column(name="USER_NAME")
    private String userName;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Please provide a valid email address")
    @Column(name="USER_EMAIL")
    private String email;
    @NotBlank(message = "Password must not be blank")
    @Column(name="USER_PASSWORD")
    @PasswordValidator
    private String password;
    @NotBlank(message = "Confirm Password must not be blank")
    @Transient
    private String confirmPassword;

    @NotBlank(message = "Mobile number must not be blank")
    @Pattern(regexp="^$|[0-9]{10}", message = "Mobile number must be 10 digits")
    @Column(name = "USER_CONTACT")
    private String contactNumber;

    @Column(name = "USER_PHOTOS_NAME", length = 64)
    private String userPhotosName;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, targetEntity = Roles.class)
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "roleId", nullable = false)
    private Roles roles;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = UserCompanyDetails.class)
    @JoinColumn(name = "COMPANY_ID", referencedColumnName = "companyId", nullable = true)
    private UserCompanyDetails company;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "U_ID", referencedColumnName = "uId")
    private UserPersonalDetails personalDetails;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "student_course",
            joinColumns = @JoinColumn(name = "STUDENT_ID", referencedColumnName = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "STD_COURSE_ID", referencedColumnName = "COURSE_ID")
    )
    private Set<Course> etmsCourses = new HashSet<>();

    @Transient
    public String getPhotosImagePath() {
        if (userPhotosName == null || userId == 0) return "/user-photos/default/default-img.jpg";
        return "/user-photos/" + userId + "/" + userPhotosName;
    }

}
