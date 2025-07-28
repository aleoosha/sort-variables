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
        InputCommand inputCommand = new InputCommand(args);
    }

}