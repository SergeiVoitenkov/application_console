package com.voitenkov.sergei;

import com.voitenkov.sergei.consoleCommands.*;
import com.voitenkov.sergei.utilities.ConsoleHelper;
import com.voitenkov.sergei.repositories.FileUserRepositoryImpl;
import com.voitenkov.sergei.services.UserServiceImpl;
import com.voitenkov.sergei.utilities.ConsoleMessageConstants;

public class ConsoleApplication {
    private final static String CREATE_USER_COMMAND = "CREATE_USER";
    private final static String EDIT_USER_COMMAND = "EDIT_USER";
    private final static String VIEW_USER_COMMAND = "VIEW_USER";
    private final static String VIEW_ALL_USERS_COMMAND = "VIEW_ALL_USERS";
    private final static String DELETE_USER_COMMAND = "DELETE_USER";
    private final static String EXIT_COMMAND = "EXIT";

    public static void main(String[] args) {
        var continueFlag = true;
        var userRepository = new FileUserRepositoryImpl();
        var userService = new UserServiceImpl(userRepository);

        while (continueFlag) {
            System.out.print("Чтобы создать пользователя, введите команду \"" + CREATE_USER_COMMAND + "\"\n" +
                    "Чтобы редактировать пользователя, введите команду \"" + EDIT_USER_COMMAND + "\"\n" +
                    "Чтобы просмотреть пользователя, введите команду \"" + VIEW_USER_COMMAND + "\"\n" +
                    "Чтобы просмотреть всех пользователей, введите команду \"" + VIEW_ALL_USERS_COMMAND + "\"\n" +
                    "Чтобы удалить пользователя, введите команду \"" + DELETE_USER_COMMAND + "\"\n" +
                    "Чтобы завершить работу, введите команду \"" + EXIT_COMMAND + "\"\n" +
                    "Введите команду: ");

            String command = ConsoleHelper.readLine();

            switch (command.toUpperCase()) {
                case CREATE_USER_COMMAND: {
                    new CreateUserCommand(userService).Execute();
                    break;
                }
                case EDIT_USER_COMMAND: {
                    new EditUserCommand(userService).Execute();
                    break;
                }
                case VIEW_USER_COMMAND: {
                    new GetUserCommand(userService).Execute();
                    break;
                }
                case VIEW_ALL_USERS_COMMAND: {
                    new GetAllUsersCommand(userService).Execute();
                    break;
                }
                case DELETE_USER_COMMAND: {
                    new DeleteUserCommand(userService).Execute();
                    break;
                }
                case EXIT_COMMAND: {
                    continueFlag = false;
                    break;
                }
                default: {
                    System.out.println(ConsoleMessageConstants.ANSWER_ERROR_MESSAGE);
                }
            }
        }
    }
}