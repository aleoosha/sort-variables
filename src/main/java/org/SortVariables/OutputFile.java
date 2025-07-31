package org.SortVariables;

import java.util.Arrays;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Класс для работы с данными и создания/редактирования результирующего файла
 */
public class OutputFile {

    /**
     * Название файла вывода данных для целых значений
     */
    final String INT_FILE_NAME = "integers.txt";

    /**
     * Название файла вывода данных для значений с плавающей точкой
     */
    final String FLOAT_FILE_NAME = "floats.txt";

    /**
     * Название файла вывода данных для стоковых значений
     */
    final String STRING_FILE_NAME = "strings.txt";

    /**
     * Директория для сохранения результирующего файла по умолчанию
     */
    final String OUTPUT_FILE_FOLDER = "/result-files";

    /**
     * Название файла
     */
    private String name;

    /**
     * Абсолютный путь до директории с результатами
     */
    private String path;

    /**
     * Добавочный путь от директории с результатами до результирующего файла
     */
    private String addedPath;

    /**
     * Файл пустой?
     */
    private boolean isEmpty;

    /**
     * Файл создан?
     */
    private boolean isCreated = false;

    /**
     * Путь до созданного файла
     */
    private String resultFilePath;

    /**
     * Данные для записи в файл
     */
    private String[] data = {};

    /**
     * Инициализация данных выходного файла
     *
     * @param type          Тип выходного файла "int", "float", "string"
     * @param inputCommand  Данные из входной команды
     * @param inputFileList Список с данными каждого входного файла
     */
    public OutputFile(String type, InputCommand inputCommand, InputFile[] inputFileList) {
        String name;

        switch (type) {
            case "int":
                name = inputCommand.getOutPrefix() + INT_FILE_NAME;
                break;
            case "float":
                name = inputCommand.getOutPrefix() + FLOAT_FILE_NAME;
                break;
            case "string":
                name = inputCommand.getOutPrefix() + STRING_FILE_NAME;
                break;
            default:
                name = inputCommand.getOutPrefix() + "undefined.txt";
        }

        this.setName(name);

        String currentDirectory = System.getProperty("user.dir");

        this.setPath(currentDirectory + this.OUTPUT_FILE_FOLDER);

        this.setAddedPath(inputCommand.getOutPath());

        for (InputFile inputFile : inputFileList) {
            String[] inputFileVariablesWithTypeList = inputFile.getVariablesWithTypeList();

            for (int i = 0; i < inputFileVariablesWithTypeList.length; i += 2) {
                if (inputFileVariablesWithTypeList[i].equals(type)) {
                    this.addDataLine(inputFileVariablesWithTypeList[i + 1]);
                }
            }
        }

        if (this.getData() == null) {
            this.setIsEmpty(true);
        }
    }

    /**
     * Создание выходного файла
     *
     * @param isRewrite нужно ли перезаписывать файл
     */
    public void create(boolean isRewrite) {
        if (this.getIsEmpty()) {
            return;
        }

        StringBuilder directoryPath = this.createResultDirectory();

        if (!this.getAddedPath().isEmpty()) {
            directoryPath = this.createAddedDirectories(directoryPath);
        }

        this.createResultFile(directoryPath, isRewrite);
    }

    /**
     * Создание директории для результатов, если она отсутствует
     */
    private StringBuilder createResultDirectory() {
        StringBuilder directoryPath = new StringBuilder(this.getPath());
        File directory = new File(directoryPath.toString());

        if (!directory.exists()) {
            if (!directory.mkdir()) {
                System.out.println("Не удалось создать директорию: " + directoryPath);
                System.out.println();
                System.exit(1);
            }
        }

        return directoryPath;
    }

    /**
     * Создание дополнительной директории по надобности
     */
    private StringBuilder createAddedDirectories(StringBuilder directoryPath) {
        String[] addedDirectoriesList = this.getAddedPath().split("/");

        for (int i = 1; i < addedDirectoriesList.length; i++) {
            directoryPath.append("/").append(addedDirectoriesList[i]);

            File addedDirectory = new File(String.valueOf(directoryPath));

            if (!addedDirectory.exists()) {
                if (!addedDirectory.mkdir()) {
                    System.out.println("Не удалось создать директорию: " + directoryPath);
                    System.out.println();
                    System.exit(1);
                }
            }
        }

        return directoryPath;
    }

    /**
     * Создание файла с результатом
     *
     * @param isRewrite Нужно ли перезаписывать файл?
     */
    private void createResultFile(StringBuilder directoryPath, boolean isRewrite) {
        File resultDirectory = new File(String.valueOf(directoryPath));

        if (resultDirectory.exists()) {

            File file = new File(resultDirectory, this.getName());

            if (!file.exists()) {

                try (FileWriter writer = new FileWriter(file)) {
                    for (String line : this.getData()) {
                        writer.write(line + System.lineSeparator());
                    }
                } catch (IOException error) {
                    System.out.println("Ошибка при работе с файлом: " + error.getMessage());
                    System.out.println();
                }

            } else if (isRewrite) {

                if (!file.delete()) {
                    System.out.println("Не удалось удалить file: " + file.toString());
                    System.out.println();
                    System.exit(1);
                }

                try (FileWriter writer = new FileWriter(file)) {
                    for (String line : this.getData()) {
                        writer.write(line + System.lineSeparator());
                    }
                } catch (IOException error) {
                    System.out.println("Ошибка при работе с файлом: " + error.getMessage());
                    System.out.println();
                }

            } else {

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file.toString(), true))) {
                    for (String line : this.getData()) {
                        writer.write(line);
                        writer.newLine();
                    }
                } catch (IOException error) {
                    System.err.println("Ошибка при записи в файл: " + error.getMessage());
                }

            }

            this.setIsCreated(true);
            this.setResultFilePath(file.toString());
        }
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    private void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

    private void setAddedPath(String addedPath) {
        this.addedPath = addedPath;
    }

    public String getAddedPath() {
        return this.addedPath;
    }

    private void setIsEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    public boolean getIsEmpty() {
        return this.isEmpty;
    }

    private void addDataLine(String line) {
        String[] newData = Arrays.copyOf(this.data, this.data.length + 1);
        newData[newData.length - 1] = line;
        this.data = newData;
    }

    public String[] getData() {
        return this.data;
    }

    public boolean getIsCreated() {
        return this.isCreated;
    }

    public void setIsCreated(boolean isCreated) {
        this.isCreated = isCreated;
    }

    public String getResultFilePath() {
        return this.resultFilePath;
    }

    public void setResultFilePath(String resultFilePath) {
        this.resultFilePath = resultFilePath;
    }
}