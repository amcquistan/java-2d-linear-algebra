package com.adammcquistan.models;

import java.util.Arrays;
import java.util.StringJoiner;

public class Matrix {
    private static final int INIT_CAPACITY = 100;
    private static final int MAX_CAPACITY = 1000000;
    private Vector[] data;
    private int curRowIdx = -1;
    
    public Matrix() {
        this.data = new Vector[INIT_CAPACITY];
    }
    
    public Matrix(double[][] arr) {
        this();
        for (int rowIdx = 0; rowIdx < arr.length; rowIdx++) {
            double[] row = arr[rowIdx];
            appendRow(new Vector(row));
        }
    }

    public boolean appendRow(Vector v) {
        if (this.cols() != 0 && this.cols() != v.size()) {
            return false;
        }
        if (curRowIdx > (data.length - 10)) {
            int newLength = (int) 1.5 * data.length;
            if (MAX_CAPACITY < newLength) {
                return false;
            }
            
            Vector[] newData = new Vector[newLength];
            System.arraycopy(data, 0, newData, 0, data.length);
            data = newData;
        }
        data[++curRowIdx] = new Vector(v.toArray());
        return true;
    }
    
    public Vector getRow(int rowIdx) {
        if (rowIdx > curRowIdx) {
            throw new ArrayIndexOutOfBoundsException();
        }
        
        return this.data[rowIdx];
    }
    
    public void replaceRow(int rowIdx, Vector v) {
        if (rowIdx > curRowIdx) {
            throw new ArrayIndexOutOfBoundsException();
        }
        
        if (this.cols() != v.size()) {
            throw new IllegalArgumentException("Vector size (" + v.size() + ") <> matrix columns (" + this.cols() + ")");
        }
        
        this.data[rowIdx] = new Vector(v.toArray());
    }
    
    public int rows() {
        return curRowIdx + 1;
    }
    
    public int cols() {
        if (curRowIdx < 0) {
            return 0;
        }
        return this.data[0].size();
    }
    
    public Matrix add(Matrix m) {
        checkSizeElementWiseCompatibility(m);
        Matrix result = new Matrix();
        for (int row = 0; row < this.rows(); row++) {
            Vector vResult = this.getRow(row).add(m.getRow(row));
            result.appendRow(vResult);
        }
        
        return result;
    }
    
    public Matrix add(Vector v) {
        if (v.size() != 1) {
            throw new ArithmeticException("Addition must be with vector of size 1");
        }
        Matrix result = new Matrix();
        for (int row = 0; row < this.rows(); row++) {
            result.appendRow(this.getRow(row).add(v.get(0)));
        }
        return result;
    }
    
    public Matrix add(double x) {
        Matrix result = new Matrix();
        for (int row = 0; row < this.rows(); row++) {
            result.appendRow(this.getRow(row).add(x));
        }
        return result;
    }
    
    public Matrix subtract(Matrix m) {
        checkSizeElementWiseCompatibility(m);
        Matrix result = new Matrix();
        for (int row = 0; row < this.rows(); row++) {
            Vector vResult = this.getRow(row).subtract(m.getRow(row));
            result.appendRow(vResult);
        }
        
        return result;
    }
    
    public Matrix subtract(Vector v) {
        if (v.size() != 1) {
            throw new ArithmeticException("Subtraction must be with vector of size 1");
        }
        Matrix result = new Matrix();
        for (int row = 0; row < this.rows(); row++) {
            result.appendRow(this.getRow(row).subtract(v.get(0)));
        }
        return result;
    }
    
    public Matrix subtract(double x) {
        Matrix result = new Matrix();
        for (int row = 0; row < this.rows(); row++) {
            result.appendRow(this.getRow(row).subtract(x));
        }
        return result;
    }
    
    public Matrix multiply(Matrix m) {
        checkSizeElementWiseCompatibility(m);
        Matrix result = new Matrix();
        for (int row = 0; row < this.rows(); row++) {
            Vector myVector = this.getRow(row);
            Vector theirVector = m.getRow(row);
            result.appendRow(myVector.multiply(theirVector));
        }
        return result;
    }
    
