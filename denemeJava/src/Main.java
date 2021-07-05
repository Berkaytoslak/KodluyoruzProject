import java.util.*;
import java.io.*;
import java.net.*;
import java.util.stream.Collectors;

public class Main {

    public static String getStringFromStream(final InputStream inputStream){
        String deger = null;
        BufferedReader streamReader = null;
        try {
            streamReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
            deger=streamReader.lines().collect(Collectors.joining("\n"));
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
            return null;
        }
        finally {
            try {
                if (streamReader != null){
                    streamReader.close();
                }
                if (inputStream != null){
                    inputStream.close();
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        return deger;
    }

    public static String simpleParseArrayProperty(String json, final String propertyName){
        if (json == null){
            return null;
        }
        int lastIndex = json.lastIndexOf(String.format("\"%s\"",propertyName ));
        json = json.substring(lastIndex);
        lastIndex = json.lastIndexOf("[");
        json = json.substring(lastIndex+1);
        return json = json
                .replaceAll("[\\]}\"]","")
                .replaceAll("\\,",",")
                .trim();
    }

    public static void main (String[] args) {
        System.setProperty("http.agent", "Chrome");
        try {
            URL url = new URL("https://coderbyte.com/api/challenges/json/rest-get-simple");
            try {
                URLConnection connection = url.openConnection();
                InputStream inputStream = connection.getInputStream();
                System.out.println(simpleParseArrayProperty(getStringFromStream(inputStream),"hobbies"));
            } catch (IOException ioEx) {
                System.out.println(ioEx);
            }
        } catch (MalformedURLException malEx) {
            System.out.println(malEx);
        }
    }

}
