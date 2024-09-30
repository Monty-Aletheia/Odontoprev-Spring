package com.fiap.br.challenger.application.exception;

import com.fiap.br.challenger.application.utils.decorator.ValidEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidationEnum implements ConstraintValidator<ValidEnum, Enum<?>> {
    private Enum<?>[] enumValues;

    @Override
    public void initialize(ValidEnum annotation) {
        enumValues = annotation.enumClass().getEnumConstants();
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        for (Enum<?> enumValue : enumValues) {
            if (enumValue.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