    public Matrix multiply(Vector v) {
        /* First try standard vector * matrix multiplication */
        if (v.size() == this.cols()) {
            Matrix result = new Matrix();
            for (int row = 0; row < this.rows(); row++) {
                result.appendRow(v.multiply(this.getRow(row)));
            }
            return result;
        }
        
        /* Vector may be of size one, in that case its intended as a scalar */
        if (v.size() != 1) {
            throw new ArithmeticException("Multiplication must be with vector of size 1");
        }
        Matrix result = new Matrix();
        for (int row = 0; row < this.rows(); row++) {
            Vector myVector = this.getRow(row);
            result.appendRow(myVector.multiply(v.get(0)));
        }
        return result;
    }
    
    public Matrix multiply(double x) {
        Matrix result = new Matrix();
        for (int row = 0; row < this.rows(); row++) {
            Vector myVector = this.getRow(row);
            result.appendRow(myVector.multiply(x));
        }
        return result;
    }

    public Matrix divide(Matrix m) {
        checkSizeElementWiseCompatibility(m);
        Matrix result = new Matrix();
        for (int row = 0; row < this.rows(); row++) {
            Vector myVector = this.getRow(row);
            Vector theirVector = m.getRow(row);
            result.appendRow(myVector.divide(theirVector));
        }
        return result;
    }
    
    public Matrix divide(Vector v) {
        if (v.size() != 1) {
            throw new ArithmeticException("Division must be with vector of size 1");
        }
        Matrix result = new Matrix();
        for (int row = 0; row < this.rows(); row++) {
            Vector myVector = this.getRow(row);
            result.appendRow(myVector.divide(v.get(0)));
        }
        return result;
    }
    
    public Matrix divide(double x) {
        Matrix result = new Matrix();
        for (int row = 0; row < this.rows(); row++) {
            Vector myVector = this.getRow(row);
            result.appendRow(myVector.divide(x));
        }
        return result;
    }
    
    
    public Matrix transpose() {
        double[][] original = this.toArray();
        double[][] transposed = new double[this.cols()][this.rows()];
        Matrix result = new Matrix();
        for (int col = 0; col < this.cols(); col++) {
            for (int row = 0; row < this.rows(); row++) {
                transposed[col][row] = original[row][col];
            }
            result.appendRow(new Vector(transposed[col]));
        }
        return result;
    }
    
    public Matrix T() {
        return this.transpose();
    }
    
    
    
    public Matrix matmul(Matrix m) {
        checkMatMulCompatibility(m);
        Matrix trans = m.T();
        double[][] resultArr = new double[this.rows()][m.cols()];
        for (int row = 0; row < this.rows(); row++) {
            Vector r = this.getRow(row);
            for (int col = 0; col < m.cols(); col++) {
                Vector v = trans.getRow(col);
                resultArr[row][col] = r.dot(v);
            }
        }
        return new Matrix(resultArr);
    }
    
    public static Matrix sortPivot(Matrix c, int row, int col) {
        Matrix m = new Matrix(c.toArray());
        
        double[][] rowToValueMap = new double[m.rows() - row][2];
        int rowToValueMapIdx = 0;
        for (int i = row; i < m.rows(); i++) {
            Vector w = m.getRow(i);
            rowToValueMap[rowToValueMapIdx++] = new double[]{ i, w.get(col) };
        }
        // reverse sort largest to smallest
        Arrays.sort(rowToValueMap, (a, b) -> Double.compare(b[1], a[1]));
        Matrix copyM = new Matrix(m.toArray());
        rowToValueMapIdx = 0;
        for (int i = row; i < m.rows(); i++) {
            int replacementIdx = (int) rowToValueMap[rowToValueMapIdx++][0];
            Vector replacement = m.getRow(replacementIdx);
            copyM.replaceRow(i, replacement);
        }
        
        return copyM;
    }
    
    public static Matrix sortPivots(Matrix c) {
        Matrix m = new Matrix(c.toArray());
        // first determine the pivot based off the first column to not have a zero
        int pivot = 0;
       
        for (int row = 0; row < m.rows(); row++) {
            Vector v = m.getRow(row);
            if (pivot < v.size()) {
                m = sortPivot(m, row, pivot++);
            }
        }
        
        return m;
    }
    
