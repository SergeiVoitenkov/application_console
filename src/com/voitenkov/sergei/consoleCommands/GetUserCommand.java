package com.voitenkov.sergei.consoleCommands;

import com.voitenkov.sergei.utilities.ConsoleHelper;
import com.voitenkov.sergei.entities.User;
import com.voitenkov.sergei.services.UserService;
import com.voitenkov.sergei.utilities.ConsoleMessageConstants;

public class GetUserCommand extends GetAllUsersCommand {
    public GetUserCommand(UserService userService) {
        super(userService);
    }

    @Override
    public void Execute() {
        long id;

        System.out.print(ConsoleMessageConstants.ENTER_USER_ID_MESSAGE);
        String consoleInput = ConsoleHelper.readLine();

        try {
            id = Long.parseLong(consoleInput);
        } catch (NumberFormatException ex) {
            System.out.println(ConsoleMessageConstants.NUMBER_ERROR_MESSAGE);
            return;
        }

        User user = userService.getUser(id);

        if (user == null) {
            System.out.println(ConsoleMessageConstants.USER_NOT_FOUND_MASSAGE);
        } else {
            System.out.println(user);
        }
    }
}