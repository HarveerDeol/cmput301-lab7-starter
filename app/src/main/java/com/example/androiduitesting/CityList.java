package com.example.androiduitesting;

import java.util.ArrayList;
import java.util.List;

public class CityList {
    private static CityList instance;
    private final List<String> cities;

    private CityList() {
        cities = new ArrayList<>();
    }

    public static synchronized CityList getInstance() {
        if (instance == null) {
            instance = new CityList();
        }
        return instance;
    }

    public List<String> getCities() {
        return cities;
    }

    public void addCity(String city) {
        cities.add(city);
    }

    public void clear() {
        cities.clear();
    }
}
