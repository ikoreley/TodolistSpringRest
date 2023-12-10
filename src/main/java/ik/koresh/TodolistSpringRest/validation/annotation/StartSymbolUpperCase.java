package ik.koresh.TodolistSpringRest.validation.annotation;


import ik.koresh.TodolistSpringRest.validation.StartSymbolUpperCaseValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;


@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StartSymbolUpperCaseValidator.class)
public @interface StartSymbolUpperCase {
    String message() default "First letter must Upper";

    Class<?>[] groups() default { };

    Class<? extends Payload> []  payload() default { };

}
