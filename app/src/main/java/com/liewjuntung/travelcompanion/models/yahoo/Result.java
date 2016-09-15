package com.liewjuntung.travelcompanion.models.yahoo;

import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("channel")
    private Channel channel;

    public Channel getChannel() {
        return channel;
    }
}