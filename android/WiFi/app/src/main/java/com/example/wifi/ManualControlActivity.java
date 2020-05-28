package com.example.wifi;


import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ManualControlActivity extends AppCompatActivity {

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
            Toast.makeText(getApplicationContext(), "Stopping", Toast.LENGTH_SHORT).show();

        });


        speedDown.setOnClickListener(v -> {
            RequestHelper.requestToServer("/D");
            Toast.makeText(getApplicationContext(), "Stopping", Toast.LENGTH_SHORT).show();

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
}
