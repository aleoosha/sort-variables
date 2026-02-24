package org.SortVariables;

public class UnknownFileHandler extends FileHandler {

    public UnknownFileHandler(ProcessedFile processedFile){
        super(processedFile);
    }

    @Override
    public String readLine(long lineNumber) {
        System.out.println("Ошибка чтения файла: " + this.processedFile.getPathString() + ". Разрешение файла не поддерживается.");
        return null;
    }

    @Override
    public void writeLine(String line) {
        System.out.println("Ошибка записи в файл: " + this.processedFile.getPathString() + ". Разрешение файла не поддерживается.");
    }

    @Override
    public void createFileIfNotExists() {
        System.out.println("Ошибка создания файла: " + this.processedFile.getPathString() + ". Разрешение файла не поддерживается.");
    }

    @Override
    public long countLines() {
        if (!this.checkExists()){
            System.out.println("Файл не найден: " + this.processedFile.getPathString());
        } else {
            System.out.println("Ошибка чтения файла: " + this.processedFile.getPathString() + ". Разрешение файла не поддерживается.");
        }

        return 0;
    }
}