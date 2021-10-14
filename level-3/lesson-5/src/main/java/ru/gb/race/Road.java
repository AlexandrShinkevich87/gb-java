package ru.gb.race;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Road extends Stage {

    public Road(int length) {
        this.length = length;
        this.description = "Дорога " + length + " метров";
    }

    @SneakyThrows
    @Override
    public void go(Car c) {
        log.info(c.getName() + " начал этап: " + description);
        Thread.sleep(length / c.getSpeed() * 1000);
        log.info(c.getName() + " закончил этап: " + description);

    }
}
