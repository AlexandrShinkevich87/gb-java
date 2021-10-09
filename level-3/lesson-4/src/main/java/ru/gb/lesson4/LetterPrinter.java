package ru.gb.lesson4;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LetterPrinter implements Runnable {

    private final char startFromLetter = 'A';

    private static final int NUMBER_OF_PRINTS = 5;
    private final Object lock = new Object();

    private char currentLetter;
    private final char nextLetter;


    public LetterPrinter(char currentLetter, char nextLetter) {
        this.currentLetter = currentLetter;
        this.nextLetter = nextLetter;
    }

    @SneakyThrows
    @Override
    public void run() {
        synchronized (lock) {
            for (int i = 0; i < NUMBER_OF_PRINTS; i++) {
                while (startFromLetter != currentLetter) {
                    log.info("{}. {} -> {}", startFromLetter, currentLetter, nextLetter);
                    lock.wait();
                }
                log.info(String.valueOf(currentLetter));
                currentLetter = nextLetter;
                lock.notifyAll();
            }
        }
    }
}
