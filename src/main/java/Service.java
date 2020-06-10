
import java.util.ArrayList;
import java.util.InputMismatchException;

public class Service {
    // get key
    public static ArrayList<String> key (String url){
        ArrayList<String> result = new ArrayList<String>();

        url = url.substring(url.indexOf('?')+1);
        while (url != " ") {

            result.add(url.substring(0, url.indexOf('=')));
            if (url.indexOf('&') != -1)
                url = url.substring(url.indexOf('&') + 1);
            else url = " ";
        }

        return result;
    }
    // get value
    public static ArrayList<String > value(String url){
        ArrayList<String> res = new ArrayList<String>();

        url = url.substring(url.indexOf('?')+1);
        while (url != " ") {

            if (url.indexOf('&') != -1) {
                res.add(url.substring(url.indexOf("=") + 1, url.indexOf('&')));
                url = url.substring(url.indexOf('&') + 1);
            } else {
                res.add(url.substring(url.indexOf("=") + 1, url.indexOf(' ')));
                url = " ";
            }

        }
        return res;
    }

}