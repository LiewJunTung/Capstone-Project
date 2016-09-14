package com.liewjuntung.travelcompanion.providers;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.References;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.REAL;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

/**
 * Popular Movie App
 * Created by jtlie on 9/15/2016.
 */

public interface ItinerariesTableColumns {
    public static final String TABLE_NAME = "itineraries";

    @DataType(INTEGER) @PrimaryKey
    @AutoIncrement
    String _ID = "_id";

    @DataType(INTEGER)
    @References(table = TripsTableColumns.TABLE_NAME, column = TripsTableColumns._ID)
    @NotNull
    String TRIP_ID = "trip_id";

    @DataType(TEXT) @NotNull
    String NAME = "name";

    @DataType(TEXT) @NotNull String DATE_TIME = "date_time";

    @DataType(TEXT) @NotNull String PLACE = "place";

    @DataType(REAL) @NotNull String LATITUDE = "latitude";

    @DataType(REAL) @NotNull String LONGITUDE = "longitude";

    @DataType(INTEGER) String WEATHER_CODE = "weather_code";

    @DataType(INTEGER) String HIGH_TEMP = "high_temperature";

    @DataType(INTEGER) String LOW_TEMP = "low_temperature";
}
