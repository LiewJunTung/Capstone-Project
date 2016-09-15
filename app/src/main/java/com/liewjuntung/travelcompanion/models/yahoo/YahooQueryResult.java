package com.liewjuntung.travelcompanion.models.yahoo;

import com.google.gson.annotations.SerializedName;
import com.liewjuntung.travelcompanion.models.Weather;

import java.util.List;

/**
 * Popular Movie App
 * Created by jtlie on 9/16/2016.
 */

public class YahooQueryResult {
    @SerializedName("query")
    private YahooQuery query;

    public YahooQuery getQuery() {
        return query;
    }

    public List<Weather> weathers() {
        if (query == null ||
                query.getResult() == null ||
                query.getResult().getChannel() == null ||
                query.getResult().getChannel().getItem() == null ||
                query.getResult().getChannel().getItem().getWeatherList() == null) {
            return null;
        }
        return query.getResult().getChannel().getItem().getWeatherList();
    }
}
