package msc.studies;

import com.github.sh0nk.matplotlib4j.NumpyUtils;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import org.ejml.data.FMatrix;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Plotter {
    private Runtime rt;
    private OperatingSystemMXBean osBean;
    private List<Double> cpuUtilization;
    private List<Long> totalMemory;
    private List<Long> freeMemory;
    private List<Long> usedMemory;
    private final int MB = 1048576;
    private boolean running;

    public Plotter() {
        this.rt = Runtime.getRuntime();
        this.osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        this.cpuUtilization = new ArrayList<>();
        this.usedMemory = new ArrayList<>();
        this.freeMemory = new ArrayList<>();
        this.totalMemory = new ArrayList<>();
        this.running = true;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void plotMatrix(FMatrix mtrx) throws IOException, PythonExecutionException {
        List<Float> x = new ArrayList<>();
        List<Double> y;

        for (int i = 0; i < mtrx.getNumRows(); i++) {
            for (int j = 0; j < mtrx.getNumCols(); j++) {
                x.add(mtrx.get(i, j));
            }
        }

        x = x.stream().sorted().collect(Collectors.toList());
        y = NumpyUtils.arange(0, 1, 0.0000001);

        Plot plt = Plot.create();
        plt.plot().add(x, y, "o").label("element");
        plt.legend().loc("upper right");
        plt.title("Matrix A");
        plt.xlabel("VALUE");
        plt.ylabel("ECDF");
        plt.show();
    }

    public void plotMemory() throws IOException, PythonExecutionException {
        Plot plt = Plot.create();
        plt.plot().add(this.usedMemory).color("blue").label("Used memory sed in MB");
        plt.plot().add(this.freeMemory).color("red").label("Free memory in MB");
        plt.plot().add(this.totalMemory).color("yellow").label("Total in MB");
        plt.legend().loc("upper right");
        plt.title("Memory usage (Max. memory 32GB)");
        plt.show();
    }

    public void plotCPU() throws IOException, PythonExecutionException{
        Plot plt = Plot.create();
        plt.plot().add(this.cpuUtilization).color("red").label("CPU Utlization");
        plt.legend().loc("upper right");
        plt.title("CPU (Thread) utilization during application runtime.");
        plt.show();
    }

    public void saveMemoryAndCpuUsage() throws IOException, PythonExecutionException, InterruptedException{
        while(running) {
            this.cpuUtilization.add(osBean.getSystemLoadAverage());
            this.usedMemory.add((rt.totalMemory() - rt.freeMemory()) / MB);
            this.totalMemory.add(rt.totalMemory() / MB);
            this.freeMemory.add((rt.freeMemory() / MB));
            // Sleeping for 100 millis
            Thread.sleep(100);
        }
        plotMemory();
        plotCPU();
    }
}
