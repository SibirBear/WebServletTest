package org.example;

public class Counter {

    private static int idCounter;

    public static void initCounter() {
        idCounter = 0;
    }

    public static int getId() {
        return ++idCounter;
    }
}
