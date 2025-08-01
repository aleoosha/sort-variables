package org.SortVariables;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

/**
 * Класс для подсчета статистики
 */
public class FileStatistic {

    /**
     * Путь до результирующего файла
     */
    private String filePath;

    /**
     * Тип результирующего файла "int", "float", "string"
     */
    private String fileType;

    /**
     * Тип статистики для результирующего файла "brief", "full"
     */
    private String type;

    /**
     * Количество элементов/строк в файле
     */
    private BigInteger elementsCount;

    /**
     * Был ли произведен расчет статистики?
     */
    private boolean isCalculated;

    /**
     * Минимальное целочисленное значение в файле целыми с числами
     */
    private BigInteger minIntValue;

    /**
     * Максимальное целочисленное значение в файле целыми с числами
     */
    private BigInteger maxIntValue;

    /**
     * Суммарное значение всех целочисленных значений в файле с целыми числами
     */
    private BigInteger sumIntValue;

    /**
     * Среднее значение всех целочисленных значений в файле с целыми числами
     */
    private BigDecimal avgIntValue;

    /**
     * Минимальное дробное значение в файле с дробными числами
     */
    private BigDecimal minFloatValue;

    /**
     * Максимальное дробное значение в файле с дробными числами
     */
    private BigDecimal maxFloatValue;

    /**
     * Суммарное значение всех дробных чисел в файле с дробными числами
     */
    private BigDecimal sumFloatValue;

    /**
     * Среднее значение всех дробных чисел в файле с дробными числами
     */
    private BigDecimal avgFloatValue;

    /**
     * Максимальная длина строки в файле со строками
     */
    private BigInteger maxLengthStringValue;

    /**
     * Минимальная длина строки в файле со строками
     */
    private BigInteger minLengthStringValue;

    /**
     * Массив с данными из результирующего файла
     */
    private String[] fileData = {};

    /**
     * Были ли прочитаны данные из результирующего файла?
     */
    private boolean isFileDataSet;

    /**
     * Инициализация данных для подсчета статистики
     *
     * @param type     Тип статистики
     * @param filePath Путь до результирующего файла
     * @param fileType Тип результирующего файла
     */
    public FileStatistic(String type, String filePath, String fileType) {
        this.setFilePath(filePath);
        this.setFileType(fileType);
        this.setType(type);
    }

    public FileStatistic calculate() {
        this.setElementsCount(this.countElements());

        if (this.type.equals("brief")) {
            this.setIsCalculated();
            return this;
        }

        if (this.getElementsCount() != null) {

            switch (this.getFileType()) {
                case "int":
                    this.setMaxIntValue(this.foundMaxIntValue());
                    this.setMinIntValue(this.foundMinIntValue());
                    this.setSumIntValue(this.calculateSumIntValue());
                    this.setAvgIntValue(this.calculateAvgIntValue());
                    break;
                case "float":
                    this.setMaxFloatValue(this.foundMaxFloatValue());
                    this.setMinFloatValue(this.foundMinFloatValue());
                    this.setSumFloatValue(this.calculateSumFloatValue());
                    this.setAvgFloatValue(this.calculateAvgFloatValue());
                    break;
                case "string":
                    this.setMaxLengthStringValue(this.calculateMaxLengthStringValue());
                    this.setMinLengthStringValue(this.calculateMinLengthStringValue());
                    break;
                default:
                    break;

            }
        }

        this.setIsCalculated();
        return this;
    }

    public void print() {
        if (!this.getIsCalculated()) {
            return;
        }

        System.out.println("Статистика по файлу: " + this.getFilePath());
        System.out.println();

        switch (this.getType()) {
            case "brief":
                this.printBriefStatistic();
                break;
            case "full":
                this.printFullStatistic();
                break;
            default:
                break;
        }
    }

    private void printBriefStatistic() {
        System.out.println("Количество элементов: " + this.getElementsCount());
        System.out.println();
    }

