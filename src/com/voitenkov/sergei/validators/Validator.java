package com.voitenkov.sergei.validators;

public interface Validator <T> {

    ValidationResult execute(T value);
}
