package com.example.covidtracker;

import com.example.covidtracker.Country.CountryInformation;
import com.google.gson.annotations.SerializedName;

public class Model {
    String updated;
    String flag;
    String country;
    String todayCases, todayDeaths, todayRecovered;
    String cases, active, recovered, deaths, tests;

    @SerializedName("countryInfo")
    private CountryInformation countryInfo;

    public Model(String updated, String flag, String country, String todayCases, String todayDeaths, String todayRecovered, String cases, String active, String recovered, String deaths, String tests, CountryInformation countryInfo) {
        this.updated = updated;
        this.flag = flag;
        this.country = country;
        this.todayCases = todayCases;
        this.todayDeaths = todayDeaths;
        this.todayRecovered = todayRecovered;
        this.cases = cases;
        this.active = active;
        this.recovered = recovered;
        this.deaths = deaths;
        this.tests = tests;
        this.countryInfo = countryInfo;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTodayCases() {
        return todayCases;
    }

    public void setTodayCases(String todayCases) {
        this.todayCases = todayCases;
    }

    public String getTodayDeaths() {
        return todayDeaths;
    }

    public void setTodayDeaths(String todayDeaths) {
        this.todayDeaths = todayDeaths;
    }

    public String getTodayRecovered() {
        return todayRecovered;
    }

    public void setTodayRecovered(String todayRecovered) {
        this.todayRecovered = todayRecovered;
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getTests() {
        return tests;
    }

    public void setTests(String tests) {
        this.tests = tests;
    }

    public CountryInformation getCountryInfo() {
        return countryInfo;
    }

    public void setCountryInfo(CountryInformation countryInfo) {
        this.countryInfo = countryInfo;
    }
}
