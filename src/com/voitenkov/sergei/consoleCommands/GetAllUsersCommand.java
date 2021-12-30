package com.voitenkov.sergei.consoleCommands;

import com.voitenkov.sergei.entities.*;
import com.voitenkov.sergei.services.*;

import java.util.List;

public class GetAllUsersCommand extends UserConsoleCommand {
    public GetAllUsersCommand(UserService userService) {
        super(userService);
    }

    @Override
    public void Execute() {
        List<User> users = userService.getUsers();

        for (User user : users) {
            System.out.println(user);
        }
    }
}