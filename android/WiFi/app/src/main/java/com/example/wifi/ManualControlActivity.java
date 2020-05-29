package com.example.wifi;


import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ManualControlActivity extends AppCompatActivity {
    private static final String msg = "Android: ";

    ImageButton forward;
    ImageButton backward;
    ImageButton left;
    ImageButton right;
    ImageButton stop;
    Button speedUp;
    Button speedDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_control);
        buttonsInitializer();

        forward.setOnClickListener(v -> {
            RequestHelper.requestToServer("/F");

                Toast.makeText(getApplicationContext(),"Going forward..", Toast.LENGTH_SHORT).show();


        });

        backward.setOnClickListener(v -> {
            RequestHelper.requestToServer("/B");

                Toast.makeText(getApplicationContext(),"Going backward", Toast.LENGTH_SHORT).show();


        });

        left.setOnClickListener(v -> {

            RequestHelper.requestToServer("/L");
            Toast.makeText(getApplicationContext(),"Going left", Toast.LENGTH_SHORT).show();


        });

        right.setOnClickListener(v -> {
            RequestHelper.requestToServer("/R");
                Toast.makeText(getApplicationContext(),"Going right", Toast.LENGTH_SHORT).show();


        });

        stop.setOnClickListener(v -> {
            RequestHelper.requestToServer("/S");
                Toast.makeText(getApplicationContext(), "Stopping", Toast.LENGTH_SHORT).show();



        });


        speedUp.setOnClickListener(v -> {
            RequestHelper.requestToServer("/A");
            Toast.makeText(getApplicationContext(), "Speed up", Toast.LENGTH_SHORT).show();

        });


        speedDown.setOnClickListener(v -> {
            RequestHelper.requestToServer("/D");
            Toast.makeText(getApplicationContext(), "Slowed down", Toast.LENGTH_SHORT).show();

        });

    }

    private void buttonsInitializer() {
        forward   =  findViewById(R.id.forward);
        backward  =  findViewById(R.id.backward);
        left      =  findViewById(R.id.left);
        right     =  findViewById(R.id.right);
        stop      =  findViewById(R.id.stop);
        speedDown =  findViewById(R.id.speedDown);
        speedUp   =  findViewById(R.id.speedUp);

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
