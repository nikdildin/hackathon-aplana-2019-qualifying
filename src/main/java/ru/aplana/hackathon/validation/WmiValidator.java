package ru.aplana.hackathon.validation;

import ru.aplana.hackathon.service.ConstantsProvider;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class WmiValidator implements ConstraintValidator<WmiConstraint, String> {
    @Override
    public boolean isValid(String contactField, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.nonNull(contactField)) {
            if (contactField.length() != 3) {
                return false;
            }
            for (char s : contactField.toUpperCase().toCharArray()) {
                if (!String.valueOf(ConstantsProvider.SYMBOLS).contains(String.valueOf(s))) {
                    return false;
                }
            }
        }
        return true;
    }
}
