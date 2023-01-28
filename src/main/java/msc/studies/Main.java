package msc.studies;

import org.ejml.simple.SimpleMatrix;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Multiplier sm = new Multiplier();
            SimpleMatrix res = sm.run();
        } catch (IOException exception) {
            System.err.println("ERROR" + exception.getMessage());
        }
    }
}