package com.liewjuntung.travelcompanion.models;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import static com.liewjuntung.travelcompanion.utility.TravelCompanionUtility.ITER_DATE_TIME;
import static com.liewjuntung.travelcompanion.utility.TravelCompanionUtility.ITER_HIGH_TEMP;
import static com.liewjuntung.travelcompanion.utility.TravelCompanionUtility.ITER_ID;
import static com.liewjuntung.travelcompanion.utility.TravelCompanionUtility.ITER_LATITUDE;
import static com.liewjuntung.travelcompanion.utility.TravelCompanionUtility.ITER_LONGITUDE;
import static com.liewjuntung.travelcompanion.utility.TravelCompanionUtility.ITER_LOW_TEMP;
import static com.liewjuntung.travelcompanion.utility.TravelCompanionUtility.ITER_NAME;
import static com.liewjuntung.travelcompanion.utility.TravelCompanionUtility.ITER_NOTE;
import static com.liewjuntung.travelcompanion.utility.TravelCompanionUtility.ITER_PLACE;
import static com.liewjuntung.travelcompanion.utility.TravelCompanionUtility.ITER_TRIP_ID;
import static com.liewjuntung.travelcompanion.utility.TravelCompanionUtility.ITER_WEATHER_CODE;

/**
 * Popular Movie App
 * Created by jtlie on 9/15/2016.
 */

public class Itinerary implements Parcelable {
    public static final Creator<Itinerary> CREATOR = new Creator<Itinerary>() {
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
    private String note;
    private double latitude;
    private double longitude;
    private int weatherCode;
    private int highTemp;
    private int lowTemp;

    public Itinerary() {
    }

    public Itinerary(int id, int tripId, String name, String dateTime, String place, double latitude, double longitude, int weatherCode, int highTemp, int lowTemp, String note) {
        this.id = id;
        this.tripId = tripId;
        this.name = name;
        this.dateTime = dateTime;
        this.place = place;
        this.latitude = latitude;
        this.longitude = longitude;
        this.weatherCode = weatherCode;
        this.highTemp = highTemp;
        this.lowTemp = lowTemp;
        this.note = note;
    }

    protected Itinerary(Parcel in) {
        this.id = in.readInt();
        this.tripId = in.readInt();
        this.name = in.readString();
        this.dateTime = in.readString();
        this.place = in.readString();
        this.note = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.weatherCode = in.readInt();
        this.highTemp = in.readInt();
        this.lowTemp = in.readInt();
    }

    public static Itinerary create(@NonNull Cursor cursor) {
        int weatherCode = -1;
        if (cursor.isNull(ITER_WEATHER_CODE)) {
            weatherCode = -1;
        } else {
            weatherCode = cursor.getInt(ITER_WEATHER_CODE);
        }
        return new Itinerary(
                cursor.getInt(ITER_ID),
                cursor.getInt(ITER_TRIP_ID),
                cursor.getString(ITER_NAME),
                cursor.getString(ITER_DATE_TIME),
                cursor.getString(ITER_PLACE),
                cursor.getDouble(ITER_LATITUDE),
                cursor.getDouble(ITER_LONGITUDE),
                weatherCode,
                cursor.getInt(ITER_HIGH_TEMP),
                cursor.getInt(ITER_LOW_TEMP),
                cursor.getString(ITER_NOTE)
        );
    }

    public String getDisplayTime(){
        if (dateTime == null){
            return null;
        }
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        return localDateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm a"));
    }

    public String getLocalTimeString() {
        if (dateTime == null) {
            return null;
        }
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        return localDateTime.toLocalTime().toString();
    }

    public String getDisplayDate() {
        if (dateTime == null) {
            return null;
        }
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        return localDateTime.toLocalDate().toString();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
    public String toString() {
        return "Itinerary{" +
                "id=" + id +
                ", tripId=" + tripId +
                ", name='" + name + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", place='" + place + '\'' +
                ", note='" + note + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", weatherCode=" + weatherCode +
                ", highTemp=" + highTemp +
                ", lowTemp=" + lowTemp +
                '}';
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
        dest.writeString(this.note);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeInt(this.weatherCode);
        dest.writeInt(this.highTemp);
        dest.writeInt(this.lowTemp);
    }
}
