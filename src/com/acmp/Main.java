package com.acmp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.FileInputStream;

public class Main {

    private static int[][] matrix;
    private static int matrixHeight;
    private static int matrixWidth;

    public static void main(String[] args) {
        try {
            readAndCheckFile();
            calculateCheapestPrice();
            writeFile();
        } catch (TaskException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void calculateCheapestPrice() {
        for (int line = 0; line < matrixHeight; line++) {
            for (int column = 0; column < matrixWidth; column++) {
                if (line > 0 && column > 0) {
                    matrix[line][column] += Math.min(matrix[line - 1][column], matrix[line][column - 1]);
                } else if (column > 0) {
                    matrix[line][column] += matrix[line][column - 1];
                } else if (line > 0) {
                    matrix[line][column] += matrix[line - 1][column];
                }
            }
        }
    }

    private static void readAndCheckFile() throws IOException, TaskException {
        FileInputStream file = new FileInputStream("INPUT.txt");
        Scanner inputFile = new Scanner(file);
        matrixHeight = inputFile.nextInt();
        matrixWidth = inputFile.nextInt();
        checkInputFile();
        matrix = new int[matrixHeight][matrixWidth];
        for (int line = 0; line < matrixHeight; line++) {
            for (int column = 0; column < matrixWidth; column++) {
                matrix[line][column] = inputFile.nextInt();
            }
        }
    }

    private static void checkInputFile() throws TaskException {
        boolean wrongHeight = 1 > matrixHeight && 20 < matrixHeight;
        boolean wrongWidth = 1 > matrixWidth && 20 < matrixWidth;
        if (wrongHeight && wrongWidth) {
            throw new TaskException("Please, check INPUT.txt file for right matrix settings.");
        }
    }

    private static void writeFile() {
        File file = new File("OUTPUT.txt");
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(file);
            writer.print(matrix[matrixHeight - 1][matrixWidth - 1]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            writer.flush();
            writer.close();
        }
    }
}

class TaskException extends Exception {

    public TaskException(String message) {
        super(message);
    }

}
