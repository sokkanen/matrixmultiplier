package msc.studies;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Plotter plotter = new Plotter();
        try {
            new Thread(() -> {
                runPlotter(plotter);
            }).start();
            Multiplier sm = new Multiplier(plotter);
            sm.run();
            plotter.setRunning(false);
        } catch (Exception exception) {
            System.err.println("ERROR" + exception.getClass() + " " + exception.getMessage());
            plotter.setRunning(false);
        }
    }

    private static void runPlotter(Plotter plotter) {
        try {
            plotter.saveMemoryAndCpuUsage();
        } catch (Exception e) {
            System.err.println("ERROR" + e.getClass() + " " + e.getMessage());
        }
    }
}