package com.adammcquistan.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.adammcquistan.models.Matrix;
import com.adammcquistan.models.Vector;

public class MatrixTest {

    private static double[][] arr;
    private static Matrix m;
    private static double[][] arr2;
    private static Matrix m2;
    private static final double ALLOWED_DELTA = 0.00001d;
    
    @BeforeAll
    public static void setUp() {
        arr = new double[][] {
            { 0.0, 1.0, 2.0 },
            { 3.0, 4.0, 5.0 }
        };
        m = new Matrix(arr);
        
        arr2 = new double[][] {
            { 0.0, 2.0, 4.0 },
            { 6.0, 8.0, 10.0 }
        };
        m2 = new Matrix(arr2);
    }

    @Test
    public void testMatrixCreationFromArray() {
        assertEquals(2, m.rows());
        assertEquals(3, m.cols());
        for (int row = 0; row < arr.length; row++) {
            double[] rowData = arr[row];
            Vector v = m.getRow(row);
            assertEquals(rowData.length, v.size());
            int idx = 0;
            for (double x : rowData) {
                assertEquals(x, v.get(idx++), ALLOWED_DELTA);
            }
        }
        
        assertEquals(2, m2.rows());
        assertEquals(3, m2.cols());
        for (int row = 0; row < arr2.length; row++) {
            double[] rowData = arr2[row];
            Vector v = m2.getRow(row);
            assertEquals(rowData.length, v.size());
            int idx = 0;
            for (double x : rowData) {
                assertEquals(x, v.get(idx++), ALLOWED_DELTA);
            }
        }
    }
    
    @Test
    public void testMatrixAddition() {
        Matrix result = m.add(m2);

        assertEquals(m.rows(), result.rows());
        assertEquals(m.cols(), result.cols());
        for (int row = 0; row < m.rows(); row++) {
            Vector resultVector = result.getRow(row);
            Vector mVector = m.getRow(row);
            Vector m2Vector = m2.getRow(row);
            assertEquals(mVector.size(), resultVector.size());
            for (int col = 0; col < mVector.size(); col++) {
                double exp = mVector.get(col) + m2Vector.get(col);
                assertEquals(exp, resultVector.get(col), ALLOWED_DELTA);
            }
        }
    }
    
    @Test
    public void testMatrixAddition_incompatibleDimensions() {
        double[][] incompatibleArr = new double[][] {
            { 1, 2 },
            { 3, 4 }
        };
        Matrix incompatibleMatrix = new Matrix(incompatibleArr);
        assertThrows(ArithmeticException.class, () -> {
            m.add(incompatibleMatrix);
        });
    }
    
    @Test
    public void testMatrixScalarAsVectorAddition() {
        Vector v = new Vector();
        double x = 2.0;
        v.append(2);
        Matrix result = m.add(v);
        
        assertEquals(m.rows(), result.rows());
        assertEquals(m.cols(), result.cols());
        for (int row = 0; row < m.rows(); row++) {
            Vector resultVector = result.getRow(row);
            Vector mVector = m.getRow(row);
            assertEquals(mVector.size(), resultVector.size());
            for (int col = 0; col < mVector.size(); col++) {
                double exp = mVector.get(col) + x;
                assertEquals(exp, resultVector.get(col), ALLOWED_DELTA);
            }
        }
    }
    
    @Test
    public void testMatrixScalarAddition() {
        double x = 2.0;
        Matrix result = m.add(x);
        
        assertEquals(m.rows(), result.rows());
        assertEquals(m.cols(), result.cols());
        for (int row = 0; row < m.rows(); row++) {
            Vector resultVector = result.getRow(row);
            Vector mVector = m.getRow(row);
            assertEquals(mVector.size(), resultVector.size());
            for (int col = 0; col < mVector.size(); col++) {
                double exp = mVector.get(col) + x;
                assertEquals(exp, resultVector.get(col), ALLOWED_DELTA);
            }
        }
    }
    
