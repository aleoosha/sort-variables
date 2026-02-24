package org.SortVariables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

public class Command {

    private String outPrefix;

    private String outPath;

    private String statisticType;

    private boolean isOutRewrite = true;

    private List<InputProcessedFile> inputFileList = new ArrayList<>();

    final String INT_FILE_NAME = "integers.txt";

    final String FLOAT_FILE_NAME = "floats.txt";

    final String STRING_FILE_NAME = "strings.txt";

    private OutputProcessedFile intOutputFile;

    private OutputProcessedFile floatOutputFile;

    private OutputProcessedFile stringOutputFile;

    public Command(String[] args) {
        this.setInputArgs(args);
        this.setOutputFiles();
    }

    private void setInputArgs(String[] args) {
        List<String> argsList = new ArrayList<>(Arrays.asList(args));
        ListIterator<String> iterator = argsList.listIterator();

        boolean nextElementContinue = false;

        while (iterator.hasNext()) {
            String arg = iterator.next();

            switch (arg) {
                case "-s":
                    this.statisticType = "brief";
                    break;
                case "-f":
                    this.statisticType = "full";
                    break;
                case "-a":
                    this.isOutRewrite = false;
                    break;
                case "-o":
                    if (iterator.hasNext()) {
                        this.outPath = iterator.next();
                    }
                    break;
                case "-p":
                    if (iterator.hasNext()) {
                        this.outPrefix = iterator.next();
                    }
                    break;
                default:
                    this.inputFileList.add(new InputProcessedFile(arg));
            }
        }
    }

    public void execute() {
        if (this.isOutRewrite) {
            this.deleteOldOutputFiles();
        }
        this.handleInputFiles();
        this.calculateStatistic();
    }

    private void setOutputFiles() {
        this.intOutputFile = new OutputProcessedFile(
                Objects.toString(this.outPath, "") + "/" + Objects.toString(this.outPrefix, "") + this.INT_FILE_NAME,
                "int"
        );
        this.floatOutputFile = new OutputProcessedFile(
                Objects.toString(this.outPath, "") + "/" + Objects.toString(this.outPrefix, "") + this.FLOAT_FILE_NAME,
                "float"
        );
        this.stringOutputFile = new OutputProcessedFile(
                Objects.toString(this.outPath, "") + "/" + Objects.toString(this.outPrefix, "") + this.STRING_FILE_NAME,
                "string"
        );
    }

    private void deleteOldOutputFiles() {
        FileHandlerFactory fileHandlerFactory = new FileHandlerFactory();
        FileHandler intFileHandler = fileHandlerFactory.getFileHandler(this.intOutputFile);
        FileHandler floatFileHandler = fileHandlerFactory.getFileHandler(this.floatOutputFile);
        FileHandler stringFileHandler = fileHandlerFactory.getFileHandler(this.stringOutputFile);

        intFileHandler.deleteIfExists();
        floatFileHandler.deleteIfExists();
        stringFileHandler.deleteIfExists();
    }

    private void handleInputFiles() {
        LineHandler intHandler = new IntegerHandler(this.intOutputFile);
        LineHandler floatHandler = new FloatHandler(this.floatOutputFile);
        LineHandler stringHandler = new StringHandler(this.stringOutputFile);

        intHandler.setNext(floatHandler);
        floatHandler.setNext(stringHandler);

        FileHandlerFactory fileHandlerFactory = new FileHandlerFactory();

        for (InputProcessedFile inputFile : this.inputFileList) {

            FileHandler inputFileHandler = fileHandlerFactory.getFileHandler(inputFile);

            for (int line = 1; line <= inputFile.getLineCount(); line++) {
                String fileLine = inputFileHandler.readLine(line);
                intHandler.handle(fileLine);
            }
        }
    }

    private void calculateStatistic() {
        StatisticStrategyFactory statisticStrategyFactory = new StatisticStrategyFactory();
        StatisticStrategy intStatisticStrategy = statisticStrategyFactory
                .getStatisticStrategy(this.intOutputFile, this.statisticType);
        StatisticStrategy floatStatisticStrategy = statisticStrategyFactory
                .getStatisticStrategy(this.floatOutputFile, this.statisticType);
        StatisticStrategy stringStatisticStrategy = statisticStrategyFactory
                .getStatisticStrategy(this.stringOutputFile, this.statisticType);

        intStatisticStrategy.calculate();
        floatStatisticStrategy.calculate();
        stringStatisticStrategy.calculate();
    }
}
