package com.voitenkov.sergei.services;

import com.voitenkov.sergei.entities.User;
import com.voitenkov.sergei.repositories.UserRepository;
import com.voitenkov.sergei.validators.ValidationResult;
import com.voitenkov.sergei.validators.ValidatorCode;
import com.voitenkov.sergei.validators.ValidatorFactory;

import java.util.HashMap;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) throws EntityValidationException {
        if (user == null) {
            throw new NullPointerException();
        }

        var errors = validateUser(user);
        if (!errors.isEmpty()) {
            throw new EntityValidationException("User", errors);
        }

        return userRepository.createUser(user);
    }

    @Override
    public User editUser(User user) throws EntityValidationException {
        if (user == null) {
            throw new NullPointerException();
        }

        var errors = validateUser(user);
        if (!errors.isEmpty()) {
            throw new EntityValidationException("User", errors);
        }

        return userRepository.editUser(user);
    }

    @Override
    public User getUser(long id) {
        return userRepository.getUser(id);
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteUser(id);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    private HashMap<String, String> validateUser(User user) {
        var emptyTextValidator = ValidatorFactory.getValidator(ValidatorCode.EMPTY_TEXT);
        var emailValidator = ValidatorFactory.getValidator(ValidatorCode.EMAIL);
        var phoneValidator = ValidatorFactory.getValidator(ValidatorCode.PHONE);
        var roleValidator = ValidatorFactory.getValidator(ValidatorCode.ROLE);
        var rolesValidator = ValidatorFactory.getValidator(ValidatorCode.ROLE_LIST);

        ValidationResult result;
        HashMap<String, String> validationResults = new HashMap<>();

        result = emptyTextValidator.execute(user.getFirstName());
        if (!result.isValid()) {
            validationResults.put("FirstName", result.getErrorMessage());
        }

        result = emptyTextValidator.execute(user.getLastName());
        if (!result.isValid()) {
            validationResults.put("LastName", result.getErrorMessage());
        }

        result = emailValidator.execute(user.getEmail());
        if (!result.isValid()) {
            validationResults.put("Email", result.getErrorMessage());
        }

        var phoneList = user.getPhoneNumbersList();
        for (var index = 0; index < phoneList.size(); index++) {
            result = phoneValidator.execute(phoneList.get(index));
            if (!result.isValid()) {
                validationResults.put("Phone #" + index, result.getErrorMessage());
            }
        }

        var roleList = user.getRolesList();
        for (var index = 0; index < roleList.size(); index++) {
            result = roleValidator.execute(roleList.get(index).toString());
            if (!result.isValid()) {
                validationResults.put("Role " + index, result.getErrorMessage());
            }
        }

        var rolesList = user.getRolesList();
        result = rolesValidator.execute(rolesList);
        if (!result.isValid()) {
            validationResults.put("Role ", result.getErrorMessage());
        }

        return validationResults;
    }
}