package org.SortVariables;

public class InputProcessedFile extends ProcessedFile {

    private Long lineCount;

    public InputProcessedFile(String pathString) {
        super(pathString);
        this.setLineCount();
    }

    private void setLineCount() {
        FileHandlerFactory fileHandlerFactory = new FileHandlerFactory();
        FileHandler fileHandler = fileHandlerFactory.getFileHandler(this);
        this.lineCount = fileHandler.countLines();
    }

    public Long getLineCount(){
        return this.lineCount;
    }
}