import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

class Module1 {
    public static double remainder(double a, double b) {
        return a % b;
    }

    public static double triArea(double a, double h) {
        return (a / 2) * h;
    }

    public static int animals(int chicken, int cows, int pigs) {
        return chicken * 2 + cows * 4 + pigs * 4;
    }

    public static boolean profitableGamble(double prob, double prize, double pay) {
        return prob * prize > pay;
    }

    public static String operation(int N, int a, int b) {
        if (a + b == N) {
            return "added";
        } else if (a - b == N) {
            return "subtracted";
        } else if (a * b == N) {
            return "multiplied";
        } else if (a / b == N) {
            return "divided";
        } else {
            return "none";
        }
    }

    public static int ctoa(char c) {
        return c;
    }

    public static int addUpTo(int a) {
        if (a == 1) {
            return 1;
        } else {
            return a + addUpTo(a - 1);
        }
    }

    public static int nextEdge(int a, int b) {
        return a + b - 1;
    }

    public static double sumOfCubes(double[] arr) {
        double sum = 0;

        for (double v : arr) {
            sum += v * v * v;
        }

        return sum;
    }

    public static boolean abcmath(double a, double b, double c) {
        for (int i = 0; i < b; i++) {
            a += a;
        }

        return a % c == 0;
    }
}

class Tasks {
    public static void log(String str) {
        System.out.println(str);
    }

    public static void log(double str) {
        System.out.println(str);
    }

    public static void log(boolean str) {
        System.out.println(str);
    }

    public static void log(int[] arr) {
        System.out.println(Arrays.toString(arr));
    }

    public static void log(String[] str) {
        System.out.println(String.join(" ", str));
    }

    public static void log(ArrayList<String> arr) {
        System.out.println(Arrays.toString(arr.toArray()));
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        log("Модуль 1");
        log("************************************");
        log(Module1.remainder(3, 5));
        log(Module1.triArea(5, 4));
        log(Module1.animals(3, 5, 2));
        log(Module1.profitableGamble(0.8, 3, 10));
        log(Module1.operation(25, 13, 12));
        log(Module1.ctoa('A'));
        log(Module1.addUpTo(10));
        log(Module1.nextEdge(13, 25));
        log(Module1.sumOfCubes(new double[]{10, 20, 30}));
        log(Module1.abcmath(10, 3, 15));
    }
}