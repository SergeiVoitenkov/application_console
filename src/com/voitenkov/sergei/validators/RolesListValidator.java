package com.voitenkov.sergei.validators;

import com.voitenkov.sergei.entities.Role;
import com.voitenkov.sergei.utilities.ConsoleMessageConstants;

import java.util.*;
import java.util.stream.Collectors;

public class RolesListValidator implements Validator<List<Role>> {

    @Override
    public ValidationResult execute(List<Role> rolesList) {
        var result = new ValidationResult();
        Set<Integer> roleLevels = Arrays.stream(Role.values()).toList()
                .stream().map(Role::getLevel)
                .collect(Collectors.toCollection(TreeSet::new));

        if (rolesList.size() > 1 && !roleLevels.isEmpty()) {
            int roleLevelPrevious = rolesList.get(rolesList.size() - 2).getLevel();
            int roleLevel = rolesList.get(rolesList.size() - 1).getLevel();

            if (roleLevelPrevious >= roleLevel) {
                result.setValid(false);
                result.setErrorMessage(ConsoleMessageConstants.ADD_ROLE_ERROR_MESSAGE);
            } else {

                for (int level : roleLevels) {

                    if (roleLevelPrevious < level && roleLevel == level) {
                        result.setValid(true);
                        result.setErrorMessage(null);
                        break;
                    } else {
                        result.setValid(false);
                        result.setErrorMessage(ConsoleMessageConstants.ADD_ROLE_ERROR_MESSAGE);
                    }
                }
            }
        }
        return result;
    }
}