package ru.aplana.hackathon.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class YearValidator implements ConstraintValidator<WmiConstraint, Integer> {
    @Override
    public boolean isValid(Integer contactField, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.nonNull(contactField)) {
            // TODO
        }
        return true;
    }
}
