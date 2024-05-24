package org.sena.Database;


import java.io.*;
import java.net.URL;
import java.util.Scanner;

public class BorrowBookManager implements IBaseManager
{
    private static final String BORROW_BOOK = "/database/borrow_book.txt";
    private static final String BORROW_BOOK_TEMP = "/database/borrow_book_temp.txt";

    private String getBorrowBookFilePath() {
        URL resourceUrl = getClass().getResource(BORROW_BOOK);
        return resourceUrl.getPath();
    }

    private String getBorrowBookTempFilePath() {
        URL resourceUrl = getClass().getResource(BORROW_BOOK_TEMP);
        return resourceUrl.getPath();
    }

    @Override
    public void insert(String bookName, String authorName, String numberOfPage, String type, String userName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getBorrowBookFilePath(), true))) {
            writer.write(bookName + "," + authorName + "," + numberOfPage + "," + type + "," + userName);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete(String bookName) {
        File inputFile = new File(getBorrowBookFilePath());
        File tempFile = new File(getBorrowBookTempFilePath());

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

    public int borrowBookControl(String bookName) {
        try (Scanner scanner = new Scanner(new File(getBorrowBookFilePath()))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(",");
                if (fields[0].equalsIgnoreCase(bookName)) {
                    return 0;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }


    //Burada ödünç alınan kitap borrowbook tablosundan silinip books tablosuna ekleniyor
    public void returnBook(String bookName) {
        File inputFile = new File(getBorrowBookFilePath());
        File tempFile = new File(getBorrowBookTempFilePath());

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            BooksManager booksManager = new BooksManager();
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields[0].equalsIgnoreCase(bookName)) {
                    booksManager.insertBook(fields[0], fields[1], fields[2], fields[3]);
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

    public String userNameControl(String userName) {
        try (Scanner scanner = new Scanner(new File(getBorrowBookFilePath()))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(",");
                if (fields[4].equalsIgnoreCase(userName)) {
                    return fields[0]; // Return the book name
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "YOK";
    }

}