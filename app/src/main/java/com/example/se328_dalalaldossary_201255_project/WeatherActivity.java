package com.example.se328_dalalaldossary_201255_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherActivity extends AppCompatActivity {

    EditText editWeather;
    Button editBttn;

    String originalUrl = "https://api.openweathermap.org/data/2.5/weather?q=london,uk&appid=ce355355a86e6175580bb7e0b181f1bf&units=metric";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        editWeather = (EditText) findViewById(R.id.editWeather);
        editBttn = (Button) findViewById(R.id.changeWeatherBttn);

        weather(originalUrl);

        editBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://api.openweathermap.org/data/2.5/weather?q=";
                String newPlace = editWeather.getText().toString();
                url = url + newPlace + "&appid=ce355355a86e6175580bb7e0b181f1bf&units=metric";
                weather(url);
            }
        });
    }

    public void weather(String url) {
        JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("Dalal", "Response Received");
                        Log.d("Dalal-JSON", response.toString());
                        try {
                            JSONObject jsonMain = response.getJSONObject("main");

                            double temp = jsonMain.getDouble("temp");
                            Log.d("Dalal-Temp", String.valueOf(temp));
                            String tempMain = "Temp: " + String.valueOf(temp) + "C";

                            int humidity = jsonMain.getInt("humidity");
                            Log.d("Dalal-Humidity", String.valueOf(temp));
                            String humidMain ="Humidity: " + String.valueOf(humidity) + "%";

                            String conditionMain = "Condition: XXXX";
                            JSONArray jsonArray = response.getJSONArray("weather");
                            for (int i=0; i<jsonArray.length();i++){
                                Log.d("Dalal-array",jsonArray.getString(i));
                                JSONObject oneObject = jsonArray.getJSONObject(i);
                                String wheater =
                                        oneObject.getString("main");
                                Log.d("Dalal-w",wheater);

                                if(wheater.equals("Clear")) {
                                    conditionMain = "Condition: Clear";

                                } else if(wheater.equals("Clouds")) {
                                    conditionMain = "Condition: Clouds";

                                } else if(wheater.equals("Rain")) {
                                    conditionMain = "Condition: Rain";

                                } else if(wheater.equals("Snow")) {
                                    conditionMain = "Condition: Snow";

                                } else if(wheater.equals("Thunderstorm")) {
                                    conditionMain = "Condition: Thunderstorm";

                                } else if(wheater.equals("Drizzle")) {
                                    conditionMain = "Condition: Drizzle";
                                }
                            }

                            SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this);
                            SharedPreferences.Editor edit = shared.edit();
                            edit.putString("condition", conditionMain);
                            edit.putString("temp", tempMain);
                            edit.putString("humid", humidMain);
                            edit.commit();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("Receive Error", e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Dalal", "Error Retrieving URL");
            }
        }
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObj);
    }

}