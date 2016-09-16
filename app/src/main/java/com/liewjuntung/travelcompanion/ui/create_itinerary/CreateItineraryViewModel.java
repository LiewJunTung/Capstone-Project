package com.liewjuntung.travelcompanion.ui.create_itinerary;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.databinding.BaseObservable;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.format.DateTimeFormatter;

/**
 * Popular Movie App
 * Created by jtlie on 9/17/2016.
 */

public class CreateItineraryViewModel extends BaseObservable {
    CreateItineraryActivity mActivity;
    private int tripId;
    private String name;
    private String mDate;
    private String time;
    private String place;
    private String tripStartDate;
    private String tripEndDate;
    private double longitude;
    private double latitude;
    private boolean isValidated;

    public CreateItineraryViewModel(CreateItineraryActivity activity, int tripId, String tripStartDate, String tripEndDate) {
        mActivity = activity;
        this.tripId = tripId;
        this.tripStartDate = tripStartDate;
        this.tripEndDate = tripEndDate;
    }

    public void clickDateDialog(View view) {
        LocalDateTime date = LocalDateTime.now();
        LocalDate tripMinDate = LocalDate.parse(tripStartDate);

        DatePickerDialog dialog = new DatePickerDialog(mActivity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                LocalDate localDate = LocalDate.of(year, month + 1, dayOfMonth);
                mDate = localDate.toString();
                notifyChange();
            }
        }, date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
        dialog.getDatePicker().setMinDate(tripMinDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        if (tripEndDate != null) {
            LocalDate dateTo = LocalDate.parse(tripEndDate);
            dialog.getDatePicker().setMaxDate(dateTo.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        }
        dialog.getDatePicker().setMinDate(date.toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        dialog.show();
    }

    public void clickTimeDialog(View view) {
        LocalDateTime dateTime = LocalDateTime.now();
        LocalTime localTime = dateTime.toLocalTime();
        TimePickerDialog dialog = new TimePickerDialog(mActivity, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                LocalTime localTime1 = LocalTime.of(hourOfDay, minute);
                time = localTime1.toString();
                notifyChange();
            }
        }, localTime.getHour(), localTime.getMinute(), false);
        dialog.show();
    }

    public void afterTextChanged(Editable s) {
        Log.e("TextWatcherTest", "afterTextChanged:\t" + s.toString());
        name = s.toString();
        notifyChange();
    }

    public void saveData(View view) {
        String dateTime = mDate + " " + time;
    }

    public boolean isValidated() {
        return isValidated;
    }

    public void setValidated(boolean validated) {
        isValidated = validated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        this.mDate = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDisplayTime() {
        if (time == null || time.isEmpty()) {
            return null;
        }
        return LocalTime.parse(time).format(DateTimeFormatter.ofPattern("HH:mm a"));
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public String getTripStartDate() {
        return tripStartDate;
    }

    public void setTripStartDate(String tripStartDate) {
        this.tripStartDate = tripStartDate;
    }

    public String getTripEndDate() {
        return tripEndDate;
    }

    public void setTripEndDate(String tripEndDate) {
        this.tripEndDate = tripEndDate;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
