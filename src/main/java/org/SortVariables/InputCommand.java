package org.SortVariables;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Класс для интерпретации и проверки команды
 */
public class InputCommand {

    /**
     * Позиции и наименования файлов из аргументов командной строки
     */
    private String[] listFilesPosition = {};

    /**
     * Список сообщений с ошибками
     */
    private String[] errorMessages = {};

    /**
     * Есть ли ошибки в веденной команде
     */
    private boolean isHasErrors = false;

    /**
     * Файлы с результатами должны быть перезаписаны?
     */
    private boolean isOutRewrite = true;

    /**
     * Нужно ли вывести краткую статистику?
     */
    private boolean isBriefStat = false;

    /**
     * Нужно ли вывести полную статистику?
     */
    private boolean isFullStat = false;

    /**
     * Префикс в названии для результирующих файлов
     */
    private String outPrefix;

    /**
     * Регулярное выражение для проверки префикса результирующих файлов
     */
    final String outPrefixRegex = "^[^\\x00-\\x1F\\\\\\/:\\*\\?\"<>\\|]{1,50}$";

    /**
     * Относительный путь до результирующих файлов от директории с результатами по умолчанию
     */
    private String outPath;

    /**
     * Регулярное выражение для проверки пути до результирующих файлов
     */
    final String outPathRegex = "^\\/(?:[^<>:\"\\/\\\\|?*\\x00-\\x1F]{1,100}\\/?)+$";

    /**
     * Позиция последнего флага из командной строки
     */
    private int lastFlagIndex = -1;

    /**
     * Регулярное выражение для проверки названия txt файла
     */
    final String txtFilePattern = "^[^\\\\\\/:*?\"<>|]+\\.txt$";

    /**
     * Инициализация объекта входной команды и проверка входных данных
     *
     * @param args Аргументы из командной строки
     */
    public InputCommand(String[] args) {
        this.parseInputArgs(args);
        this.checkParameters();

        if (this.errorMessages.length > 0) {
            for (int i = 0; i < this.errorMessages.length; i += 1) {
                System.out.println(this.errorMessages[i]);
            }

            System.exit(1);
        }

        System.out.println(Arrays.toString(this.listFilesPosition));
    }

    /**
     * Установление параметров из командной строки в свойства класса
     *
     * @param args Аргументы из командной строки
     */
    private void parseInputArgs(String[] args) {

        boolean nextElementContinue = false;

        for (int i = 0; i < args.length; i++) {
            if (nextElementContinue) {
                nextElementContinue = false;
                continue;
            }

            String element = args[i];

            switch (element) {
                case "-s":
                    this.setLastFlagIndex(i);
                    this.setIsBriefStat(true);
                    break;
                case "-f":
                    this.setLastFlagIndex(i);
                    this.setIsFullStat(true);
                    break;
                case "-a":
                    this.setLastFlagIndex(i);
                    this.setIsOutRewrite(false);
                    break;
                case "-o":
                    this.setLastFlagIndex(i);
                    nextElementContinue = true;
                    this.setOutPath(args[i + 1]);
                    break;
                case "-p":
                    this.setLastFlagIndex(i);
                    nextElementContinue = true;
                    this.setOutPrefix(args[i + 1]);
                    break;
                default:
                    this.addFileToFilePositionList(String.valueOf(i), element);
            }
        }
    }

    /**
     * Проверка значений свойств класса и формирование сообщения пользователю при наличии ошибок ввода команды
     */
    private void checkParameters() {
        this.checkSimultaneousStatisticUse();
        this.checkOutPath();
        this.checkOutPrefix();
        this.checkInputFileExists();
        this.checkFileNamePosition();
        this.checkFileTypes();
        this.checkDuplicateFiles();
    }

    /**
     * Проверить на одновременное использование флагов краткой и подробной статистики
     */
    private void checkSimultaneousStatisticUse() {
        if (!this.isBriefStat || !this.isFullStat) {
            return;
        }

        this.isHasErrors = true;
        this.addMessageErrorMessageList("Разрешено одновременное использование только одного из флагов:");
        this.addMessageErrorMessageList("-s: для получения краткой статистики;");
        this.addMessageErrorMessageList("-f: для получения полной статистики.");
        this.addMessageErrorMessageList("");

    }

    /**
     * Проверить на корректность указанный путь для создания результирующих файлов
     */
    private void checkOutPath() {
        Pattern outPathPattern = Pattern.compile(this.outPathRegex);
        Matcher outPathmatcher = outPathPattern.matcher(this.outPrefix);

        if ((this.outPath == null) || outPathmatcher.matches()) {
            return;
        }

        this.isHasErrors = true;
        this.addMessageErrorMessageList("Ошибка в пути для создания результирующих файлов (после флага -o).");
        this.addMessageErrorMessageList("Возможные причины:");
        this.addMessageErrorMessageList("Запрещенные символы: <, >, :, \", \\, |, ?, *;");
        this.addMessageErrorMessageList("Пустой путь - / или несколько / подряд;");
        this.addMessageErrorMessageList("Количество символов больше 100.");
        this.addMessageErrorMessageList("");

    }

