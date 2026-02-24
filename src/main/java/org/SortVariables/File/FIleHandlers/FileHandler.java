package org.SortVariables;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public abstract class FileHandler {

    protected ProcessedFile processedFile;

    public FileHandler(ProcessedFile processedFile) {
        this.processedFile = processedFile;
    }

    public abstract String readLine(long lineNumber);

    public abstract void writeLine(String line);

    public abstract void createFileIfNotExists();

    protected void createDirectory() {
        Path path = Paths.get(this.processedFile.directory);

        try {
            if (!Files.isDirectory(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при создании: " + e.getMessage());
        }
    }

    public void deleteIfExists() {
        Path filePath = Paths.get(this.processedFile.directory + "/" + this.processedFile.name);
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            System.err.println("Ошибка удаления: " + e.getMessage());
        }
    }
}