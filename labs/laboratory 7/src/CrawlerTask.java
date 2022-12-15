import java.util.*;

public class CrawlerTask implements Runnable { // класс самого потока
    public URLDepthPair depthPair; // переменная URLа
    public URLPool myPool;//переменная пула
    public CrawlerTask(URLPool pool) {
        myPool = pool;
    } //конструктор - на входе получаем пул
    public void run() {
        depthPair = myPool.get();//достаем одну ссылку из пула
        for (String link : Crawler.getAllLinks(depthPair))//находим там все ссылки
            myPool.put(new URLDepthPair(link, depthPair.getDepth() + 1));//и заносим в пул
    }
}