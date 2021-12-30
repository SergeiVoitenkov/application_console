package com.voitenkov.sergei.utilities;

public final class RegexExpressions {
    public static final String USER_REGEX = "(?<propertyName>[^{}\\s]*)=['\\[](?<value>.+?)['\\]]";
    public static final String USER_ID_REGEX = "id=(?<id>[0-9]+)";
    public static final String NUMBER_REGEX = "^[0-9]+$";
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9]+@[a-zA-Z]+\\.[a-zA-Z]+$";
    public static final String PHONE_NUMBER_REGEX = "^375[0-9]{9}$";

    private RegexExpressions() {
    }
}