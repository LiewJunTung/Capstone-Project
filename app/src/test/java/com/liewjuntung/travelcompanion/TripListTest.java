package com.liewjuntung.travelcompanion;

import com.liewjuntung.travelcompanion.models.Trip;
import com.liewjuntung.travelcompanion.utility.TravelCompanionUtility;

import org.junit.Before;
import org.junit.Test;
import org.threeten.bp.LocalDate;
import org.threeten.bp.Period;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Popular Movie App
 * Created by jtlie on 9/16/2016.
 */

public class TripListTest {
    List<Object> tripObject;

    @Before
    public void initTrip() {
        List<Trip> tripList = new ArrayList<>();
        LocalDate fromDate = LocalDate.now();
        fromDate = fromDate.plusDays(10);
        LocalDate toDate = LocalDate.now();
        toDate = toDate.plusDays(2);
        tripList.add(new Trip(1, "Malaysia", "2016-09-10", toDate.toString(), "Malaysia", "Kuala Lumpur", ""));
        tripList.add(new Trip(1, "Malaysia", fromDate.toString(), toDate.toString(), "Malaysia", "Kuala Lumpur", ""));
        tripList.add(new Trip(1, "Malaysia", fromDate.toString(), toDate.toString(), "Malaysia", "Kuala Lumpur", ""));
        tripList.add(new Trip(1, "Malaysia", fromDate.toString(), toDate.toString(), "Malaysia", "Kuala Lumpur", ""));
        tripList.add(new Trip(1, "Malaysia", fromDate.toString(), toDate.toString(), "Malaysia", "Kuala Lumpur", ""));
        tripObject = TravelCompanionUtility.initMainList(tripList);
    }

    @Test
    public void testPeriod() {
        LocalDate from = LocalDate.parse("2016-09-10");
        LocalDate to = LocalDate.parse("2016-09-20");
        LocalDate currentDate = LocalDate.now();
        Period fromPeriod = Period.between(currentDate, from);
        Period toPeriod = Period.between(currentDate, to);
        System.out.println("From: " + fromPeriod.getDays());
        System.out.println("To: " + toPeriod.getDays());
        assertTrue(fromPeriod.isNegative());
        assertFalse(toPeriod.isNegative());
    }

    @Test
    public void tripList_isNotNull() {
        assertNotNull("Trip list not supposed to be empty", tripObject);
    }

    @Test
    public void tripList_firstElementIsString() {
        assertTrue("First item is a list", tripObject.get(0) instanceof String);
        assertEquals("First item is called Current", "Current", tripObject.get(0));
    }


}
