package org.sena.Database;


import java.io.*;
import java.net.URL;
import java.util.Scanner;

public class UsersManager {
    private static final String USERS = "/database/users.txt";
    private static final String SEPERATOR = ",";
    public void insert(String name, String surname, String userName, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getUsersFilePath(), true))) {
            writer.write(name + SEPERATOR + surname + SEPERATOR + userName + SEPERATOR + password);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getUsersFilePath() {
        URL resourceUrl = getClass().getResource(USERS);
        return resourceUrl.getPath();
    }

    public int loginControl(String userName, String password) {
        try (Scanner scanner = new Scanner(new File(getUsersFilePath()))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(SEPERATOR);
                if (fields[2].equals(userName) && fields[3].equals(password)) {
                    return 0; // Login successful
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return 1; // Login failed
    }

    public int usernameControl(String userName) {
        try (Scanner scanner = new Scanner(new File(getUsersFilePath()))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(SEPERATOR);
                if (fields[2].equals(userName)) {
                    return 0; // Username exists
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return 1; // Username does not exist
    }

    public String nameSurname(String userName) {
        try (Scanner scanner = new Scanner(new File(getUsersFilePath()))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(SEPERATOR);
                if (fields[2].equals(userName)) {
                    return fields[0] + " " + fields[1]; // Return name and surname
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "Hata"; // Error
    }


    public int numberOfUser() {
        int total = 0;
        try (Scanner scanner = new Scanner(new File(getUsersFilePath()))) {
            while (scanner.hasNextLine()) {
                scanner.nextLine();
                total++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return total;
    }

}