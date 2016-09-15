package com.liewjuntung.travelcompanion.models.yahoo;

import com.google.gson.annotations.SerializedName;

public class Channel {
    @SerializedName("item")
    private Item item;

    public Item getItem() {
        return item;
    }
}