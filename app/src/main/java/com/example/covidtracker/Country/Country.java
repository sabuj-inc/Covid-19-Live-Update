package com.example.covidtracker.Country;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidtracker.CovidInterface;
import com.example.covidtracker.R;
import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Country extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView countryRecyclerView;
    EditText search;
    ImageView editClean;
    CardView progress;
    String url = "https://corona.lmao.ninja/v2/";
    CountryAdapter countryAdapter;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);
        whiteStatus();
        //nav
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_menu);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Affected country");

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);

        //navigationView
        navigationView.setNavigationItemSelectedListener(this);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });


        findView();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CovidInterface covidInterface = retrofit.create(CovidInterface.class);
        Call<List<CountryModel>> call = covidInterface.countryInterface();
        call.enqueue(new Callback<List<CountryModel>>() {
            @Override
            public void onResponse(Call<List<CountryModel>> call, Response<List<CountryModel>> response) {
                progress.setVisibility(View.GONE);
                countryAdapter = new CountryAdapter(Country.this, response.body());
                countryRecyclerView.setAdapter(countryAdapter);
            }

            @Override
            public void onFailure(Call<List<CountryModel>> call, Throwable t) {
                Toast.makeText(Country.this, "Something Wrong! Check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void findView() {
        countryRecyclerView = findViewById(R.id.countryRecyclerView);
        search = findViewById(R.id.editTextId);
        editClean = findViewById(R.id.editClean);
        progress = findViewById(R.id.progress);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        countryRecyclerView.setLayoutManager(linearLayoutManager);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                countryAdapter.getFilter().filter(s.toString().toLowerCase().trim());
                if (!s.toString().isEmpty()) {
                    editClean.setVisibility(View.VISIBLE);
                } else {
                    editClean.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.setText("");
            }
        });
    }
    //white activity
    public void whiteStatus() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
            getWindow().setStatusBarColor(ContextCompat.getColor(this, android.R.color.white));// set status background white
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Toast.makeText(Country.this, "Home", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_share:
                Toast.makeText(Country.this, "Share", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_rate:
                Toast.makeText(Country.this, "Rate", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_about:
                Toast.makeText(Country.this, "About", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_feedback:
                Toast.makeText(Country.this, "Feedback", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_instagram:
                Toast.makeText(Country.this, "Instagram", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_setting:
                Toast.makeText(Country.this, "Setting", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_privacy:
                Toast.makeText(Country.this, "Privacy", Toast.LENGTH_SHORT).show();
                break;
        }
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        return true;
    }
}