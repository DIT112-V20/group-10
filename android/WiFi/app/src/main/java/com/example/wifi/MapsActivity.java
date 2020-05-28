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
        String response = RequestHelper.mapCoordinatesRequestToServer();

         //Create markers for the city data.
        // Must run this on the UI thread since it's a UI operation.
        runOnUiThread(new Runnable() {
            public void run() {
                try {
    
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
        
        String []lat = array[1].split("\n");
        
    
        jsonObj.put(array[0],lat[0]);
        jsonObj.put(lat[1], array[2]);
        System.out.println("My Objects are "+ jsonObj);
        //LatLng magess = new LatLng(57.706478,11.9374733);
        LatLng magess = new LatLng(jsonObj.getDouble("Lat:"),jsonObj.getDouble("Lon:"));
        
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

