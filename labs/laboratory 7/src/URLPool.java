import java.util.*;

public class URLPool {
    static boolean DEBUG = false;
    private LinkedList<URLDepthPair> pendingURLs;
    public LinkedList<URLDepthPair> processedURLs;
    private ArrayList<String> seenURLs = new ArrayList<String>();
    public int waitingThreads;
    public int depth;
    public URLPool(int dept) {
        depth = dept;
        waitingThreads = 0;
        pendingURLs = new LinkedList<URLDepthPair>();
        processedURLs = new LinkedList<URLDepthPair>();
    }
    public synchronized ArrayList<String> getSeenList() {
        return seenURLs;
    }
    public synchronized int getWaits() {
        return waitingThreads;
    }
    public synchronized int size() {
        return pendingURLs.size();
    }
    public synchronized boolean put(URLDepthPair depthPair) {
        boolean added = false;
        if (depthPair.getDepth() <= depth) {
            if(!seenURLs.contains(depthPair.getURL())) pendingURLs.addLast(depthPair);

            added = true;
            if (DEBUG) System.out.println(depthPair);
            if (DEBUG) System.out.println(waitingThreads);
            if (waitingThreads > 0) waitingThreads--;
            this.notify();
        } else { seenURLs.add(depthPair.getURL()); }
        return added;
    }

    public synchronized URLDepthPair get() {
        URLDepthPair myDepthPair = null;
        if (pendingURLs.size() == 0) {
            waitingThreads+=1;
            try { this.wait(); }
            catch (InterruptedException e) { if (DEBUG) System.err.println("MalformedURLException: " + e.getMessage());
                return null; } }

        myDepthPair = pendingURLs.removeFirst();
        seenURLs.add(myDepthPair.getURL());
        processedURLs.add(myDepthPair);
        return myDepthPair;
    }
}