package ru.gb.box;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Lesson1Task2 {

    public static void main(String[] args) {
        Apple apple = new Apple();
        Orange orange = new Orange();

        Box<Apple> boxApple = new Box<>();
        Box<Orange> boxOrange = new Box<>();

        boxApple.add(apple);
        boxApple.add(apple);
        boxApple.add(apple);
        boxOrange.add(orange);

        boxOrange.add(orange);
        boxOrange.add(orange);
        boxOrange.add(orange);
        boxOrange.add(orange);

        int compareBoxes = boxApple.compareTo(boxOrange);
        if (compareBoxes == 0) {
            log.info("Коробка с apple weight {} и коробка с orange weight {} одинаковы",
                    boxApple.getWeight(), boxOrange.getWeight());
        } else if (compareBoxes > 0) {
            log.info("Коробка с apple weight {} БОЛЬШЕ коробки с orange weight {} одинаковы",
                    boxApple.getWeight(), boxOrange.getWeight());
        } else if (compareBoxes < 0) {
            log.info("Коробка с apple weight {} МЕНЬШЕ коробки с orange weight {} одинаковы",
                    boxApple.getWeight(), boxOrange.getWeight());
        }

        log.info("две коробки равны? {}", boxApple.equals(boxOrange));

        log.info("всыпаем яблоки в нашу коробоку с другой");
        Box<Apple> otherBoxApple = new Box<>();
        otherBoxApple.add(List.of(apple, apple, apple, apple));
        log.info("вес чужой коробки с яблоками ДО персыпания {}", otherBoxApple.getWeight());

        log.info("вес коробки с яблоками ДО персыпания {}", boxApple.getWeight());
        boxApple.pourFruitFrom(otherBoxApple);
        log.info("вес коробки с яблоками ПОСЛЕ персыпания {}", boxApple.getWeight());
        log.info("теперь чужая коробка с яблоками пуста, т.к. вес ее равен {}", otherBoxApple.getWeight());

    }
}
