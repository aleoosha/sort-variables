package org.SortVariables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

public class Command {

    private String outPrefix;

    private String outPath;

    private String statFlag;

    private boolean isOutRewrite = true;

    private List<ProcessedFile> inputFileList = new ArrayList<>();

    final String INT_FILE_NAME = "integers.txt";

    final String FLOAT_FILE_NAME = "floats.txt";

    final String STRING_FILE_NAME = "strings.txt";

    private ProcessedFile intOutputFile;

    private ProcessedFile floatOutputFile;

    private ProcessedFile stringOutputFile;

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
                case "-f":
                    this.statFlag = arg;
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
                    this.inputFileList.add(new ProcessedFile(arg));
            }
        }
    }

    public void execute() {
        if (this.isOutRewrite){
            this.deleteOldOutputFiles();
        }
        this.handleInputFiles();
    }

    private void setOutputFiles(){
        this.intOutputFile = new ProcessedFile(
                Objects.toString(this.outPath, "") + "/" + Objects.toString(this.outPrefix, "") + this.INT_FILE_NAME
        );
        this.floatOutputFile = new ProcessedFile(
                Objects.toString(this.outPath, "") + "/" + Objects.toString(this.outPrefix, "") + this.FLOAT_FILE_NAME
        );
        this.stringOutputFile = new ProcessedFile(
                Objects.toString(this.outPath, "") + "/" + Objects.toString(this.outPrefix, "") + this.STRING_FILE_NAME
        );
    }

    private void deleteOldOutputFiles(){
        FileHandlerFactory fileHandlerFactory = new FileHandlerFactory();
        FileHandler intFileHandler = fileHandlerFactory.getFileHandler(this.intOutputFile);
        FileHandler floatFileHandler = fileHandlerFactory.getFileHandler(this.floatOutputFile);
        FileHandler stringFileHandler = fileHandlerFactory.getFileHandler(this.stringOutputFile);

        intFileHandler.deleteIfExists();
        floatFileHandler.deleteIfExists();
        stringFileHandler.deleteIfExists();
    }

    private void handleInputFiles(){
        LineHandler intHandler = new IntegerHandler(this.intOutputFile);
        LineHandler floatHandler = new FloatHandler(this.floatOutputFile);
        LineHandler stringHandler = new StringHandler(this.stringOutputFile);

        intHandler.setNext(floatHandler);
        floatHandler.setNext(stringHandler);

        FileHandlerFactory fileHandlerFactory = new FileHandlerFactory();

        for (ProcessedFile inputFile : this.inputFileList){

            FileHandler inputFileHandler = fileHandlerFactory.getFileHandler(inputFile);

            for (int line = 1; line <= inputFile.lineCount; line++) {
                String fileLine = inputFileHandler.readLine(line);
                intHandler.handle(fileLine);
            }
        }
    }
}
