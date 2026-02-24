package org.SortVariables;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ProcessedFile {

    private String name;
    private String directory;
    private String fileAbsolutePath;
    private String extension;
    private String pathString;

    public ProcessedFile(String pathString) {
        this.pathString = pathString;

        Path path = Paths.get(System.getProperty("user.dir") + "/" + pathString);

        this.name = path.getFileName().toString();
        this.directory = path.getParent().toString();

        this.setFileExtension();
    }

    protected void setFileExtension() {
        this.extension = "";
        int i = this.name.lastIndexOf('.');
        if (i > 0) {
            this.extension = this.name.substring(i + 1);
        }
    }

    public String getName(){
        return this.name;
    }

    public String getDirectory(){
        return this.directory;
    }

    public String getFileAbsolutePath(){
        return this.fileAbsolutePath;
    }

    public String getExtension(){
        return this.extension;
    }

    public String getPathString(){
        return this.pathString;
    }
}