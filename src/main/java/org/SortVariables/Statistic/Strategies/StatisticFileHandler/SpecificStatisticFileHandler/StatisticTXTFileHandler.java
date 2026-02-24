package org.SortVariables;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class StatisticTXTFileHandler extends StatisticFileHandler {

    public StatisticTXTFileHandler(OutputProcessedFile outputProcessedFile) {
        super(outputProcessedFile);
    }

    @Override
    public long countLines() {
        Path path = Paths.get(this.outputProcessedFile.getDirectory() + "/" + this.outputProcessedFile.getName());

        long lineCount = 0;

        try (Stream<String> lines = Files.lines(path)) {
            lineCount = lines.count();
        } catch (IOException e) {
            System.err.println("Ошибка при обработке файла: " + e.getMessage());
        }

        return lineCount;
    }
}