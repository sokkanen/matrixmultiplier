package msc.studies;

import com.github.sh0nk.matplotlib4j.NumpyUtils;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import org.ejml.data.FMatrix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Plotter {

    public void plot(FMatrix mtrx) throws IOException, PythonExecutionException {
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
}
