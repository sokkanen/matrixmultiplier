import msc.studies.Multiplier;
import org.ejml.data.MatrixType;
import org.ejml.simple.SimpleMatrix;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MultiplierTest {

    Multiplier ml = new Multiplier();
    SimpleMatrix expected;
    SimpleMatrix a, b, c;

    @BeforeAll
    public void beforeAll() {
        ml.setDimension(5);
        float[] values = new float[] { 165.0f, 165.0f, 165.0f, 165.0f, 165.0f};
        expected = new SimpleMatrix(5, 1, false, values);
    }
    @Test
    public void shouldMultiplyCorrectly() {
        SimpleMatrix[] matrices = createMatrices();
        SimpleMatrix result = ml.multiplyMatrices(matrices);
        assertEquals(expected.toString(), result.toString());
    }

    private SimpleMatrix[] createMatrices() {
        SimpleMatrix a = new SimpleMatrix(5, 2, MatrixType.FDRM);
        SimpleMatrix b = new SimpleMatrix(2, 5, MatrixType.FDRM);
        SimpleMatrix c = new SimpleMatrix(5, 1, MatrixType.FDRM);

        a.setRow(0, 0, 1.0f, 2.0f);
        a.setRow(1, 0, 1.0f, 2.0f);
        a.setRow(2, 0, 1.0f, 2.0f);
        a.setRow(3, 0, 1.0f, 2.0f);
        a.setRow(4, 0, 1.0f, 2.0f);

        b.setRow(0, 0, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f);
        b.setRow(1, 0, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f);

        c.setRow(0, 0, 1.0f);
        c.setRow(1, 0, 2.0f);
        c.setRow(2, 0, 3.0f);
        c.setRow(3, 0, 4.0f);
        c.setRow(4, 0, 5.0f);
        SimpleMatrix[] matrices = new SimpleMatrix[] {a, b, c};
        return matrices;
    }
}
