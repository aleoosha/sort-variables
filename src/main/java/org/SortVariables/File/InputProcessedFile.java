package org.SortVariables;

public class InputProcessedFile extends ProcessedFile {

    private long lineCount;

    public InputProcessedFile(String pathString) {
        super(pathString);
        this.setLineCount();
    }

    private void setLineCount() {
        FileHandlerFactory fileHandlerFactory = new FileHandlerFactory();
        FileHandler fileHandler = fileHandlerFactory.getFileHandler(this);
        this.lineCount = fileHandler.countLines();
    }

    public long getLineCount(){
        return this.lineCount;
    }
}