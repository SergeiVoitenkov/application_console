package com.voitenkov.sergei.consoleCommands;

import com.voitenkov.sergei.utilities.ConsoleMessageConstants;
import com.voitenkov.sergei.utilities.ConsoleConstants;
import com.voitenkov.sergei.utilities.ConsoleHelper;
import com.voitenkov.sergei.entities.Role;
import com.voitenkov.sergei.entities.User;
import com.voitenkov.sergei.services.EntityValidationException;
import com.voitenkov.sergei.services.UserService;
import com.voitenkov.sergei.validators.ValidatorCode;
import com.voitenkov.sergei.validators.ValidatorFactory;

import java.util.*;

public class EditUserCommand extends UserConsoleCommand {
    public EditUserCommand(UserService userService) {
        super(userService);
    }

    @Override
    public void Execute() {
        User user;
        long id;
        boolean flag = true;

        while (flag) {
            System.out.printf("%s", ConsoleMessageConstants.ENTER_USER_ID_MESSAGE);
            String consoleInput = ConsoleHelper.readLine();

            var validator = ValidatorFactory.getValidator(ValidatorCode.NUMBER);
            var validatorResult = validator.execute(consoleInput);

            if (validatorResult.isValid()) {
                id = Long.parseLong(consoleInput);
                user = userService.getUser(id);

                if (user == null) {
                    System.out.printf("%s%n", ConsoleMessageConstants.USER_NOT_FOUND_MASSAGE);
                } else {
                    editFirstName(user);
                    editLastName(user);
                    editEmail(user);
                    editRole(user);
                    editPhoneNumbers(user);

                    System.out.printf("%s \"%s\"%n%s ",
                            ConsoleMessageConstants.SAVE_CHANGES_MASSAGE,
                            user,
                            ConsoleMessageConstants.YES_NO_MESSAGE);
                    consoleInput = ConsoleHelper.readLine();

                    validator = ValidatorFactory.getValidator(ValidatorCode.YES_NO);
                    validatorResult = validator.execute(consoleInput);

                    if (validatorResult.isValid() && consoleInput.equals(ConsoleMessageConstants.ANSWER_YES_MASSAGE)) {
                        try {
                            userService.editUser(user);
                        } catch (EntityValidationException e) {
                            e.printStackTrace();
                        }
                        flag = false;
                    } else if (validatorResult.getErrorMessage() != null) {
                        System.out.println(validatorResult.getErrorMessage());
                    }
                }
            } else {
                System.out.println(validatorResult.getErrorMessage());
            }
        }
    }

    public void editFirstName(User user) {
        String consoleInput;
        boolean assignedAValue = false;

        while (!assignedAValue) {
            System.out.printf("%s \"%s\"%n%s",
                    ConsoleMessageConstants.EDIT_FIRST_NAME_MESSAGE,
                    user.getFirstName(),
                    ConsoleMessageConstants.YES_NO_MESSAGE);
            consoleInput = ConsoleHelper.readLine();

            var validator = ValidatorFactory.getValidator(ValidatorCode.YES_NO);
            var validatorResult = validator.execute(consoleInput);

            if (validatorResult.isValid() && consoleInput.equals(ConsoleMessageConstants.ANSWER_YES_MASSAGE)) {

                while (!assignedAValue) {
                    System.out.printf("%s ", ConsoleMessageConstants.ENTER_FIRST_NAME_MESSAGE);
                    consoleInput = ConsoleHelper.readLine();

                    validator = ValidatorFactory.getValidator(ValidatorCode.EMPTY_TEXT);
                    validatorResult = validator.execute(consoleInput);

                    if (validatorResult.isValid()) {
                        user.setFirstName(consoleInput);
                        assignedAValue = true;
                    } else {
                        System.out.println(validatorResult.getErrorMessage());
                    }
                }
            } else if (validatorResult.getErrorMessage() != null) {
                System.out.println(validatorResult.getErrorMessage());
            } else {
                assignedAValue = true;
            }
        }
    }