    @Test
    public void testMatrixSubtraction() {
        Matrix result = m.subtract(m2);

        assertEquals(m.rows(), result.rows());
        assertEquals(m.cols(), result.cols());
        for (int row = 0; row < m.rows(); row++) {
            Vector resultVector = result.getRow(row);
            Vector mVector = m.getRow(row);
            Vector m2Vector = m2.getRow(row);
            assertEquals(mVector.size(), resultVector.size());
            for (int col = 0; col < mVector.size(); col++) {
                double exp = mVector.get(col) - m2Vector.get(col);
                assertEquals(exp, resultVector.get(col), ALLOWED_DELTA);
            }
        }
    }
    
    @Test
    public void testMatrixScalarAsVectorSubtraction() {
        Vector v = new Vector();
        double x = 2.0;
        v.append(2);
        Matrix result = m.subtract(v);
        
        assertEquals(m.rows(), result.rows());
        assertEquals(m.cols(), result.cols());
        for (int row = 0; row < m.rows(); row++) {
            Vector resultVector = result.getRow(row);
            Vector mVector = m.getRow(row);
            assertEquals(mVector.size(), resultVector.size());
            for (int col = 0; col < mVector.size(); col++) {
                double exp = mVector.get(col) - x;
                assertEquals(exp, resultVector.get(col), ALLOWED_DELTA);
            }
        }
    }
    
    @Test
    public void testMatrixScalarSubtraction() {
        double x = 2.0;
        Matrix result = m.subtract(x);
        
        assertEquals(m.rows(), result.rows());
        assertEquals(m.cols(), result.cols());
        for (int row = 0; row < m.rows(); row++) {
            Vector resultVector = result.getRow(row);
            Vector mVector = m.getRow(row);
            assertEquals(mVector.size(), resultVector.size());
            for (int col = 0; col < mVector.size(); col++) {
                double exp = mVector.get(col) - x;
                assertEquals(exp, resultVector.get(col), ALLOWED_DELTA);
            }
        }
    }
    
    @Test
    public void testMatrixMultiplication () {
        double[][] expArr = new double[][] {
            { arr[0][0] * arr2[0][0], arr[0][1] * arr2[0][1], arr[0][2] * arr2[0][2] },
            { arr[1][0] * arr2[1][0], arr[1][1] * arr2[1][1], arr[1][2] * arr2[1][2] }
        };
        
        Matrix result = m.multiply(m2);
        assertEquals(m.rows(), result.rows());
        assertEquals(m.cols(), result.cols());
        
        for (int row = 0; row < expArr.length; row++) {
            Vector resultVector = result.getRow(row);
            double[] expRowData = expArr[row];
            assertEquals(expRowData.length, resultVector.size());
            assertEquals(expRowData[0], resultVector.get(0), ALLOWED_DELTA);
            assertEquals(expRowData[1], resultVector.get(1), ALLOWED_DELTA);
            assertEquals(expRowData[2], resultVector.get(2), ALLOWED_DELTA);
        }
    }
    
    @Test
    public void testMatrixScalarAsVectorMultiplication () {
        double x = 2.0;
        double[][] expArr = new double[][] {
            { arr[0][0] * x, arr[0][1] * x, arr[0][2] * x },
            { arr[1][0] * x, arr[1][1] * x, arr[1][2] * x }
        };
        
        Vector v = new Vector(new double[] { x });
        Matrix result = m.multiply(v);
        assertEquals(m.rows(), result.rows());
        assertEquals(m.cols(), result.cols());
        
        for (int row = 0; row < expArr.length; row++) {
            Vector resultVector = result.getRow(row);
            double[] expRowData = expArr[row];
            assertEquals(expRowData.length, resultVector.size());
            assertEquals(expRowData[0], resultVector.get(0), ALLOWED_DELTA);
            assertEquals(expRowData[1], resultVector.get(1), ALLOWED_DELTA);
            assertEquals(expRowData[2], resultVector.get(2), ALLOWED_DELTA);
        }
    }
    
