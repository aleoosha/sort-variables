package org.SortVariables;

public class IntFullStatisticStrategy extends IntBriefStatisticStrategy implements StatisticStrategy {

    public IntFullStatisticStrategy(OutputProcessedFile outpuFile){
        super(outpuFile);
    }

    public void calculate(){
        super.calculate();
        System.out.println("Минимальное значение: " + this.statisticFileHandler.getOrSetMin().longValue());
        System.out.println("Максимальное значение: " + this.statisticFileHandler.getOrSetMax().longValue());
        System.out.println("Сумма всех элементов: " + this.statisticFileHandler.getOrSetSum().longValue());
        System.out.println("Среднее значение элементов: " + this.statisticFileHandler.getOrSetAverage().doubleValue());
    }
}
