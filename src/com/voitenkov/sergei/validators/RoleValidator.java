package com.voitenkov.sergei.validators;

import com.voitenkov.sergei.entities.Role;
import com.voitenkov.sergei.utilities.ConsoleMessageConstants;

import java.util.Arrays;
import java.util.List;

public class RoleValidator implements Validator<String> {

    @Override
    public ValidationResult execute(String value) {
        var result = new ValidationResult();
        List<Role> availableRoles;
        boolean isFoundRole = false;

        if (value != null && !value.isBlank()) {
            availableRoles = Arrays.stream(Role.values()).toList();

            for (Role availableRole : availableRoles) {

                if (availableRole.toString().equalsIgnoreCase(value)) {
                    isFoundRole = true;
                    break;
                }
            }

            if (!isFoundRole) {
                result.setValid(false);
                result.setErrorMessage(ConsoleMessageConstants.ROLE_INCORRECT_ERROR_MESSAGE);
                return result;
            }
        } else {
            result.setValid(false);
            result.setErrorMessage(ConsoleMessageConstants.STRING_EMPTY_ERROR_MESSAGE);
        }
        return result;
    }
}