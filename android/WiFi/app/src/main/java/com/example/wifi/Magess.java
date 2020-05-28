package com.example.wifi;

import java.net.MalformedURLException;
import java.net.URL;

class Magess {

    private URL url;


    Magess(String url) throws MalformedURLException {
        this.url = new URL(url);

    }



    URL getUrl() {
        return this.url;
    }
}


