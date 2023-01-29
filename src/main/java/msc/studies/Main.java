package msc.studies;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Plotter plotter = new Plotter();
        try {
            new Thread(() -> {
                runPlotter(plotter);
            }).start();
            Multiplier multiplier = new Multiplier(plotter);
            multiplier.run();
            plotter.setRunning(false);
        } catch (Exception e) {
            printErr(e);
            plotter.setRunning(false);
        }
    }

    /**
     * Helper method for running the plotter during application execution.
     * @param plotter a plotter object.
     */
    private static void runPlotter(Plotter plotter) {
        try {
            plotter.saveMemoryAndCpuUsage();
        } catch (Exception e) {
            printErr(e);
        }
    }

    private static void printErr(Exception e) {
        System.err.println("ERROR" + e.getClass() + " " + e.getMessage());
    }
}