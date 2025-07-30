package org.SortVariables;

import java.util.Arrays;

public class Main {

    public static void main(String[] args)
    {
        try {
            InputCommand inputCommand = new InputCommand(args);

            if (inputCommand.getIsHasErrors()) {
                sendErrorMessages(inputCommand.getErrorMessages());
                System.exit(1);
            }

            InputFile[] inputFilesList = getInputFilesList(inputCommand);

            OutputFile intOutputFile = new OutputFile("int", inputCommand, inputFilesList);
            OutputFile stringOutputFile = new OutputFile("string", inputCommand, inputFilesList);
            OutputFile floatOutputFile = new OutputFile("float", inputCommand, inputFilesList);

            intOutputFile.create(inputCommand.getIsOutRewrite());
            stringOutputFile.create(inputCommand.getIsOutRewrite());
            floatOutputFile.create(inputCommand.getIsOutRewrite());

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

    /**
     * Получить список объектов входных файлов
     *
     * @param inputCommand Объект входной команды
     * @return список объектов входных файлов
     */
    private static InputFile[] getInputFilesList(InputCommand inputCommand) {
        String[] listFilesPosition = inputCommand.getListFilesPosition();

        InputFile[] inputFilesList = new InputFile[0];

        for (int i = 1; i < listFilesPosition.length; i += 2) {
            InputFile newInputFile = new InputFile(listFilesPosition[i]);
            InputFile[] newInputFilesList = Arrays.copyOf(inputFilesList, inputFilesList.length + 1);
            newInputFilesList[newInputFilesList.length - 1] = newInputFile;
            inputFilesList = newInputFilesList;

            if (newInputFile.getIsHasErrors()) {
                sendErrorMessages(newInputFile.getErrorMessages());
            }
        }

        return inputFilesList;
    }

}