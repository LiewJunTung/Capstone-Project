package com.liewjuntung.travelcompanion.models;

import com.google.gson.annotations.SerializedName;

/**
 * Popular Movie App
 * Created by jtlie on 9/15/2016.
 */

public class YahooQuery {
    @SerializedName("count")
    private int count;

    @SerializedName("created")
    private String created;

    public String getCreated() {
        return created;
    }

    public int getCount() {
        return count;
    }
}
