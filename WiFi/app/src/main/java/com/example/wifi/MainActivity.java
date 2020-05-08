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
    TextView textView;
    //HTTP server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.makeDelivText);
        //server = new HTTP();
        button = (Button) findViewById(R.id.manuallyBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                RequestHelper.requestToServer("");
                //Toast.makeText(getApplicationContext(), "Response code "+ String.valueOf(requestStatus) ,Toast.LENGTH_SHORT).show();
                //server.request(Cars.magess.getUrl().toString());
                //
                    openActivitySecond();
//                }else{
//                    Toast.makeText(getApplicationContext(),"Not connected",Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }
    public void openActivitySecond() {
        Intent intent = new Intent(MainActivity.this, ManualControlActivity.class);
        startActivity(intent);

    }




}
