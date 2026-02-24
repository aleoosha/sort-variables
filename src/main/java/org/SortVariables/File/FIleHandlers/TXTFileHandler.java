package org.SortVariables;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class TXTFileHandler extends FileHandler {

    public TXTFileHandler(ProcessedFile processedFile) {
        super(processedFile);
    }

    @Override
    public String readLine(long lineNumber) {
        String line = null;

        try {
            line = Files.lines(Paths.get(this.processedFile.directory + "/" + this.processedFile.name))
                    .skip(lineNumber - 1)
                    .findFirst()
                    .orElse(null);
        } catch (IOException e) {
            System.err.println("Ошибка чтения: " + e.getMessage());
        }

        return line;
    }

    @Override
    public void writeLine(String line) {
        try (FileWriter writer =
                     new FileWriter(this.processedFile.directory + "/" + this.processedFile.name, true)) {
            writer.write(line + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Ошибка записи: " + e.getMessage());
        }
    }

    @Override
    public void createFileIfNotExists() {
        this.createDirectory();

        Path path = Paths.get(System.getProperty("user.dir") + "/" + this.processedFile.pathString);
        try {
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
        } catch (IOException e) {
            System.err.println("Файл уже существует или ошибка: " + e.getMessage());
        }
    }
}