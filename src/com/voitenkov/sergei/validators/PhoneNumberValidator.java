package com.voitenkov.sergei.validators;

import com.voitenkov.sergei.utilities.ConsoleMessageConstants;
import com.voitenkov.sergei.utilities.RegexExpressions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class PhoneNumberValidator implements Validator<String> {

    @Override
    public ValidationResult execute(String value) {
        var result = new ValidationResult();
        Pattern pattern = Pattern.compile(RegexExpressions.PHONE_NUMBER_REGEX);
        Matcher phoneNumberMatcher = pattern.matcher(value.replaceAll(" ", ""));

        if (value.isBlank()) {
            result.setValid(false);
            result.setErrorMessage(ConsoleMessageConstants.STRING_EMPTY_ERROR_MESSAGE);
        } else {

            if (!phoneNumberMatcher.find()) {
                result.setValid(false);
                result.setErrorMessage(ConsoleMessageConstants.PHONE_NUMBER_ERROR_MESSAGE);
            }
        }
        return result;
    }
}