package com.liewjuntung.travelcompanion.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Popular Movie App
 * Created by jtlie on 9/15/2016.
 */

public class Itinerary implements Parcelable {
    public static final Parcelable.Creator<Itinerary> CREATOR = new Parcelable.Creator<Itinerary>() {
        @Override
        public Itinerary createFromParcel(Parcel source) {
            return new Itinerary(source);
        }

        @Override
        public Itinerary[] newArray(int size) {
            return new Itinerary[size];
        }
    };
    private int id;
    private int tripId;
    private String name;
    private String dateTime;
    private String place;
    private double latitude;
    private double longitude;
    private int weatherCode;
    private int highTemp;
    private int lowTemp;

    public Itinerary() {
    }

    protected Itinerary(Parcel in) {
        this.id = in.readInt();
        this.tripId = in.readInt();
        this.name = in.readString();
        this.dateTime = in.readString();
        this.place = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.weatherCode = in.readInt();
        this.highTemp = in.readInt();
        this.lowTemp = in.readInt();
    }

    public int getId() {
        return id;
    }

    public int getTripId() {
        return tripId;
    }

    public String getName() {
        return name;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getPlace() {
        return place;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getWeatherCode() {
        return weatherCode;
    }

    public int getHighTemp() {
        return highTemp;
    }

    public int getLowTemp() {
        return lowTemp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.tripId);
        dest.writeString(this.name);
        dest.writeString(this.dateTime);
        dest.writeString(this.place);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeInt(this.weatherCode);
        dest.writeInt(this.highTemp);
        dest.writeInt(this.lowTemp);
    }
}
