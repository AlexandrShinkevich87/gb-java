package ru.gb.race;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MainClass {
    public static final int CARS_COUNT = 4;
    private static final List<Thread> CARS_FOR_RACE = new ArrayList<>();

    @SneakyThrows
    public static void main(String[] args) {
        log.info("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(CARS_COUNT, new Road(60), new Tunnel(CARS_COUNT / 2), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }
        for (int i = 0; i < cars.length; i++) {
            Thread carArrivedToStart = new Thread(cars[i]);
            CARS_FOR_RACE.add(carArrivedToStart);
            carArrivedToStart.start();
        }
        for (int i = 0; i < cars.length; i++) {
            CARS_FOR_RACE.get(i).join();
        }

        log.info("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        log.info("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}
