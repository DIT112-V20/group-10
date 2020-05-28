package com.example.wifi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private Button button;
    TextView textView;

    private Button location;
    private Button automatic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonsInitialiser();


        button.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View v) {

                openActivitySecond();
//                }
            }
        });


        automatic.setOnClickListener(new View.OnClickListener(){
                                         @Override
                                         public void onClick(View v) {
                                             RequestHelper.requestToServer("/G");
                                         }
                                     }
        );



        location.setOnClickListener(new View.OnClickListener(){

                                        @Override

                                        public void onClick(View v) {
                                            openMapActivity();
                                            //RequestHelper.mapCoordinatesRequestToServer("/M");
                                        }
                                    }
        );
    }


    public void buttonsInitialiser(){
        textView  = (TextView) findViewById(R.id.makeDelivText);
        button    = (Button)    findViewById(R.id.manuallyBtn);
        location  = (Button)   findViewById(R.id.currentLocation);
        automatic = (Button)  findViewById(R.id.manuallyBtn2);
    }


    public void openActivitySecond() {
        Intent intent = new Intent(MainActivity.this, ManualControlActivity.class);
        startActivity(intent);
    }

    public void openMapActivity (){
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(intent);
    }




}
