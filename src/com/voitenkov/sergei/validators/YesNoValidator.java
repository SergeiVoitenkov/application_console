package com.voitenkov.sergei.validators;

import com.voitenkov.sergei.utilities.ConsoleMessageConstants;

public class YesNoValidator implements Validator<String> {

    @Override
    public ValidationResult execute(String value) {
        var result = new ValidationResult();

        if (value.isBlank()) {
            result.setValid(false);
            result.setErrorMessage(ConsoleMessageConstants.STRING_EMPTY_ERROR_MESSAGE);
        } else if (!value.equals(ConsoleMessageConstants.ANSWER_NO_MASSAGE) &&
                !value.equals(ConsoleMessageConstants.ANSWER_YES_MASSAGE)) {
            result.setValid(false);
            result.setErrorMessage(ConsoleMessageConstants.ANSWER_ERROR_MESSAGE);
        }
        return result;
    }
}