package com.adammcquistan;

import com.adammcquistan.models.Matrix;
import com.adammcquistan.models.Vector;

public class Main {

    public static void main(String[] args) {
        Vector v1 = new Vector();
        Vector v2 = new Vector();
        for (int i = 0; i < 4; i++) {
            v1.append(i * 1.0d);
            v2.append(i * 10.0d);
        }
        
        Vector vAdd = v1.add(v2);
        Vector vSub = v1.subtract(v2);
        Vector vMult = v1.multiply(v2);
        double vDot = v1.dot(v2);
        double normL1 = v1.normL1();
        double normL2 = v1.normL2();
        double normMax = v1.normMax();
        
        Matrix m1 = new Matrix();
        m1.appendRow(v1);
        m1.appendRow(v2);
        m1.appendRow(vAdd);
        m1.appendRow(vSub);
        m1.appendRow(vMult);
        m1.appendRow(v1.add(100));
        m1.appendRow(v1.add(200));
        m1.appendRow(v1.add(300));
        
        Matrix m2 = new Matrix();
        m2.appendRow(v1);
        m2.appendRow(v2);
        
        Matrix m3 = new Matrix(
                new double[][] {
                    { 0.0, 1.0, 2.0 },
                    { 3.0, 4.0, 5.0 }
                }
        );

        System.out.println("v1 " + v1);
        System.out.println("v2 " + v2);
        System.out.println("vAdd " + vAdd);
        System.out.println("vSub " + vSub);
        System.out.println("vMult " + vMult);
        System.out.println("vDot " + vDot);
        System.out.println("normL1 " + normL1);
        System.out.println("normL2 " + normL2);
        System.out.println("normMax " + normMax);
        
        System.out.println(m1);
        System.out.println(m2);
        System.out.println(m3);
    }

}
