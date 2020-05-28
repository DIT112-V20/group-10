package com.example.wifi;

import android.util.Log;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

class MapCoordinatesRequest {
    String serverResponse = "";


     public String request(String url) throws IOException {
         OkHttpClient newClient = new OkHttpClient.Builder()
                 .connectTimeout(5, TimeUnit.MINUTES) // connect timeout
                 .writeTimeout(5, TimeUnit.MINUTES) // write timeout
                 .readTimeout(5, TimeUnit.MINUTES) // read timeout
                 .build();

         Request request = new Request.Builder().url(url).build();

         try (Response response = newClient.newCall(request).execute()) {
             try {
                 //set time in mili
                 Thread.sleep(4000);

             }catch (Exception e){
                 e.printStackTrace();
             }

            String responseBody = response.body().string();
            //System.out.println("First response is " +responseBody);
            serverResponse = responseBody ;
            //System.out.println("Second response is " + serverResponse);
            // ... do something with response
        }
        return serverResponse;
    }
    }

