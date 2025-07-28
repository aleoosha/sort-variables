package org.SortVariables;

import java.util.Arrays;

public class InputCommand {

    /** Аргументы командной строки которые не удалось обработать */
    private String[] unprocessedArgs = {};

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

    /**  */
    private int lastFlagIndex = -1;

    public InputCommand(String[] args) {
        this.setParameters(args);

        System.out.println(Arrays.toString(this.unprocessedArgs));
    }

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
                    this.addElementUnprocessedArg(String.valueOf(i), element);
            }
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

    public String[] unprocessedArgs() {
        return this.unprocessedArgs;
    }

    public void addElementUnprocessedArg(String key, String unprocessedArg) {
        String[] newUnprocessedArgs = Arrays.copyOf(this.unprocessedArgs, this.unprocessedArgs.length + 2);
        newUnprocessedArgs[newUnprocessedArgs.length - 2] = key;
        newUnprocessedArgs[newUnprocessedArgs.length - 1] = unprocessedArg;
        this.unprocessedArgs = newUnprocessedArgs;
    }

    public int getLastFlagIndex() {
        return this.lastFlagIndex;
    }

    public void setLastFlagIndex(int lastFlagIndex) {
        this.lastFlagIndex = lastFlagIndex;
    }
}
