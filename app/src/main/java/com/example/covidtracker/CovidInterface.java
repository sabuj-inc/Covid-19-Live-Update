package com.example.covidtracker;

import com.example.covidtracker.Country.CountryModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CovidInterface {
    @GET("countries")
    Call<List<Model>> getCovidInterface();

    @GET("countries")
    Call<List<CountryModel>> countryInterface();
}
