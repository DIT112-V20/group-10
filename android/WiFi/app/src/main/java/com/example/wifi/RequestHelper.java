package com.example.wifi;

import java.io.IOException;
import java.net.MalformedURLException;


class RequestHelper {

    public static Magess magess;
    static HTTP server = new HTTP();
    static MapCoordinatesRequest mapCoordinatesRequest = new MapCoordinatesRequest();

    static {
        try {
            magess = new Magess("http://192.168.43.40");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void requestToServer(String command) {
        server.request(magess.getUrl().toString() + command);
    }

    static String mapCoordinatesRequestToServer() throws IOException {
        return mapCoordinatesRequest.request(magess.getUrl().toString()+ "/M");
    }

}

