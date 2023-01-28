package msc.studies;

import org.ejml.simple.SimpleMatrix;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        printMemoryUsage();
        try {
            Multiplier sm = new Multiplier();
            SimpleMatrix res = sm.run();
        } catch (IOException exception) {
            System.err.println("ERROR" + exception.getMessage());
        }
        printMemoryUsage();
    }

    public static void printMemoryUsage() {
        int MB = 1048576;

        Runtime rt = Runtime.getRuntime();
        System.out.println("\nJVM Heap stats in MB");
        System.out.printf("Total: %d\n", (rt.totalMemory() / MB));
        System.out.printf("Free: %d\n", (rt.freeMemory() / MB));
        System.out.printf("Used: %d\n", (rt.totalMemory() - rt.freeMemory()) / MB);
        System.out.printf("Max: %d\n\n", (rt.maxMemory() / MB));
    }
}