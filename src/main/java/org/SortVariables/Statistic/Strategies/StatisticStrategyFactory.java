package org.SortVariables;

public class StatisticStrategyFactory {
    public StatisticStrategy getStatisticStrategy(String fileContentType, String statisticType) {
        switch (fileContentType + "." + statisticType) {
            case "int.brief":
                return new IntBriefStatisticStrategy();
            case "float.brief":
                return new FloatBriefStatisticStrategy();
            case "string.brief":
                return new StringBriefStatisticStrategy();
            case "int.full":
                return new IntFullStatisticStrategy();
            case "float.full":
                return new FloatFullStatisticStrategy();
            case "string.full":
                return new StringFullStatisticStrategy();
            default:
                return new EmptyStatisticStrategy();
        }
    }
}