    public static Matrix makeIdentityMatrix(int n) {
        double[][] arr = new double[n][n];
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                arr[row][col] = row == col ? 1.0 : 0.0;
            }
        }
        return new Matrix(arr);
    }
    
    /**
     * Reduces the n x n matrix to row-echelon form carrying out the ops on it and
     * an identity matrix
     * @return a the matrix inverse
     */
    public Matrix getInverse() throws ArithmeticException {
        if (rows() != cols()) {
            throw new ArithmeticException("This matrix is not an n x n matrix it's a " + rows() + " x " + cols());
        }
        
        Matrix m = new Matrix(toArray());
        Matrix ident = Matrix.makeIdentityMatrix(m.rows());

        for (int row = 0; row < m.rows(); row++) {
            Vector pivotRow = m.getRow(row);
            Vector iV = ident.getRow(row);
            if (row == 0) {
                // make sure first element of first row vector is not 0
                // and if it is make it a 1
                if (pivotRow.get(row) == 0) {
                    pivotRow = pivotRow.add(1);
                    iV = iV.add(1);
                }
            }
            
            if (pivotRow.get(row) == 0) {
                // move the pivot row down and find another that is not zero in the pivot position
                for (int i = (m.rows() - 1); i > row; i--) {
                    Vector swapRow = m.getRow(i);
                    Vector swapIdentRow = ident.getRow(i);
                    if (swapRow.get(row) != 0) {
                        m.replaceRow(i, pivotRow);
                        ident.replaceRow(i, iV);
                        pivotRow = swapRow;
                        iV = swapIdentRow;
                        break;
                    }
                }
            }
            
            if (pivotRow.get(row) != 0) {
                // get first non-zero value into a 1
                double divisor = pivotRow.get(row);
                pivotRow = pivotRow.divide(divisor);
                iV = iV.divide(divisor);
                for (int i = (row + 1); i < m.rows(); i++) {
                    Vector rowV = m.getRow(i);
                    Vector rowIdent = ident.getRow(i);
                    double multiple = rowV.get(row);
                    rowV = rowV.subtract(pivotRow.multiply(multiple));
                    rowIdent = rowIdent.subtract(iV.multiply(multiple));
                    m.replaceRow(i, rowV);
                    ident.replaceRow(i, rowIdent);
                }
                for (int i = (row - 1); i >= 0; i--) {
                    Vector rowV = m.getRow(i);
                    Vector rowIdent = ident.getRow(i);
                    double multiple = rowV.get(row);
                    rowV = rowV.subtract(pivotRow.multiply(multiple));
                    rowIdent = rowIdent.subtract(iV.multiply(multiple));
                    m.replaceRow(i, rowV);
                    ident.replaceRow(i, rowIdent);
                }
            }
            
            if (pivotRow.get(row) == 0) {
                throw new ArithmeticException("Matrix is singular and not invertable");
            }
            
            m.replaceRow(row, pivotRow);
            ident.replaceRow(row, iV);
        }

        return ident;
    }
    
    /**
     * reduces the n x n square matrix to row echelon and looks to see 
     * if the last row is all 0s
     * @return true if singular, false if non-singular and invertable
     */
    public boolean isSingular() {
        if (this.rows() != this.cols()) {
            // not sure if I should return true / false or raise and exception
            return true;
        }
        

        
        return false;
    }
    
    
    private void checkSizeElementWiseCompatibility(Matrix m) throws ArithmeticException {
        if (this.rows() != m.rows()|| this.cols() != m.cols())
            throw new ArithmeticException("Incompatible matrix dimensions [" + this.rows() + " x " + this.cols() + "] vs [" + m.rows() + " x " + m.cols() + "]");
    }
    
    private void checkMatMulCompatibility(Matrix m) throws ArithmeticException {
        if (this.cols() != m.rows()) {
            throw new ArithmeticException("Incompatible matrix dimensions [" + this.rows() + " x " + this.cols() + "] vs [" + m.rows() + " x " + m.cols() + "]");
        }
    }
    
    public double[][] toArray() {
        double[][] arr = new double[this.rows()][];
        for (int row = 0; row < this.rows(); row++) {
            Vector v = this.getRow(row);
            arr[row] = v.toArray();
        }
        return arr;
    }
    
    @Override
    public String toString() {
        String s = "Matrix[" + rows() + " x " + cols() + "]\n  [\n";
        StringJoiner sj = new StringJoiner(",\n");

        int row = 0;
        for (Vector v : data) {
            if (v != null && v.size() > 0) {
                if (rows() > 6 && (row < 2 || row > (rows() - 3))) {
                    sj.add(String.format("%6d", row) + ": " + v);
                } else if (rows() > 6 && row == 2) {
                    sj.add("       ... ");
                } else if (rows() <= 6) {
                    sj.add(String.format("%6d", row) + ": " + v);
                }
                row++;
            }

        }

        
        return s + sj + "\n  ]";
    }
}
