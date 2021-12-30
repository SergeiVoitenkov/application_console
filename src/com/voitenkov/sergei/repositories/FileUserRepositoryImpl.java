package com.voitenkov.sergei.repositories;

import com.voitenkov.sergei.entities.Role;
import com.voitenkov.sergei.entities.User;
import com.voitenkov.sergei.utilities.RegexExpressions;

import java.io.*;
import java.util.regex.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class FileUserRepositoryImpl implements UserRepository {
    @Override
    public User createUser(User user) {
        createFile();

        try (FileWriter fileWriter = new FileWriter(Constants.FILE_PATH, true)) {
            user.setId(IdGenerator.generate());

            String text = user.toString().concat("\n");
            fileWriter.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public User editUser(User user) {
        createFileCopy();

        try (BufferedReader reader = new BufferedReader(new FileReader(Constants.FILE_PATH))) {
            String line;

            while ((line = reader.readLine()) != null) {
                Pattern patternUserId = Pattern.compile(RegexExpressions.USER_ID_REGEX);
                Matcher matcherLineUser = patternUserId.matcher(line);

                while (matcherLineUser.find()) {
                    long userId = Long.parseLong(matcherLineUser.group("id"));
                    File file = new File(Constants.COPY_FILE_PATH);
                    BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.COPY_FILE_PATH, true));

                    if (!file.exists()) {
                        file.createNewFile();
                    }

                    if (user.getId() == userId) {
                        try (writer) {
                            writer.write(user.toString().concat("\n"));
                        } catch (
                                IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try (writer) {
                            writer.write(line.concat("\n"));
                        } catch (
                                IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        replaceFiles();
        return user;
    }

    @Override
    public User getUser(long id) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(Constants.FILE_PATH))) {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                Pattern userIdPattern = Pattern.compile(RegexExpressions.USER_ID_REGEX);
                Matcher userLineMatcher = userIdPattern.matcher(line);

                while (userLineMatcher.find()) {
                    long userId = Long.parseLong(userLineMatcher.group("id"));
                    if (id == userId) {
                        return parseUser(line);
                    }
                }
            }
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }

        return null;
    }

    @Override
    public void deleteUser(long id) {
        createFileCopy();

        try (BufferedReader reader = new BufferedReader(new FileReader(Constants.FILE_PATH))) {
            String line;

            while ((line = reader.readLine()) != null) {
                Pattern userIdPattern = Pattern.compile(RegexExpressions.USER_ID_REGEX);
                Matcher userLineMatcher = userIdPattern.matcher(line);

                while (userLineMatcher.find()) {
                    long userId = Long.parseLong(userLineMatcher.group("id"));
                    if (id != userId) {
                        File file = new File(Constants.COPY_FILE_PATH);

                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.COPY_FILE_PATH, true))) {
                            if (!file.exists()) {
                                file.createNewFile();
                            }
                            writer.write(line.concat("\n"));
                        } catch (
                                IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        replaceFiles();
    }

    @Override
    public List<User> getUsers() {
        var users = new ArrayList<User>();
        String line;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(Constants.FILE_PATH))) {
            while ((line = bufferedReader.readLine()) != null) {
                var user = parseUser(line);

                users.add(user);
            }

        } catch (IOException fileException) {
            fileException.printStackTrace();
        }

        return users;
    }

    private User parseUser(String line) {
        User user = new User();

        Pattern userIdPattern = Pattern.compile(RegexExpressions.USER_ID_REGEX);
        Matcher userLineMatcher = userIdPattern.matcher(line);

        while (userLineMatcher.find()) {
            user.setId(Long.parseLong(userLineMatcher.group("id")));
        }

        Pattern patternVariableName = Pattern.compile(RegexExpressions.USER_REGEX);
        Matcher matcherVariableName = patternVariableName.matcher(line);

        while (matcherVariableName.find()) {
            String variableName = matcherVariableName.group("propertyName");
            List<String> valuesList = Arrays.stream(matcherVariableName.group("value").split(", ")).toList();

            switch (variableName) {
                case "id":
                    for (String value : valuesList) {
                        user.setId(Long.parseLong(value));
                    }
                    break;
                case "firstName":
                    for (String value : valuesList) {
                        user.setFirstName(value);
                    }
                    break;
                case "lastName":
                    for (String value : valuesList) {
                        user.setLastName(value);
                    }
                    break;
                case "email":
                    for (String value : valuesList) {
                        user.setEmail(value);
                    }
                    break;
                case "roleList":
                    if (user.getRolesList() == null) {
                        user.setRolesList(new ArrayList<>());
                    }

                    for (String value : valuesList) {
                        user.getRolesList().add(Role.valueOf(value.toUpperCase(Locale.ENGLISH)));
                    }
                    break;
                case "phoneList":
                    if (user.getPhoneNumbersList() == null) {
                        user.setPhoneNumbersList(new ArrayList<>());
                    }
                    for (String value : valuesList) {
                        user.getPhoneNumbersList().add(value);
                    }
                    break;
            }
        }
        return user;
    }

    private void createFile() {
        File file;

        try {
            file = new File(Constants.FILE_DIR_PATH);

            if (file.exists()) {
                file = new File(Constants.FILE_PATH);
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createFileCopy() {
        File file;

        try {
            file = new File(Constants.COPY_FILE_DIR_PATH);

            if (file.exists()) {
                file = new File(Constants.COPY_FILE_PATH);
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void replaceFiles() {
        File originalUserFile = new File(Constants.FILE_PATH);
        File copiedUserFile = new File(Constants.COPY_FILE_PATH);
        boolean created = originalUserFile.isFile();
        if (created) {
            if (originalUserFile.delete()) {
                copiedUserFile.renameTo(new File(String.valueOf(originalUserFile)));
            }
        }
    }
}