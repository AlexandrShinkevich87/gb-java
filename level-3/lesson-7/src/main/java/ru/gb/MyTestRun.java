package ru.gb;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MyTestRun {

    public static void main(String[] args) {
        start(MyTest.class);
//        start("ru.gb.MyTest");
    }

    public static void start(Class clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Class не может быть null");
        }

        int countBeforeAnnotation = 0;
        int countAfterAnnotation = 0;
        Map<Method, Integer> methodsForTest = new HashMap<>();
        Method[] methods = clazz.getDeclaredMethods();

        for (Method m : methods) {
            if (m.isAnnotationPresent(BeforeSuite.class)) {
                countAfterAnnotation++;
                methodsForTest.put(m, Integer.MIN_VALUE);
            }
            if (m.isAnnotationPresent(AfterSuite.class)) {
                countBeforeAnnotation++;
                methodsForTest.put(m, Integer.MAX_VALUE);
            }
            if (m.isAnnotationPresent(Test.class)) {
                methodsForTest.put(m, m.getAnnotation(Test.class).priority());
            }
        }

        if (countAfterAnnotation > 1 || countBeforeAnnotation > 1) {
            throw new IllegalArgumentException(" анотаций AfterSuit||BeforeSuit в классе больше 1");
        }

        methodsForTest.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(methodIntegerEntry -> {
                    try {
                        methodIntegerEntry.getKey().invoke(clazz.newInstance());
                    } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                        e.printStackTrace();
                    }
                });
    }

    public static void start(String className) {
        Class c = null;
        try {
            c = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        start(c);
    }
}
