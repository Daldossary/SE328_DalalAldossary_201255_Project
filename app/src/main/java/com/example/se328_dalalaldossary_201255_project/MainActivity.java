package com.example.se328_dalalaldossary_201255_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button firebase, sql, weather;

    static TextView tempMain, humidMain, conditionMain;

    SharedPreferences shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shared = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        firebase = (Button) findViewById(R.id.firebaseBttn);
        sql = (Button) findViewById(R.id.sqlBttn);
        weather = (Button) findViewById(R.id.weatherBttn);

        tempMain = (TextView) findViewById(R.id.tempText);
        humidMain = (TextView) findViewById(R.id.humidText);
        conditionMain = (TextView) findViewById(R.id.conditionText);

        tempMain.setText(shared.getString("temp", ""));
        humidMain.setText(shared.getString("humid", ""));
        conditionMain.setText(shared.getString("condition", ""));

        firebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this , FirebaseActivity.class));
            }
        });

        sql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SqlActivity.class));
            }
        });

        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, WeatherActivity.class));
            }
        });

    }

}