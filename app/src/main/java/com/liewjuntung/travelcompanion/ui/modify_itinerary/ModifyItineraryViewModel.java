package com.liewjuntung.travelcompanion.ui.modify_itinerary;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.liewjuntung.travelcompanion.models.Itinerary;
import com.liewjuntung.travelcompanion.utility.TravelCompanionUtility;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.format.DateTimeFormatter;

/**
 * Popular Movie App
 * Created by jtlie on 9/17/2016.
 */

public class ModifyItineraryViewModel extends BaseObservable implements Parcelable {
    public static final Creator<ModifyItineraryViewModel> CREATOR = new Creator<ModifyItineraryViewModel>() {
        @Override
        public ModifyItineraryViewModel createFromParcel(Parcel source) {
            return new ModifyItineraryViewModel(source);
        }

        @Override
        public ModifyItineraryViewModel[] newArray(int size) {
            return new ModifyItineraryViewModel[size];
        }
    };
    ModifyItineraryActivity mActivity;
    int weatherCode;
    private int id;
    private int tripId;
    private String name;
    private String mDate;
    private String time;
    private String place;
    private String tripStartDate;
    private String tripEndDate;
    private double longitude;
    private double latitude;
    private String note;

    public ModifyItineraryViewModel(ModifyItineraryActivity activity, Itinerary itinerary, String tripStartDate, String tripEndDate) {
        mActivity = activity;
        this.id = itinerary.getId();
        this.tripId = itinerary.getTripId();
        this.name = itinerary.getName();
        mDate = itinerary.getDisplayDate();
        this.time = itinerary.getLocalTimeString();
        this.place = itinerary.getPlace();
        this.tripStartDate = tripStartDate;
        this.tripEndDate = tripEndDate;
        this.longitude = itinerary.getLongitude();
        this.latitude = itinerary.getLatitude();
        this.note = itinerary.getNote();
        this.weatherCode = itinerary.getWeatherCode();
    }

    protected ModifyItineraryViewModel(Parcel in) {
        this.tripId = in.readInt();
        this.name = in.readString();
        this.mDate = in.readString();
        this.time = in.readString();
        this.place = in.readString();
        this.tripStartDate = in.readString();
        this.tripEndDate = in.readString();
        this.longitude = in.readDouble();
        this.latitude = in.readDouble();
        this.note = in.readString();
        this.weatherCode = in.readInt();
    }

    public void setActivity(ModifyItineraryActivity activity) {
        if (mActivity == null) {
            mActivity = activity;
        }
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

    public void itineraryAfterTextChanged(Editable s) {
        Log.e("TextWatcherTest", "afterTextChanged:\t" + s.toString());
        setName(s.toString());
    }

    public void placeAfterTextChanged(Editable s) {
        Log.e("TextWatcherTest", "afterTextChanged:\t" + s.toString());
        setPlace(s.toString());
    }

    public void noteAfterTextChanged(Editable s) {
        Log.e("TextWatcherTest", "afterTextChanged:\t" + s.toString());
        setNote(s.toString());
    }

    public void saveData(View view) {
        String dateTime = mDate + " " + time;
        TravelCompanionUtility.updateItinerary(mActivity, id,
                tripId, name, dateTime, place, latitude, longitude, note);
        mActivity.setResult(Activity.RESULT_OK);
        mActivity.finish();
    }

    public boolean isValidated() {
        return name != null && mDate != null && time != null && place != null &&
                !name.isEmpty() && !mDate.isEmpty() && !time.isEmpty() && !place.isEmpty();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyChange();
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
        return LocalTime.parse(time).format(DateTimeFormatter.ofPattern("hh:mm a"));
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
        notifyChange();
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
        notifyChange();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.tripId);
        dest.writeString(this.name);
        dest.writeString(this.mDate);
        dest.writeString(this.time);
        dest.writeString(this.place);
        dest.writeString(this.tripStartDate);
        dest.writeString(this.tripEndDate);
        dest.writeDouble(this.longitude);
        dest.writeDouble(this.latitude);
        dest.writeString(this.note);
        dest.writeInt(this.weatherCode);
    }
}
