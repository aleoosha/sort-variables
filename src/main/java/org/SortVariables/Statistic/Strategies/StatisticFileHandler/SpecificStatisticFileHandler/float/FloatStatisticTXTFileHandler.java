package org.SortVariables;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

public class FloatStatisticTXTFileHandler extends StatisticTXTFileHandler {

    public FloatStatisticTXTFileHandler(OutputProcessedFile outputProcessedFile) {
        super(outputProcessedFile);
    }

    protected Double findMin(){
        Path filePath = Paths.get(this.outputProcessedFile.getDirectory() + "/" + this.outputProcessedFile.getName());

        Double min = null;

        try (Stream<String> lines = Files.lines(filePath)) {
            min = lines
                    .mapToDouble(Double::parseDouble)
                    .min()
                    .orElse(Double.NaN);
        } catch (IOException e) {
            System.err.println("Ошибка обработки файла: " + e.getMessage());
        }

        return min;
    }

    protected Double findMax(){
        Path filePath = Paths.get(this.outputProcessedFile.getDirectory() + "/" + this.outputProcessedFile.getName());

        Double max = null;

        try (Stream<String> lines = Files.lines(filePath)) {
            max = lines
                    .mapToDouble(Double::parseDouble)
                    .max()
                    .orElse(Double.NaN);
        } catch (IOException e) {
            System.err.println("Ошибка обработки файла: " + e.getMessage());
        }

        return max;
    }

    protected Double findSum(){
        Path filePath = Paths.get(this.outputProcessedFile.getDirectory() + "/" + this.outputProcessedFile.getName());

        double sum = 0;

        try (Stream<String> lines = Files.lines(filePath)) {
            sum = lines
                    .mapToDouble(Double::parseDouble)
                    .sum();
        } catch (IOException e) {
            System.err.println("Ошибка обработки файла: " + e.getMessage());
        }

        return Double.valueOf(sum);
    }
}