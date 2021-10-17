package ru.gb;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Main {
    public static int[] newArrayAfter4(int[] numbers) throws RuntimeException {

        if (numbers == null || Array.getLength(numbers) == 0) {
            throw new IllegalArgumentException("пустой массив");
        }

        int indexOf4 = -1;
        for (int i = numbers.length - 1; i >= 0; i--) {
            if (numbers[i] == 4) {
                indexOf4 = i + 1;
                break;
            }
        }
        if ((indexOf4 == -1)) {
            throw new IllegalArgumentException("Массив не содержит ни одной цифры 4");
        }

        return Arrays.copyOfRange(numbers, indexOf4, numbers.length);


    }
}