package org.SortVariables;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ProcessedFile {

    public String name;
    public String directory;
    public String fileAbsolutePath;
    public String extension;
    public String pathString;
    public long lineCount;

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
}