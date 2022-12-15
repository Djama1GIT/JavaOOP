import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

class Module3 {
    public static int solutions(double a, double b, double c) {
        double discriminant = Math.pow(b, 2) - 4 * a * c;

        if (discriminant > 0) {
            return 2;
        } else if (discriminant == 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public static int findZip(String str) {
        if (str.indexOf("zip") == -1) {
            return -1;
        }

        return str.replaceFirst("zip", "").indexOf("zip");
    }

    public static boolean checkPerfect(int num) {
        ArrayList<Integer> dividers = new ArrayList<>();

        for (int i = 1; i < num/2; i++) {
            if (num / i == 0) dividers.add(i);
        }

        int sum = 0;

        for (double divider : dividers) {
            sum += divider;
        }

        return num == sum;
    }

    public static String flipEndChars(String str) {
        int length = str.length();

        if (length < 2) {
            return "Несовместимо.";
        }

        if (str.charAt(0) == str.charAt(length - 1)) {
            return "Два-это пара.";
        }

        return str.charAt(length - 1) + str.substring(1, length - 2) + str.charAt(0);
    }

    public static boolean isValidHexCode(String str) {
        return str.matches("^#[0-9a-fA-F]+${7}");
    }

    public static boolean same(int[] arr1, int[] arr2) {
        int firstCount = 0;
        int secondCount = 0;

        for (int i = 0; i < arr1.length; i++) {
            boolean unique = true;

            for (int j = 0; j < i; j++) {
                if (arr1[i] == arr1[j]) {
                    unique = false;
                    break;
                }
            }

            if (unique) firstCount++;
        }

        for (int i = 0; i < arr2.length; i++) {
            boolean unique = true;

            for (int j = 0; j < i; j++) {
                if (arr2[i] == arr2[j]) {
                    unique = false;
                    break;
                }
            }

            if (unique) secondCount++;
        }

        return firstCount == secondCount;
    }

    public static boolean isKaprekar(int num) {
        String square = Integer.toString((int) Math.pow(num, 2));
        int length = square.length();

        if (length == 1) {
            return Integer.parseInt(square) == num;
        }

        if (length == 2) {
            return (int) square.charAt(0) + (int) square.charAt(1) == num;
        }

        int firstPart = Integer.parseInt(square.substring(0, Math.round((length - 1) / 2)));
        int lastPart = Integer.parseInt(square.substring(Math.round((length - 1) / 2), length));

        return firstPart + lastPart == num;
    }

    public static String longestZero(String str) {
        String[] zeros = str.split("1+");
        double max = Double.NEGATIVE_INFINITY;
        int index = 0;

        for (int i = 0; i < zeros.length; i++) {
            int length = zeros[i].length();

            if (length > max) {
                max = length;
                index = i;
            }
        }

        return zeros[index];
    }

    public static int nextPrime(int num) {
        boolean isArgumentPrime = true;

        for (int i = 2; i < num/2; i++) {
            if (num % i == 0) {
                isArgumentPrime = false;
                break;
            }
        }

        if (isArgumentPrime) return num;

        int result = 0;

        for (int i = num + 1; i < Double.POSITIVE_INFINITY; i++) {
            boolean isPrime = true;

            for (int j = 2; j < i; j++) {
                if (i % j == 0) {
                    isPrime = false;
                    break;
                }
            }

            if (isPrime) {
                result = i;
                break;
            }
        }

        return result;
    }

    public static boolean rightTriangle(int a, int b, int c) {
        return Math.pow(a, 2) + Math.pow(b, 2) == Math.pow(c, 2) || Math.pow(b, 2) + Math.pow(c, 2) == Math.pow(a, 2) || Math.pow(c, 2) + Math.pow(a, 2) == Math.pow(b, 2);
    }
}

class Tasks3 {
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
        log("Модуль 3");
        log("************************************");
        log(Module3.solutions(1, 0, -1));
        log(Module3.findZip("all zip files are zipped"));
        log(Module3.checkPerfect(6));
        log(Module3.flipEndChars("Cat, dog, and mouse."));
        log(Module3.isValidHexCode("#cde3f5"));
        log(Module3.same(new int[]{1, 3, 4, 4, 4}, new int[]{2, 5, 7}));
        log(Module3.isKaprekar(5));
        log(Module3.longestZero("000000111110000111111000"));
        log(Module3.nextPrime(12));
        log(Module3.rightTriangle(3, 4, 5));
    }
}