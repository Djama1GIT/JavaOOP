import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws MalformedURLException {
        URL url;
        if (!args[0].startsWith("http://")){
            url = new URL("http://" + args[0]);
        } else {
            url = new URL(args[0]);
        }
        Crawler parser = new Crawler();
//        System.out.println(url);
        parser.startParsing(url, Integer.valueOf(args[1]), 0);

        LinkedList<UrlDepthPair> result = parser.proccessed;
        HashMap<String, Integer> ress = new HashMap<String, Integer>();
        result.forEach(urlDepthPair -> {
            if (ress.containsKey(urlDepthPair.url)){
                if (ress.get(urlDepthPair.url) < urlDepthPair.depth) ress.replace(urlDepthPair.url, urlDepthPair.depth);
            } else {
                ress.put(urlDepthPair.url, urlDepthPair.depth);
            }
        });
        ress.forEach((ress_url, ress_depth) -> {
            System.out.println("URL is " + ress_url + " | depth: " + ress_depth);

        });
    }
}
