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
@Table(name="company_details")
public class UserCompanyDetails extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name="native", strategy = "native")
    private int companyId;
    @NotBlank(message = "Company name must not be blank")
    @Size(min = 3,message = "Company name must be at-least 3 characters long")
    private String companyName;
    @NotBlank(message = "Company role must not be blank")
    @Size(min = 3,message = "Company role must be at-least 3 characters long")
    private String roleInCompany;

    @NotBlank(message = "Company joining must not be blank")
    @Column(name = "JOINED_DATE")
    private String joiningDate;

    @Column(name = "LEAVE_DATE")
    private String leavingDate;

}
