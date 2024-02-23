package com.example.Employee.Training.Management.System.model;

import com.example.Employee.Training.Management.System.annotation.FieldsValueMatch;
import com.example.Employee.Training.Management.System.annotation.PasswordValidator;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "newPassword",
                fieldMatch = "confirmPassword",
                message = "Passwords do not match!"
        )
})
public class Setting {

    @NotBlank(message = "Password must not be blank")
    private String currPassword;
    @NotBlank(message = "Password must not be blank")
    private String newPassword;
    @NotBlank(message = "Confirm Password must not be blank")
    private String confirmPassword;
}
