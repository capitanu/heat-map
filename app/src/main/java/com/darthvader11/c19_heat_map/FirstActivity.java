package com.darthvader11.c19_heat_map;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FirstActivity extends AppCompatActivity {


   //public boolean firstStart;
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);

        boolean firstStart = getSharedPreferences("pref",MODE_PRIVATE)
                .getBoolean("firstStart",false);


         /* if(firstStart){
              startActivity(new Intent(FirstActivity.this, MapsActivity.class));
         }*/

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSharedPreferences("pref", MODE_PRIVATE).edit()
                        .putBoolean("firstStart", true).apply();
                Intent intent = new Intent(FirstActivity.this,MapsActivity.class);
                startActivity(intent);
            }
        });
    }
}
