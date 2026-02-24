package org.SortVariables;

import java.io.FileWriter;
import java.io.IOException;

public abstract class LineHandler {
    protected LineHandler next;
    protected ProcessedFile outputFile;

    public LineHandler(ProcessedFile outputFile) {
        this.outputFile = outputFile;
    }

    public void setNext(LineHandler next) {
        this.next = next;
    }

    public void handle(String line) {
        if (canHandle(line)) {
            save(line);
        } else if (next != null) {
            next.handle(line);
        }
    }

    protected abstract boolean canHandle(String line);

    protected void save(String line) {
        FileHandlerFactory fileHandlerFactory = new FileHandlerFactory();
        FileHandler fileHandler = fileHandlerFactory.getFileHandler(this.outputFile);
        fileHandler.createFileIfNotExists();
        fileHandler.writeLine(line);
    }
}