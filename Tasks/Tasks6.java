import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

class Module6 {
    /*Число Белла - это количество способов, которыми массив из n элементов может
быть разбит на непустые подмножества. Создайте функцию, которая принимает
число n и возвращает соответствующее число Белла.*/
    public static int bell(int n) {
        int[][] styrling = new int[n + 1][n + 1];//вычисляем числа стирлинга
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                if (j > i) styrling[i][j] = 0;
                else if (i == j) styrling[i][j] = 1;
                else if (i == 0 || j == 0) styrling[i][j] = 0;
                else styrling[i][j] = j * styrling[i - 1][j] + styrling[i - 1][j - 1];
            }
        }
        int bell = 0;
        for (int i = 0; i < n + 1; i++) {
            bell += styrling[n][i];
        }
        return bell;
    }
/*В «поросячей латыни» (свинский латинский) есть два очень простых правила:
– Если слово начинается с согласного, переместите первую букву (буквы) слова до
гласного до конца слова и добавьте «ay» в конец.
have ➞ avehay
cram ➞ amcray
take ➞ aketay
cat ➞ atcay
shrimp ➞ impshray
trebuchet ➞ ebuchettray
– Если слово начинается с гласной, добавьте "yay" в конце слова.
ate ➞ ateyay
apple ➞ appleyay
oaken ➞ oakenyay
eagle ➞ eagleyay
Напишите две функции, чтобы сделать переводчик с английского на свинский латинский.
Первая функция translateWord (word) получает слово на английском и возвращает это
слово, переведенное на латинский язык. Вторая функция translateSentence (предложение)
берет английское предложение и возвращает это предложение, переведенное на латинский
язык.*/
    public static String translateWord(String word) {
        String result = word;
        if (String.valueOf(result.charAt(0)).toLowerCase().matches("[aeiouy]")) { // первое буква - гласная
            result += "yay";
        } else {
            result = result.toLowerCase();
            String newWord = result.split("[aeiouy]")[0];//первую согласную(-е) переносим
            result = result.replaceFirst(newWord,"") + newWord + "ay";//до конца слова + "ay"
            result = String.valueOf(result.charAt(0)).toUpperCase() + result.substring(1);
        }
        return result;
    }
/*Вторая функция translateSentence (предложение)
берет английское предложение и возвращает это предложение, переведенное на латинский
язык.*/
    public static String translateSentence(String str) {
        String[] tokens = str.split(" ");//разделяем слова

        for (int i = 0; i < tokens.length; i++) {//проходимся по всем словам
            if (String.valueOf(tokens[i].charAt(0)).toLowerCase().matches("[aeiouy]")) {
                if (String.valueOf(tokens[i].charAt(tokens[i].length() - 1)).matches("[!?.,:;]")) {
                    tokens[i] = tokens[i].substring(0, tokens[i].length() - 1) + "yay" + tokens[i].charAt(tokens[i].length() - 1);
                } else {
                    tokens[i] += "yay";
                }
            } else {
                if (String.valueOf(tokens[i].charAt(0)).matches("[QWRTPSDFGHJKLZXCVBNM]")) {//если слово начинается с больш буквы
                    if (String.valueOf(tokens[i].charAt(tokens[i].length() - 1)).matches("[!?.,:;]")) {
                        char mark = tokens[i].charAt(tokens[i].length() - 1);/////////////^^^^^^^^^
                        tokens[i] = tokens[i].substring(0, tokens[i].length() - 1);//without^^^^^
                        tokens[i] = tokens[i].toLowerCase();//в нижний регистр
                        String newWord = tokens[i].split("[aeiouy]")[0];// первую согласную переносим
                        tokens[i] = tokens[i].replaceFirst(newWord,"") + newWord + "ay"; //до конца слова + ау
                        tokens[i] = String.valueOf(tokens[i].charAt(0)).toUpperCase() + tokens[i].substring(1) + mark;
                    } else { //^^^ первую гласную в верхний регистр + остальное слово + mark
                        tokens[i] = tokens[i].toLowerCase();
                        String newWord = tokens[i].split("[aeiouy]")[0];
                        tokens[i] = tokens[i].replaceFirst(newWord,"") + newWord + "ay";
                        tokens[i] = String.valueOf(tokens[i].charAt(0)).toUpperCase() + tokens[i].substring(1);
                    }
                } else {//почти тоже самое, только букву большой не делаем
                    if (String.valueOf(tokens[i].charAt(tokens[i].length() - 1)).matches("[!?.,:;]")) {
                        char mark = tokens[i].charAt(tokens[i].length() - 1);
                        tokens[i] = tokens[i].substring(0, tokens[i].length() - 1);
                        tokens[i] = tokens[i].toLowerCase();
                        String newWord = tokens[i].split("[aeiouy]")[0];
                        tokens[i] = tokens[i].replaceFirst(newWord,"") + newWord + "ay";
                        tokens[i] = tokens[i] + mark;
                    } else {
                        tokens[i] = tokens[i].toLowerCase();
                        String newWord = tokens[i].split("[aeiouy]")[0];
                        tokens[i] = tokens[i].replaceFirst(newWord,"") + newWord + "ay";
                    }
                }
            }
        }

        return String.join(" ", tokens);//выводим предложение
    }
