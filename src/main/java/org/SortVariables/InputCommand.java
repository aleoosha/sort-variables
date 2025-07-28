package org.SortVariables;

import java.util.Arrays;

public class InputCommand {

    /** Позиции и наименования файлов из аргументов командной строки */
    private String[] listFilesPosition = {};

    /** Файлы с результатами должны быть перезаписаны? */
    private boolean isOutRewrite = true;

    /** Нужно ли вывести краткую статистику? */
    private boolean isBriefStat = false;

    /** Нужно ли вывести полную статистику? */
    private boolean isFullStat = false;

    /** Префикс в названии для результерующих файлов */
    private String outPrefix;

    /** Относительный путь до результирующих файлов от директории с результатами по умолчанию  */
    private String outPath;

    /** Позиция последнего флага из командной строки */
    private int lastFlagIndex = -1;

    /**
     * Инициализация объекта входной команды и проверка входных данных
     *
     * @param args Аргументы из командной строки
     */
    public InputCommand(String[] args) {
        this.setParameters(args);
        this.checkParameters();

        System.out.println(Arrays.toString(this.listFilesPosition));
    }

    /**
     * Установление параметров из командной строки в свойства класса
     *
     * @param args Аргументы из командной строки
     */
    private void setParameters(String[] args) {

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
     * @param key Позиция элемента в массиве аргументов командной строки
     * @param fileName Наименование файла
     *
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
}
