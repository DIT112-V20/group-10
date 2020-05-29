package com.example.wifi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private Button button;
    TextView textView;

    private Button location;
    private Button automatic;

    private static final String msg = "Android: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonsInitialiser();


        button.setOnClickListener(v -> openActivitySecond());


        automatic.setOnClickListener(v -> RequestHelper.requestToServer("/G"));



        location.setOnClickListener(v -> openMapActivity());
    }


    public void buttonsInitialiser(){
        textView  =  findViewById(R.id.makeDelivText);
        button    =  findViewById(R.id.manuallyBtn);
        location  =  findViewById(R.id.currentLocation);
        automatic =  findViewById(R.id.manuallyBtn2);
    }


    public void openActivitySecond() {
        Intent intent = new Intent(MainActivity.this, ManualControlActivity.class);
        startActivity(intent);
    }

    public void openMapActivity (){
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(intent);
    }


    /** Called when the activity has become visible. */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(msg, "The onResume() event");
    }

    /** Called when another activity is taking focus. */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(msg, "The onPause() event");
    }

    /** Called when the activity is no longer visible. */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(msg, "The onStop() event");
    }

    /** Called just before the activity is destroyed. */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(msg, "The onDestroy() event");
    }



}



