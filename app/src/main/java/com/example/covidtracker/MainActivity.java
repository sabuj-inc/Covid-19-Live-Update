package com.example.covidtracker;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    String url = "https://corona.lmao.ninja/v2/";
    ImageView countryFlag;
    TextView countryName, lastUpdate;
    TextView todayCase, todayRecovered, todayDead;
    TextView confirm, active, recovered, dead, tests;
    PieChart pieChart;
    LinearLayout progress;
    String country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        country = getIntent().getStringExtra("country");


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CovidInterface covidInterface = retrofit.create(CovidInterface.class);
        Call<List<Model>> call = covidInterface.getCovidInterface();

        call.enqueue(new Callback<List<Model>>() {
            @Override
            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                List<Model> modelList = response.body();
                for (int i = 0; i < modelList.size(); i++) {
                    if (modelList.get(i).getCountry().equalsIgnoreCase(country)) {
                        progress.setVisibility(View.GONE);
                        Picasso.get().load(modelList.get(i).getCountryInfo().getFlag())
                                .into(countryFlag);
                        //Toast.makeText(MainActivity.this, ""+modelList.get(i).getCountryInfo().getFlag(), Toast.LENGTH_SHORT).show();
                        countryName.setText(modelList.get(i).getCountry());
                        lastUpdate.setText("Last Update: " + format(modelList.get(i).getUpdated()));

                        startCountAnimation(todayCase, modelList.get(i).getTodayCases());
                        startCountAnimation(todayRecovered, modelList.get(i).getTodayRecovered());
                        startCountAnimation(todayDead, modelList.get(i).getTodayDeaths());


                        startCountAnimation(confirm, modelList.get(i).getCases());
                        startCountAnimation(active, modelList.get(i).getActive());
                        startCountAnimation(recovered, modelList.get(i).getRecovered());
                        startCountAnimation(dead, modelList.get(i).getDeaths());
                        startCountAnimation(tests, modelList.get(i).getTests());

                        active.setText(numberFormat(modelList.get(1).getActive()));
                        recovered.setText(numberFormat(modelList.get(1).getRecovered()));
                        dead.setText(numberFormat(modelList.get(1).getDeaths()));
                        tests.setText(numberFormat(modelList.get(1).getTests()));

                        pieChart.addPieSlice(new PieModel("Confirm", Integer.parseInt(modelList.get(i).getCases()), getResources().getColor(R.color.pi_yellow)));
                        pieChart.addPieSlice(new PieModel("Active", Integer.parseInt(modelList.get(i).getActive()), getResources().getColor(R.color.pi_blue)));
                        pieChart.addPieSlice(new PieModel("Recovered", Integer.parseInt(modelList.get(i).getRecovered()), getResources().getColor(R.color.pi_green)));
                        pieChart.addPieSlice(new PieModel("Dead", Integer.parseInt(modelList.get(i).getDeaths()), getResources().getColor(R.color.pi_red)));
                        pieChart.startAnimation();
                    }
                }


            }

            @Override
            public void onFailure(Call<List<Model>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void startCountAnimation(TextView view, String number) {
        ValueAnimator animator = ValueAnimator.ofInt(0, Integer.parseInt(number));
        animator.setDuration(1500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setText(numberFormat(animation.getAnimatedValue().toString()));
                //view.setText(animation.getAnimatedValue().toString());
            }
        });
        animator.start();
    }


    private String numberFormat(String number) {
        return NumberFormat.getInstance().format(Integer.parseInt(number));
    }

    //millisecond to date convart
    private String format(String updated) {
        DateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        long milliSeconds = Long.parseLong(updated);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    private void findView() {
        countryFlag = findViewById(R.id.countryFlag);
        countryName = findViewById(R.id.countryName);
        lastUpdate = findViewById(R.id.lastUpdate);
        todayCase = findViewById(R.id.todayCases);
        todayRecovered = findViewById(R.id.todayRecovered);
        todayDead = findViewById(R.id.todayDead);

        confirm = findViewById(R.id.confirm);
        active = findViewById(R.id.active);
        recovered = findViewById(R.id.recovered);
        dead = findViewById(R.id.dead);
        tests = findViewById(R.id.tests);

        pieChart = findViewById(R.id.pieChart);
        progress = findViewById(R.id.progress);
    }
}