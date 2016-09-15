package com.liewjuntung.travelcompanion.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Popular Movie App
 * Created by jtlie on 9/15/2016.
 */

public class Weather implements Parcelable {

    public static final Parcelable.Creator<Weather> CREATOR = new Parcelable.Creator<Weather>() {
        @Override
        public Weather createFromParcel(Parcel source) {
            return new Weather(source);
        }

        @Override
        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };
    /**
     * code : 32
     * date : Thu, 15 Sep 2016 10:00 AM EDT
     * temp : 67
     * text : Sunny
     */

    @SerializedName("code")
    private String code;
    @SerializedName("date")
    private String date;
    @SerializedName("day")
    private String day;
    @SerializedName("high")
    private int high;
    @SerializedName("low")
    private int low;
    @SerializedName("text")
    private String text;

    public Weather() {
    }

    protected Weather(Parcel in) {
        this.code = in.readString();
        this.date = in.readString();
        this.day = in.readString();
        this.high = in.readInt();
        this.low = in.readInt();
        this.text = in.readString();
    }

    public String getCode() {
        return code;
    }

    public String getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }

    public int getHigh() {
        return high;
    }

    public int getLow() {
        return low;
    }

    public String getText() {
        return text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.date);
        dest.writeString(this.day);
        dest.writeInt(this.high);
        dest.writeInt(this.low);
        dest.writeString(this.text);
    }
}

