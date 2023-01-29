package msc.studies;

import org.ejml.data.FMatrix;
import org.ejml.data.MatrixType;
import org.ejml.simple.SimpleMatrix;

import java.io.IOException;
import java.util.Random;

public class Multiplier {

    private int dimension = 1000000;
    private static final String filename = "result.csv";
    private Plotter plotter;

    public Multiplier(Plotter plotter) {
        this.plotter = plotter;
    }

    public Multiplier() {
        this.plotter = new Plotter();
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    /**
     * Runs the application and saves the result on disk.
     * @return SimpleMatrix (mainly for testing purposes)
     * @throws IOException
     */
    public void run() throws IOException {
        long totalStartTime = System.currentTimeMillis();
        Random rnd = new Random();
        SimpleMatrix[] matrices = createMatrices(rnd, false);
        SimpleMatrix result = multiplyMatrices(matrices);

        long writeStartTime = System.currentTimeMillis();
        result.saveToFileCSV(filename);
        long writeDoneTime = System.currentTimeMillis();
        System.out.printf("Wrote results to %s in %d milliseconds\n", filename, (writeDoneTime - writeStartTime));

        long totalEndTime = System.currentTimeMillis();
        System.out.printf("\nTotal execution time: %d milliseconds\n", (totalEndTime - totalStartTime));
    }

    /**
     * Multiplies a matrix with a vector.
     * Matrix and vector need to match so that
     * matrix.getNumRows() == vector.getNumCols()
     *
     * @param matrix input matrix of any size
     * @param vector input vector.
     * @param isFinal dictates whether to create a DDRM or a FDRM type of matrix.
     * @return SimpleMatrix
     */
    public SimpleMatrix multiplyMatrixWithVector(FMatrix matrix, FMatrix vector, boolean isFinal) {
        SimpleMatrix res = new SimpleMatrix(matrix.getNumRows(), 1, isFinal ? MatrixType.DDRM : MatrixType.FDRM);
        for (int i = 0; i < matrix.getNumRows(); i++) {
            for (int j = 0; j < matrix.getNumCols(); j++) {
                float matrixValue = matrix.get(i, j);
                float vectorValue = vector.get(j, 0);
                float previousValue = (float) res.get(i, 0);
                float newValue = matrixValue * vectorValue;
                newValue += previousValue;
                res.set(i, 0, newValue);
            }
        }
        return res;
    }

    /**
     * Multiplies three matrices, of which the last one is a vector.
     * @param matrices SimpleMatrix[]
     * @return SimpleMatrix
     */
    public SimpleMatrix multiplyMatrices(SimpleMatrix[] matrices){
        System.out.println("Multiplying matrices...");
        long startTime = System.currentTimeMillis();
        FMatrix a = matrices[0].getMatrix();
        FMatrix b = matrices[1].getMatrix();
        FMatrix c = matrices[2].getMatrix();

        SimpleMatrix intermediate = multiplyMatrixWithVector(b, c, false);
        SimpleMatrix result = multiplyMatrixWithVector(a, intermediate.getMatrix(), true);
        long endTime = System.currentTimeMillis();
        System.out.printf("Multiplied matrix calculated in %d milliseconds\n", (endTime - startTime));

        return result;
    }

    /**
     * Creates 3 matrices filled with random floats between 0 and 1.
     * @param rnd Java.Random instance
     * @return SimpleMatrix[] containing three created matrices.
     */
    public SimpleMatrix[] createMatrices(Random rnd, boolean plot) {
        long startTime = System.currentTimeMillis();
        SimpleMatrix first = SimpleMatrix.random_FDRM(dimension, dimension / 1000, 0, 1, rnd);
        long firstCreatedTime = System.currentTimeMillis();
        System.out.printf("First matrix created in %d milliseconds\n", (firstCreatedTime - startTime));
        if (plot) {
            try {
                this.plotter.plotMatrix(first.getMatrix());
            } catch (Exception e) {
                System.err.println("Error in plotting Matrix: " + e.getMessage());
            }
        }
        SimpleMatrix second = SimpleMatrix.random_FDRM(dimension / 1000, dimension, 0, 1, rnd);
        long secondCreatedTime = System.currentTimeMillis();
        System.out.printf("Second matrix created in %d milliseconds\n", (secondCreatedTime - firstCreatedTime));

        SimpleMatrix third = SimpleMatrix.random_FDRM(dimension, 1, 0, 1, rnd);
        long thirdCreatedTime = System.currentTimeMillis();
        System.out.printf("Third matrix created in %d milliseconds\n", (thirdCreatedTime - secondCreatedTime));

        return new SimpleMatrix[]{first, second, third};
    }
}
