package com.example.component;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.AbstractValidator;


public class MyNotNullValidator extends AbstractValidator {
    /**
     * Constructs a validator with the given error message. The substring "{0}"
     * is replaced by the value that failed validation.
     *
     * @param errorMessage the message to be included in a failed result, not null
     */
    public MyNotNullValidator(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public ValidationResult apply(Object value, ValueContext context) {
        if (value == null)
            return toResult(value, false);

        return toResult(value, true);
    }

    @Override
    public Object apply(Object o, Object o2) {
        return null;
    }
}

