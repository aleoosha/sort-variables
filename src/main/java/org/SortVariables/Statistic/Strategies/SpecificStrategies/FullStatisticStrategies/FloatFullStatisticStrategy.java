package org.SortVariables;

public class FloatFullStatisticStrategy extends FloatBriefStatisticStrategy implements StatisticStrategy {

    public FloatFullStatisticStrategy(OutputProcessedFile outpuFile){
        super(outpuFile);
    }

    public void calculate(){
        super.calculate();
        System.out.println("Минимальное значение: " + this.statisticFileHandler.getOrSetMin().doubleValue());
        System.out.println("Максимальное значение: " + this.statisticFileHandler.getOrSetMax().doubleValue());
        System.out.println("Сумма всех элементов: " + this.statisticFileHandler.getOrSetSum().doubleValue());
        System.out.println("Среднее значение элементов: " + this.statisticFileHandler.getOrSetAverage().doubleValue());
    }
}
