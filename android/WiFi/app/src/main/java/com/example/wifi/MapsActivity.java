package com.example.wifi;

import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static java.lang.System.in;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String LOG_TAG = "MapActivity";
    //private static final String url = RequestHelper.magess.getUrl().toString() + "/M";
    //http://www.mocky.io/v2/5ece50ae3000005000ea1045

    //private static final CharSequence[] MAP_TYPE_ITEMS = {"Road Map", "Hybrid", "Satellite", "Terrain"};

    //private static final String SERVICE_URL = RequestHelper.magess.getUrl().toString() + "/M";
    private GoogleMap map;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//         SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//               .findFragmentById(R.id.map);
//         mapFragment.getMapAsync(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }


    private void setUpMapIfNeeded() {
        if (map == null) {
            SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFrag.getMapAsync( this);
            if (map != null) {
                setUpMap();
            }
        }
    }


    private void setUpMap() {
        // Retrieve the location data from the web service
        // In a worker thread since it's a network operation.
        new Thread(new Runnable() {
            public void run() {
                try {
                    retrieveAndAddCities();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Cannot retreive location", e);
                    return;
                }
            }
        }).start();
    }


    protected void retrieveAndAddCities() throws IOException {
        //System.out.println(SERVICE_URL);
        String response = RequestHelper.mapCoordinatesRequestToServer();

        System.out.println("The body is in mapactivity is " + response);
//        final StringBuilder json = new StringBuilder();
//        try {
//            // Connect to the web service
//            //URL url = new URL(SERVICE_URL);
//            //System.out.println(SERVICE_URL);
//            //conn = (HttpURLConnection) url.openConnection();
//            //InputStreamReader in = new InputStreamReader(conn.getInputStream());
//
//            // Read the data into the StringBuilder
////            int read;
////            char[] buff = new char[1024];
////            while (response != "<br>") {
////                json.append(response);
////                //System.out.println("My values are changing "+ json);
////            }
//
//         //   System.out.print("My json String is "+ json.toString());
//
//            //System.out.print("My json before was " + json.substring(0,4) + " ");
//            //System.out.println("My json before was " + json.length() + " ");
//            //System.out.println();
//        } catch (IOException e) {
//            Log.e(LOG_TAG, "Error connecting to service", e);
//            throw new IOException("Error connecting to service", e);
//        } finally {
//            if (conn != null) {
//                conn.disconnect();
//            }

         //Create markers for the city data.
        // Must run this on the UI thread since it's a UI operation.
        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    //System.out.println("My values are "+ json.toString());
                    createMarkersFromJson(response);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "Error processing JSON", e);
                }
            }
        });
    }



    void createMarkersFromJson(String json) throws JSONException {

        System.out.println("My json is " + json);
        JSONObject jsonObj = new JSONObject();

        String []array = json.split(" ");
        System.out.println("My array length " + array.length);
        System.out.println("My first element "+ array[0]);
        System.out.println("My second element "+ array[1]);
        String []lat = array[1].split("\n");
        System.out.println("My lat length is " + lat.length);
        System.out.println("My third element "+ array[2]);
        //String []lat = array[0].split(":");
        //String lon[] = array[1].split(":");
        //String latitude = lat[0].trim();
//        String latVal = lat[1].trim();
//        String longitude = lon[0].trim();
//        String lonVal = lon[1].trim();
//        String lati[] = new String[lat.length];
//        String longi[] = new String[lon.length];
//        lati[0] = latitude;
//        lati[1] = latVal;
//        longi[0] = longitude;
//        longi[1] = lonVal;

        //System.out.println("My first lat is " + array[0]);
        //System.out.println("My lat is "+ array[1]);
        //System.out.println("My lon is "+ array[3]);
        //String []lat = json.split("<br>");
//        System.out.println("My array size is "+ lat.length);
//        System.out.println("My first value "+ lat[0]);
//        System.out.println("My second value "+ lat[1]);
        //System.out.println("My third value "+ array[2]);
        jsonObj.put(array[0], 57.706478);
                //lat[0]);
        jsonObj.put(lat[1], 11.9374733);
                //array[2]);
        System.out.println("My Objects are "+ jsonObj);
        //LatLng magess = new LatLng(57.706478,11.9374733);
        LatLng magess = new LatLng(jsonObj.getDouble("Lat:"),jsonObj.getDouble("Lon:"));
        //Print statements were mainly used for figuring out the JsonObject
        //But gonna leave it here as comments for later activities.
        //System.out.println("My whaaat 1 "+ jsonObj.getDouble("lat"));
        //System.out.println("My whaaat 2 "+ jsonObj.getDouble("lng"));
        map.addMarker(new MarkerOptions().position(magess).title("Marker of Magess"));
        map.moveCamera(CameraUpdateFactory.newLatLng(magess));

    }


    private void showMapTypeSelectorDialog() {
        // Prepare the dialog by setting up a Builder.
//        final String fDialogTitle = "Select Map Type";
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(fDialogTitle);
//
//        // Find the current map type to pre-check the item representing the current state.
//        int checkItem = map.getMapType() - 1;
//
//        // Add an OnClickListener to the dialog, so that the selection will be handled.
//        builder.setSingleChoiceItems(
//                MAP_TYPE_ITEMS,
//                checkItem,
//                new DialogInterface.OnClickListener() {
//
//                    public void onClick(DialogInterface dialog, int item) {
//                        // Locally create a finalised object.
//
//                        // Perform an action depending on which item was selected.
//                        switch (item) {
//                            case 1:
//                                map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//                                break;
//                            case 2:
//                                map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
//                                break;
//                            case 3:
//                                map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//                                break;
//                            default:
//                                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//                        }
//                        dialog.dismiss();
//                    }
//                }
//        );
//
//        // Build the dialog and show it.
//        AlertDialog fMapTypeDialog = builder.create();
//        fMapTypeDialog.setCanceledOnTouchOutside(true);
//        fMapTypeDialog.show();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    //@Override


    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        setUpMap();
        //showMapTypeSelectorDialog();
        
        /* The codes below were generated automatically
        so Just leaving it there 
        */

        // Add a marker in Sydney and move the camera

//        LatLng sydney = new LatLng(57.706478,11.9374733);
//        map.addMarker(new MarkerOptions().position(sydney).title("Marker of Magess"));
//        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}

