package org.SortVariables;

public class StatisticUnknownFileHandler extends StatisticFileHandler {

    public StatisticUnknownFileHandler(OutputProcessedFile outputProcessedFile){
        super(outputProcessedFile);
    }

    @Override
    public Long countLines() {
        System.out.println("Ошибка чтения файла: " + this.outputProcessedFile.getPathString() + ". Разрешение файла не поддерживается.");

        return null;
    }

    protected Double findMin(){
        return null;
    }

    protected Double findMax(){
        return null;
    }

    protected Double findSum(){
        return null;
    }
}