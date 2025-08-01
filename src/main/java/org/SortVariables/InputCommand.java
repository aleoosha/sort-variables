package org.SortVariables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
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
     * Необработанные флаги из командной строки
     */
    private String[] undefinedFlagList = {};

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
    private String outPrefix = "";

    /**
     * Регулярное выражение для проверки префикса результирующих файлов
     */
    final String OUT_PREFIX_REGEX = "^[^\\x00-\\x1F\\\\\\/:\\*\\?\"<>\\|]{1,50}$";

    /**
     * Относительный путь до результирующих файлов от директории с результатами по умолчанию
     */
    private String outPath = "";

    /**
     * Использовался ли флаг добавочного пути в командной строке
     */
    private boolean isOutPathFlagExists = false;

    /**
     * Использовался ли флаг добавочного префикса в командной строке
     */
    private boolean isOutPrefixFlagExists = false;

    /**
     * Регулярное выражение для проверки пути до результирующих файлов
     */
    final String OUT_PATH_REGEX = "^\\/(?:[^<>:\"\\/\\\\|?*\\x00-\\x1F]{1,100}\\/?)+$";

    /**
     * Регулярное выражение для проверки на флаг из командной строки
     */
    final String FLAG_REGEX = "--?\\w+";

    /**
     * Позиция последнего флага из командной строки
     */
    private int lastFlagIndex = -1;

    /**
     * Регулярное выражение для проверки названия txt файла
     */
    final String TXT_FILE_REGEX = "^[^\\\\\\/:*?\"<>|]+\\.txt$";

    /**
     * Инициализация объекта входной команды и проверка входных данных
     *
     * @param args Аргументы из командной строки
     */
    public InputCommand(String[] args) {
        this.parseInputArgs(args);
        this.checkParameters();
    }

    /**
     * Установление параметров из командной строки в свойства класса
     *
     * @param args Аргументы из командной строки
     */
    private void parseInputArgs(String[] args) {
        List<String> argsList = new ArrayList<>(Arrays.asList(args));
        ListIterator<String> iterator = argsList.listIterator();

        boolean nextElementContinue = false;

        while (iterator.hasNext()) {
            String arg = iterator.next();

            if (nextElementContinue) {
                nextElementContinue = false;
                continue;
            }

            int index = iterator.nextIndex();

            switch (arg) {
                case "-s":
                    this.setLastFlagIndex(index);
                    this.setIsBriefStat(true);
                    break;
                case "-f":
                    this.setLastFlagIndex(index);
                    this.setIsFullStat(true);
                    break;
                case "-a":
                    this.setLastFlagIndex(index);
                    this.setIsOutRewrite(false);
                    break;
                case "-o":
                    this.setLastFlagIndex(index);
                    this.setIsOutPathFlagExists(true);
                    nextElementContinue = true;
                    if (iterator.hasNext()) {
                        this.setOutPath(iterator.next());
                        iterator.previous();
                    }
                    break;
                case "-p":
                    this.setLastFlagIndex(index);
                    this.setIsOutPrefixFlagExists(true);
                    nextElementContinue = true;
                    if (iterator.hasNext()) {
                        this.setOutPrefix(iterator.next());
                        iterator.previous();
                    }
                    break;
                default:
                    this.filterOtherArguments(String.valueOf(index), arg);
            }
        }
    }

    /**
     * Проверка значений свойств класса и формирование сообщения пользователю при наличии ошибок ввода команды
     */
    private void checkParameters() {
        this.checkSimultaneousStatisticUse();
        this.checkNoStatisticUse();
        this.checkOutPath();
        this.checkOutPrefix();
        this.checkInputFileExists();
        this.checkFileNamePosition();
        this.checkFileTypes();
        this.checkDuplicateFiles();
        this.checkUndefinedFlags();
    }

    /**
     * Проверить использовались ли необрабатываемые флаги
     */
    private void checkUndefinedFlags() {
        if (this.undefinedFlagList.length == 0) {
            return;
        }

        this.setIsHasErrors(true);
        for (String flag : this.undefinedFlagList) {
            this.addMessageErrorMessageList("Обнаружен неизвестный флаг: " + flag);
        }
        this.addMessageErrorMessageList("Разрешено использование следующих флагов:");
        this.addMessageErrorMessageList("-p: Задать префикс выходному файлу;");
        this.addMessageErrorMessageList("-o: Задать путь выходному файлу относительно результирующей директории;");
        this.addMessageErrorMessageList("-s: Выбор краткой статистики;");
        this.addMessageErrorMessageList("-f: Выбор полной статистики;");
        this.addMessageErrorMessageList("-a: Режим добавления данных в существующие файлы.");
        this.addMessageErrorMessageList("");
    }

    /**
     * Проверить на одновременное использование флагов краткой и подробной статистики
     */
    private void checkSimultaneousStatisticUse() {
        if (!this.isBriefStat || !this.isFullStat) {
            return;
        }

        this.setIsHasErrors(true);
        this.addMessageErrorMessageList("Разрешено одновременное использование только одного из флагов:");
        this.addMessageErrorMessageList("-s: для получения краткой статистики;");
        this.addMessageErrorMessageList("-f: для получения полной статистики.");
        this.addMessageErrorMessageList("");

    }

    /**
     * Проверить на отсутствие использования флагов статистики
     */
    private void checkNoStatisticUse() {
        if (this.isBriefStat || this.isFullStat) {
            return;
        }

        this.setIsHasErrors(true);
        this.addMessageErrorMessageList("Пропущен выбор статистики.");
        this.addMessageErrorMessageList("Выберите какую статистику хотите получить:");
        this.addMessageErrorMessageList("-s: для получения краткой статистики;");
        this.addMessageErrorMessageList("-f: для получения полной статистики.");
        this.addMessageErrorMessageList("");

    }

    /**
     * Проверить на корректность указанный путь для создания результирующих файлов
     */
    private void checkOutPath() {
        String outPath = this.getOutPath();

        if (this.getIsOutPathFlagExists() && outPath.isEmpty()) {
            this.setIsHasErrors(true);
            this.addMessageErrorMessageList("Ошибка: не указан путь для создания результирующих файлов (после флага -o).");
            this.addMessageErrorMessageList("");
        }

        if (outPath.isEmpty()) {
            return;
        }

        Pattern outPathPattern = Pattern.compile(this.OUT_PATH_REGEX);
        Matcher outPathMatcher = outPathPattern.matcher(outPath);

        if (outPathMatcher.matches()) {
            return;
        }

        this.setIsHasErrors(true);
        this.addMessageErrorMessageList("Ошибка в пути для создания результирующих файлов (после флага -o).");
        this.addMessageErrorMessageList("Возможные причины:");
        this.addMessageErrorMessageList("Запрещенные символы: <, >, :, \", \\, |, ?, *;");
        this.addMessageErrorMessageList("Пустой путь \"/\" или несколько \"/\" подряд;");
        this.addMessageErrorMessageList("Путь начинается не с символа \"/\"");
        this.addMessageErrorMessageList("Количество символов больше 100.");
        this.addMessageErrorMessageList("");

    }

    /**
     * Проверить на корректность указанный префикс для результирующих файлов
     */
    private void checkOutPrefix() {
        String outPrefix = this.getOutPrefix();

        if (this.getIsOutPrefixFlagExists() && outPrefix.isEmpty()) {
            this.setIsHasErrors(true);
            this.addMessageErrorMessageList("Ошибка: не указан путь для создания результирующих файлов (после флага -o).");
            this.addMessageErrorMessageList("");
        }

        if (outPrefix.isEmpty()) {
            return;
        }

        Pattern outPrefixPattern = Pattern.compile(this.OUT_PREFIX_REGEX);
        Matcher outPrefixMatcher = outPrefixPattern.matcher(outPrefix);

        if (outPrefixMatcher.matches()) {
            return;
        }

        this.setIsHasErrors(true);
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
        if (this.getListFilesPosition().length != 0) {
            return;
        }

        this.setIsHasErrors(true);
        this.addMessageErrorMessageList("Укажите названия текстовых файлов, которые нужно обработать.");
        this.addMessageErrorMessageList("");

    }

    /**
     * Проверить, на каких позициях находятся имена входных файлов в командной строке
     */
    private void checkFileNamePosition() {

        String[] listFilesPosition = this.getListFilesPosition();

        for (int i = 0; i < listFilesPosition.length; i += 2) {

            if (Integer.parseInt(listFilesPosition[i]) > this.getLastFlagIndex()) {
                continue;
            }

            this.setIsHasErrors(true);
            this.addMessageErrorMessageList("Указанное название файла: " + listFilesPosition[i + 1] + " должно находиться после использования флагов и их аргументов.");
            this.addMessageErrorMessageList("");

        }
    }

    /**
     * Проверить тип входных файлов
     */
    private void checkFileTypes() {
        String[] listFilesPosition = this.getListFilesPosition();

        Pattern txtFilePattern = Pattern.compile(this.TXT_FILE_REGEX);

        for (int i = 1; i < listFilesPosition.length; i += 2) {
            Matcher txtFileMatcher = txtFilePattern.matcher(listFilesPosition[i]);

            if (txtFileMatcher.matches()) {
                continue;
            }

            this.setIsHasErrors(true);
            this.addMessageErrorMessageList("Указанный файл: \"" + listFilesPosition[i] + "\" не является текстовым документом.");
            this.addMessageErrorMessageList("");

        }
    }

    /**
     * Проверить есть ли дублирующиеся названия в файлах
     */
    private void checkDuplicateFiles() {
        String[] listFilesPosition = this.getListFilesPosition();

        if (!ArrayHelper.hasDuplicates(listFilesPosition)) { return; }

        this.setIsHasErrors(true);
        this.addMessageErrorMessageList("В команде присутствуют дублирующиеся названия файлов.");
        this.addMessageErrorMessageList("Если вы хотите обработать несколько файлов с одинаковыми данными - задайте им разные называния");
        this.addMessageErrorMessageList("");
    }

    public boolean getIsOutRewrite() {
        return this.isOutRewrite;
    }

    public void setIsOutRewrite(boolean outRewrite) {
        this.isOutRewrite = outRewrite;
    }

    public boolean getIsBriefStat() {
        return this.isBriefStat;
    }

    public void setIsBriefStat(boolean isBriefStat) {
        this.isBriefStat = isBriefStat;
    }

    public boolean getIsFullStat() {
        return this.isFullStat;
    }

    public void setIsFullStat(boolean isFullStat) {
        this.isFullStat = isFullStat;
    }

    public String getOutPrefix() {
        return this.outPrefix;
    }

    public void setOutPrefix(String outPrefix) {
        this.outPrefix = outPrefix;
    }

    public String getOutPath() {
        return this.outPath;
    }

    public void setOutPath(String outPath) {
        this.outPath = outPath;
    }

    public String[] getListFilesPosition() {
        return this.listFilesPosition;
    }

    /**
     * Разделить оставшиеся аргументы на необработанные флаги и потенциальные названия файлов
     *
     * @param key Позиция среди аргументов командной строки
     * @param argument Значение аргумента
     */
    private void filterOtherArguments(String key, String argument) {
        Pattern flagPattern = Pattern.compile(this.FLAG_REGEX);
        Matcher flagMatcher = flagPattern.matcher(argument);

        if (flagMatcher.matches()) {
            this.addFlagToUndefinedFlagList(argument);
            return;
        }

        this.addFileToFilePositionList(key, argument);
    }

    /**
     * Формирование списка с позициями и наименованиями файлов
     *
     * @param key      Позиция элемента в массиве аргументов командной строки
     * @param fileName Наименование файла
     * @example [4, in1.txt, 5, in2.txt]
     */
    private void addFileToFilePositionList(String key, String fileName) {
        String[] newListFilesPosition = Arrays.copyOf(this.listFilesPosition, this.listFilesPosition.length + 2);
        newListFilesPosition[newListFilesPosition.length - 2] = key;
        newListFilesPosition[newListFilesPosition.length - 1] = fileName;
        this.listFilesPosition = newListFilesPosition;
    }

    /**
     * Добавление флага в список не обработанных флагов
     *
     * @param flag Флаг из командной строки
     */
    private void addFlagToUndefinedFlagList(String flag) {
        String[] newListFilesPosition = Arrays.copyOf(this.undefinedFlagList, this.undefinedFlagList.length + 1);
        newListFilesPosition[newListFilesPosition.length - 1] = flag;
        this.undefinedFlagList = newListFilesPosition;
    }

    public int getLastFlagIndex() {
        return this.lastFlagIndex;
    }

    private void setLastFlagIndex(int lastFlagIndex) {
        this.lastFlagIndex = lastFlagIndex;
    }

    public String[] getErrorMessages() {
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

    public boolean getIsHasErrors() {
        return this.isHasErrors;
    }

    public void setIsHasErrors(boolean isHasErrors) {
        this.isHasErrors = isHasErrors;
    }

    public boolean getIsOutPathFlagExists() {
        return isOutPathFlagExists;
    }

    public void setIsOutPathFlagExists(boolean isOutPathFlagExists) {
        this.isOutPathFlagExists = isOutPathFlagExists;
    }

    public boolean getIsOutPrefixFlagExists() {
        return isOutPrefixFlagExists;
    }

    public void setIsOutPrefixFlagExists(boolean isOutPrefixFlagExists) {
        this.isOutPrefixFlagExists = isOutPrefixFlagExists;
    }
}
