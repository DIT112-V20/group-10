package com.example.wifi;

import java.net.MalformedURLException;
import java.net.URL;

public class Magess {

    private URL url;
    //private String name;
    //private boolean active;

    public Magess(String url, String name) throws MalformedURLException {
        URL carUrl = new URL(url);
        this.url = carUrl;
        //this.name = name;
        //active = true;
    }
    public void setUrl(URL url){
        this.url = url;
    }

//    public void setName(String name) {
//        this.name = name;
//    }

    public URL getUrl(){
        return this.url;
    }

//    public String getName(){
//        return this.name;
//    }
//
//    public boolean isActive() {
//        return active;
//    }
//
//    public void setActive(boolean active) {
//        this.active = active;
//    }
}
