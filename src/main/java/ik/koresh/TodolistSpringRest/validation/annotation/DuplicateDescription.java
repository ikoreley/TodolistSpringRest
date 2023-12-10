package ik.koresh.TodolistSpringRest.validation.annotation;

import ik.koresh.TodolistSpringRest.validation.DuplicateDescriptionValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DuplicateDescriptionValidator.class)
public @interface DuplicateDescription {
    String message() default "It's description exist already!";

    Class<?>[] groups() default { };

    Class<? extends Payload> []  payload() default { };
}
