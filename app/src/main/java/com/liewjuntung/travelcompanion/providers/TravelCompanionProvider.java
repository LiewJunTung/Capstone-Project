package com.liewjuntung.travelcompanion.providers;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Popular Movie App
 * Created by jtlie on 9/15/2016.
 */

@ContentProvider(authority = TravelCompanionProvider.AUTHORITY, database = TravelCompanionDB.class)
public class TravelCompanionProvider {

    public static final String AUTHORITY = "com.liewjuntung.travelcompanion";

    @TableEndpoint(table = TravelCompanionDB.TRIPS)
    public static class Trips {
        @ContentUri(
                path = "trips",
                type = "vnd.android.cursor.dir/trip",
                defaultSort = TripsTableColumns.DATE_FROM + " ASC"
        )
        public static final Uri TRIPS = Uri.parse("content://" + AUTHORITY + "/trips");
    }

    @TableEndpoint(table = TravelCompanionDB.ITINERARIES)
    public static class Itineraries {
        @ContentUri(
                path = "itineraries",
                type = "vnd.android.cursor.dir/itinerary",
                defaultSort = ItinerariesTableColumns.DATE_TIME + " ASC"
        )
        public static final Uri ITINERARIES = Uri.parse("content://" + AUTHORITY + "/itineraries");

        @InexactContentUri(
                path = "itineraries/#",
                name = "ITINERARY_ID",
                type = "vnd.android.cursor.item/itinerary",
                whereColumn = ItinerariesTableColumns._ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return Uri.parse("content://" + AUTHORITY + "/itineraries/" + id);
        }
    }


}
