package org.SortVariables;

public class StatisticFileHandlerFactory {
    public StatisticFileHandler getStatisticFileHandler(OutputProcessedFile outputProcessedFile) {
        switch (outputProcessedFile.getContentType() + "." + outputProcessedFile.getExtension()) {
            case "int.txt":
                return new IntStatisticTXTFileHandler(outputProcessedFile);
            case "float.txt":
                return new FloatStatisticTXTFileHandler(outputProcessedFile);
            case "string.txt":
                return new StringStatisticTXTFileHandler(outputProcessedFile);
            default:
                return new StatisticUnknownFileHandler(outputProcessedFile);
        }
    }
}