    public void editLastName(User user) {
        String consoleInput;
        boolean assignedAValue = true;

        while (assignedAValue) {
            System.out.printf("%s \"%s\"%n%s ",
                    ConsoleMessageConstants.EDIT_LAST_NAME_MESSAGE,
                    user.getLastName(),
                    ConsoleMessageConstants.YES_NO_MESSAGE);
            consoleInput = ConsoleHelper.readLine();

            var validator = ValidatorFactory.getValidator(ValidatorCode.YES_NO);
            var validatorResult = validator.execute(consoleInput);

            if (validatorResult.isValid() && consoleInput.equals(ConsoleMessageConstants.ANSWER_YES_MASSAGE)) {
                System.out.printf("%s ", ConsoleMessageConstants.ENTER_LAST_NAME_MESSAGE);
                consoleInput = ConsoleHelper.readLine();

                validator = ValidatorFactory.getValidator(ValidatorCode.EMPTY_TEXT);
                validatorResult = validator.execute(consoleInput);

                if (validatorResult.isValid()) {
                    user.setLastName(consoleInput);
                    assignedAValue = false;
                } else {
                    System.out.println(validatorResult.getErrorMessage());
                }
            } else if (validatorResult.getErrorMessage() != null) {
                System.out.println(validatorResult.getErrorMessage());
            } else {
                assignedAValue = false;
            }
        }
    }

    public void editEmail(User user) {
        String consoleInput;
        boolean assignedAValue = true;

        while (assignedAValue) {
            System.out.printf("%s \"%s\"%n%s",
                    ConsoleMessageConstants.EDIT_EMAIL_MESSAGE,
                    user.getEmail(),
                    ConsoleMessageConstants.YES_NO_MESSAGE);
            consoleInput = ConsoleHelper.readLine();

            var validator = ValidatorFactory.getValidator(ValidatorCode.YES_NO);
            var validatorResult = validator.execute(consoleInput);

            if (validatorResult.isValid() && consoleInput.equals(ConsoleMessageConstants.ANSWER_YES_MASSAGE)) {
                while (assignedAValue) {
                    System.out.printf("%s", ConsoleMessageConstants.ENTER_EMAIL_MESSAGE);
                    consoleInput = ConsoleHelper.readLine();

                    validator = ValidatorFactory.getValidator(ValidatorCode.EMAIL);
                    validatorResult = validator.execute(consoleInput);

                    if (validatorResult.isValid()) {
                        user.setEmail(consoleInput);
                        assignedAValue = false;
                    } else {
                        System.out.println(validatorResult.getErrorMessage());
                    }
                }
            } else if (validatorResult.getErrorMessage() != null) {
                System.out.println(validatorResult.getErrorMessage());
            } else {
                assignedAValue = false;
            }
        }
    }

