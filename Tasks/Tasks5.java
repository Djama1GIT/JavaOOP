import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

class Module5 {
    /*Пришло время отправлять и получать секретные сообщения.
Создайте две функции, которые принимают строку и массив и возвращают
закодированное или декодированное сообщение.
Первая буква строки или первый элемент массива представляет собой символьный код
этой буквы. Следующие элементы-это различия между символами: например, A +3 --> C
или z -1 --> y. */
    public static int[] encrypt(String str) {//просто цикл, все банально
        int length = str.length();
        int[] result = new int[length];//создаем массив чисел
        result[0] = str.charAt(0); // т.к. первую букву не шифруем просто вставляем в массив ее число

        for (int i = 1; i < length; i++) {//остальные согласно условию
            result[i] = str.charAt(i) - str.charAt(i - 1);/// e - H(101-72), l - e, ...
        }

        return result; // вернуть строку
    }

    public static String decrypt(int[] arr) {
        int[] encoded = new int[arr.length];//переменная декодировки
        System.arraycopy(arr, 0, encoded, 0, arr.length);//копируем массив
        char[] result = new char[arr.length];//переменная результата
        result[0] = (char) arr[0];//т.к. первая буква не шифрована просто преобразуем ее

        for (int i = 1; i < encoded.length; i++) {//остальные преобразуем согласно условию
            result[i] = (char) (encoded[i] + encoded[i - 1]); // char(29 + 72) = char(101) = e
            encoded[i] = encoded[i] + encoded[i - 1];// 101
        }

        return new String(result);//выводим
    }
/*Создайте функцию, которая принимает имя шахматной фигуры, ее положение и
целевую позицию. Функция должна возвращать true, если фигура может двигаться
к цели, и false, если она не может этого сделать.
Возможные входные данные - "пешка", "конь", "слон", "Ладья", "Ферзь"и " король".*/
    public static boolean canMove(String name, String start, String end) {//на самом здесь деле все просто
        char startLetter = start.charAt(0);//просто в переменные вставляем данные с клавы и согласно правилам шахмат
        int startNumber = Integer.parseInt(String.valueOf(start.charAt(1)));//пишем switch case, слложной логики тут нет
        char endLetter = end.charAt(0);
        int endNumber = Integer.parseInt(String.valueOf(end.charAt(1)));

        if (startLetter == endLetter && startNumber == endNumber) return false;

        switch (name) {
            case "Pawn": {
                if (startLetter == endLetter && startNumber == 2 && endNumber == 4)
                    return true;
                return startLetter == endLetter && endNumber == (startNumber + 1);
            }
            case "Knight": {
                return (Math.abs(startLetter - endLetter) == 2 && Math.abs(startNumber - endNumber) == 1) || (Math.abs(startLetter - endLetter) == 1 && Math.abs(startNumber - endNumber) == 2);
            }
            case "Bishop": {
                return Math.abs(startLetter - endLetter) == Math.abs(startNumber - endNumber);
            }
            case "Rook": {
                return (startLetter == endLetter && startNumber != endNumber) || (startLetter != endLetter && startNumber == endNumber);
            }
            case "Queen": {
                if ((startLetter == endLetter && startNumber != endNumber) || (startLetter != endLetter && startNumber == endNumber))
                    return true;
                if (Math.abs(startLetter - endLetter) == Math.abs(startNumber - endNumber))
                    return true;
                break;
            }
            case "King": {
                return Math.abs(startLetter - endLetter) < 2 && Math.abs(startNumber - endNumber) < 2;
            }
            default:
                return false;
        }

        return false;
    }
/*Входная строка может быть завершена, если можно добавить дополнительные
буквы, и никакие буквы не должны быть удалены, чтобы соответствовать слову.
Кроме того, порядок букв во входной строке должен быть таким же, как и порядок
букв в последнем слове.
Создайте функцию, которая, учитывая входную строку, определяет, может ли слово быть
завершено. */
    public static boolean canComplete(String str1, String str2) {//проходимся по массиву с указателем
        char[] chars = str1.toCharArray();
        int startOfSearch = 0;
        for (char c : chars) {//ищем
            if (str2.indexOf(String.valueOf(c), startOfSearch) != -1)
                startOfSearch = str2.indexOf(String.valueOf(c), startOfSearch) + 1;//нашли - передвигаем ползунок
            else
                return false;//если есть несоответствие какое-то
        }
        return true;//если все нашли
    }
/*Создайте функцию, которая принимает числа в качестве аргументов, складывает их
вместе и возвращает произведение цифр до тех пор, пока ответ не станет длиной
всего в 1 цифру. */
    public static int sumDigProd(int[] mass) {//5 таски, а сложность первого...
        int sum = 0;
        for (int value : mass) {//находим сумму
            sum += value;
        }
        while (sum > 9) {//пока больше 9
            int mult = 1;
            while (sum > 0) {//складываем произведение цифр
                mult *= sum % 10;
                sum /= 10;
            }
            sum = mult;
        }
        return sum;
    }
/*Напишите функцию, которая выбирает все слова, имеющие все те же гласные (в
любом порядке и / или количестве), что и первое слово, включая первое слово.*/
    public static ArrayList<String> sameVowelGroup(String[] strs) {
        String[] allVowels = new String[]{"a", "e", "y", "u", "i", "o"};//гласные
        String vowels = "";//переменная найденных гласных
        ArrayList<String> result = new ArrayList<>();//переменная слов

        for (int i = 0; i < allVowels.length; i++) {//ищем все гласные в первом слове
            if (strs[0].contains(allVowels[i]) && !vowels.contains(allVowels[i])) {
                vowels += allVowels[i];
            }
        }

        if (vowels.length() > 0) {//добавить в результат первое слово, если есть хоть 1 гласная
            result.add(strs[0]);
        } else {
            return result;
        }

        for (int i = 1; i < strs.length; i++) {//проходимся по остальным словам
            boolean pass = true;

            for (int j = 0; j < vowels.length(); j++) {//и по их символам
                if (!strs[i].contains(String.valueOf(vowels.charAt(j)))) {//если есть все гласные их первого слова
                    pass = false;
                    break;
                }
            }

            if (pass) result.add(strs[i]);//то добавляем в результат
        }

        return result;//в конце возвращаем результат
    }
/*Создайте функцию, которая принимает число в качестве аргумента и возвращает
true, если это число является действительным номером кредитной карты, а в
противном случае-false.
Номера кредитных карт должны быть длиной от 14 до 19 цифр и проходить тест Луна,
описанный ниже:
– Удалите последнюю цифру (это"контрольная цифра").
– Переверните число.
– Удвойте значение каждой цифры в нечетных позициях. Если удвоенное значение имеет
более 1 цифры, сложите цифры вместе (например, 8 x 2 = 16 ➞ 1 + 6 = 7).
– Добавьте все цифры.
– Вычтите последнюю цифру суммы (из шага 4) из 10. Результат должен быть равен
контрольной цифре из Шага 1.*/
    public static boolean validateCard(long cardNum) {//на вход получаем номер карты
        StringBuilder str = new StringBuilder();//переменные...
        long number = cardNum;

        if (Long.toString(number).length() >= 14 && Long.toString(number).length() <= 19) {
            // Номера кредитных карт должны быть длиной от 14 до 19 цифр и проходить тест Луна,
            // Удалите последнюю цифру (это"контрольная цифра").
            long lastNum = number % 10;
            StringBuilder cardNumStr = new StringBuilder(Long.toString(number /= 10));
            // Переверните число.
            cardNumStr.reverse();
            //Удвойте значение каждой цифры в нечетных позициях. Если удвоенное значение имеет
            //более 1 цифры, сложите цифры вместе (например, 8 x 2 = 16 ➞ 1 + 6 = 7).
            for (int i = 0; i < cardNumStr.length(); i++) {
                if (i % 2 == 0) {
                    int c = Character.getNumericValue(cardNumStr.charAt(i)) * 2;
                    if (c > 9) {
                        String buf = Integer.toString(c);
                        str.append(Character.getNumericValue(buf.charAt(0)) + Character.getNumericValue(buf.charAt(1)));
                    } else {
                        str.append(c);
                    }
                } else {
                    str.append(cardNumStr.charAt(i));
                }
            }

            int sum = 0;
            //Добавьте все цифры.
            for (int i = 0; i < str.length(); i++) {
                sum += Character.getNumericValue(str.charAt(i));
            }

            //– Вычтите последнюю цифру суммы (из шага 4) из 10.
            return lastNum == 10 - sum % 10; // Результат должен быть равен контрольной цифре из Шага 1.
        }

        return false;
    }
/*Напишите функцию, которая принимает положительное целое число от 0 до 999
включительно и возвращает строковое представление этого целого числа,
написанное на английском языке*/
    public static String numToEng(int num) {//просто много switch-case и почти никакой логики
        String str = "";
        if (num == 0) return "zero";

        switch (num / 100) {//сотни
            case 1: {
                str += "one hundred ";
                break;
            }
            case 2: {
                str += "two hundred ";
                break;
            }
            case 3: {
                str += "three hundred ";
                break;
            }
            case 4: {
                str += "four hundred ";
                break;
            }
            case 5: {
                str += "five hundred ";
                break;
            }
            case 6: {
                str += "six hundred ";
                break;
            }
            case 7: {
                str += "seven hundred ";
                break;
            }
            case 8: {
                str += "eight hundred ";
                break;
            }
            case 9: {
                str += "nine hundred ";
                break;
            }
        }

        switch (num / 10 % 10) {// десятки
            case 1: {
                switch (num % 10) { // 10-19
                    case 0: {
                        str += "ten";
                        return str;
                    }
                    case 1: {
                        str += "eleven";
                        return str;
                    }
                    case 2: {
                        str += "twelve";
                        return str;
                    }
                    case 3: {
                        str += "thirteen";
                        return str;
                    }
                    case 4: {
                        str += "fourteen";
                        return str;
                    }
                    case 5: {
                        str += "fifteen";
                        return str;
                    }
                    case 6: {
                        str += "sixteen";
                        return str;
                    }
                    case 7: {
                        str += "seventeen";
                        return str;
                    }
                    case 8: {
                        str += "eighteen";
                        return str;
                    }
                    case 9: {
                        str += "nineteen";
                        return str;
                    }
                }
            }

            case 2: { // 20+
                str += "twenty ";
                break;
            }
            case 3: {
                str += "thirty ";
                break;
            }
            case 4: {
                str += "forty ";
                break;
            }
            case 5: {
                str += "fifty ";
                break;
            }
            case 6: {
                str += "sixty ";
                break;
            }
            case 7: {
                str += "seventy ";
                break;
            }
            case 8: {
                str += "eighty ";
                break;
            }
            case 9: {
                str += "ninety ";
                break;
            }
        }

        switch (num % 10) {//единицы
            case 1: {
                str += "one";
                break;
            }
            case 2: {
                str += "two";
                break;
            }
            case 3: {
                str += "three";
                break;
            }
            case 4: {
                str += "four";
                break;
            }
            case 5: {
                str += "five";
                break;
            }
            case 6: {
                str += "six";
                break;
            }
            case 7: {
                str += "seven";
                break;
            }
            case 8: {
                str += "eight";
                break;
            }
            case 9: {
                str += "nine";
                break;
            }
        }

        return str;
    }
//тоже самое для русского языка
    public static String numToRus(int num) {
        String str = "";

        if (num == 0) return "ноль";

        switch (num / 100) {
            case 1: {
                str += "сто ";
                break;
            }
            case 2: {
                str += "двести ";
                break;
            }
            case 3: {
                str += "триста ";
                break;
            }
            case 4: {
                str += "четыреста ";
                break;
            }
            case 5: {
                str += "пятьсот ";
                break;
            }
            case 6: {
                str += "шестьсот ";
                break;
            }
            case 7: {
                str += "семьсот ";
                break;
            }
            case 8: {
                str += "восемьсот ";
                break;
            }
            case 9: {
                str += "девятьсот ";
                break;
            }
        }

        switch (num / 10 % 10) {
            case 1: {
                switch (num % 10) {
                    case 0: {
                        str += "десять";
                        return str;
                    }
                    case 1: {
                        str += "одиннадцать";
                        return str;
                    }
                    case 2: {
                        str += "двенадцать";
                        return str;
                    }
                    case 3: {
                        str += "тринадцать";
                        return str;
                    }
                    case 4: {
                        str += "четырнадцать";
                        return str;
                    }
                    case 5: {
                        str += "пятнадцать";
                        return str;
                    }
                    case 6: {
                        str += "шестнадцать";
                        return str;
                    }
                    case 7: {
                        str += "семнадцать";
                        return str;
                    }
                    case 8: {
                        str += "восемьнадцать";
                        return str;
                    }
                    case 9: {
                        str += "двадцать";
                        return str;
                    }
                }
            }

            case 2: {
                str += "двадцать ";
                break;
            }
            case 3: {
                str += "тридцать ";
                break;
            }
            case 4: {
                str += "сорок ";
                break;
            }
            case 5: {
                str += "пятьдесят ";
                break;
            }
            case 6: {
                str += "шестьдесят ";
                break;
            }
            case 7: {
                str += "семьдесят ";
                break;
            }
            case 8: {
                str += "восемьдесят ";
                break;
            }
            case 9: {
                str += "девяносто ";
                break;
            }
        }

        switch (num % 10) {
            case 1: {
                str += "один";
                break;
            }
            case 2: {
                str += "два";
                break;
            }
            case 3: {
                str += "три";
                break;
            }
            case 4: {
                str += "четыре";
                break;
            }
            case 5: {
                str += "пять";
                break;
            }
            case 6: {
                str += "шесть";
                break;
            }
            case 7: {
                str += "семь";
                break;
            }
            case 8: {
                str += "восемь";
                break;
            }
            case 9: {
                str += "девять";
                break;
            }
        }

        return str;
    }
/*
Создайте функцию, которая возвращает безопасный хеш SHA-256 для данной строки.
Хеш должен быть отформатирован в виде шестнадцатеричной цифры.*/
    public static String getSha256Hash(String str) throws NoSuchAlgorithmException {//алгоритм хеширования
        MessageDigest md = MessageDigest.getInstance("SHA-256");//получаем экземпляр sha-256
        byte[] abobaHash = md.digest(str.getBytes(StandardCharsets.UTF_8));//преобразуем строку в байты и хешируем
        BigInteger number = new BigInteger(1, abobaHash);
        StringBuilder hexString = new StringBuilder(number.toString(16)); // и преобразуем в 16-рязрядный код
        while (hexString.length() < 64) // сработает, например при хешировании "1234"
        {//обязательно, т.к. не всегда бывает нужное количество символов
            hexString.insert(0, '0');
        }
        return hexString.toString();//возвращаем хешированную строку
    }
/*Напишите функцию, которая принимает строку и возвращает строку с правильным
регистром для заголовков символов в серии "Игра престолов".
Слова and, the, of и in должны быть строчными. Все остальные слова должны иметь
первый символ в верхнем регистре, а остальные-в Нижнем. */
    public static String correctTitle(String str) {//еще один банальнейший алгоритм
        String[] tokens = str.split(" ");//сплитим строку
        for (int i = 0; i < tokens.length; i++) {//проходимся по словам и меняем согласно условию
            if (!tokens[i].equalsIgnoreCase("of") && !tokens[i].equalsIgnoreCase("and") && !tokens[i].equalsIgnoreCase("the") && !tokens[i].equalsIgnoreCase("in")) {
                tokens[i] = String.valueOf(tokens[i].charAt(0)).toUpperCase() + tokens[i].substring(1).toLowerCase();
            } else {
                tokens[i] = tokens[i].toLowerCase();
            }
        }
        return String.join(" ", tokens);//возвращаем строку
    }
/*Напишите функцию, которая принимает целое число n и возвращает "недопустимое", если
n не является центрированным шестиугольным числом или его иллюстрацией в виде
многострочной прямоугольной строки в противном случае.*/
    public static String haxLattice(int n) {//а тут уже интересно..........
        int num = 1;//вводим переменные
        int i = 1;
        String res = "";
        String str2 = "";

        while (n > num) {//цикл который находит ближайшее к введенному числу шестиугольное число
            i++;
            num = 3 * i * (i - 1) + 1;//формула шестиугольного числа i-порядковый номер 1(1)-7(2)-19(3)---
        }

        int l = i;//i=кол-во точек в верхей грани
        if (n != num)//если найденное циклом и введенное число не совпадают, то строка = invalid
            res = "invalid";
        else {
            while (l < i * 2 - 1) {//отрисовка до центральной строки //// пока кол-во точек < центральной строки
                for (int a = 0; a < i * 2 - 1 - l; a++) // отрисовываем отступ
                    res += "  ";
                for (int b = 0; b < l; b++)//и сами точки
                    res += " o  ";
                res += "\n";//перенос строки
                l++;//кол-во точек в строке++
            }
            while (l >= i) {//отрисовка от центральной строки включительно
                for (int a = 0; a < i * 2 - 1 - l; a++)//отрисовываем отступ
                    res += "  ";//////////////////////////тут поменялись знаки и числа в отрисовке точек
                for (int b = l; b > 0; b--)//и сами точки
                    res += " o  ";
                res += "\n";//перенос строки
                l--;
            }
        }
        return res;//возвращаем строку
    }
}

class Tasks5 {
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
        log("Модуль 5");
        log("************************************");
        log(Module5.encrypt("Hello"));
        log(Module5.decrypt(new int[]{72, 29, 7, 0, 3}));
        log(Module5.canMove("Rook", "A8", "H8"));
        log(Module5.canComplete("butl", "beautiful"));
        log(Module5.sumDigProd(new int[]{16, 28}));
        log(Module5.sameVowelGroup(new String[]{"hoops", "chuff", "bot", "bottom"}));
        log(Module5.validateCard(1234567890123452L));
        log(Module5.numToEng(18));
        log(Module5.numToRus(18));
        log(Module5.getSha256Hash("password123"));
        log(Module5.correctTitle("TYRION LANNISTER, HAND OF THE QUEEN."));
        log(Module5.haxLattice(91));
    }
}