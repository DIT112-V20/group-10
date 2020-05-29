package com.example.wifi;


import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

class MapCoordinatesRequest {


    String request(String url) throws IOException {
         OkHttpClient newClient = new OkHttpClient.Builder()
                 .connectTimeout(5, TimeUnit.MINUTES) // connect timeout
                 .writeTimeout(5, TimeUnit.MINUTES) // write timeout
                 .readTimeout(5, TimeUnit.MINUTES) // read timeout
                 .build();

         Request request = new Request.Builder().url(url).build();

        String serverResponse ;
        try (Response response = newClient.newCall(request).execute()) {
             try {
                 Thread.sleep(3000);

             }catch (Exception e){
                 e.printStackTrace();
             }

            serverResponse = Objects.requireNonNull(response.body()).string();
        }
        return serverResponse;
    }
    }