    @Test
    public void testMatrixScalarMultiplication() {
        double x = 2.0;
        double[][] expArr = new double[][] {
            { arr[0][0] * x, arr[0][1] * x, arr[0][2] * x },
            { arr[1][0] * x, arr[1][1] * x, arr[1][2] * x }
        };
        
        Matrix result = m.multiply(x);
        assertEquals(m.rows(), result.rows());
        assertEquals(m.cols(), result.cols());
        
        for (int row = 0; row < expArr.length; row++) {
            Vector resultVector = result.getRow(row);
            double[] expRowData = expArr[row];
            assertEquals(expRowData.length, resultVector.size());
            assertEquals(expRowData[0], resultVector.get(0), ALLOWED_DELTA);
            assertEquals(expRowData[1], resultVector.get(1), ALLOWED_DELTA);
            assertEquals(expRowData[2], resultVector.get(2), ALLOWED_DELTA);
        }
    }
    
    @Test
    public void testMatrixDivision () {
        double[][] expArr = new double[][] {
            { arr[0][0] / arr2[0][0], arr[0][1] / arr2[0][1], arr[0][2] / arr2[0][2] },
            { arr[1][0] / arr2[1][0], arr[1][1] / arr2[1][1], arr[1][2] / arr2[1][2] }
        };
        
        Matrix result = m.divide(m2);
        assertEquals(m.rows(), result.rows());
        assertEquals(m.cols(), result.cols());
        
        for (int row = 0; row < expArr.length; row++) {
            Vector resultVector = result.getRow(row);
            double[] expRowData = expArr[row];
            assertEquals(expRowData.length, resultVector.size());
            assertEquals(expRowData[0], resultVector.get(0), ALLOWED_DELTA);
            assertEquals(expRowData[1], resultVector.get(1), ALLOWED_DELTA);
            assertEquals(expRowData[2], resultVector.get(2), ALLOWED_DELTA);
        }
    }
    
    @Test
    public void testMatrixScalarAsVectorDivision () {
        double x = 2.0;
        double[][] expArr = new double[][] {
            { arr[0][0] / x, arr[0][1] / x, arr[0][2] / x },
            { arr[1][0] / x, arr[1][1] / x, arr[1][2] / x }
        };
        
        Vector v = new Vector(new double[] { x });
        Matrix result = m.divide(v);
        assertEquals(m.rows(), result.rows());
        assertEquals(m.cols(), result.cols());
        
        for (int row = 0; row < expArr.length; row++) {
            Vector resultVector = result.getRow(row);
            double[] expRowData = expArr[row];
            assertEquals(expRowData.length, resultVector.size());
            assertEquals(expRowData[0], resultVector.get(0), ALLOWED_DELTA);
            assertEquals(expRowData[1], resultVector.get(1), ALLOWED_DELTA);
            assertEquals(expRowData[2], resultVector.get(2), ALLOWED_DELTA);
        }
    }
    
    @Test
    public void testMatrixScalarDivision() {
        double x = 2.0;
        double[][] expArr = new double[][] {
            { arr[0][0] / x, arr[0][1] / x, arr[0][2] / x },
            { arr[1][0] / x, arr[1][1] / x, arr[1][2] / x }
        };
        
        Matrix result = m.divide(x);
        assertEquals(m.rows(), result.rows());
        assertEquals(m.cols(), result.cols());
        
        for (int row = 0; row < expArr.length; row++) {
            Vector resultVector = result.getRow(row);
            double[] expRowData = expArr[row];
            assertEquals(expRowData.length, resultVector.size());
            assertEquals(expRowData[0], resultVector.get(0), ALLOWED_DELTA);
            assertEquals(expRowData[1], resultVector.get(1), ALLOWED_DELTA);
            assertEquals(expRowData[2], resultVector.get(2), ALLOWED_DELTA);
        }
    }
    
