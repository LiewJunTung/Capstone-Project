package com.liewjuntung.travelcompanion.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

/**
 * Popular Movie App
 * Created by jtlie on 9/15/2016.
 */

public class Trip implements Parcelable {
    private int id;
    private String name;
    private String dateFrom;
    private String dateUntil;
    private String country;
    private String place;
    private String image;
    private boolean isFuture;

    public Trip(int id, String name, String dateFrom, String dateUntil, String country, String place, String image) {
        this.id = id;
        this.name = name;
        this.dateFrom = dateFrom;
        this.dateUntil = dateUntil;
        this.country = country;
        this.place = place;
        this.image = image;
    }

    public Trip() {
    }

    public static Creator<Trip> getCREATOR() {
        return CREATOR;
    }

    public String getImage() {
        return image;
    }

    public boolean isFuture() {
        return isFuture;
    }

    public void setFuture(boolean future) {
        isFuture = future;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDisplayDate() {
        LocalDate from = LocalDate.parse(dateFrom);
        LocalDate to = LocalDate.parse(dateUntil);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return from.format(formatter) + " - " + to.format(formatter);
    }

    public String getDateUntil() {
        return dateUntil;
    }

    public void setDateUntil(String dateUntil) {
        this.dateUntil = dateUntil;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateFrom='" + dateFrom + '\'' +
                ", dateUntil='" + dateUntil + '\'' +
                ", country='" + country + '\'' +
                ", place='" + place + '\'' +
                '}';
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
        dest.writeString(this.image);
        dest.writeByte(this.isFuture ? (byte) 1 : (byte) 0);
    }

    protected Trip(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.dateFrom = in.readString();
        this.dateUntil = in.readString();
        this.country = in.readString();
        this.place = in.readString();
        this.image = in.readString();
        this.isFuture = in.readByte() != 0;
    }

    public static final Creator<Trip> CREATOR = new Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel source) {
            return new Trip(source);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };
}
