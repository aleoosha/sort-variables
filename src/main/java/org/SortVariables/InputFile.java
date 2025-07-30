package org.SortVariables;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.math.BigInteger;

/**
 * Класс для проверки и интерпретации входного файла
 */
public class InputFile {

    final String inputFileFolder = "/inputFiles/";

    /**
     * Список сообщений с ошибками
     */
    private String[] errorMessages = {};

    /**
     * Массив со значениями переменных и их типами
     *
     * @example ["int", "12125123", "float" "124155.523", "string", "Тестовое задание"]
     */
    private String[] variablesWithTypeList = {};

    /**
     * Есть ли ошибки при обработке файлов
     */
    private boolean isHasErrors = false;

    /**
     * Инициализация данных входного файла и их проверка
     *
     * @param fileName Название файла
     */
    public InputFile(String fileName) {
        String currentDirectory = System.getProperty("user.dir");
        String filePath = currentDirectory + this.inputFileFolder + fileName;

        try (Scanner scanner = new Scanner(new File(filePath))) {
            this.setFileValuesWithType(scanner);
        } catch (FileNotFoundException error) {
            this.setIsHasErrors(true);
            this.addMessageErrorMessageList("Не удалось найти файл - " + fileName);
            this.addMessageErrorMessageList("Полный путь до файла: " + filePath);
            this.addMessageErrorMessageList("");
        }
    }

    private void setFileValuesWithType(Scanner scanner) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            boolean isInteger;
            boolean isFloat;
            String type;

            try {
                new BigInteger(line);
                isInteger = true;
            } catch (NumberFormatException e) {
                isInteger = false;
            }

            if (!isInteger) {
                try {
                    Double.parseDouble(line);
                    isFloat = true;
                } catch (NumberFormatException e) {
                    isFloat = false;
                }
            } else {
                isFloat = false;
            }

            if (isInteger) {
                type = "int";
            } else if (isFloat) {
                type = "float";
            } else {
                type = "string";
            }

            this.addVariableWithType(type, line);
        }
    }

    public String[] getErrorMessages() {
        return this.errorMessages;
    }

    /**
     * Формирование списка с сообщениями об ошибках при обработке входных файлов
     *
     * @param errorMessage Сообщение об ошибке
     */
    public void addMessageErrorMessageList(String errorMessage) {
        String[] newErrorMessageList = Arrays.copyOf(this.errorMessages, this.errorMessages.length + 1);
        newErrorMessageList[newErrorMessageList.length - 1] = errorMessage;
        this.errorMessages = newErrorMessageList;
    }

    public boolean getIsHasErrors() {
        return this.isHasErrors;
    }

    public void setIsHasErrors(boolean isHasErrors) {
        this.isHasErrors = isHasErrors;
    }

    /**
     * Формирование списка с переменными и их типом
     *
     * @param variableType Значение типа переменной, например "string", "float", "int"
     * @param variable Значение стоки из файла
     */
    public void addVariableWithType(String variableType, String variable) {
        String[] newVariableWithTypeList = Arrays.copyOf(this.variablesWithTypeList, this.variablesWithTypeList.length + 2);
        newVariableWithTypeList[newVariableWithTypeList.length - 2] = variableType;
        newVariableWithTypeList[newVariableWithTypeList.length - 1] = variable;
        this.variablesWithTypeList = newVariableWithTypeList;
    }

    public String[] getVariablesWithTypeList() {
        return this.variablesWithTypeList;
    }
}
