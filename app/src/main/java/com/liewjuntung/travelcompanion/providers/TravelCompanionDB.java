package com.liewjuntung.travelcompanion.providers;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.OnUpgrade;
import net.simonvt.schematic.annotation.Table;

import static android.content.ContentValues.TAG;

/**
 * Popular Movie App
 * Created by jtlie on 9/15/2016.
 */

@Database(version = TravelCompanionDB.VERSION)
public class TravelCompanionDB {
    public static final int VERSION = 1;

    @Table(ItinerariesTableColumns.class)
    public static final String ITINERARIES = ItinerariesTableColumns.TABLE_NAME;

    @Table(TripsTableColumns.class)
    public static final String TRIPS = TripsTableColumns.TABLE_NAME;

    @OnUpgrade
    public static void upgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "upgrade");
    }
}