/*Учитывая параметры RGB (A) CSS, определите, является ли формат принимаемых
значений допустимым или нет. Создайте функцию, которая принимает строку
(например, " rgb(0, 0, 0)") и возвращает true, если ее формат правильный, в
противном случае возвращает false.*/
    public static boolean validColor(String str) {
        if (!str.startsWith("rgb") && !str.startsWith("rgba")) {
            return false;
        }
        String[] numbers = str.split("\\(")[1].split(","); // [0] [0] [0)]
        numbers[numbers.length - 1] = numbers[numbers.length - 1].substring(0, numbers[numbers.length - 1].length() - 1);// [0][0][0]
        if (str.startsWith("rgb") && !str.startsWith("rgba")) {//rgb
            if (str.contains(".")) return false;//проверка что нет нецелых чисел
            for (int i = 0; i < numbers.length; i ++) {
                if (numbers[i].trim().equals("")) return false; //проверка на пустоту
                int num = Integer.parseInt(numbers[i].trim());
                if (!(num >= 0 && num <= 255)) return false;
            }
        } else {//rgba
            for (int i = 0; i < numbers.length - 1; i ++) {
                if (numbers[i].trim().equals("")) return false;// проверка на пустоту
                int num = Integer.parseInt(numbers[i].trim());
                if (!(num >= 0 && num <= 255)) return false;
            }
            if (numbers[3].trim().equals("")) return false; // проверка на пустоту
            double num = Double.parseDouble(numbers[3].trim());
            return num >= 0 && num <= 1; // double проверка
        }
        return true;
    }
/*Создайте функцию, которая принимает URL (строку), удаляет дублирующиеся
параметры запроса и параметры, указанные во втором аргументе (который будет
необязательным массивом).*/
    public static String stripUrlParams(String url, String ...paramsToStrip) {
        String str;
        if (!url.contains("?"))//нет аргументов - сразу возврат
            return url;
        else {
            str = url.substring(url.indexOf("?") + 1);//get
            url = url.substring(0, url.indexOf("?") + 1);//url
        }
        StringBuilder result = new StringBuilder();
        HashMap<String, String> parameters = new HashMap<String, String>();
        String[] params = str.split("&");
        for (String paam : params){
            String[] paams = paam.split("=");
            String param = paams[0];
            String val = paams[1];
            boolean bool = true;
            for (String pts : paramsToStrip) if (pts.equals(param)) bool = false;
            if (bool) parameters.put(param, val);
        }
        StringBuilder get = new StringBuilder();
        parameters.forEach((parametr, value) -> {
            get.append(parametr+"="+value+"&");
        });
        return url + get.substring(0, get.length()-1);
    }
/*Напишите функцию, которая извлекает три самых длинных слова из заголовка
газеты и преобразует их в хэштеги. Если несколько слов одинаковой длины,
найдите слово, которое встречается первым.*/
    public static ArrayList<String> getHashTags(String str){
        String[] tokens = str.toLowerCase().split(" ");//сплитим строку
        ArrayList<String> hashtags = new ArrayList<>(); // создаем массив хештешлв

        while (hashtags.size() < 3) { // пока не нашли 3 хештега
            double maxLength = Double.NEGATIVE_INFINITY;
            String word = "";
            int idx = 0;//переменная самого длинного слова

            try {
                for (int i = 0; i < tokens.length; i++) {//ищем самое длинное слово
                    if (tokens[i].length() > maxLength) {
                        maxLength = tokens[i].length();
                        word = tokens[i];
                        idx = i;
                    }
                }

                if (String.valueOf(word.charAt(word.length() - 1)).matches("[!?.,;:]")) { // добавляем хештег в результат
                    hashtags.add("#" + word.substring(0, word.length() - 1));
                } else {
                    hashtags.add("#" + word);
                }
                tokens[idx] = ""; // удаляем хештег из массива
            } catch (StringIndexOutOfBoundsException e) {
                return hashtags;//если меньше 3 слов
            }
        }

        return hashtags; // вернуть хештеги
    }

    public static int ulam (int n){ // последовательность улама
        int[] arr = new int[n]; // массив
        arr[0]=1; // первое и второе число
        arr[1]=2;
        int len=2, next=3;

        while (next < Integer.MAX_VALUE && len < n){ // ищем пока не найдем n-ное число
            int count = 0; // кол-во найденных способов для составления числа

            for (int i = 0; i < len; i++){ // i < len идем до числа n
                for (int j = len-1; j > i; j--){ // идем от числа n ///навстречу друг другу)0
                    if (arr[i] + arr[j] == next && arr[i] != arr[j]) // если два числа не равны и их сумму равна сумме поиска
                        count++; //кол-во ++
                    else if (count > 1) // break
                        break;
                }

                if (count > 1)// break((((
                    break;
            }
            if (count == 1) {//если count==1 то добавить число в массив чисел Улама
                arr[len] = next;
                len++;
            }
            next++;//следующее число........
        }
        return arr[n-1]; //возврат искомого числа Улама
    }
