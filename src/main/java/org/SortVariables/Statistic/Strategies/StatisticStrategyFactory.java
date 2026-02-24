package org.SortVariables;

public class StatisticStrategyFactory {
    public StatisticStrategy getStatisticStrategy(OutputProcessedFile outpuFile, String statisticType) {
        switch (outpuFile.getContentType() + "." + statisticType) {
            case "int.brief":
                return new IntBriefStatisticStrategy(outpuFile);
            case "float.brief":
                return new FloatBriefStatisticStrategy(outpuFile);
            case "string.brief":
                return new StringBriefStatisticStrategy(outpuFile);
            case "int.full":
                return new IntFullStatisticStrategy(outpuFile);
            case "float.full":
                return new FloatFullStatisticStrategy(outpuFile);
            case "string.full":
                return new StringFullStatisticStrategy(outpuFile);
            default:
                return new EmptyStatisticStrategy();
        }
    }
}
