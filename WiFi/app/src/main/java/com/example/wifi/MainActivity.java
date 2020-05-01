package com.example.wifi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.manuallyBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                openActivitySecond();
            }
        });
    }
    public void openActivitySecond() {
        Intent intent = new Intent(this, ManualControlActivity.class);
        startActivity(intent);
    }




}
