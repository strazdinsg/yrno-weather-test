package no.ntnu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class that fetches a string from a specified Web URL. Creates a new thread
 * for the request
 *
 * @author Girts Strazdins, 2014-09-11
 *
 */
public class FetchWebString {

    /**
     * The result will be stored here (buffer)
     */
    private String resultBuffer;

    /**
     * Fetches a string from the web, sends HTTP GET request
     *
     * @param url
     * @return String response, or null on error
     */
    public static String fetch(String url) {
        FetchWebString fetcher = new FetchWebString();
        return fetcher.internalFetch(url);
    }

    /**
     * Fetches a string from the web, sends HTTP GET request
     *
     * @param url - the address where to send the request to
     * @return String response, or null on error
     */
    private String internalFetch(final String urlString) {
        resultBuffer = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
//            System.out.println("Staring connection...");
            // Starts the query
            conn.connect();
//            System.out.println("Connected");
            InputStream stream = conn.getInputStream();
            resultBuffer = convertStreamToString(stream);
            stream.close();
        } catch (Exception e) {
            System.out.println("Error fetching the web page: " + e.getMessage());
        }
        return resultBuffer;
    }

    static String convertStreamToString(java.io.InputStream is) {
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        String response = "";
        String inputLine;
        try {
            while ((inputLine = in.readLine()) != null) {
                response += inputLine;
            }
        } catch (IOException ex) {
            System.out.println("Could not read the data from HTTP response: " 
                    + ex.getMessage());
        } finally {
        }
        return response;
    }

}
