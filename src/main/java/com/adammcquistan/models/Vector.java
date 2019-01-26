package com.adammcquistan.models;

import java.util.StringJoiner;

public class Vector {
    private static final int INIT_CAPACITY = 100;
    private static final int MAX_CAPACITY = 1000000;

    private int currentIdx = -1;
    private double[] data;
    
    public Vector() {
        data = new double[INIT_CAPACITY];
    }
    
    public Vector(int size) {
        data = new double[size];
    }
    
    public Vector(double[] arr) {
        data = new double[arr.length];
        System.arraycopy(arr, 0, data, 0, arr.length);
        currentIdx = arr.length - 1;
    }
    
    public boolean append(double item) {
        if (currentIdx > (data.length - 10)) {
            int newLength = (int) 1.5 * data.length;
            if (MAX_CAPACITY < newLength) {
                return false;
            }
            
            double[] newData = new double[newLength];
            System.arraycopy(data, 0, newData, 0, data.length);
            data = newData;
        }
        data[++currentIdx] = item;
        return true;
    }
    
    public void fill(double x) {
        for (int i = 0; i < this.size(); i++) {
            this.data[i] = x;
        }
    }
    
    public double get(int idx) {
        if (idx > currentIdx) {
            throw new ArrayIndexOutOfBoundsException();
        }
        
        return this.data[idx];
    }
    
    public int size() {
        return currentIdx + 1;
    }
    
    public Vector add(Vector v) {
        if (v.size() == 1) {
            return add(v.get(0));
        }
        checkSizeCompatibility(v);

        Vector result = new Vector(v.size());
        for (int i = 0; i < this.size(); i++) {
            result.append(this.data[i] + v.data[i]);
        }
        return result;
    }
    
    public Vector add(double x) {
        Vector result = new Vector(this.size());
        for (int i = 0; i < this.size(); i++) {
            result.append(this.data[i] + x);
        }
        return result;
    }
    
    public Vector subtract(Vector v) {
        if (v.size() == 1) {
            return subtract(v.get(0));
        }
        checkSizeCompatibility(v);
        
        Vector result = new Vector(v.size());
        for (int i = 0; i < this.size(); i++) {
            result.append(this.data[i] - v.data[i]);
        }
        return result;
    }
    
    public Vector subtract(double x) {
        Vector result = new Vector(this.size());
        for (int i = 0; i < this.size(); i++) {
            result.append(this.data[i] - x);
        }
        return result;
    }
    
    public Vector multiply(Vector v) {
        if (v.size() == 1) {
            return multiply(v.get(0));
        }
        
        checkSizeCompatibility(v);
        
        Vector result = new Vector(v.size());
        for (int i = 0; i < this.size(); i++) {
            result.append(this.data[i] * v.data[i]);
        }
        return result;
    }
    
    public Vector multiply(double x) {
        Vector result = new Vector(this.size());
        for (int i = 0; i < this.size(); i++) {
            result.append(this.data[i] * x);
        }
        return result;
    }
    
    public Vector divide(Vector v) {
        checkSizeCompatibility(v);
        
        Vector result = new Vector(v.size());
        for (int i = 0; i < this.size(); i++) {
            result.append(this.data[i] / v.data[i]);
        }
        return result;
    }
    
    public Vector divide(double x) {
        Vector result = new Vector(this.size());
        for (int i = 0; i < this.size(); i++) {
            result.append(this.data[i] / x);
        }
        return result;
    }

    public double dot(Vector v) {
        checkSizeCompatibility(v);
        
        double product = 0.0d;
        for (int i = 0; i < this.size(); i++) {
            product += (this.data[i] * v.data[i]);
        }
        return product;
    }
    
    public double normL1() {
        double norm = 0.0d;
        for (int i = 0; i < this.size(); i++) {
            norm += Math.abs(this.data[i]);
        }
        return norm;
    }
    
    public double normL2() {
        return Math.sqrt(this.sumOfSquares());
    }
    
    public double normMax() {
        double absMax = Math.abs(this.data[0]);
        for (int i = 0; i < this.size(); i++) {
            double cur = Math.abs(this.data[i]);
            if (cur > absMax) {
                absMax = cur;
            }
        }
        return absMax;
    }
    
    public double sum() {
        double s = 0.0d;
        for (int i = 0; i < this.size(); i++) {
            s += this.data[i];
        }
        return s;
    }
    
    public double sumOfSquares() {
        double ss = 0.0d;
        for (int i = 0; i < this.size(); i++) {
            ss += (this.data[i] * this.data[i]);
        }
        return ss;
    }
    
    private void checkSizeCompatibility(Vector v) throws ArithmeticException {
        if (this.size() != v.size())
            throw new ArithmeticException("Incompatible vector sizes " + v.size() + " and " + this.size());
    }
    
    public double[] toArray() {
        double[] result = new double[this.size()];
        for (int i = 0; i < this.size(); i++) {
            result[i] = this.get(i);
        }
        return result;
    }
    
    @Override
    public String toString() {
        String s = "Vector[" + this.size() + "] => ";
        StringJoiner sj = new StringJoiner(",");
        if (this.size() > 8) {
            for (int i = 0; i < 3; i++) {
                sj.add(String.format("%10.4f", this.data[i]));
            }
            sj.add("...");
            for (int i = this.size() - 3; i < this.size(); i++) {
                sj.add(String.format("%10.4f", this.data[i]));
            }
        } else {
            for (int i = 0; i < this.size(); i++) {
                sj.add(String.format("%10.4f", this.data[i]));
            }
        }
        return s + "[" + sj + "]";
    }
}
