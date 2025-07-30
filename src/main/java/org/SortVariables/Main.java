package org.SortVariables;

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

            if (inputCommand.getIsHasErrors()) {
                sendErrorMessages(inputCommand.getErrorMessages());
                System.exit(1);
            }

            String[] listFilesPosition = inputCommand.getListFilesPosition();

            for (int i = 1; i < listFilesPosition.length; i += 2) {
                InputFile newInputFile = new InputFile(listFilesPosition[i]);

                if (newInputFile.getIsHasErrors()) {
                    sendErrorMessages(newInputFile.getErrorMessages());
                }
            }

        } catch (OutOfMemoryError error) {
            System.err.println("Ошибка нехватки памяти: " + error.getMessage());
            System.exit(1);
        }
    }

    /**
     * Вывести сообщения об ошибках
     *
     * @param errorMessages Массив строк с ошибками разделенными пустыми строками
     */
    private static void sendErrorMessages(String[] errorMessages) {
        for (String errorMessage : errorMessages) {
            System.out.println(errorMessage);
        }
    }

}