    @Test
    public void testMatrixTranspose() {
        Matrix result = m.transpose();
        double[][] expected = new double[][] {
            { 0.0, 3.0 },
            { 1.0, 4.0 },
            { 2.0, 5.0 }
        };
        
        assertEquals(3, result.rows());
        assertEquals(2, result.cols());
        for (int r = 0; r < expected.length; r++) {
            Vector rowVector = result.getRow(r);
            double[] rowData = expected[r];
            for (int c = 0; c < rowData.length; c++) {
                assertEquals(rowData[c], rowVector.get(c), ALLOWED_DELTA);
            }
        }
        
        Matrix backToOriginal = result.T();
        assertEquals(m.rows(), backToOriginal.rows());
        assertEquals(m.cols(), backToOriginal.cols());
        for (int r = 0; r < m.rows(); r++) {
            Vector origVector = m.getRow(r);
            Vector newOrigVector = backToOriginal.getRow(r);
            for (int c = 0; c < origVector.size(); c++) {
                assertEquals(origVector.get(c), newOrigVector.get(c), ALLOWED_DELTA);
            }
        }
        
        double[][] sqrArr = new double[][] {
            { 1, 2 },
            { 3, 4 }
        };
        double[][] expTransArr = new double[][] {
            { 1, 3 },
            { 2, 4 }
        };
        Matrix sqrM = new Matrix(sqrArr);
        Matrix sqrT = sqrM.T();
        assertEquals(2, sqrT.rows());
        assertEquals(2, sqrT.cols());
        for (int r = 0; r < 2; r++) {
            Vector v = sqrT.getRow(r);
            double[] rowData = expTransArr[r];
            for (int c = 0; c < 2; c++) {
                assertEquals(rowData[c], v.get(c), ALLOWED_DELTA);
            }
        }
    }
    
    @Test
    public void testMatrixMatMul() {
        double[][] expArr = new double[][] {
            { 10.0, 28.0 },
            { 28.0, 100.0 }
        };
        
        // Transpose m2 so its compatible with matrix multiplication
        Matrix m2T = m2.T();
        Matrix result = m.matmul(m2T);
        
        assertEquals(2, result.rows());
        assertEquals(2, result.cols());
        for (int r = 0; r < 2; r++) {
            Vector v = result.getRow(r);
            double[] rowData = expArr[r];
            for (int c = 0; c < 2; c++) {
                assertEquals(rowData[c], v.get(c), ALLOWED_DELTA);
            }
        }
    }
    
    @Test
    public void testMatrixMatMul_incompatibleDimensions() {
        assertThrows(ArithmeticException.class, () -> {
            m.matmul(m2);
        });
    }
    
    @Test
    public void testIdentityMatrix() {
        Matrix m2x2 = Matrix.makeIdentityMatrix(2);
        double[][] exp2x2 = new double[][] {
            { 1, 0 },
            { 0, 1 }
        };
        for (int i = 0; i < 2; i++) {
            Vector v = m2x2.getRow(i);
            double[] expRow = exp2x2[i];
            for (int j = 0; j < 2; j++) {
                assertEquals(expRow[j], v.get(j));
            }
        }
        
        Matrix m3x3 = Matrix.makeIdentityMatrix(3);
        double[][] exp3x3 = new double[][] {
            { 1, 0, 0 },
            { 0, 1, 0 },
            { 0, 0, 1 }
        };
        for (int i = 0; i < 2; i++) {
            Vector v = m3x3.getRow(i);
            double[] expRow = exp3x3[i];
            for (int j = 0; j < 2; j++) {
                assertEquals(expRow[j], v.get(j));
            }
        }
    }
    
