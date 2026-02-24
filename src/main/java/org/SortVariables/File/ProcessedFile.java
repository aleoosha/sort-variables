package org.SortVariables;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
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

        try {
            this.name = path.getFileName().toString();
            this.directory = path.getParent().toString();
            try (Stream<String> lines = Files.lines(path)) {
                this.lineCount = lines.count();
            } catch (NoSuchFileException e) {
                this.lineCount = 0;
            }
        } catch (IOException e) {
            System.err.println("Ошибка при обработке файла: " + e.getMessage());
        }

        this.setFileExtension();

        System.out.println(this.lineCount);
        System.out.println(this.directory);

    }

    private void setFileExtension() {
        this.extension = "";
        int i = this.name.lastIndexOf('.');
        if (i > 0) {
            this.extension = this.name.substring(i + 1);
        }
    }
}