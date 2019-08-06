package ru.aplana.hackathon.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class WmiValidator implements ConstraintValidator<WmiConstraint, String> {
    @Override
    public boolean isValid(String contactField, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.nonNull(contactField)) {
            // TODO
        }
        return true;
    }
}
