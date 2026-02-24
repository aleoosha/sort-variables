package org.SortVariables;

public abstract class StatisticFileHandler {

    protected OutputProcessedFile outputProcessedFile;

    public abstract long countLines();

    public StatisticFileHandler(OutputProcessedFile outputProcessedFile) {
        this.outputProcessedFile = outputProcessedFile;
    }
}