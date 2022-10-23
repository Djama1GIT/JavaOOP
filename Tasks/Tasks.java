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
class Module2 {
    public static String repeat(String str, int count) {
        StringBuilder result = new StringBuilder();
        int length = str.length();

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < count; j++) {
                result.append(str.charAt(i));
            }
        }

        return result.toString();
    }

    public static int differenceMaxMin(int[] arr) {
        double min = Double.POSITIVE_INFINITY;
        double max = Double.NEGATIVE_INFINITY;

        for (int i : arr) {
            if (i < min) {
                min = i;
            } else if (i > max) {
                max = i;
            }
        }

        return (int) (max - min);
    }

    public static boolean isAvgWhole(int[] arr) {
        double sum = 0;

        for (int i : arr) {
            sum += i;
        }

        double average = sum / arr.length;

        return Math.round(average) % (average) == 0;
    }

    public static int[] cumulativeSum(int[] arr) {
        int[] result = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            result[i] = 0;

            for (int j = 0; j <= i; j++) {
                result[i] += arr[j];
            }
        }

        return result;
    }

    public static int getDecimalPlaces(String str) {
        String decimal = str.split("\\.")[1];
        return decimal.length();
    }

    public static int Fibonacci(int i) {
        int idx = i - 1;
        int[] fibonacci = new int[i];

        for (int j = 0; j <= idx; j++) {
            if (j == 0 || j == 1) {
                fibonacci[j] = j + 1;
                continue;
            }

            fibonacci[j] = fibonacci[j - 1] + fibonacci[j - 2];
        }
        return fibonacci[idx];
    }

    public static boolean isValid(String str) {
        return (str.length() == 5) && str.matches("\\d+");
    }

    public static boolean isStrangePair(String str1, String str2) {
        if (str1.equals("") && str2.equals("")) return true;

        return str1.charAt(0) == str2.charAt(str2.length() - 1) && str1.charAt(str1.length() - 1) == str2.charAt(0);
    }

    public static boolean isPrefix(String word, String prefix) {
        return word.startsWith(prefix.substring(0, prefix.length() - 2));
    }

    public static boolean isSuffix(String word, String suffix) {
        return word.endsWith(suffix.substring(1));
    }

    public static int boxSeq(int n) {
        int result = 0;

        if (n == 0) {
            return result;
        }

        for (int i = 1; i <= n; i++) {
            if (i % 2 == 0) {
                result -= 1;
            } else {
                result += 3;
            }
        }

        return result;
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
        log("Модуль 2");
        log("************************************");
        log(Module2.repeat("apple", 4));
        log(Module2.differenceMaxMin(new int[]{10, 5, 4, 30, -1}));
        log(Module2.isAvgWhole(new int[]{3, 5, 10, 14, 8}));
        log(Module2.cumulativeSum(new int[]{3, 4, 1, 5, 7, 8}));
        log(Module2.getDecimalPlaces("3.5477"));
        log(Module2.Fibonacci(7));
        log(Module2.isValid("32454"));
        log(Module2.isStrangePair("", ""));
        log(Module2.isPrefix("automation", "auto-"));
        log(Module2.isSuffix("arachnophobia", "-phobia"));
        log(Module2.boxSeq(4));
    }
}