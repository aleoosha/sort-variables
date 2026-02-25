package org.SortVariables;

public abstract class BaseBriefStatisticStrategy {

    protected OutputProcessedFile outpuFile;
    protected Long countElements;
    protected StatisticFileHandler statisticFileHandler;

    public BaseBriefStatisticStrategy(OutputProcessedFile outpuFile){
        this.outpuFile = outpuFile;
        this.setStatisticFileHandler();
    }

    protected void calculate(){
        this.printTitle();
        this.calculateElements();
    }

    private void setStatisticFileHandler(){
        StatisticFileHandlerFactory statisticFileHandlerFactory = new StatisticFileHandlerFactory();
        StatisticFileHandler statisticFileHandler = statisticFileHandlerFactory.getStatisticFileHandler(outpuFile);
        this.statisticFileHandler = statisticFileHandler;
    }

    private void calculateElements(){
        this.countElements = this.statisticFileHandler.getOrSetCountLines();
        System.out.println("Количество элементов: " + this.countElements.longValue());
    }

    private void printTitle(){
        System.out.println("");
        System.out.println("Статистика для файла " + this.outpuFile.getPathString() + ":");
        System.out.println("");
    }
}