package ru.gb.race;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

@Slf4j
public class Tunnel extends Stage {
    private final Semaphore semaphore;

    public Tunnel(int numberOfParticipants) {
        this.semaphore = new Semaphore(numberOfParticipants);
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
    }

    @Override
    public void go(Car c) {
        try {
            try {
                log.info(c.getName() + " готовится к этапу(ждет): " + description);
                semaphore.acquire();
                log.info(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            } finally {
                log.info(c.getName() + " закончил этап: " + description);
                semaphore.release();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