    @Test
    public void testGetInverse() {
        Matrix m2x2 = new Matrix(new double[][] {
            { 2, 3 },
            { 1, 4 }
        });
        
        // 2   3   | 1    0   
        // 1   4   | 0    1   
        //   1/2R1
        //   R2 - R1
        // 1   3/2 | 1/2  0
        // 0   5/2 | -1/2 1  
        //   2/5 R2
        // 1   3/2 | 1/2  0    1/2 + (3/2)(1/5) = 1/2 + 3/10 = 8/10, 0 - (3/5) = -3/5
        // 0   1   | -1/5 2/5
        //   R1 - 3/2 R2
        // 1   0   | 11/20 -3/5
        // 0   1   | -1/5 2/5
        
        double[][] expM2x2 = new double[][] {
            { 4.0d / 5.0d,  -3.0d / 5.0d },
            { -1.0d / 5.0d, 2.0d / 5.0d }
        };
        Matrix m2x2Inv = m2x2.getInverse();
        for (int i = 0; i < m2x2.rows(); i++) {
            Vector v = m2x2Inv.getRow(i);
            double[] expRow = expM2x2[i];
            for (int j = 0; j < m2x2.cols(); j++) {
                assertEquals(expRow[j], v.get(j), ALLOWED_DELTA);
            }
        }
        
        Matrix ident2x2 = Matrix.makeIdentityMatrix(m2x2.rows());
        Matrix testInv2x2 = m2x2.matmul(m2x2Inv);
        for (int i = 0; i < ident2x2.rows(); i++) {
            Vector iV = ident2x2.getRow(i);
            Vector v = testInv2x2.getRow(i);
            for (int j = 0; j < ident2x2.cols(); j++) {
                assertEquals(iV.get(j), v.get(j), ALLOWED_DELTA);
            }
        }
        
        Matrix m3x3 = new Matrix(new double[][] {
            { 1, 1, 3 },
            { 1, 2, 4 },
            { 1, 1, 2 }
        });
        
        // 1   1   3   | 1    0    0 
        // 1   2   4   | 0    1    0
        // 1   1   2   | 0    0    1
        //   R2 - R1
        //   R3 - R1
        // 1   1   3   | 1    0    0 
        // 0   1   1   | -1   1    0
        // 0   0   -1  | -1   0    1
        //   -R3
        // 1   1   3   | 1    0    0 
        // 0   1   1   | -1   1    0
        // 0   0   1   | 1    0   -1
        //    R2 - R3
        // 1   1   3   | 1    0    0 
        // 0   1   0   | -2   1    1
        // 0   0   1   | 1    0   -1
        //    R1 - 3R3
        // 1   1   0   | -2   0    3 
        // 0   1   0   | -2   1    1
        // 0   0   1   | 1    0   -1
        //    R1 - R2
        // 1   0   0   | 0    -1   2 
        // 0   1   0   | -2   1    1
        // 0   0   1   | 1    0   -1
        
        double[][] exp3x3 = new double[][] {
            { 0.0d, -1.0d, 2.0d },
            { -2.0d, 1.0d, 1.0d },
            { 1.0d, 0.0d, -1.0d }
        };
        Matrix m3x3Inv = m3x3.getInverse();
        for (int i = 0; i < m3x3.rows(); i++) {
            Vector v = m3x3Inv.getRow(i);
            double[] expRow = exp3x3[i];
            for (int j = 0; j < m3x3.cols(); j++) {
                assertEquals(expRow[j], v.get(j), ALLOWED_DELTA);
            }
        }
        
        Matrix m3x3ExpIdent = Matrix.makeIdentityMatrix(3);
        Matrix m3x3Ident = m3x3.matmul(m3x3Inv);
        for (int i = 0; i < m3x3.rows(); i++) {
            Vector expV = m3x3ExpIdent.getRow(i);
            Vector v = m3x3Ident.getRow(i);
            for (int j = 0; j < m3x3.cols(); j++) {
                assertEquals(expV.get(j), v.get(j), ALLOWED_DELTA);
            }
        }
        

    }
    
    @Test
    public void testNonInvertibleSingularMatrix() {
        // note that row 3 is the linear combination of the sum row 1 + row 2
        // and thus not invertible (ie, singular)
        Matrix singular3x3 = new Matrix(new double[][] {
            { 1, 1, 3 },
            { 1, 2, 4 },
            { 2, 3, 7 }
        });
        
        assertThrows(ArithmeticException.class, () -> {
            singular3x3.getInverse();
        });
    }
    
    @Test
    public void testOrthoNormalize() {
        Matrix m3x3 = new Matrix(new double[][] {
            { 1, 2, 4 },
            { 0, 0, 5 },
            { 0, 3, 6 }
        });
        double[][] exp3x3 = new double[][] {
            { 1.0d, 0.0d, 0.0d },
            { 0.0d, 0.0d, 1.0d },
            { 0.0d, 1.0d, 0.0d }
        };
        Matrix o3x3 = m3x3.orthoNormalize();
        
        for (int i = 0; i < m3x3.rows(); i++) {
            Vector v = o3x3.getRow(i);
            double[] expRow = exp3x3[i];
            for (int j = 0; j < m3x3.cols(); j++) {
                assertEquals(expRow[j], v.get(j), ALLOWED_DELTA);
            }
        }
    }
}
