package com.liewjuntung.travelcompanion;

import com.liewjuntung.travelcompanion.models.Itinerary;
import com.liewjuntung.travelcompanion.utility.TravelCompanionUtility;

import org.junit.Before;
import org.junit.Test;
import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Popular Movie App
 * Created by jtlie on 9/17/2016.
 */

public class ItineraryListTest {
    List<Object> mItineraryObject;

    @Before
    public void initItineraryList() {
        List<Itinerary> itineraryList = new ArrayList<>();
        LocalDateTime currentDateTime = LocalDateTime.now();
        String currentDateTimeString = currentDateTime.format(TravelCompanionUtility.DEFAULT_DATE_TIME_FORMATTER);

        itineraryList.add(new Itinerary(1, 1, "hello", currentDateTimeString, "KLCC", 0.0, 0.0, 0, 0, 0, ""));
        itineraryList.add(new Itinerary(1, 1, "hello", currentDateTime.plusDays(1).format(TravelCompanionUtility.DEFAULT_DATE_TIME_FORMATTER), "KLCC", 0.0, 0.0, 0, 0, 0, ""));
        itineraryList.add(new Itinerary(1, 1, "hello", currentDateTime.plusDays(1).format(TravelCompanionUtility.DEFAULT_DATE_TIME_FORMATTER), "KLCC", 0.0, 0.0, 0, 0, 0, ""));
        itineraryList.add(new Itinerary(1, 1, "hello", currentDateTime.plusDays(1).format(TravelCompanionUtility.DEFAULT_DATE_TIME_FORMATTER), "KLCC", 0.0, 0.0, 0, 0, 0, ""));
        itineraryList.add(new Itinerary(1, 1, "hello", currentDateTime.plusDays(2).format(TravelCompanionUtility.DEFAULT_DATE_TIME_FORMATTER), "KLCC", 0.0, 0.0, 0, 0, 0, ""));
        itineraryList.add(new Itinerary(1, 1, "hello", currentDateTime.plusDays(2).format(TravelCompanionUtility.DEFAULT_DATE_TIME_FORMATTER), "KLCC", 0.0, 0.0, 0, 0, 0, ""));
        itineraryList.add(new Itinerary(1, 1, "hello", currentDateTime.plusDays(2).format(TravelCompanionUtility.DEFAULT_DATE_TIME_FORMATTER), "KLCC", 0.0, 0.0, 0, 0, 0, ""));
        itineraryList.add(new Itinerary(1, 1, "hello", currentDateTime.plusDays(2).format(TravelCompanionUtility.DEFAULT_DATE_TIME_FORMATTER), "KLCC", 0.0, 0.0, 0, 0, 0, ""));
        itineraryList.add(new Itinerary(1, 1, "hello", currentDateTime.plusDays(3).format(TravelCompanionUtility.DEFAULT_DATE_TIME_FORMATTER), "KLCC", 0.0, 0.0, 0, 0, 0, ""));
        itineraryList.add(new Itinerary(1, 1, "hello", currentDateTime.plusDays(4).format(TravelCompanionUtility.DEFAULT_DATE_TIME_FORMATTER), "KLCC", 0.0, 0.0, 0, 0, 0, ""));
        itineraryList.add(new Itinerary(1, 1, "hello", currentDateTime.plusDays(4).format(TravelCompanionUtility.DEFAULT_DATE_TIME_FORMATTER), "KLCC", 0.0, 0.0, 0, 0, 0, ""));
        itineraryList.add(new Itinerary(1, 1, "hello", currentDateTime.plusDays(4).format(TravelCompanionUtility.DEFAULT_DATE_TIME_FORMATTER), "KLCC", 0.0, 0.0, 0, 0, 0, ""));
        itineraryList.add(new Itinerary(1, 1, "hello", currentDateTime.plusDays(5).format(TravelCompanionUtility.DEFAULT_DATE_TIME_FORMATTER), "KLCC", 0.0, 0.0, 0, 0, 0, ""));
        itineraryList.add(new Itinerary(1, 1, "hello", currentDateTime.plusDays(6).format(TravelCompanionUtility.DEFAULT_DATE_TIME_FORMATTER), "KLCC", 0.0, 0.0, 0, 0, 0, ""));
        itineraryList.add(new Itinerary(1, 1, "hello", currentDateTime.plusDays(7).format(TravelCompanionUtility.DEFAULT_DATE_TIME_FORMATTER), "KLCC", 0.0, 0.0, 0, 0, 0, ""));
        mItineraryObject = TravelCompanionUtility.initItineraryListForTest(itineraryList);
    }

    @Test
    public void testItineraryObjectListNotNull() {
        for (Object it :
                mItineraryObject) {
            System.out.println(it);
        }
        assertNotNull("object should not be null", mItineraryObject);
    }
}
