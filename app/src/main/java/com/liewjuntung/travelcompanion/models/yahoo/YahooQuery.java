package com.liewjuntung.travelcompanion.models.yahoo;

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

    @SerializedName("lang")
    private String lang;

    @SerializedName("results")
    private Result result;

    public int getCount() {
        return count;
    }

    public String getCreated() {
        return created;
    }

    public String getLang() {
        return lang;
    }

    public Result getResult() {
        return result;
    }


}
