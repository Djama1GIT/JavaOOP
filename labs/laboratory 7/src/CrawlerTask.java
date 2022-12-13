import java.util.*;

public class CrawlerTask implements Runnable {
    public URLDepthPair depthPair;
    public URLPool myPool;
    public CrawlerTask(URLPool pool) {
        myPool = pool;
    }
    public void run() {
        depthPair = myPool.get();
        for (String link : Crawler.getAllLinks(depthPair)) myPool.put(new URLDepthPair(link, depthPair.getDepth() + 1));
    }
}