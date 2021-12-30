package com.voitenkov.sergei.services;

import java.util.HashMap;

public class EntityValidationException extends Exception {
    private final String entityName;
    private final HashMap<String, String> errors;

    public EntityValidationException(String entityName, HashMap<String, String> errors) {
        this.entityName = entityName;
        this.errors = errors;
    }

    public String getEntityName() {
        return entityName;
    }

    public HashMap<String, String> getErrors() {
        return errors;
    }
}