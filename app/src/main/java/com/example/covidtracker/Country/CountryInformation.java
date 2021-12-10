package com.example.covidtracker.Country;

import com.google.gson.annotations.SerializedName;

public class CountryInformation {
    @SerializedName("flag")
    String flag;

    public CountryInformation(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }

}
