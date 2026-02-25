package org.SortVariables;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class StringStatisticTXTFileHandler extends StatisticTXTFileHandler {

    public StringStatisticTXTFileHandler(OutputProcessedFile outputProcessedFile) {
        super(outputProcessedFile);
    }

    protected Double findMin(){
        Path filePath = Paths.get(this.outputProcessedFile.getDirectory() + "/" + this.outputProcessedFile.getName());

        long min = 0;

        try (Stream<String> lines = Files.lines(filePath)) {
            min = lines
                    .mapToInt(String::length)
                    .min()
                    .orElse(0);;

        } catch (IOException e) {
            System.err.println("Ошибка обработки файла: " + e.getMessage());
        }

        return Double.valueOf(min);
    }

    protected Double findMax(){
        Path filePath = Paths.get(this.outputProcessedFile.getDirectory() + "/" + this.outputProcessedFile.getName());

        long max = 0;

        try (Stream<String> lines = Files.lines(filePath)) {
            max = lines
                    .mapToInt(String::length)
                    .max()
                    .orElse(0);;

        } catch (IOException e) {
            System.err.println("Ошибка обработки файла: " + e.getMessage());
        }

        return Double.valueOf(max);
    }

    protected Double findSum(){
        Path filePath = Paths.get(this.outputProcessedFile.getDirectory() + "/" + this.outputProcessedFile.getName());

        long sum = 0;

        try {
            sum = Files.lines(filePath)
                    .mapToInt(String::length)
                    .sum();
        } catch (IOException e) {
            System.err.println("Ошибка обработки файла: " + e.getMessage());
        }

        return Double.valueOf(sum);
    }
}