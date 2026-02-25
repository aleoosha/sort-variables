package org.SortVariables;

import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class StatisticFileHandler {

    protected OutputProcessedFile outputProcessedFile;
    protected Long countLines;
    protected Double max;
    protected Double min;
    protected Double sum;
    protected Double average;

    public StatisticFileHandler(OutputProcessedFile outputProcessedFile) {
        this.outputProcessedFile = outputProcessedFile;
        if (!this.checkExists()){
            this.setEmptyStatistic();
        };
    }

    protected abstract Long countLines();

    protected abstract Double findMin();

    protected abstract Double findMax();

    protected abstract Double findSum();

    protected Double findAverage(){
        return Double.valueOf(this.getOrSetSum() / this.getOrSetCountLines());
    }

    private boolean checkExists(){
        return Files.exists(Paths.get(this.outputProcessedFile.getDirectory() + "/" + this.outputProcessedFile.getName()));
    }

    public Long getOrSetCountLines(){
        if (this.countLines != null) {
            return this.countLines;
        } else {
            return this.countLines = this.countLines();
        }
    }

    public Double getOrSetMin(){
        if (this.min != null) {
            return this.min;
        } else {
            return this.min = this.findMin();
        }
    }

    public Double getOrSetMax(){
        if (this.max != null) {
            return this.max;
        } else {
            return this.max = this.findMax();
        }
    }

    public Double getOrSetSum(){
        if (this.sum != null) {
            return this.sum;
        } else {
            return this.sum = this.findSum();
        }
    }

    public Double getOrSetAverage(){
        if (this.average != null) {
            return this.average;
        } else {
            return this.average = this.findAverage();
        }
    }

    private void setEmptyStatistic(){
        this.countLines = Long.valueOf(0);
        this.max = Double.valueOf(0);
        this.min = Double.valueOf(0);
        this.sum = Double.valueOf(0);
        this.average = Double.valueOf(0);
    }
}