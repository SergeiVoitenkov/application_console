package com.voitenkov.sergei.validators;

public class ValidatorFactory {
    public static <T> Validator<T> getValidator(ValidatorCode code) {
        switch (code) {
            case EMPTY_TEXT -> {
                return (Validator<T>) new EmptyTextValidator();
            }
            case EMAIL -> {
                return (Validator<T>) new EmailValidator();
            }
            case PHONE -> {
                return (Validator<T>) new PhoneNumberValidator();
            }
            case YES_NO -> {
                return (Validator<T>) new YesNoValidator();
            }
            case NUMBER -> {
                return (Validator<T>) new NumberValidator();
            }
            case ROLE -> {
                return (Validator<T>) new RoleValidator();
            }
            case ROLE_LIST -> {
                return (Validator<T>) new RolesListValidator();
            }
        }
        return null;
    }
}