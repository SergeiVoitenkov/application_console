package com.voitenkov.sergei.validators;

import com.voitenkov.sergei.utilities.ConsoleMessageConstants;
import com.voitenkov.sergei.utilities.RegexExpressions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class EmailValidator implements Validator<String> {

    @Override
    public ValidationResult execute(String value) {
        var result = new ValidationResult();
        Pattern emailPattern = Pattern.compile(RegexExpressions.EMAIL_REGEX);
        Matcher emailMatcher = emailPattern.matcher(value);

        if (value.isBlank()) {
            result.setValid(false);
            result.setErrorMessage(ConsoleMessageConstants.STRING_EMPTY_ERROR_MESSAGE);
        } else if (!emailMatcher.find()) {
            result.setValid(false);
            result.setErrorMessage(ConsoleMessageConstants.EMAIL_ERROR_MESSAGE);
        }
        return result;
    }
}