/*Напишите функцию, которая возвращает самую длинную неповторяющуюся
подстроку для строкового ввода.*/
    public static String longestNonrepeatingSubstring(String str){
        String substr = "";
        char [] chars = str.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char c : chars) {
            if (!builder.toString().contains(String.valueOf(c)))
                builder.append(c);
            else {
                if (builder.length() > substr.length()) {//перешли на новую последовательность
                    substr = builder.toString();//записываем ее если она длиннее предыдущей
                }
                builder = new StringBuilder("" + c); // билдер равен просто символу найденному последнему
            }
        }

        str = builder.toString();//преобразуем в строку

        if (str.length() > substr.length()) // если вся строка - одна последовательность
            substr = str;

        return substr;
    }
/*Создайте функцию, которая принимает арабское число и преобразует его в римское
число*/
    public static String convertToRoman (int num){
        StringBuilder roman = new StringBuilder();

        if (num < 1 || num > 3999)
            return "Введите число поменьше. ";

        while (num >= 1000) {
            roman.append("M");
            num -= 1000;
        }

        while (num >= 900) {
            roman.append("CM");
            num -= 900;
        }

        while (num >= 500) {
            roman.append("D");
            num -= 500;
        }

        while (num >= 400) {
            roman.append("CD");
            num -= 400;
        }

        while (num >= 100) {
            roman.append("C");
            num -= 100;
        }

        while (num >= 90) {
            roman.append("XC");
            num -= 90;
        }

        while (num >= 50) {
            roman.append("L");
            num -= 50;
        }

        while (num >= 40) {
            roman.append("XL");
            num -= 40;
        }

        while (num >= 10) {
            roman.append("X");
            num -= 10;
        }

        while (num >= 9) {
            roman.append("IX");
            num -= 9;
        }

        while (num >= 5) {
            roman.append("V");
            num -= 5;
        }

        while (num >= 4) {
            roman.append("IV");
            num -= 4;
        }

        while (num >= 1) {
            roman.append("I");
            num -= 1;
        }

        return roman.toString();
    }
/*Создайте функцию, которая принимает строку и возвращает true или false в
зависимости от того, является ли формула правильной или нет*/
    public static boolean formula(String formula){
        String[] tokens = formula.split(" ");
        int ans = 0;
        int expectedResult = 0;

        if (!Character.isDigit(tokens[0].charAt(0))) return false;//проверка на число
        else ans = Integer.parseInt(tokens[0]);//ans - первое число
        int i = 1;
        if (tokens[i].equals("+")){ // второе слипнутое слово
            ans += Integer.parseInt(tokens[i + 1]); // третье слипнутое слово
        }
        else if (tokens[i].equals("-")){
            ans -= Integer.parseInt(tokens[i + 1]);
        }
        else if (tokens[i].equals("*")){
            ans *= Integer.parseInt(tokens[i + 1]);
        }
        else if (tokens[i].equals("/")){
            ans /= Integer.parseInt(tokens[i + 1]);
        }

        i = formula.indexOf('=');
        String check = formula.substring(i + 1);

        if (check.contains("=")) return false;
        else expectedResult = Integer.parseInt(tokens[tokens.length - 1]);

        return ans == expectedResult;
    }

    public static boolean palindromedescendant(int num){
        boolean isDescendant = false;
        StringBuffer buffer1 =new StringBuffer(num);
        StringBuffer buffer2 =new StringBuffer(num);

        if (buffer1.length() % 2 != 0)//из примечания : числа всегда будут иметь четное количество цифр
            return false;
        else {
            while (!isDescendant){//пока не найдем палиндром
                if(buffer1 != buffer1.reverse()){ // если число не палиндром
                    for(int i = 0; i < buffer1.length(); i += 2){
                        int a = Integer.parseInt(String.valueOf(buffer1.charAt(i))); /// 11|21|12|30  1+1 2+1 1+2 3+0 2333 ((( ->>>>
                        int b = Integer.parseInt(String.valueOf(buffer1.charAt(i + 1)));
                        buffer2.append(a + b);
                        if ((a+b) < 11) return false;
                    }
                } else isDescendant = true;
            }
        }
        return isDescendant;
    }
}

class Tasks6 {
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
        log("Модуль 6");
        log("************************************");
        log(Module6.bell(5));
        log(Module6.translateWord("have"));
        log(Module6.translateSentence("I like to eat honey waffles."));
        log(Module6.validColor("rgba(0,0,0,0.123456789)"));
        log(Module6.stripUrlParams("https://edabit.com?a=2&b=3", "b"));
        log(Module6.getHashTags("Visualizing Science"));
        log(Module6.ulam(206));
        log(Module6.longestNonrepeatingSubstring("abcabcbb"));
        log(Module6.convertToRoman(16));
        log(Module6.formula("6 * 4 = 24"));
        log(Module6.palindromedescendant(11211230));
    }
}