import java.net.*;
import java.util.*;
import java.io.*;
import java.sql.Timestamp;

public class Crawler { // главный класс
    static boolean DEBUG = false; // константа для отображения дебага

    public static void main(String[] args) {
        // Вводим в систему то, что ввели с клавы
        int depth = 0; int threads = 0; String url_str = ""; // инициализация - ввод - обработка исключений
        try { url_str = args[0]; depth = Integer.parseInt(args[1]); threads = Integer.parseInt(args[2]); }
        catch (Exception e) { System.out.println("Usage: java Crawler <URL> <depth> <threads>"); System.exit(1); }

        URLPool pool = new URLPool(depth); // инициализация пула URLов
        pool.put(new URLDepthPair(url_str, 0)); // заносим туда URL введенный с клавы
        
        long time1 = System.currentTimeMillis();
        int totalThreads = 0; int waits = 0; int initActives = Thread.activeCount(); // подготовка к запуску потоков
        while (waits < threads+1) { // цикл работает до тех пор пока каждый поток не даст сигнал, что свободен
            if (Thread.activeCount() - initActives < threads) { // запускаем потоки в количестве указанном в аргументе
                if (DEBUG) System.out.println(threads);
                if (DEBUG) System.out.println("Активных потоков: " + Integer.toString(Thread.activeCount()));
                if (pool.size() > 0) {
                    Thread thread = new Thread(new CrawlerTask(pool)); // запуск потока обработки
                    thread.start();
//                    try { thread.join(); } catch (InterruptedException e) { e.printStackTrace(); }
                    waits = 0; // переменная для прекращения цикла если ничего не происходит
                }
            } else { try { Thread.sleep(500); }// сделать задержку, если в
            catch (Exception e) { if (DEBUG) System.out.println("Exception " + e.getMessage()); }}// моменте нет задач
            if (Thread.activeCount() == initActives) waits++;
        }
        long time2 = System.currentTimeMillis();
        ////////////////все обработалось
        TreeMap<String, Integer> ress = new TreeMap<String, Integer>();
        for (URLDepthPair it : pool.processedURLs){
            if (ress.containsKey(it.getURL())){
                if (ress.get(it.getURL()) > it.getDepth()) ress.replace(it.getURL(), it.getDepth());
            } else { ress.put(it.getURL(), it.getDepth()); }
        } // вывод наименьшей глубины до каждой ссылки
        sortByValues.sortByValues(ress).forEach((ress_url, ress_depth) -> { //  сортировка, просто для эстетики
            System.out.println("URL is " + ress_url + " | depth: " + ress_depth);}); // вывод URLов
        System.out.println("Время работы программы: " + Long.toString(time2-time1) + "м/с."); // и времени выполнения
        System.exit(0); // прекращение программы

    }

    public static LinkedList<String> getAllLinks(URLDepthPair myDepthPair) { // поиск всех ссылок в программе
        LinkedList<String> URLs = new LinkedList<String>(); // вводим переменную со всеми ссылками
        if (myDepthPair == null) return URLs; // если получили пустой объект URLа, return
        Socket socket; // создаем сокет и формируем его
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
                if (index < 0) break;
                index += "<a href=\"".length();
                int endIndex = 0;
                endIndex = line.indexOf("\"", index);
                if (endIndex < 0) endIndex = line.length();
                String substr = line.substring(index, endIndex);
                if (DEBUG) System.out.println(substr);
                if (substr.length() < 2) continue;
                if (substr.substring(0,2).equals("//"))
                {
                    substr = "http:" + substr;//обработка относительных путей
                }
                else if (substr.substring(0,1).equals("/"))
                {
                    substr = "http://" + host + substr;//обработка относительных путей
                }
                URLs.add(substr); // добавляем все ссылки в массив
            }
        }
        return URLs; // возвращаем массив
    }
}

