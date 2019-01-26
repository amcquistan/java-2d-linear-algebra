package com.adammcquistan.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.adammcquistan.models.Vector;

public class VectorTest {
    
    private static Vector v;
    private static Vector v2;
    private static double[] arr;
    private static double[] arr2;
    
    @BeforeAll
    public static void setUp() {
        arr = new double[] { 1.0, 2.0, 3.0, 4.0 };
        v = new Vector(arr);
        arr2 = new double[] { 2.0, 4.0, 6.0, 8.0 };
        v2 = new Vector(arr2);
    }
    
   @Test
   public void testVectorCreationFromArray() {
       assertEquals(4, v.size());
       assertEquals(1.0, v.get(0));
       assertEquals(2.0, v.get(1));
       assertEquals(3.0, v.get(2));
       assertEquals(4.0, v.get(3));
   }
   
   @Test
   public void testVectorAddition_incompatibleDimensions() {
       Vector incompatibleVector = new Vector(new double[] { 1, 2 });
       assertThrows(ArithmeticException.class, () -> {
           v.add(incompatibleVector);
       });
   }
   
   @Test
   public void testVectorAddition() {
       Vector result = v.add(v2);
       assertEquals(4, result.size());
       double exp0 = arr[0] + arr2[0];
       double exp1 = arr[1] + arr2[1];
       double exp2 = arr[2] + arr2[2];
       double exp3 = arr[3] + arr2[3];
       assertEquals(exp0, result.get(0));
       assertEquals(exp1, result.get(1));
       assertEquals(exp2, result.get(2));
       assertEquals(exp3, result.get(3));
   }
   
   @Test
   public void testVectorScalarAddition() {
       double scalar = 2.0;
       Vector result = v.add(scalar);
       assertEquals(4, result.size());
       double exp0 = arr[0] + scalar;
       double exp1 = arr[1] + scalar;
       double exp2 = arr[2] + scalar;
       double exp3 = arr[3] + scalar;
       assertEquals(exp0, result.get(0));
       assertEquals(exp1, result.get(1));
       assertEquals(exp2, result.get(2));
       assertEquals(exp3, result.get(3));
   }
   
   @Test
   public void testVectorSubtraction() {
       Vector result = v.subtract(v2);
       double exp0 = arr[0] - arr2[0];
       double exp1 = arr[1] - arr2[1];
       double exp2 = arr[2] - arr2[2];
       double exp3 = arr[3] - arr2[3];
       assertEquals(exp0, result.get(0));
       assertEquals(exp1, result.get(1));
       assertEquals(exp2, result.get(2));
       assertEquals(exp3, result.get(3));
   }
   
   @Test
   public void testVectorScalarSubtraction() {
       double scalar = 2.0;
       Vector result = v.subtract(scalar);
       assertEquals(4, result.size());
       double exp0 = arr[0] - scalar;
       double exp1 = arr[1] - scalar;
       double exp2 = arr[2] - scalar;
       double exp3 = arr[3] - scalar;
       assertEquals(exp0, result.get(0));
       assertEquals(exp1, result.get(1));
       assertEquals(exp2, result.get(2));
       assertEquals(exp3, result.get(3));
   }
   
   @Test
   public void testVectorMultiplication() {
       Vector result = v.multiply(v2);
       double exp0 = arr[0] * arr2[0];
       double exp1 = arr[1] * arr2[1];
       double exp2 = arr[2] * arr2[2];
       double exp3 = arr[3] * arr2[3];
       assertEquals(exp0, result.get(0));
       assertEquals(exp1, result.get(1));
       assertEquals(exp2, result.get(2));
       assertEquals(exp3, result.get(3));
   }
   
   @Test
   public void testVectorScalarMultiplication() {
       double scalar = 2.0;
       Vector result = v.multiply(scalar);
       assertEquals(4, result.size());
       double exp0 = arr[0] * scalar;
       double exp1 = arr[1] * scalar;
       double exp2 = arr[2] * scalar;
       double exp3 = arr[3] * scalar;
       assertEquals(exp0, result.get(0));
       assertEquals(exp1, result.get(1));
       assertEquals(exp2, result.get(2));
       assertEquals(exp3, result.get(3));
   }
   
   @Test
   public void testVectorDivision() {
       Vector result = v.divide(v2);
       double exp0 = arr[0] / arr2[0];
       double exp1 = arr[1] / arr2[1];
       double exp2 = arr[2] / arr2[2];
       double exp3 = arr[3] / arr2[3];
       assertEquals(exp0, result.get(0));
       assertEquals(exp1, result.get(1));
       assertEquals(exp2, result.get(2));
       assertEquals(exp3, result.get(3));
   }
   
   @Test
   public void testVectorScalarDivision() {
       double scalar = 2.0;
       Vector result = v.divide(scalar);
       assertEquals(4, result.size());
       double exp0 = arr[0] / scalar;
       double exp1 = arr[1] / scalar;
       double exp2 = arr[2] / scalar;
       double exp3 = arr[3] / scalar;
       assertEquals(exp0, result.get(0));
       assertEquals(exp1, result.get(1));
       assertEquals(exp2, result.get(2));
       assertEquals(exp3, result.get(3));
   }
   
   @Test
   public void testVectorDotProduct() {
       double result = v.dot(v2);
       double p0 = arr[0] * arr2[0];
       double p1 = arr[1] * arr2[1];
       double p2 = arr[2] * arr2[2];
       double p3 = arr[3] * arr2[3];
       double dp = p0 + p1 + p2 + p3;
       assertEquals(dp, result);
   }
   
   @Test
   public void testVectorNormL1() {
       double result = v.normL1();
       double exp = 0.0;
       for (double x : arr) {
           exp += Math.abs(x);
       }
       assertEquals(exp, result);
   }
   
   @Test
   public void testVectorNormL2() {
       double result = v.normL2();
       double exp = 0.0;
       for (double x : arr) {
           exp += (x * x);
       }
       exp = Math.sqrt(exp);
       assertEquals(exp, result);
   }
   
   @Test
   public void testVectorNormMax() {
       double result = v.normMax();
       double exp = arr[0];
       for (double x : arr) {
           exp = Math.max(x, exp);
       }
       assertEquals(exp, result);
   }
}
