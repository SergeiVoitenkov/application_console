package com.voitenkov.sergei.consoleCommands;

import com.voitenkov.sergei.utilities.*;
import com.voitenkov.sergei.entities.*;
import com.voitenkov.sergei.services.EntityValidationException;
import com.voitenkov.sergei.services.UserService;
import com.voitenkov.sergei.validators.*;

import java.util.*;
import java.util.stream.Collectors;

public class CreateUserCommand extends UserConsoleCommand {
    public CreateUserCommand(UserService userService) {
        super(userService);
    }

    @Override
    public void Execute() {
        User user = new User();

        setFirstName(user);
        setLastName(user);
        setEmail(user);
        setRoles(user);
        setPhoneNumbers(user);

        try {
            userService.createUser(user);
        } catch (EntityValidationException validationException) {
            System.out.printf("%s%n", ConsoleMessageConstants.USER_VALIDATION_ERROR_MASSAGE);

            for (Map.Entry<String, String> entry : validationException.getErrors().entrySet()) {
                String propertyName = entry.getKey();
                String propertyError = entry.getValue();

                System.out.printf("%s %s %s %s%n",
                        ConsoleMessageConstants.PROPERTY_MASSAGE,
                        propertyName,
                        ConsoleMessageConstants.ERROR_MASSAGE,
                        propertyError);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void setFirstName(User user) {
        boolean assignedAValue = false;

        while (!assignedAValue) {
            System.out.printf("%s ", ConsoleMessageConstants.ENTER_FIRST_NAME_MESSAGE);
            String consoleInput = ConsoleHelper.readLine();

            var validator = ValidatorFactory.getValidator(ValidatorCode.EMPTY_TEXT);
            var validatorResult = validator.execute(consoleInput);

            if (validatorResult.isValid()) {
                user.setFirstName(consoleInput);
                assignedAValue = true;
            } else {
                System.out.println(validatorResult.getErrorMessage());
            }
        }
    }

    private void setLastName(User user) {
        boolean assignedAValue = false;

        while (!assignedAValue) {
            System.out.printf("%s ", ConsoleMessageConstants.ENTER_LAST_NAME_MESSAGE);
            String consoleInput = ConsoleHelper.readLine();

            var validator = ValidatorFactory.getValidator(ValidatorCode.EMPTY_TEXT);
            var validatorResult = validator.execute(consoleInput);

            if (validatorResult.isValid()) {
                user.setLastName(consoleInput);
                assignedAValue = true;
            } else {
                System.out.println(validatorResult.getErrorMessage());
            }
        }
    }

    private void setEmail(User user) {
        boolean assignedAValue = false;

        while (!assignedAValue) {
            System.out.printf("%s", ConsoleMessageConstants.ENTER_EMAIL_MESSAGE);
            String consoleInput = ConsoleHelper.readLine();

            var validator = ValidatorFactory.getValidator(ValidatorCode.EMAIL);
            var validatorResult = validator.execute(consoleInput);

            if (validatorResult.isValid()) {
                user.setEmail(consoleInput);
                assignedAValue = true;
            } else {
                System.out.println(validatorResult.getErrorMessage());
            }
        }
    }

    private void setRoles(User user) {
        String consoleInput;
        boolean isAddRole = true;
        user.setRolesList(new ArrayList<>());
        List<Role> roles = Arrays.stream(Role.values()).toList();
        int maxLevel = roles.stream()
                .max(Comparator.comparing(Role::getLevel))
                .orElseThrow(NoSuchElementException::new)
                .getLevel();

        while (isAddRole) {
            System.out.printf("%s \"%s\"%n%s ",
                    ConsoleMessageConstants.ROLES_LIST_MESSAGE,
                    roles,
                    ConsoleMessageConstants.ENTRY_ROLE_MESSAGE);
            consoleInput = ConsoleHelper.readLine();

            var validator = ValidatorFactory.getValidator(ValidatorCode.ROLE);
            var validatorResult = validator.execute(consoleInput);

            if (validatorResult.isValid()) {
                Role role = Role.valueOf(consoleInput.toUpperCase());
                user.getRolesList().add(role);

                validator = ValidatorFactory.getValidator(ValidatorCode.ROLE_LIST);
                validatorResult = validator.execute(user.getRolesList());

                if (!validatorResult.isValid()) {
                    user.getRolesList().remove(role);
                    System.out.println(validatorResult.getErrorMessage());
                } else if (role.getLevel() == maxLevel) {
                    isAddRole = false;
                }
            } else {
                System.out.println(validatorResult.getErrorMessage());
                continue;
            }

            if (isAddRole) {
                System.out.printf("%s%n%s",
                        ConsoleMessageConstants.ADD_ROLE_MESSAGE,
                        ConsoleMessageConstants.YES_NO_MESSAGE);
                consoleInput = ConsoleHelper.readLine();

                validator = ValidatorFactory.getValidator(ValidatorCode.YES_NO);
                validatorResult = validator.execute(consoleInput);

                if (consoleInput.equals(ConsoleMessageConstants.ANSWER_NO_MASSAGE)) {
                    isAddRole = false;
                } else if (!validatorResult.isValid()) {
                    System.out.println(validatorResult.getErrorMessage());
                }
            }
        }
    }

    private void setPhoneNumbers(User user) {
        String consoleInput;
        user.setPhoneNumbersList(new ArrayList<>());
        var assignedAValue = true;

        while (user.getPhoneNumbersList().size() < ConsoleConstants.MAX_NUMBER_OF_MOBILE_NUMBERS && assignedAValue) {
            System.out.printf("%s ", ConsoleMessageConstants.ENTER_PHONE_NUMBER_MESSAGE);
            consoleInput = ConsoleHelper.readLine().replaceAll("\\s+", "");

            var validator = ValidatorFactory.getValidator(ValidatorCode.PHONE);
            var validatorResult = validator.execute(consoleInput);

            if (validatorResult.isValid()) {
                user.getPhoneNumbersList().add(new StringBuffer(consoleInput).insert(5, " ").toString());
                user.setPhoneNumbersList(user.getPhoneNumbersList().stream()
                        .distinct()
                        .collect(Collectors.toList()));

                while (user.getPhoneNumbersList().size() < ConsoleConstants.MAX_NUMBER_OF_MOBILE_NUMBERS) {
                    System.out.printf("%s%n%s",
                            ConsoleMessageConstants.ADD_PHONE_NUMBER_MESSAGE,
                            ConsoleMessageConstants.YES_NO_MESSAGE);
                    consoleInput = ConsoleHelper.readLine();

                    validator = ValidatorFactory.getValidator(ValidatorCode.YES_NO);
                    validatorResult = validator.execute(consoleInput);

                    if (validatorResult.isValid() && consoleInput.equals(ConsoleMessageConstants.ANSWER_YES_MASSAGE)) {
                        break;
                    } else if (consoleInput.equals(ConsoleMessageConstants.ANSWER_NO_MASSAGE)) {
                        assignedAValue = false;
                        break;
                    } else {
                        System.out.println(validatorResult.getErrorMessage());
                    }
                }
            } else {
                System.out.println(validatorResult.getErrorMessage());
            }
        }
    }
}