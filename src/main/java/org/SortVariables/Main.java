package org.SortVariables;
import java.util.Scanner;

public class Main {

    /** Название файла вывода данных для целых значений */
    final String INT_FILE_NAME = "integers.txt";

    /** Название файла вывода данных для значений с плавающей точкой */
    final String FLOAT_FILE_NAME = "floats.txt";

    /** Название файла вывода данных для стоковых значений */
    final String STRING_FILE_NAME = "strings.txt";

    public static void main(String[] args)
    {
        try {
            InputCommand inputCommand = new InputCommand(args);
        } catch (OutOfMemoryError error) {
            System.err.println("Ошибка нехватки памяти: " + error.getMessage());
            error.printStackTrace();
            System.exit(1);
        }
    }

}