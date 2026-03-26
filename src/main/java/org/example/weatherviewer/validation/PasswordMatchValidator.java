package org.example.weatherviewer.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {

    private String passwordField;
    private String confirmPasswordField;
    private String message;

    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
        this.passwordField = constraintAnnotation.passwordField();
        this.confirmPasswordField = constraintAnnotation.confirmPasswordField();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object password = new BeanWrapperImpl(value).getPropertyValue(passwordField);
        Object confirmPassword = new BeanWrapperImpl(value).getPropertyValue(confirmPasswordField);

        if (password == null && confirmPassword == null) {
            return true;
        }

        if (password == null || confirmPassword == null) {
            addViolation(context, confirmPasswordField);
            return false;
        }

        boolean isValid = password.equals(confirmPassword);

        if (!isValid) {
            addViolation(context, confirmPasswordField);
        }

        return isValid;
    }

    private void addViolation(ConstraintValidatorContext context, String fieldName) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(fieldName)
                .addConstraintViolation();
    }
}