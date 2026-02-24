package org.SortVariables;

public class FileHandlerFactory {
    public FileHandler getFileHandler(ProcessedFile processedFile) {
        switch (processedFile.extension) {
            case "txt":
                return new TXTFileHandler(processedFile);
            default:
                return new UnknownFileHandler(processedFile);
        }
    }
}