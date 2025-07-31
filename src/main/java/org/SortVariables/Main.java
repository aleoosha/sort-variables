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
            OutputFile floatOutputFile = new OutputFile("float", inputCommand, inputFilesList);
            OutputFile stringOutputFile = new OutputFile("string", inputCommand, inputFilesList);

            intOutputFile.create(inputCommand.getIsOutRewrite());
            floatOutputFile.create(inputCommand.getIsOutRewrite());
            stringOutputFile.create(inputCommand.getIsOutRewrite());

            if (inputCommand.getIsBriefStat()) {
                new FileStatistic("brief", intOutputFile.getResultFilePath(), "int").calculate().print();
                new FileStatistic("brief", floatOutputFile.getResultFilePath(), "float").calculate().print();
                new FileStatistic("brief", stringOutputFile.getResultFilePath(), "string").calculate().print();
            } else if (inputCommand.getIsFullStat()) {
                new FileStatistic("full", intOutputFile.getResultFilePath(), "int").calculate().print();
                new FileStatistic("full", floatOutputFile.getResultFilePath(), "float").calculate().print();
                new FileStatistic("full", stringOutputFile.getResultFilePath(), "string").calculate().print();
            }

        } catch (OutOfMemoryError error) {
            System.err.println("Ошибка нехватки памяти: " + error.getMessage());
            System.out.println();
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
            System.err.println(errorMessage);
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