package org.sena.Database;

public class User {
    private static final String SEPERATOR = ",";
    private String name;
    private String surname;
    private String userName;
    private String password;

    public User(String name, String surname, String userName, String password)
    {
        this.name = name;
        this.surname = surname;
        this.userName = userName;
        this.password = password;
    }

    @Override
    public String toString() {
        return name + SEPERATOR + surname + SEPERATOR + userName + SEPERATOR + password;
    }
}