    /**
     * Вывести полную статистику
     */
    private void printFullStatistic() {
        if (this.getElementsCount() != null) {
            System.out.println("Количество элементов: " + this.getElementsCount());

            switch (this.getFileType()) {
                case "int":
                    System.out.println("Максимальное значение: " + this.getMaxIntValue().toString());
                    System.out.println("Минимальное значение: " + this.getMinIntValue().toString());
                    System.out.println("Сумма элементов: " + this.getSumIntValue().toString());
                    System.out.println("Среднее значение: " + this.getAvgIntValue());
                    System.out.println();
                    break;
                case "float":
                    System.out.println("Максимальное значение: " + this.getMaxFloatValue());
                    System.out.println("Минимальное значение: " + this.getMinFloatValue());
                    System.out.println("Сумма элементов: " + this.getSumFloatValue());
                    System.out.println("Среднее значение: " + this.getAvgFloatValue());
                    System.out.println();
                    break;
                case "string":
                    System.out.println("Максимальная длина строки: " + this.getMaxLengthStringValue());
                    System.out.println("Минимальное длина строки: " + this.getMinLengthStringValue());
                    System.out.println();
                    break;
                default:
                    break;

            }
        } else {
            System.out.println("Количество элементов: 0");

            switch (this.getFileType()) {
                case "int":
                case "float":
                    System.out.println("Максимальное значение: N/A");
                    System.out.println("Минимальное значение: N/A");
                    System.out.println("Сумма элементов: N/A");
                    System.out.println("Среднее значение: N/A");
                    System.out.println();
                    break;
                case "string":
                    System.out.println("Максимальная длина строки: 0");
                    System.out.println("Минимальное длина строки: 0");
                    System.out.println();
                    break;
                default:
                    break;

            }
        }
    }

    /**
     * Посчитать минимальную длину строки в файле
     *
     * @return Минимальная длина строки в файле
     */
    private BigInteger calculateMinLengthStringValue() {
        String[] fileData = this.getOrSetFileData();

        BigInteger minLength = null;

        for (String line : fileData) {
            BigInteger lineLength = BigInteger.valueOf(line.length());
            if (minLength == null || lineLength.compareTo(minLength) < 0) {
                minLength = lineLength;
            }
        }

        return minLength;
    }

    /**
     * Посчитать максимальную длину строки в файле
     *
     * @return Максимальная длина строки в файле
     */
    private BigInteger calculateMaxLengthStringValue() {
        String[] fileData = this.getOrSetFileData();

        BigInteger maxLength = null;

        for (String line : fileData) {
            BigInteger lineLength = BigInteger.valueOf(line.length());
            if (maxLength == null || lineLength.compareTo(maxLength) > 0) {
                maxLength = lineLength;
            }
        }

        return maxLength;
    }

    /**
     * Посчитать среднее значение целочисленных значений в файле
     *
     * @return Среднее значение целочисленных значений в файле
     */
    private BigDecimal calculateAvgIntValue() {
        BigDecimal sumIntValue = new BigDecimal(this.getSumIntValue());
        BigDecimal countElement = new BigDecimal(this.getElementsCount());

        return sumIntValue.divide(countElement, 2, RoundingMode.HALF_UP);
    }

    /**
     * Посчитать среднее значение дробных чисел в файле
     *
     * @return Среднее значение дробных чисел в файле
     */
    private BigDecimal calculateAvgFloatValue() {
        BigDecimal sumFloatValue = this.getSumFloatValue();
        BigDecimal countElement = new BigDecimal(this.getElementsCount());

        return sumFloatValue.divide(countElement, RoundingMode.HALF_UP);
    }

    /**
     * Получить сумму дробных чисел из файла
     *
     * @return Сумма дробных чисел из файла
     */
    private BigDecimal calculateSumFloatValue() {
        String[] fileData = this.getOrSetFileData();
        BigDecimal sum = null;

        for (String line : fileData) {
            try {
                BigDecimal number = new BigDecimal(line.trim());
                if (sum == null) {
                    sum = number;
                } else {
                    sum = sum.add(number);
                }
            } catch (NumberFormatException error) {
                System.err.println("Ошибка преобразования строки в число: " + line);
                System.err.println("В файле: " + this.filePath);
                System.out.println();
                System.exit(1);
            }
        }

        return sum;
    }

