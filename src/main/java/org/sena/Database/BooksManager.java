package org.sena.Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.URL;
import java.util.Scanner;

public class BooksManager
{
    private static final String BOOKS = "/database/books.txt";
    private static final String BOOKS_TEMP = "/database/books_temp.txt";

    public ObservableList<Books> getDataBooks() {
        ObservableList<Books> list = FXCollections.observableArrayList();
        try (Scanner scanner = new Scanner(new File(getBooksFilePath()))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(",");
                list.add(new Books(fields[0], fields[1], fields[2], fields[3]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insertBook(String bookName, String authorName, String numberOfPage, String type) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getBooksFilePath(), true))) {
            writer.write(bookName + "," + authorName + "," + numberOfPage + "," + type);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteBook(String bookName) {
        File inputFile = new File(getBooksFilePath());
        File tempFile = new File(getBooksTempFilePath());

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (!fields[0].equalsIgnoreCase(bookName)) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!inputFile.delete()) {
            System.out.println("Could not delete file");
            return;
        }

        if (!tempFile.renameTo(inputFile)) {
            System.out.println("Could not rename file");
        }
    }

    public int bookControl(String borrowBookName) {
        try (Scanner scanner = new Scanner(new File(getBooksFilePath()))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(",");
                if (fields[0].equalsIgnoreCase(borrowBookName)) {
                    return 0;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }


    public void booksData(String bookName, String userName) {
        File inputFile = new File(getBooksFilePath());
        File tempFile = new File(getBooksTempFilePath());

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            BorrowBookManager borrowBookManager = new BorrowBookManager();
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields[0].equalsIgnoreCase(bookName)) {
                    borrowBookManager.insert(fields[0], fields[1], fields[2], fields[3], userName);
                } else {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!inputFile.delete()) {
            System.out.println("Could not delete file");
            return;
        }

        if (!tempFile.renameTo(inputFile)) {
            System.out.println("Could not rename file");
        }
    }

    public int numberOfBook() {
        int total = 0;
        try (Scanner scanner = new Scanner(new File(getBooksFilePath()))) {
            while (scanner.hasNextLine()) {
                scanner.nextLine();
                total++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return total;
    }

    private String getBooksFilePath() {
        URL resourceUrl = getClass().getResource(BOOKS);
        return resourceUrl.getPath();
    }

    private String getBooksTempFilePath() {
        URL resourceUrl = getClass().getResource(BOOKS_TEMP);
        return resourceUrl.getPath();
    }

}