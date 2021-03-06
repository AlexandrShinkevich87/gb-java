package ru.gb;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Slf4j
public class Lesson1Task1 {
    /**
     * 1. Написать метод, который меняет два элемента массива местами.(массив может быть любого ссылочного типа);
     *
     * @param args
     */
    public static void main(String[] args) {
        String[] a = {"Hello", "Goodbye"};
        swap(a, 0, 1);
        log.info("a:" + Arrays.toString(a));
        List<String> l = new ArrayList<>(Arrays.asList(a));
        swap(l, 0, 1);
        log.info("l:" + l);
    }

    public static <T> void swap(T[] a, int i, int j) {
        T t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static <T> void swap(List<T> l, int i, int j) {
        Collections.<T>swap(l, i, j);
    }
}
