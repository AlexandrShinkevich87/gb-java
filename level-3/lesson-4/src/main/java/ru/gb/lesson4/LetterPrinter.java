package ru.gb.lesson4;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LetterPrinter implements Runnable {

    private char currentLetterForStart;

    private static final int NUMBER_OF_PRINTS = 5;
    private final Object lock = new Object();

    public LetterPrinter(char currentLetterForStart) {
        this.currentLetterForStart = currentLetterForStart;
    }

    @SneakyThrows
    @Override
    public void run() {
    }

    @SneakyThrows
    public void print(char inboundLetter, char nextLetter) {
//        synchronized (this) {
        synchronized (lock) {
            for (int i = 0; i < NUMBER_OF_PRINTS; i++) {
                while (currentLetterForStart != inboundLetter) {
//                    log.info("{}. {} -> {}", currentLetterForStart, inboundLetter, nextLetter);
                    lock.wait();
                }
//                log.info(String.valueOf(inboundLetter));
//                Thread.sleep(100);
                System.out.print(inboundLetter);
                currentLetterForStart = nextLetter;
                lock.notifyAll();
            }
        }
    }

}
