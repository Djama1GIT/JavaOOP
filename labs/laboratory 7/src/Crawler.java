import java.net.*;
import java.util.*;
import java.io.*;

public class Crawler { // главный класс
    static boolean DEBUG = false; // константа для отображения дебага

    public static void main(String[] args) {
        // Вводим в систему то, что ввели с клавы
        int depth = 0; int threads = 0; String url_str = ""; // инициализация - ввод - обработка исключений
        try { url_str = args[0]; depth = Integer.parseInt(args[1]); threads = Integer.parseInt(args[2]); }
        catch (Exception e) { System.out.println("Usage: java Crawler <URL> <depth> <threads"); System.exit(1); }

        URLPool pool = new URLPool(depth); // инициализация пула URLов
        pool.put(new URLDepthPair(url_str, 0)); // заносим туда URL введенный с клавы
        

        int totalThreads = 0; int waits = 0; int initActives = Thread.activeCount(); // подготовка к запуску потоков
        while (waits < 15) {
            if (Thread.activeCount() - initActives < threads) { // запускаем потоки в количестве указанном в аргументе
                if (DEBUG) System.out.println(threads);
                if (DEBUG) System.out.println(Thread.activeCount());
                if (pool.size() > 0) {
                    Thread thread = new Thread(new CrawlerTask(pool)); // запуск потока обработки
                    thread.start();
                    try { thread.join(); } catch (InterruptedException e) { e.printStackTrace(); }
                    waits = 0; // переменная для прекращения цикла если ничего не происходит
                }
            } else { try { if (waits <= 7) Thread.sleep(100); waits++;}// сделать задержку, если в
            catch (Exception e) { if (DEBUG) System.out.println("Exception " + e.getMessage()); }}// моменте нет задач
            if (Thread.activeCount() == initActives) waits++;
        }

        ////////////////все обработалось
        TreeMap<String, Integer> ress = new TreeMap<String, Integer>();
        for (URLDepthPair it : pool.processedURLs){
            if (ress.containsKey(it.getURL())){
                if (ress.get(it.getURL()) > it.getDepth()) ress.replace(it.getURL(), it.getDepth());
            } else { ress.put(it.getURL(), it.getDepth()); }
        } // вывод наименьшей глубины до каждой ссылки
        sortByValues(ress).forEach((ress_url, ress_depth) -> {
            System.out.println("URL is " + ress_url + " | depth: " + ress_depth);});
        System.exit(0); // прекращение программы

    }

    public static LinkedList<String> getAllLinks(URLDepthPair myDepthPair) { // поиск всех ссылок в программе
        LinkedList<String> URLs = new LinkedList<String>(); // вводим переменную со всеми ссылками
        Socket socket; // сокет
        String path = myDepthPair.getPath(); // строка c http://
        String host = myDepthPair.getHost(); // Без
        OutputStream outputStream; // поток вывода
        InputStream inputStream; // поток ввода
        try {
            socket = new Socket(myDepthPair.getHost(), 80);// инициализация сокета
            socket.setSoTimeout(1500); // задаем максимальное ожидание
            outputStream = socket.getOutputStream(); // поток вывода
            inputStream = socket.getInputStream(); // поток ввода
        }
        catch (Exception e) { // обработка исключений
            if (DEBUG) System.err.println(e.getMessage());
            return URLs;
        }

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream); // все в словах итак описано
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader); // ввод из потока (строками) (сюда)
        PrintWriter myWriter = new PrintWriter(outputStream, true); // вывод данных в поток (отсюда)

        myWriter.println("GET " + path + " HTTP/1.1"); // формируем HTTP запрос
        myWriter.println("Host: " + host);
        myWriter.println("Connection: close");
        myWriter.println(); // мы типо отправляем запрос в поток вывода, а на потоке ввода получаем результат


        while (true) { // пока можем найти добавляем все ссылки в массив
            String line; // инициализируем переменную строки
            try {line = bufferedReader.readLine();} // читаем строку или обрабатываем исключение
            catch (Exception except) {if (DEBUG) System.err.println(except.getMessage()); return URLs; }
            if (line == null) break;// если закончились строки, то прервать цикл

            int index = 0; // переменная индекса начала ссылки
            while (true) { // идем по строке и ищем все ссылки
                index = line.indexOf("<a href=\"", index);
                if (index == -1) break;
                index += "<a href=\"".length();
                URLs.add(line.substring(index, index = line.indexOf("\"", index))); // добавляем все ссылки в массив
            }
        }
        return URLs; // возвращаем массив
    }
    public static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {//сортировка сета
        Comparator<K> valueComparator = new Comparator<K>() {//взята с инета т.к. не влияет на лабу(просто визуал)
                    public int compare(K k1, K k2) {
                        int compare = map.get(k1).compareTo(map.get(k2));
                        if (compare == 0) return 1;
                        else  return compare; }};
        Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }
}

