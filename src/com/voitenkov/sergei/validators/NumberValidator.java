package com.voitenkov.sergei.validators;

import com.voitenkov.sergei.utilities.ConsoleMessageConstants;
import com.voitenkov.sergei.utilities.RegexExpressions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class NumberValidator implements Validator<String> {

    @Override
    public ValidationResult execute(String value) {
        var result = new ValidationResult();
        Pattern pattern = Pattern.compile(RegexExpressions.NUMBER_REGEX);
        Matcher numberMatcher = pattern.matcher(value.trim());

        if (numberMatcher.find()) {
            result.setValid(true);
        } else {
            result.setValid(false);
            result.setErrorMessage(ConsoleMessageConstants.NUMBER_ERROR_MESSAGE);
        }
        return result;
    }
}