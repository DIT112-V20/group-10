package com.example.wifi;

import java.net.MalformedURLException;
import java.util.ArrayList;


public class RequestHelper {

    //private static ArrayList<Magess> magessCars= new ArrayList<>();
    //private static Magess magess;
    public static Magess magess;
    static HTTP server = new HTTP();

    static {
        try {
            magess = new Magess("http://magess.local", "Magess");
           //Ip address when Gus device is connected.
            //magess = new Magess("http://192.168.43.40:80", "Magess");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static boolean requestToServer(String command) {
       return server.request(magess.getUrl().toString() + command);
    }

}