    /**
     * Получить минимальное значение с плавающей точкой в файле
     *
     * @return Минимальное значение с плавающей точкой в файле
     */
    private BigDecimal foundMinFloatValue() {
        String[] fileData = this.getOrSetFileData();
        BigDecimal min = null;

        for (String line : fileData) {
            try {
                BigDecimal number = new BigDecimal(line.trim());
                if (min == null || number.compareTo(min) < 0) {
                    min = number;
                }
            } catch (NumberFormatException error) {
                System.err.println("Ошибка преобразования строки в число: " + line);
                System.err.println("В файле: " + this.filePath);
                System.out.println();
                System.exit(1);
            }
        }

        return min;
    }

    /**
     * Получить максимальное значение с плавающей точкой в файле
     *
     * @return Максимальное значение с плавающей точкой в файле
     */
    private BigDecimal foundMaxFloatValue() {
        String[] fileData = this.getOrSetFileData();
        BigDecimal max = null;

        for (String line : fileData) {
            try {
                BigDecimal number = new BigDecimal(line.trim());
                if (max == null || number.compareTo(max) > 0) {
                    max = number;
                }
            } catch (NumberFormatException error) {
                System.err.println("Ошибка преобразования строки в число: " + line);
                System.err.println("В файле: " + this.filePath);
                System.out.println();
                System.exit(1);
            }
        }

        return max;
    }

    /**
     * Посчитать суммарное значение всех элементов в файле с целыми числами
     *
     * @return суммарное значение всех элементов в файле
     */
    private BigInteger calculateSumIntValue() {
        String[] fileData = this.getOrSetFileData();
        BigInteger sum = null;

        for (String line : fileData) {
            try {
                BigInteger number = new BigInteger(line.trim());
                if (sum == null) {
                    sum = number;
                } else {
                    sum = sum.add(number);
                }
            } catch (NumberFormatException error) {
                System.err.println("Ошибка преобразования строки в число: " + line);
                System.err.println("В файле: " + this.filePath);
                System.out.println();
                System.exit(1);
            }
        }

        return sum;
    }

    /**
     * Найти максимальное значение в результирующем файле
     *
     * @return максимальное значение
     */
    private BigInteger foundMaxIntValue() {
        String[] fileData = this.getOrSetFileData();
        BigInteger max = null;

        for (String line : fileData) {
            try {
                BigInteger number = new BigInteger(line.trim());
                if (max == null || number.compareTo(max) > 0) {
                    max = number;
                }
            } catch (NumberFormatException error) {
                System.err.println("Ошибка преобразования строки в число: " + line);
                System.err.println("В файле: " + this.filePath);
                System.out.println();
                System.exit(1);
            }
        }

        return max;
    }

    /**
     * Найти минимальное значение в результирующем файле
     *
     * @return минимальное значение
     */
    private BigInteger foundMinIntValue() {
        String[] fileData = this.getOrSetFileData();
        BigInteger min = null;

        for (String line : fileData) {
            try {
                BigInteger number = new BigInteger(line.trim());
                if (min == null || number.compareTo(min) < 0) {
                    min = number;
                }
            } catch (NumberFormatException error) {
                System.err.println("Ошибка преобразования строки в число: " + line);
                System.err.println("В файле: " + this.filePath);
                System.out.println();
                System.exit(1);
            }
        }

        return min;
    }

    public String getFilePath() {
        return this.filePath;
    }

