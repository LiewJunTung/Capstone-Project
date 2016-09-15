package com.liewjuntung.travelcompanion.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Popular Movie App
 * Created by jtlie on 9/15/2016.
 */

public class Trip implements Parcelable {
    public static final Parcelable.Creator<Trip> CREATOR = new Parcelable.Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel source) {
            return new Trip(source);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };
    private int id;
    private String name;
    private String dateFrom;
    private String dateUntil;
    private String country;
    private String place;

    public Trip() {
    }

    protected Trip(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.dateFrom = in.readString();
        this.dateUntil = in.readString();
        this.country = in.readString();
        this.place = in.readString();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public String getDateUntil() {
        return dateUntil;
    }

    public String getCountry() {
        return country;
    }

    public String getPlace() {
        return place;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.dateFrom);
        dest.writeString(this.dateUntil);
        dest.writeString(this.country);
        dest.writeString(this.place);
    }
}
