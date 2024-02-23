package com.example.Employee.Training.Management.System.validation;

import com.example.Employee.Training.Management.System.annotation.FieldsValueMatch;
import com.example.Employee.Training.Management.System.annotation.PasswordValidator;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class PasswordStrengthValidator implements ConstraintValidator<PasswordValidator, String> {

    List<String> weakPasswords;

    @Override
    public void initialize(PasswordValidator passwordValidator) {
        weakPasswords = Arrays.asList("12345", "password", "qwerty");
    }

    @Override
    public boolean isValid(String passwordField, ConstraintValidatorContext constraintValidatorContext) {
        return passwordField != null && (!weakPasswords.contains(passwordField));
    }
}
