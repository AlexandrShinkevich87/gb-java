package ru.gb.lesson4;

public class Main {
    public static void main(String[] args) {
        char currentLetterForStartAndEnd = 'A';

        new Thread(() -> new LetterPrinter(currentLetterForStartAndEnd, 'B')).start();
        new Thread(() -> new LetterPrinter('B', 'C')).start();
        new Thread(() -> new LetterPrinter('C', currentLetterForStartAndEnd)).start();

    }
}
