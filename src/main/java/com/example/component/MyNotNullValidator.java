package com.example.component;


import com.vaadin.data.ValidationResult;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.AbstractValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MyNotNullValidator extends AbstractValidator {

    private static final Logger logger = LoggerFactory.getLogger(MyNotNullValidator.class);

    public MyNotNullValidator(String errorMessage) {
        super(errorMessage);

        logger.debug("MyNotNullValidator constructor invoked;");

    }

    @Override
    public ValidationResult apply(Object value, ValueContext context) {

        if (value == null) {
            return toResult(value, false);
        } else {
            return toResult(value, true);
        }
    }

    @Override
    public Object apply(Object o, Object o2) {
        return null;
    }
}

