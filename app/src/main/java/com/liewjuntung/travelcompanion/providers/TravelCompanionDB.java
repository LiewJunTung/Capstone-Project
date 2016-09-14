package com.liewjuntung.travelcompanion.providers;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

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

}
