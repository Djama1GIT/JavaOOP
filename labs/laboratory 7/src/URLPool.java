import java.util.*;

public class URLPool {//пул URLов
    static boolean DEBUG = false;
    private LinkedList<URLDepthPair> pendingURLs;//лист ожидающих URLов
    public LinkedList<URLDepthPair> processedURLs;//лист посещенных URLов
    private ArrayList<String> seenURLs = new ArrayList<String>();//массив посеещенных URLов(просто ссылки, без глубины)
    public int depth;//глубина
    public URLPool(int dept) {//конструктор
        depth = dept;
        pendingURLs = new LinkedList<URLDepthPair>();
        processedURLs = new LinkedList<URLDepthPair>();
    }
    public synchronized ArrayList<String> getSeenList() {
        return seenURLs;
    }//геттер посещенных URLов
    public synchronized int size() {
        return pendingURLs.size();
    }//геттер размера пула
    public synchronized boolean put(URLDepthPair depthPair) {//метод добавления в пул пары url-глубина
        boolean added = false;
        if (depthPair.getDepth() <= depth) {
            if (!seenURLs.contains(depthPair.getURL()))//добавляем, если уже не посещали
            {
                pendingURLs.addLast(depthPair);
                this.notify();
            }
            added = true;
            if (DEBUG) System.out.println(depthPair);
            if (DEBUG) System.out.print("wt: ");
        } else { seenURLs.add(depthPair.getURL()); }
        return added;
    }

    public synchronized URLDepthPair get() {//метод get вытаскиват и возвращает один из URLов из пула
        URLDepthPair myDepthPair = null;//с помощью него, так скажем, распределяются обязанности между потоками
        if (pendingURLs.size() == 0) {
            try { this.wait(100);}
            catch (InterruptedException e) { if (DEBUG) System.err.println("MalformedURLException: " + e.getMessage());
                return myDepthPair; } }
        if (!(pendingURLs.size() == 0)){
            myDepthPair = pendingURLs.removeFirst();
            seenURLs.add(myDepthPair.getURL());
            processedURLs.add(myDepthPair);
        }
        return myDepthPair;
    }
}