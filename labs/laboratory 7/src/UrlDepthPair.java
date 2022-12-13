import java.net.*;

public class URLDepthPair {
    static boolean DEBUG = false;
    private int curDepth;
    private String curURL;


    public URLDepthPair(String URL, int depth) { curDepth = depth; curURL = URL; }
    public String getURL() {
        return curURL;
    }
    public int getDepth() {
        return curDepth;
    }
    public String toString() {
        return Integer.toString(curDepth) + '\t' + curURL;
    }



    public String getPath() {
        try { return new URL(curURL).getPath(); }
        catch (MalformedURLException e) { if (DEBUG) System.err.println("MalformedURLException: " + e.getMessage());
            return null; } }

    public String getHost() {
        try { return new URL(curURL).getHost(); }
        catch (MalformedURLException e) { if (DEBUG) System.err.println("MalformedURLException: " + e.getMessage());
            return null; } }
}