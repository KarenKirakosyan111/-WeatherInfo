package com.karen.weatherinfo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.karen.weatherinfo.countryRecyclerView.CountryRecyclerViewAdapter;
import com.karen.weatherinfo.model.Country;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String API_KEY = "&APPID=8fadd44faaf35e5da3c8d20487fbba3a";
    private List<Country> countries;
    private CountryRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private TextView mCountry, temp, pressure, humidity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        countries = new ArrayList<>();

        initViews();
        initAdapter();
        fillCountries();
        checkPermission();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.countryList);
        mCountry = findViewById(R.id.country);
        temp = findViewById(R.id.temp);
        pressure = findViewById(R.id.pressure);
        humidity = findViewById(R.id.humidity);
    }

    private void initAdapter() {
        adapter = new CountryRecyclerViewAdapter(this, countries);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.setIOnClickItem(onClickItem);
    }

    private void fillCountries() {
        Country country = new Country("Yerevan");
        countries.add(country);

        country = new Country("London");
        countries.add(country);

        country = new Country("Moscow");
        countries.add(country);

        country = new Country("Washington");
        countries.add(country);

        adapter.notifyDataSetChanged();
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.INTERNET}, 1);
            }
        }
    }

    private void sendRequest(String country) {
        String stringUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + country + API_KEY;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, stringUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            mCountry.setText("Country " + response.getString("name"));
                            temp.setText("Temperature " + new DecimalFormat("##.#").format(parseToCelsius(response)) + " °C");
                            pressure.setText("Pressure " + response.getJSONObject("main").getString("pressure") + " hPa");
                            humidity.setText("Humidity " + response.getJSONObject("main").getString("humidity") + " %");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mCountry.setText("ERROR " + error.getMessage());
                    }
                });

        queue.add(jsonObjectRequest);
    }

    private float parseToCelsius(JSONObject kelvin) throws JSONException {
        return Float.parseFloat((kelvin.getJSONObject("main").getString("temp"))) - 273.15f;
    }

    CountryRecyclerViewAdapter.IOnClickItem onClickItem = new CountryRecyclerViewAdapter.IOnClickItem() {
        @Override
        public void onClickItem(Country country) {
            Log.d(TAG, country.getCountryName());
            sendRequest(country.getCountryName());
        }
    };
}
