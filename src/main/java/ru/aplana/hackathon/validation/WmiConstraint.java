package ru.aplana.hackathon.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = WmiValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface WmiConstraint {
    String message() default "Invalid wmi";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
