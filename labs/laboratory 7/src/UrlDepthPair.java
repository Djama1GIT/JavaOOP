import java.net.*;

public class URLDepthPair {//класс пары URL-глубина
    static boolean DEBUG = false;
    private int curDepth;//переменная глубины
    private String curURL;//переменная URL

    public URLDepthPair(String URL, int depth) { curDepth = depth; curURL = URL; }//конструктор
    public String getURL() {
        return curURL;
    }//геттеры url и глубины
    public int getDepth() {
        return curDepth;
    }//
    public String toString() {
        return Integer.toString(curDepth) + '\t' + curURL;
    }//метод toString() для класса



    public String getPath() {//возвращает весь путь ссылки
        try { return new URL(curURL).getPath(); }
        catch (MalformedURLException e) { if (DEBUG) System.err.println("MalformedURLException: " + e.getMessage());
            return null; } }

    public String getHost() {//только хост
        try { return new URL(curURL).getHost(); }
        catch (MalformedURLException e) { if (DEBUG) System.err.println("MalformedURLException: " + e.getMessage());
            return null; } }
}