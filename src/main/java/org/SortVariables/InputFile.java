package org.SortVariables;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;
import java.math.BigInteger;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Класс для проверки и интерпретации входного файла
 */
public class InputFile {

    final String INPUT_FILES_FOLDER = "/input-files/";

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
        String filePath = currentDirectory + this.INPUT_FILES_FOLDER + fileName;

        this.checkCharsetFile(filePath);

        this.setFileValuesWithType(filePath);
    }

    /**
     * Создать список с переменными и их типами
     *
     * @param filePath Путь к файлу
     */
    private void setFileValuesWithType(String filePath) {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.isEmpty()) {
                    continue;
                }

                String type = this.checkValueType(line);

                this.addVariableWithType(type, line);
            }
        } catch (FileNotFoundException error) {
            this.setIsHasErrors(true);
            this.addMessageErrorMessageList("Не удалось найти файл: " + filePath);
            this.addMessageErrorMessageList("");
        }
    }

    /**
     * Проверить файл на UTF-8 кодировку
     *
     * @param filePath
     */
    private void checkCharsetFile(String filePath) {
        Path path = Paths.get(filePath);
        try{
            ByteBuffer buffer = ByteBuffer.wrap(Files.readAllBytes(path));
            Charset charset = StandardCharsets.UTF_8;
            CharsetDecoder decoder = charset.newDecoder();
            decoder.onMalformedInput(CodingErrorAction.REPORT);
            decoder.onUnmappableCharacter(CodingErrorAction.REPORT);
            try {
                decoder.decode(buffer.duplicate());
            } catch (Exception e) {
                this.setIsHasErrors(true);
                this.addMessageErrorMessageList("Проблема с кодировкой файла: " + filePath);
                this.addMessageErrorMessageList("");
            }
        } catch (IOException error) {
            System.err.println("Ошибка при работе с файлом: " + error.getMessage());
            System.out.println();
            System.exit(1);
        }
    }

    /**
     * Узнать тип переменной
     *
     * @param value Переменная
     * @return Тип
     */
    private String checkValueType(String value){
        boolean isInteger;
        boolean isFloat;
        String type;

        try {
            new BigInteger(value);
            isInteger = true;
        } catch (NumberFormatException error) {
            isInteger = false;
        }

        if (!isInteger) {
            try {
                Double.parseDouble(value);
                isFloat = true;
            } catch (NumberFormatException error) {
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

        return type;
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
        if (variablesWithTypeList.length == Integer.MAX_VALUE) {
            System.err.println("Слишком большое количество строк во входном файле");
            System.out.println();
            System.exit(1);
        }

        String[] newVariableWithTypeList = Arrays.copyOf(this.variablesWithTypeList, this.variablesWithTypeList.length + 2);
        newVariableWithTypeList[newVariableWithTypeList.length - 2] = variableType;
        newVariableWithTypeList[newVariableWithTypeList.length - 1] = variable;
        this.variablesWithTypeList = newVariableWithTypeList;
    }

    public String[] getVariablesWithTypeList() {
        return this.variablesWithTypeList;
    }
}