    public void editRole(User user) {
        String consoleInput;
        List<Role> roles = Arrays.stream(Role.values()).toList();
        int MaxLevel = roles.stream()
                .max(Comparator.comparing(Role::getLevel))
                .orElseThrow(NoSuchElementException::new)
                .getLevel();
        boolean assignedAValue = true;

        while (assignedAValue) {
            System.out.printf("%s \"%s\"%n%s",
                    ConsoleMessageConstants.EDIT_ROLE_LIST_MESSAGE,
                    user.getRolesList(),
                    ConsoleMessageConstants.YES_NO_MESSAGE);
            consoleInput = ConsoleHelper.readLine();

            var validator = ValidatorFactory.getValidator(ValidatorCode.YES_NO);
            var validatorResult = validator.execute(consoleInput);

            if (validatorResult.isValid() && consoleInput.equals(ConsoleMessageConstants.ANSWER_YES_MASSAGE)) {
                user.setRolesList(new ArrayList<>());
                boolean isAddRole = true;

                while (isAddRole) {
                    System.out.printf("%s \"%s\"%n%s ",
                            ConsoleMessageConstants.ROLES_LIST_MESSAGE,
                            roles,
                            ConsoleMessageConstants.ENTRY_ROLE_MESSAGE);

                    consoleInput = ConsoleHelper.readLine();

                    validator = ValidatorFactory.getValidator(ValidatorCode.ROLE);
                    validatorResult = validator.execute(consoleInput);

                    if (validatorResult.isValid()) {
                        Role role = Role.valueOf(consoleInput.toUpperCase());
                        user.getRolesList().add(role);

                        validator = ValidatorFactory.getValidator(ValidatorCode.ROLE_LIST);
                        validatorResult = validator.execute(user.getRolesList());

                        if (!validatorResult.isValid()) {
                            user.getRolesList().remove(role);
                            System.out.println(validatorResult.getErrorMessage());
                        } else if (role.getLevel() == MaxLevel) {
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

                        if (!validatorResult.isValid()
                                || consoleInput.equals(ConsoleMessageConstants.ANSWER_NO_MASSAGE)) {
                            isAddRole = false;
                        } else if (!validatorResult.isValid() && validatorResult.getErrorMessage() != null) {
                            System.out.println(validatorResult.getErrorMessage());
                        }
                    }
                }
            } else if (validatorResult.getErrorMessage() != null) {
                System.out.println(validatorResult.getErrorMessage());
            } else {
                assignedAValue = false;
            }
        }
    }

    public void editPhoneNumbers(User user) {
        String consoleInput;
        boolean assignedAValue = true;

        while (assignedAValue) {
            System.out.printf("%s \"%s\"%n%s ",
                    ConsoleMessageConstants.EDIT_PHONE_NUMBERS_MESSAGE,
                    user.getPhoneNumbersList(),
                    ConsoleMessageConstants.YES_NO_MESSAGE);
            consoleInput = ConsoleHelper.readLine();

            var validator = ValidatorFactory.getValidator(ValidatorCode.YES_NO);
            var validatorResult = validator.execute(consoleInput);

            if (validatorResult.isValid() && consoleInput.equals(ConsoleMessageConstants.ANSWER_YES_MASSAGE)) {
                int index = 0;
                System.out.printf("%s%n", ConsoleMessageConstants.EDIT_MESSAGE);

                for (int i = 0; i < user.getPhoneNumbersList().size(); i++) {
                    System.out.printf("%s \"%s\". %s \"%s\"%n ",
                            ConsoleMessageConstants.COMMAND_MASSAGE,
                            i,
                            ConsoleMessageConstants.EDIT_PHONE_NUMBER_MESSAGE,
                            user.getPhoneNumbersList().get(i));
                    index++;
                }

                if (user.getPhoneNumbersList().size() < com.voitenkov.sergei.utilities.ConsoleConstants.MAX_NUMBER_OF_MOBILE_NUMBERS) {
                    System.out.printf("%s \"%s\". %s%n",
                            ConsoleMessageConstants.COMMAND_MASSAGE,
                            index,
                            ConsoleMessageConstants.ADD_NEW_PHONE_NUMBER_MESSAGE);
                }

                System.out.printf("%s%n ", ConsoleMessageConstants.ENTRY_NUMBER_COMMAND_MASSAGE);
                consoleInput = ConsoleHelper.readLine();

                validator = ValidatorFactory.getValidator(ValidatorCode.NUMBER);
                validatorResult = validator.execute(consoleInput);

                if (validatorResult.isValid()) {
                    index = Integer.parseInt(consoleInput);
                } else {
                    System.out.println(validatorResult.getErrorMessage());
                    break;
                }

                if (index > -1 && index <= user.getPhoneNumbersList().size()) {
                    System.out.printf("%s ", ConsoleMessageConstants.ENTER_PHONE_NUMBER_MESSAGE);
                    consoleInput = ConsoleHelper.readLine().replaceAll("\\s+", "");

                    validator = ValidatorFactory.getValidator(ValidatorCode.PHONE);
                    validatorResult = validator.execute(consoleInput);

                    if (validatorResult.isValid() && index < user.getPhoneNumbersList().size()) {
                        user.getPhoneNumbersList().set(index, new StringBuffer(consoleInput).insert(5, " ")
                                .toString());
                    } else if (validatorResult.isValid() && index < ConsoleConstants.MAX_NUMBER_OF_MOBILE_NUMBERS) {
                        user.getPhoneNumbersList().add(new StringBuffer(consoleInput).insert(5, " ")
                                .toString());
                    }

                } else {
                    System.out.printf("%s%n", ConsoleMessageConstants.NUMBER_COMMAND_INCORRECT_ERROR_MESSAGE);
                }

                if (user.getPhoneNumbersList().size() == com.voitenkov.sergei.utilities.ConsoleConstants.MAX_NUMBER_OF_MOBILE_NUMBERS) {
                    assignedAValue = false;
                }

            } else if (validatorResult.getErrorMessage() != null) {
                System.out.println(validatorResult.getErrorMessage());
            } else {
                assignedAValue = false;
            }
        }
    }
}