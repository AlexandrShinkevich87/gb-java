package ru.gb.lesson4;

import lombok.val;

public class Main {
    public static void main(String[] args) {
        char currentLetterForStartAndEnd = 'A';
        val printer = new LetterPrinter(currentLetterForStartAndEnd);
        new Thread(() -> printer.print(currentLetterForStartAndEnd, 'B')).start();
        new Thread(() -> printer.print('B', 'C')).start();
        new Thread(() -> printer.print('C', currentLetterForStartAndEnd)).start();

    }
}
