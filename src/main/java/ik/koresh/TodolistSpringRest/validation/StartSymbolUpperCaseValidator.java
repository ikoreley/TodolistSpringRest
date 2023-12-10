package ik.koresh.TodolistSpringRest.validation;


import ik.koresh.TodolistSpringRest.validation.annotation.StartSymbolUpperCase;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class StartSymbolUpperCaseValidator implements ConstraintValidator<StartSymbolUpperCase, String > {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Character.isUpperCase(value.charAt(0));
    }
}
