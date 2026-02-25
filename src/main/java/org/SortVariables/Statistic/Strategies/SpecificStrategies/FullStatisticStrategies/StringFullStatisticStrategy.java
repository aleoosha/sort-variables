package org.SortVariables;

public class StringFullStatisticStrategy extends StringBriefStatisticStrategy implements StatisticStrategy {

    public StringFullStatisticStrategy(OutputProcessedFile outpuFile){
        super(outpuFile);
    }

    public void calculate(){
        super.calculate();
        System.out.println("Минимальная длина строки: " + this.statisticFileHandler.getOrSetMin().longValue());
        System.out.println("Максимальная длина строки: " + this.statisticFileHandler.getOrSetMax().longValue());
        System.out.println("Сумма длин всех строк: " + this.statisticFileHandler.getOrSetSum().longValue());
        System.out.println("Средняя длина строки: " + this.statisticFileHandler.getOrSetAverage().doubleValue());
    }
}
