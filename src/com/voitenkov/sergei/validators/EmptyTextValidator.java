package com.voitenkov.sergei.validators;

import com.voitenkov.sergei.utilities.ConsoleMessageConstants;

class EmptyTextValidator implements Validator<String> {

    @Override
    public ValidationResult execute(String value) {
        var result = new ValidationResult();

        if (value.isBlank()) {
            result.setValid(false);
            result.setErrorMessage(ConsoleMessageConstants.STRING_EMPTY_ERROR_MESSAGE);
        }
        return result;
    }
}