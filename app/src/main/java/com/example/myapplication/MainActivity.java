package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class MainActivity extends AppCompatActivity {
    private RelativeLayout homeRl;
    private ProgressBar loadingPB;
    private TextView LatitudeLongitudeTV, temperatureTV, conditionTV, humidityTV, windTV;
    private RecyclerView weatherRV;
    private TextInputLayout cityEdt;
    private ImageView backIV, iconIV, searchIV;
    private ArrayList<WeatherRVModel> weatherRVModelArrayList;
    private WeatherRVAdapter weatherRVAdapter;
    private String cityName;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MyApp", "onCreate started");

        //used to make application full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_main);
        homeRl = findViewById(R.id.idRLHome);
        loadingPB = findViewById(R.id.idPBLoading);
        LatitudeLongitudeTV = findViewById(R.id.idTVLatitudeLongitude);
        temperatureTV = findViewById(R.id.idTVTemperature);
        humidityTV = findViewById(R.id.idTVHumidity);
        windTV = findViewById(R.id.idTVWindSpeed);
        conditionTV = findViewById(R.id.idTVCondition);
        weatherRV = findViewById(R.id.idRvWeather);
        cityEdt = findViewById(R.id.idTILCity);
        backIV = findViewById(R.id.idIVBack);
        iconIV = findViewById(R.id.idIVIcon);
        searchIV = findViewById(R.id.idIVSearch);
        weatherRVModelArrayList = new ArrayList<>();
        weatherRVAdapter = new WeatherRVAdapter(this, weatherRVModelArrayList);
        weatherRV.setAdapter(weatherRVAdapter);



        //getWeatherInfo(30.28,-97.76);

        searchIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Hi", "Hi");
                //String LatitudeLongitude = LatitudeLongitudeTV.getText().toString().trim();
                //String[] t = LatitudeLongitude.split("\\|");
                //String Longitude = t[0];
                //String Latitude = t[1];
                //Log.d("Latitude","Latitude"+LatitudeLongitude);
                //Log.d("Longitude","Longitude"+LatitudeLongitude);

                getWeatherInfo(Double.parseDouble("-97.76"), Double.parseDouble("30.28"));
                //String city = cityEdt.getEditText().getText().toString();
                /*String LatitudeLongitude = LatitudeLongitudeTV.getText().toString().trim();
                String[] t = LatitudeLongitude.split("\\|");
                //double Longitude = -97.76;
                //double Latitude = 30.28;
                String Longitude = t[0];
                String Latitude = t[1];

                if (Latitude.isEmpty() || Longitude.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter latitude and longitude", Toast.LENGTH_SHORT).show();
                } else {
                    LatitudeLongitudeTV.setText(Latitude + "|" + Longitude);
                    getWeatherInfo(Double.parseDouble(Longitude), Double.parseDouble(Latitude));
                    //getWeatherInfo();
                }

                 */
            }
        });


    }

    //public void onRequestPermissionResult( )

    private void getWeatherInfo(Double longitude, Double latitude) {
        Log.d("MyApp", "getWeatherInfo started");
        // Construct the API URL with the latitude and longitude
        //String apiUrl = "https://api.open-meteo.com/v1/forecast?daily=temperature_2m_max&daily=temperature_2m_min&current_weather=true&latitude=" + latitude + "&longitude=" + longitude + "&hourly=temperature_2m&temperature_unit=fahrenheit";
        String apiUrl ="https://api.open-meteo.com/v1/forecast?latitude="+latitude+"&longitude="+longitude+"&hourly=temperature_2m,relative_humidity_2m,wind_speed_10m&temperature_unit=fahrenheit&wind_speed_unit=mph&precipitation_unit=inch";
        LatitudeLongitudeTV.setText(( latitude + "|" +  longitude));

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, apiUrl, null, response -> {
            Log.d("MyApp", "Response: " + response.toString());
            loadingPB.setVisibility(View.GONE);
            homeRl.setVisibility(View.VISIBLE);
            weatherRVModelArrayList.clear();
            try {
                String temperature = response.getJSONObject("hourly_units").getString("temperature_2m");
                //temperatureTV.setText(temperature + "°F");
                Log.d("CurrentTemp", "Temp" + temperature);
                conditionTV.setText("Great Weather!");
                Log.d("Condition", "Response: " + "Great Weather!");
                Picasso.get().load("https://cdn.weatherapi.com/weather/64x64/night/176.png").into(iconIV);
                Picasso.get().load("https://img.freepik.com/free-photo/beautiful-sea-ocean-white-cloud-blue-sky_74190-7494.jpg?w=360&t=st=1700777900~exp=1700778500~hmac=4b8a26430cbc599dde58b13b22b9982c0bcf6341ef9e26b3b2bc23ad8d964263").into(backIV);

                JSONObject hourly = response.getJSONObject("hourly");
                JSONArray timeArray = hourly.getJSONArray("time");
                JSONArray temperatureArray = hourly.getJSONArray("temperature_2m");
                JSONArray WindArray = hourly.getJSONArray("wind_speed_10m");
                JSONArray HumidityArray = hourly.getJSONArray("relative_humidity_2m");


                //Date currentTime = Calendar.getInstance().getTime();
                //String currentTime = new SimpleDateFormat("dd'T'HH", Locale.getDefault()).format(new Date());
                LocalDateTime currentTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd'T'HH");
                String formattedCurrentTime = currentTime.format(formatter);
                Log.d("CurrentTime", "CurrentTime: " + formattedCurrentTime);
                temperatureTV.setText(temperatureArray.getString(0));
                for (int i = 0; i < timeArray.length(); i++) {
                    String time = timeArray.getString(i);
                    String temp = temperatureArray.getString(i);
                    String humidity = HumidityArray.getString(i);
                    String windSpeed = WindArray.getString(i);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                    if(time.contains(formattedCurrentTime)){
                        temperatureTV.setText(temp);
                    }
                    Log.d("Time", i+ " Time: " + time);
                    Log.d("Temp", i+ " Temp: " + temperature);
                    weatherRVModelArrayList.add(new WeatherRVModel(time, temp, windSpeed, humidity, "https://cdn.weatherapi.com/weather/64x64/night/176.png"));
                }
                weatherRVAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Log.e("MyApp", "Network request error: " + error.getMessage());

            Toast.makeText(MainActivity.this, "Please enter valid Latitude/Longitude", Toast.LENGTH_SHORT).show();

        });
        requestQueue.add(jsonObjectRequest);

    }
}