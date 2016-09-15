package com.liewjuntung.travelcompanion.models.yahoo;

import com.google.gson.annotations.SerializedName;
import com.liewjuntung.travelcompanion.models.Weather;

import java.util.List;

/**
 * Popular Movie App
 * Created by jtlie on 9/16/2016.
 */

public class Item {
    @SerializedName("forecast")
    private List<Weather> weather;

    public List<Weather> getWeatherList() {
        return weather;
    }
}
