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

        SharedPreferences pref = getSharedPreferences("pref",MODE_PRIVATE);
        boolean firstStart = pref.getBoolean("firstStart",true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);
        Button button = findViewById(R.id.button);


        if(firstStart){

            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("firstStart",false);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this,MapsActivity.class);
                startActivity(intent);
            }
        });
    }
}
