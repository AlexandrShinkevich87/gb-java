package ru.gb;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyTest {
    @BeforeSuite
    public void printBefore() {
        log.info("Before");
    }

    @Test(priority = 3)
    public void print3() {
        log.info("3");
    }

    @Test(priority = 1)
    public void print1() {
        log.info("1");
    }

    @Test(priority = 2)
    public void print2() {
        log.info("2");
    }

    @AfterSuite
    public void printAfter() {
        log.info("After");
    }
}