    /**
     * Проверить на корректность указанный префикс для результирующих файлов
     */
    private void checkOutPrefix() {
        Pattern outPrefixPattern = Pattern.compile(this.outPrefixRegex);
        Matcher outPrefixMatcher = outPrefixPattern.matcher(this.outPrefix);

        if ((this.outPrefix == null) || outPrefixMatcher.matches()) {
            return;
        }

        this.isHasErrors = true;
        this.addMessageErrorMessageList("Ошибка в префиксе для создания результирующих файлов (после флага -p).");
        this.addMessageErrorMessageList("Возможные причины:");
        this.addMessageErrorMessageList("Запрещенные символы: <, >, :, \", /, \\, |, ?, *;");
        this.addMessageErrorMessageList("Количество символов больше 50.");
        this.addMessageErrorMessageList("");
    }

    /**
     * Проверить на наличие название вводных текстовых файлов
     */
    private void checkInputFileExists() {
        if (this.listFilesPosition.length != 0) {
            return;
        }

        this.isHasErrors = true;
        this.addMessageErrorMessageList("Укажите названия текстовых файлов, которые нужно обработать.");
        this.addMessageErrorMessageList("");

    }

    /**
     * Проверить, на каких позициях находятся имена входных файлов в командной строке
     */
    private void checkFileNamePosition() {
        for (int i = 0; i < this.listFilesPosition.length; i += 2) {

            if (Integer.parseInt(this.listFilesPosition[i]) > this.getLastFlagIndex()) {
                continue;
            }

            this.isHasErrors = true;
            this.addMessageErrorMessageList("Указанное название файла: " + this.listFilesPosition[i + 1] + " должно находиться после использования флагов и их аргументов.");
            this.addMessageErrorMessageList("");

        }
    }

    /**
     * Проверить тип входных файлов
     */
    private void checkFileTypes() {
        Pattern txtFilePattern = Pattern.compile(this.txtFilePattern);

        for (int i = 1; i < this.listFilesPosition.length; i += 2) {
            Matcher txtFileMatcher = txtFilePattern.matcher(this.listFilesPosition[i]);

            if (txtFileMatcher.matches()) {
                continue;
            }

            this.isHasErrors = true;
            this.addMessageErrorMessageList("Указанный файл: " + this.listFilesPosition[i] + " не является текстовым документом.");
            this.addMessageErrorMessageList("");

        }
    }

    /**
     * Проверить есть ли дублирующиеся названия в файлах
     */
    private void checkDuplicateFiles() {
        Pattern txtFilePattern = Pattern.compile(this.txtFilePattern);

        for (int i = 1; i < this.listFilesPosition.length; i += 2) {
            Matcher txtFileMatcher = txtFilePattern.matcher(this.listFilesPosition[i]);

            if (txtFileMatcher.matches()) {
                continue;
            }

            this.isHasErrors = true;
            this.addMessageErrorMessageList("Указанный файл: " + this.listFilesPosition[i] + " не является текстовым документом.");
            this.addMessageErrorMessageList("");

        }
    }


    public boolean isOutRewrite() {
        return this.isOutRewrite;
    }

    public void setIsOutRewrite(boolean outRewrite) {
        this.isOutRewrite = outRewrite;
    }

    public boolean isBriefStat() {
        return this.isBriefStat;
    }

    public void setIsBriefStat(boolean isBriefStat) {
        this.isBriefStat = isBriefStat;
    }

    public boolean isFullStat() {
        return this.isFullStat;
    }

    public void setIsFullStat(boolean isFullStat) {
        this.isFullStat = isFullStat;
    }

    public String outPrefix() {
        return this.outPrefix;
    }

    public void setOutPrefix(String outPrefix) {
        this.outPrefix = outPrefix;
    }

    public String outPath() {
        return this.outPath;
    }

    public void setOutPath(String outPath) {
        this.outPath = outPath;
    }

    public String[] listFilesPosition() {
        return this.listFilesPosition;
    }

    /**
     * Формирование списка с позициями и наименованиями файлов
     *
     * @param key      Позиция элемента в массиве аргументов командной строки
     * @param fileName Наименование файла
     * @example [4, in1.txt, 5, in2.txt]
     */
    public void addFileToFilePositionList(String key, String fileName) {
        String[] newListFilesPosition = Arrays.copyOf(this.listFilesPosition, this.listFilesPosition.length + 2);
        newListFilesPosition[newListFilesPosition.length - 2] = key;
        newListFilesPosition[newListFilesPosition.length - 1] = fileName;
        this.listFilesPosition = newListFilesPosition;
    }

    public int getLastFlagIndex() {
        return this.lastFlagIndex;
    }

    public void setLastFlagIndex(int lastFlagIndex) {
        this.lastFlagIndex = lastFlagIndex;
    }

    public String[] errorMessages() {
        return this.errorMessages;
    }

    /**
     * Формирование списка с сообщениями об ошибках ввода
     *
     * @param errorMessage Сообщение об ошибке
     */
    public void addMessageErrorMessageList(String errorMessage) {
        String[] newErrorMessageList = Arrays.copyOf(this.errorMessages, this.errorMessages.length + 1);
        newErrorMessageList[newErrorMessageList.length - 1] = errorMessage;
        this.errorMessages = newErrorMessageList;
    }

    public boolean isHasErrors() {
        return this.isHasErrors;
    }

    public void setHasErrors(boolean isHasErrors) {
        this.isHasErrors = isHasErrors;
    }
}
