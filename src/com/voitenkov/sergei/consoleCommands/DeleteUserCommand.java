package com.voitenkov.sergei.consoleCommands;

import com.voitenkov.sergei.utilities.ConsoleHelper;
import com.voitenkov.sergei.entities.User;
import com.voitenkov.sergei.services.UserService;
import com.voitenkov.sergei.utilities.ConsoleMessageConstants;
import com.voitenkov.sergei.validators.ValidatorCode;
import com.voitenkov.sergei.validators.ValidatorFactory;

public class DeleteUserCommand extends UserConsoleCommand {
    public DeleteUserCommand(UserService userService) {
        super(userService);
    }

    @Override
    public void Execute() {
        User user;
        long id;

        while (true) {
            System.out.printf("%s", ConsoleMessageConstants.ENTER_USER_ID_MESSAGE);
            String consoleInput = ConsoleHelper.readLine();

            var validator = ValidatorFactory.getValidator(ValidatorCode.NUMBER);
            var validatorResult = validator.execute(consoleInput);

            if (validatorResult.isValid()) {
                id = Long.parseLong(consoleInput);
                user = userService.getUser(id);

                if (user == null) {
                    System.out.println("Пользователь с id = " + id + " не найден.");
                } else {
                    userService.deleteUser(id);
                }
                break;
            } else {
                System.out.println(validatorResult.getErrorMessage());
            }
        }
    }
}