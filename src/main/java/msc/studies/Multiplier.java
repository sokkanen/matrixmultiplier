package msc.studies;

import org.ejml.data.FMatrix;
import org.ejml.data.MatrixType;
import org.ejml.simple.SimpleMatrix;

import java.io.IOException;
import java.util.Random;

public class Multiplier {

    private static int dimension = 1000000;
    static String filename = "result.csv";

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public SimpleMatrix run() throws IOException {
        long totalStartTime = System.currentTimeMillis();
        Random rnd = new Random();
        SimpleMatrix[] matrices = createMatrices(rnd);
        SimpleMatrix result = multiplyMatrices(matrices);

        long writeStartTime = System.currentTimeMillis();
        //result.saveToFileCSV(filename);
        long writeDoneTime = System.currentTimeMillis();
        System.out.printf("Wrote results to %s in %d milliseconds\n", filename, (writeDoneTime - writeStartTime));

        long totalEndTime = System.currentTimeMillis();
        System.out.printf("\nTotal execution time: %d milliseconds\n", (totalEndTime - totalStartTime));
        return result;
    }

    public SimpleMatrix multiplyMatrixWithVector(FMatrix matrix, FMatrix vector) {
        SimpleMatrix res = new SimpleMatrix(matrix.getNumRows(), 1, MatrixType.FDRM);
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

    public SimpleMatrix multiplyMatrices(SimpleMatrix[] matrices){
        System.out.println("Multiplying matrices...");
        long startTime = System.currentTimeMillis();
        FMatrix a = matrices[0].getMatrix();
        FMatrix b = matrices[1].getMatrix();
        FMatrix c = matrices[2].getMatrix();

        SimpleMatrix intermediate = multiplyMatrixWithVector(b, c);
        SimpleMatrix result = multiplyMatrixWithVector(a, intermediate.getMatrix());
        long endTime = System.currentTimeMillis();
        System.out.printf("Multiplied matrix calculated in %d milliseconds\n", (endTime - startTime));

        return result;
    }
    public SimpleMatrix[] createMatrices(Random rnd) {
        long startTime = System.currentTimeMillis();
        SimpleMatrix first = SimpleMatrix.random_FDRM(dimension, dimension / 1000, 0, 1, rnd);
        long firstCreatedTime = System.currentTimeMillis();
        System.out.printf("First matrix created in %d milliseconds\n", (firstCreatedTime - startTime));

        SimpleMatrix second = SimpleMatrix.random_FDRM(dimension / 1000, dimension, 0, 1, rnd);
        long secondCreatedTime = System.currentTimeMillis();
        System.out.printf("Second matrix created in %d milliseconds\n", (secondCreatedTime - firstCreatedTime));

        SimpleMatrix third = SimpleMatrix.random_FDRM(dimension, 1, 0, 1, rnd);
        long thirdCreatedTime = System.currentTimeMillis();
        System.out.printf("Third matrix created in %d milliseconds\n", (thirdCreatedTime - secondCreatedTime));

        SimpleMatrix[] matrices = new SimpleMatrix[]{first, second, third};
        return matrices;
    }
}
