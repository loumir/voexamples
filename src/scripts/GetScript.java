package scripts;

import java.io.InputStream;

public class GetScript {

    public static String get(String name){
        InputStream file = GetScript.class.getResourceAsStream(name + ".js");
        if (file != null) {
            java.util.Scanner s = new java.util.Scanner(file).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        }

        return "";
    }
}
