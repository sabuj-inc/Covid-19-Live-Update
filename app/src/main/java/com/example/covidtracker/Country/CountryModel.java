package com.example.covidtracker.Country;

import com.google.gson.annotations.SerializedName;

public class CountryModel {
    @SerializedName("countryInfo")
    CountryInformation countryInformation;

    @SerializedName("country")
    String countryName;

    public CountryModel(CountryInformation countryInformation, String countryName) {
        this.countryInformation = countryInformation;
        this.countryName = countryName;
    }

    public CountryInformation getCountryInformation() {
        return countryInformation;
    }

    public String getCountryName() {
        return countryName;
    }
}
