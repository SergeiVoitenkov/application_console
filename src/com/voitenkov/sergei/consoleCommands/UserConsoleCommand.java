package com.voitenkov.sergei.consoleCommands;

import com.voitenkov.sergei.services.UserService;

public abstract class UserConsoleCommand implements IConsoleCommand {
    protected UserService userService;

    public UserConsoleCommand(UserService userService) {
        if (userService == null) {
            throw new NullPointerException("userService");
        }

        this.userService = userService;
    }
}