    private void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return this.fileType;
    }

    private void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getType() {
        return this.type;
    }

    private void setType(String type) {
        this.type = type;
    }

    /**
     * Посчитать количество не пустых строк в файле
     *
     * @return Количество не пустых строк в файле
     */
    private BigInteger countElements() {
        String[] fileData = this.getOrSetFileData();
        BigInteger count = null;

        for (String line : fileData) {
            if (!line.trim().isEmpty()) {
                if (count == null) {
                    count = new BigInteger("1");
                } else {
                    count = count.add(new BigInteger("1"));
                }
            }
        }
        return count;
    }

    private void setElementsCount(BigInteger elementsCount) {
        this.elementsCount = elementsCount;
    }

    public BigInteger getElementsCount() {
        return this.elementsCount;
    }

    private void setIsCalculated() {
        this.isCalculated = true;
    }

    public boolean getIsCalculated() {
        return this.isCalculated;
    }

    private void setMinIntValue(BigInteger minIntValue) {
        this.minIntValue = minIntValue;
    }

    public BigInteger getMinIntValue() {
        return this.minIntValue;
    }

    private void setMaxIntValue(BigInteger maxIntValue) {
        this.maxIntValue = maxIntValue;
    }

    public BigInteger getMaxIntValue() {
        return this.maxIntValue;
    }

    private void setSumIntValue(BigInteger sumIntValue) {
        this.sumIntValue = sumIntValue;
    }

    public BigInteger getSumIntValue() {
        return this.sumIntValue;
    }

    private void setAvgIntValue(BigDecimal avgIntValue) {
        this.avgIntValue = avgIntValue;
    }

    public BigDecimal getAvgIntValue() {
        return this.avgIntValue;
    }

    private void setMinFloatValue(BigDecimal minFloatValue) {
        this.minFloatValue = minFloatValue;
    }

    public BigDecimal getMinFloatValue() {
        return this.minFloatValue;
    }

    private void setMaxFloatValue(BigDecimal maxFloatValue) {
        this.maxFloatValue = maxFloatValue;
    }

    public BigDecimal getMaxFloatValue() {
        return this.maxFloatValue;
    }

    private void setSumFloatValue(BigDecimal sumFloatValue) {
        this.sumFloatValue = sumFloatValue;
    }

    public BigDecimal getSumFloatValue() {
        return this.sumFloatValue;
    }

    private void setAvgFloatValue(BigDecimal avgFloatValue) {
        this.avgFloatValue = avgFloatValue;
    }

    public BigDecimal getAvgFloatValue() {
        return this.avgFloatValue;
    }

    private void setMaxLengthStringValue(BigInteger maxLengthStringValue) {
        this.maxLengthStringValue = maxLengthStringValue;
    }

    public BigInteger getMaxLengthStringValue() {
        return this.maxLengthStringValue;
    }

    private void setMinLengthStringValue(BigInteger minLengthStringValue) {
        this.minLengthStringValue = minLengthStringValue;
    }

    public BigInteger getMinLengthStringValue() {
        return this.minLengthStringValue;
    }

    /**
     * Получить данные из файла в виде массива строк
     *
     * @return Данные из результирующего файла
     */
    private String[] getOrSetFileData() {
        if (!this.isFileDataSet) {
            String filePath = this.getFilePath();

            try {
                BufferedReader reader = new BufferedReader(new FileReader(filePath));

                String line;

                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty()) { continue; }
                    this.addElementToFileData(line);
                }

                this.isFileDataSet = true;
            } catch (IOException error) {
                System.err.println("Ошибка при чтении файла: " + error.getMessage());
                System.out.println();
                System.exit(-1);
            }

            return this.fileData;
        } else {
            return this.fileData;
        }
    }

    /**
     * Добавить элемент в массив с данными из результирующего файла
     *
     * @param element Значение из строки результирующего файла
     */
    private void addElementToFileData(String element) {
        if (this.fileData.length == Integer.MAX_VALUE) {
            System.err.println("Слишком большое количество строк в выходном файле: " + this.getFilePath());
            System.out.println();
            System.exit(1);
        }

        String[] newFileData = Arrays.copyOf(this.fileData, this.fileData.length + 1);
        newFileData[newFileData.length - 1] = element;
        this.fileData = newFileData;
    }
}
