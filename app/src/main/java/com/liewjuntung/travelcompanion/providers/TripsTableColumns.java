package com.liewjuntung.travelcompanion.providers;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

/**
 * Popular Movie App
 * Created by jtlie on 9/15/2016.
 */

public interface TripsTableColumns {
    public static final String TABLE_NAME = "trips";

    @DataType(INTEGER) @PrimaryKey @AutoIncrement String _ID = "_id";

    @DataType(TEXT) @NotNull String NAME = "name";

    @DataType(TEXT) @NotNull String DATE_FROM = "date_from";

    @DataType(TEXT) @NotNull String DATE_UNTIL = "date_until";

    @DataType(TEXT) @NotNull String COUNTRY = "country";

    @DataType(TEXT) String PLACE = "place";

    @DataType(TEXT)
    String IMAGE = "image";
}
