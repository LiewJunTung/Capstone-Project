package com.liewjuntung.travelcompanion.networks;

import com.liewjuntung.travelcompanion.models.pixabay.PixabayResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Popular Movie App
 * Created by jtlie on 9/16/2016.
 */

public interface ImageService {
    @GET("/api")
    Call<PixabayResult> callImageApi(@Query("key") String key, @Query("q") String query, @Query("per_page") int perPage);
}
