package org.SortVariables;

public class OutputProcessedFile extends ProcessedFile {

    private String contentType;

    public OutputProcessedFile(String pathString, String contentType) {
        super(pathString);
        this.contentType = contentType;
    }

    public String getContentType(){
        return this.contentType;
    }
}