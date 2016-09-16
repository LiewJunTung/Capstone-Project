package com.liewjuntung.travelcompanion.utility;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.liewjuntung.travelcompanion.models.Trip;
import com.liewjuntung.travelcompanion.providers.TravelCompanionProvider;
import com.liewjuntung.travelcompanion.providers.TripsTableColumns;

import org.threeten.bp.LocalDate;
import org.threeten.bp.Period;

import java.util.ArrayList;
import java.util.List;

/**
 * Popular Movie App
 * Created by jtlie on 9/15/2016.
 */

public class TravelCompanionUtility {

    public static final String[] TRIP_COLUMNS = {
            TripsTableColumns.TABLE_NAME + "." + TripsTableColumns._ID,
            TripsTableColumns.NAME,
            TripsTableColumns.COUNTRY,
            TripsTableColumns.DATE_FROM,
            TripsTableColumns.DATE_UNTIL,
            TripsTableColumns.PLACE,
            TripsTableColumns.IMAGE
    };
    public static final int TRIP_ID = 0;

    public static final int TRIP_NAME = 1;
    public static final int TRIP_COUNTRY = 2;
    public static final int TRIP_DATE_FROM = 3;
    public static final int TRIP_DATE_UNTIL = 4;
    public static final int TRIP_PLACE = 5;
    public static final int TRIP_IMAGE = 6;

    public static Uri insertTrip(Activity activity, String name, String country, String dateFrom,
                                 String dateUntil, String place, String image) {
        ContentValues values = new ContentValues();
        values.put(TripsTableColumns.NAME, name);
        values.put(TripsTableColumns.COUNTRY, country);
        values.put(TripsTableColumns.DATE_FROM, dateFrom);
        values.put(TripsTableColumns.DATE_UNTIL, dateUntil);
        values.put(TripsTableColumns.PLACE, place);
        values.put(TripsTableColumns.IMAGE, image);
        return activity.getContentResolver().insert(TravelCompanionProvider.Trips.TRIPS, values);
    }

    public static List<Object> initMainList(@Nullable List<Trip> tripList) {
        if (tripList == null) {
            return new ArrayList<>();
        }
        List<Object> resultList = new ArrayList<>();
        boolean currentTitleSet = false;
        boolean upcomingTitleSet = false;
        LocalDate currentDate = LocalDate.now();

        for (int i = 0; i < tripList.size(); i++) {
            Trip trip = tripList.get(i);
            LocalDate tripFromDate = LocalDate.parse(trip.getDateFrom());
            LocalDate tripToDate = LocalDate.parse(trip.getDateUntil());


            Period fromPeriod = Period.between(currentDate, tripFromDate);
            Period toPeriod = Period.between(currentDate, tripToDate);
            if (tripFromDate.equals(currentDate) || fromPeriod.isNegative() && !toPeriod.isNegative()) {
                if (!currentTitleSet) {
                    currentTitleSet = true;
                    resultList.add(0, "Current");
                }
                trip.setFuture(false);

            } else if (!fromPeriod.isNegative() && !toPeriod.isNegative()) {
                if (!upcomingTitleSet) {
                    upcomingTitleSet = true;
                    resultList.add(i, "Upcoming");
                }
                trip.setFuture(true);
            } else {
                continue;
            }
            resultList.add(trip);
        }
        return resultList;
    }

    public static List<Object> initMainList(Cursor cursor) {
        if (cursor == null) {
            return new ArrayList<>();
        }
        List<Object> resultList = new ArrayList<>();
        boolean currentTitleSet = false;
        boolean upcomingTitleSet = false;
        LocalDate currentDate = LocalDate.now();
        int index = 0;
        while (cursor.moveToNext()) {
            Trip trip = new Trip(
                    cursor.getInt(TRIP_ID),
                    cursor.getString(TRIP_NAME),
                    cursor.getString(TRIP_DATE_FROM),
                    cursor.getString(TRIP_DATE_UNTIL),
                    cursor.getString(TRIP_COUNTRY),
                    cursor.getString(TRIP_PLACE),
                    cursor.getString(TRIP_IMAGE)
            );
            LocalDate tripFromDate = LocalDate.parse(trip.getDateFrom());
            LocalDate tripToDate = LocalDate.parse(trip.getDateUntil());
            Period fromPeriod = Period.between(currentDate, tripFromDate);
            Period toPeriod = Period.between(currentDate, tripToDate);
            if (tripFromDate.equals(currentDate) || fromPeriod.isNegative() && !toPeriod.isNegative()) {
                if (!currentTitleSet) {
                    currentTitleSet = true;
                    resultList.add(0, "Current");
                    index++;
                }
                trip.setFuture(false);

            } else if (!fromPeriod.isNegative() && !toPeriod.isNegative()) {
                if (!upcomingTitleSet) {
                    upcomingTitleSet = true;
                    resultList.add(index, "Upcoming");
                    index++;
                }
                trip.setFuture(true);
            }

            index++;
            resultList.add(trip);
        }
        return resultList;
    }
}
