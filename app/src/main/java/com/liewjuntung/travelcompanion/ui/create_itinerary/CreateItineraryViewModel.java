package com.liewjuntung.travelcompanion.ui.create_itinerary;

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

import com.liewjuntung.travelcompanion.R;
import com.liewjuntung.travelcompanion.models.Weather;
import com.liewjuntung.travelcompanion.models.yahoo.YahooQueryResult;
import com.liewjuntung.travelcompanion.networks.WeatherService;
import com.liewjuntung.travelcompanion.utility.RetrofitUtility;
import com.liewjuntung.travelcompanion.utility.TravelCompanionUtility;
import com.liewjuntung.travelcompanion.utility.WeatherUtility;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Popular Movie App
 * Created by jtlie on 9/17/2016.
 */

public class CreateItineraryViewModel extends BaseObservable implements Parcelable {
    public static final Parcelable.Creator<CreateItineraryViewModel> CREATOR = new Parcelable.Creator<CreateItineraryViewModel>() {
        @Override
        public CreateItineraryViewModel createFromParcel(Parcel source) {
            return new CreateItineraryViewModel(source);
        }

        @Override
        public CreateItineraryViewModel[] newArray(int size) {
            return new CreateItineraryViewModel[size];
        }
    };
    private final WeatherService mWeatherService = RetrofitUtility.initWeatherService();
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
    private String note;
    private int weatherCode = -1;
    private int tempHigh;
    private int tempLow;
    private boolean hasWeatherForecast = false;
    private boolean isLoadingWeather = false;
    private Timer mTimer = new Timer();
    private String weatherText;

    private final Callback<YahooQueryResult> mCallback = new Callback<YahooQueryResult>() {
        @Override
        public void onResponse(Call<YahooQueryResult> call, Response<YahooQueryResult> response) {
            if (response.isSuccessful() && response.body().getQuery().getCount() > 0) {
                List<Weather> weatherList = response.body().getQuery().getResult().getChannel().getItem().getWeatherList();

                for (Weather weather : weatherList) {
                    if (LocalDate.parse(weather.getDate(),
                            DateTimeFormatter.ofPattern("dd MMM yyyy")).equals(LocalDate.parse(mDate))) {
                        setWeather(weather);
                        break;
                    }
                }
            } else {
                weatherCode = -1;
            }
            isLoadingWeather = false;
        }

        @Override
        public void onFailure(Call<YahooQueryResult> call, Throwable t) {
            Log.d(TAG, "onFailure: " + t.getMessage(), t);
            isLoadingWeather = false;
            weatherCode = -1;
        }
    };

    public CreateItineraryViewModel(CreateItineraryActivity activity, int tripId, String tripStartDate, String tripEndDate) {
        mActivity = activity;
        this.tripId = tripId;
        this.tripStartDate = tripStartDate;
        this.tripEndDate = tripEndDate;
    }

    protected CreateItineraryViewModel(Parcel in) {
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

    public void setActivity(CreateItineraryActivity activity) {
        if (mActivity == null) {
            mActivity = activity;
        }
    }

    public void setWeather(Weather weather) {
        hasWeatherForecast = true;
        weatherCode = weather.getCode();
        weatherText = WeatherUtility.getWeatherStringFromCode(mActivity, weatherCode);
        tempHigh = weather.getHigh();
        tempLow = weather.getLow();
        notifyChange();
    }

    public String getWeatherText() {
        return weatherText;
    }

    public int getWeatherCode() {
        return weatherCode;
    }

    public int getTempHigh() {
        return tempHigh;
    }

    public int getTempLow() {
        return tempLow;
    }

    public String getTempLowString() {
        return mActivity.getString(R.string.format_temperature, tempLow);
    }

    public String getTempHighString() {
        return mActivity.getString(R.string.format_temperature, tempHigh);
    }

    public boolean isHasWeatherForecast() {
        return hasWeatherForecast;
    }

    public void clickDateDialog(View view) {
        LocalDateTime date = LocalDateTime.now();
        LocalDate tripMinDate = LocalDate.parse(tripStartDate);

        DatePickerDialog dialog = new DatePickerDialog(mActivity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                LocalDate localDate = LocalDate.of(year, month + 1, dayOfMonth);
                mDate = localDate.toString();
                initWeatherForecastLatLong();
                notifyChange();
            }
        }, date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
        dialog.getDatePicker().setMinDate(tripMinDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

        LocalDate dateTo = LocalDate.parse(tripEndDate);
        dialog.getDatePicker().setMaxDate(dateTo.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

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

    //delay 500 milliseconds as soon as aftertextlistener fired
    public void initWeatherForecastLatLong() {
        if (latitude == 0.0 && longitude == 0.0) {
            initWeatherForecastPlace();
            return;
        }
        mTimer.cancel();
        if (place == null || mDate == null || isLoadingWeather) {
            return;
        }
        isLoadingWeather = true;
        RetrofitUtility.getWeatherByLongAndLat(mWeatherService, latitude, longitude).enqueue(mCallback);
    }

    public void initWeatherForecastPlace() {
        if (place == null || mDate == null || isLoadingWeather) {
            return;
        }
        latitude = 0.0;
        longitude = 0.0;
        mTimer.cancel();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                isLoadingWeather = true;
                RetrofitUtility.getWeatherByPlaceName(mWeatherService, place).enqueue(mCallback);
            }
        }, 1000);
    }

    public void itineraryAfterTextChanged(Editable s) {
        Log.e("TextWatcherTest", "afterTextChanged:\t" + s.toString());
        setName(s.toString());
    }

    public void placeAfterTextChanged(Editable s) {
        Log.e("TextWatcherTest", "afterTextChanged:\t" + s.toString());
        setPlace(s.toString(), true);

    }

    public void noteAfterTextChanged(Editable s) {
        Log.e("TextWatcherTest", "afterTextChanged:\t" + s.toString());
        setNote(s.toString());
    }

    public void saveData(View view) {
        String dateTime = mDate + " " + time;
        TravelCompanionUtility.insertItinerary(mActivity,
                tripId, name, dateTime, place, latitude, longitude, note, weatherCode, tempHigh, tempLow);
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

    public void setPlace(String place, boolean loadWeather) {
        this.place = place;
        if (loadWeather) {
            initWeatherForecastPlace();
        }
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
