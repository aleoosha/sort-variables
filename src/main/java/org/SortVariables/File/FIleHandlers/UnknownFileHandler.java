package org.SortVariables;

public class UnknownFileHandler extends FileHandler {

    public UnknownFileHandler(ProcessedFile processedFile){
        super(processedFile);
    }

    @Override
    public String readLine(long lineNumber) {
        System.out.println("Ошибка чтения файла: " + this.processedFile.pathString + ". Разрешение файла не поддерживается.");
        return null;
    }

    @Override
    public void writeLine(String line) {
        System.out.println("Ошибка записи в файл: " + this.processedFile.pathString + ". Разрешение файла не поддерживается.");
    }

    @Override
    public void createFileIfNotExists() {
        System.out.println("Ошибка создания файла: " + this.processedFile.pathString + ". Разрешение файла не поддерживается.");
    }
}