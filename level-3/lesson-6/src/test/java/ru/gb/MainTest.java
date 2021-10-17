package ru.gb;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

class MainTest {

    public static Stream<Arguments> dataForCheckNewArrayAfter4() {
        List<Arguments> out = new ArrayList<>();
        out.add(Arguments.arguments(new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7}, new int[]{1, 7}));
        out.add(Arguments.arguments(new int[]{4, 4, 2}, new int[]{2}));
        out.add(Arguments.arguments(new int[]{4, 4, 4}, new int[]{}));

        return out.stream();
    }

    @ParameterizedTest
    @MethodSource("dataForCheckNewArrayAfter4")
    void shouldReturnNewArrayAfterEndOf4(int[] array, int[] result) {
        Assertions.assertArrayEquals(result, Main.newArrayAfter4(array));
    }

    @Test
    void throwExceptionIfArrayDoesntContainsNumber4() {
        Assertions.assertThrows(RuntimeException.class, () -> Main.newArrayAfter4(new int[]{1, 1, 1}));
    }

    @Test
    void throwExceptionIfArrayIsEmpty() {
        Assertions.assertThrows(RuntimeException.class, () -> Main.newArrayAfter4(new int[]{}));
    }
}