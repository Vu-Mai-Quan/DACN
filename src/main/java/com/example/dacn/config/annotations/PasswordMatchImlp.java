package com.example.dacn.config.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class PasswordMatchImlp implements ConstraintValidator<PasswordMatch, Object> {
    private String passwordField, confirmPasswordField;

    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
        this.passwordField = constraintAnnotation.passwordField();
        this.confirmPasswordField = constraintAnnotation.confirmPasswordField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object password = new BeanWrapperImpl(value).getPropertyValue(passwordField),
                confirmPassword = new BeanWrapperImpl(value).getPropertyValue(confirmPasswordField);


        boolean isValid = false;
        if (password != null && confirmPassword != null) {
            isValid = password.equals(confirmPassword);
        }

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode(confirmPasswordField) // Associate the error with the confirm password field
                    .addConstraintViolation();
        }
        return isValid;
    }
}
