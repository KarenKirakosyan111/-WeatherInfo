package com.karen.weatherinfo;

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

    private void sendRequest(final String country) {
        String stringUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + country + API_KEY;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, stringUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d(TAG, String.valueOf(response.getJSONObject("main").getString("temp")));
                            mCountry.setText("Country " + response.getString("name"));
                            temp.setText("temp " + response.getJSONObject("main").getString("temp"));
                            pressure.setText("pressure " + response.getJSONObject("main").getString("pressure"));
                            humidity.setText("humidity " + response.getJSONObject("main").getString("humidity"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        queue.add(jsonObjectRequest);
    }

    CountryRecyclerViewAdapter.IOnClickItem onClickItem = new CountryRecyclerViewAdapter.IOnClickItem() {
        @Override
        public void onClickItem(Country country) {
            Log.d(TAG, country.getCountryName());
            sendRequest(country.getCountryName());
        }
    };
}
