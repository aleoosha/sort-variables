package org.SortVariables;

import java.util.HashSet;
import java.util.Set;

/**
 * Вспомогательные методы для работы с массивами
 */
public class ArrayHelper {

    /**
     * Проверка на наличие дублирующихся строк в массиве
     *
     * @param array Проверяемый массив
     *
     * @return true при наличии дубликатов, иначе false
     */
    public static boolean hasDuplicates(String[] array) {
        Set<String> set = new HashSet<>();
        for (String element : array) {
            if (!set.add(element)) {
                return true;
            }
        }
        return false;
    }

}
