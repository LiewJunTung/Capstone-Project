package com.liewjuntung.travelcompanion.networks;

import com.liewjuntung.travelcompanion.models.yahoo.YahooQueryResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Popular Movie App
 * Created by jtlie on 9/15/2016.
 */

public interface WeatherService {

    @GET("/v1/public/yql")
    Call<YahooQueryResult> yahooQuery(@Query("q") String query);
}
