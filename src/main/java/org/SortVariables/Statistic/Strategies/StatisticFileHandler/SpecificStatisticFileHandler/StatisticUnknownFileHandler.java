package org.SortVariables;

public class StatisticUnknownFileHandler extends StatisticFileHandler {

    public StatisticUnknownFileHandler(OutputProcessedFile outputProcessedFile){
        super(outputProcessedFile);
    }

    @Override
    public long countLines() {
        System.out.println("Ошибка чтения файла: " + this.outputProcessedFile.getPathString() + ". Разрешение файла не поддерживается.");

        return 0;
    }
}