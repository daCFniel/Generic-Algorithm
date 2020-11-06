package com.jetbrains;

import java.io.PrintStream;

class Assess {
    Assess() {
    }

    public static void checkIn(String var0, String var1, double[] var2, boolean[] var3) {
        System.out.println("****************************************************** ");
        System.out.println("Submission information: ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println("Your name is: " + var0);
        System.out.println("Your login is: " + var1);
        System.out.println("Your solution to problem 1: ");
        System.out.println(" ");

        int var4;
        for(var4 = 0; var4 < var2.length; ++var4) {
            System.out.print(var2[var4] + " ");
        }

        System.out.println(" ");
        System.out.println(" ");
        System.out.println("Fitness 1 is: " + getTest1(var2));
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println("Problem 2: ");
        System.out.println(" ");
        System.out.println("Your packed items: ");

        for(var4 = 0; var4 < 100; ++var4) {
            if (var3[var4]) {
                System.out.print(var4 + " ");
            }
        }

        System.out.println(" ");
        System.out.println(" ");
        PrintStream var10000 = System.out;
        double var10001 = getTest2(var3)[0];
        var10000.println("Weight and utility for problem  2 are : " + var10001 + "  " + getTest2(var3)[1]);
    }

    public static double getTest1(double[] var0) {
        double var1 = 0.528D;
        double var3 = (double)(10 * var0.length);

        for(int var5 = 0; var5 < var0.length; ++var5) {
            var3 += Math.pow(var0[var5] + var1, 2.0D) - 10.0D * Math.cos(12.566370614359172D * (var0[var5] + var1));
        }

        return var3;
    }

    public static double[] getTest2(boolean[] var0) {
        int var1 = 0;
        double var2 = 0.0D;

        for(int var4 = 0; var4 < 100; ++var4) {
            if (var0[var4]) {
                var1 += var4;
                var2 += Math.ceil(0.1D + Math.pow(Math.sin((double)var4), 2.0D) * 10.0D);
            }
        }

        double[] var5 = new double[]{(double)var1, var2};
        return var5;
    }
}
