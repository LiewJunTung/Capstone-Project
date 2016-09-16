package com.liewjuntung.travelcompanion.models.pixabay;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Popular Movie App
 * Created by jtlie on 9/16/2016.
 */

public class PixabayResult {
    @SerializedName("total")
    private int total;

    @SerializedName("totalHits")
    private int totalHits;

    @SerializedName("hits")
    private List<Hit> hits;

    public int getTotal() {
        return total;
    }

    public int getTotalHits() {
        return totalHits;
    }

    public List<Hit> getHits() {
        return hits;
    }
}
