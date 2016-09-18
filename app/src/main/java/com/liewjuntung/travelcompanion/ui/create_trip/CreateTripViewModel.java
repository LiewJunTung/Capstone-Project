package com.liewjuntung.travelcompanion.ui.create_trip;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.databinding.BaseObservable;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import com.liewjuntung.travelcompanion.BuildConfig;
import com.liewjuntung.travelcompanion.R;
import com.liewjuntung.travelcompanion.models.pixabay.PixabayResult;
import com.liewjuntung.travelcompanion.networks.ImageService;
import com.liewjuntung.travelcompanion.utility.RetrofitUtility;
import com.liewjuntung.travelcompanion.utility.TravelCompanionUtility;
import com.mukesh.countrypicker.fragments.CountryPicker;
import com.mukesh.countrypicker.interfaces.CountryPickerListener;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Popular Movie App
 * Created by jtlie on 9/16/2016.
 */

public class CreateTripViewModel extends BaseObservable implements Parcelable {
    public static final Parcelable.Creator<CreateTripViewModel> CREATOR = new Parcelable.Creator<CreateTripViewModel>() {
        @Override
        public CreateTripViewModel createFromParcel(Parcel source) {
            return new CreateTripViewModel(source);
        }

        @Override
        public CreateTripViewModel[] newArray(int size) {
            return new CreateTripViewModel[size];
        }
    };
    private static final String LOG_TAG = CreateTripViewModel.class.getSimpleName();
    CreateTripActivity mActivity;
    private String name;
    private String dateFrom;
    private boolean dateFromIsSet;
    private String dateUntil;
    private String country;
    private String place;
    private String image;
    private int flag;

    public CreateTripViewModel(CreateTripActivity activity) {
        mActivity = activity;
        flag = R.drawable.ic_flag_black_24dp;
        dateFromIsSet = false;
    }

    protected CreateTripViewModel(Parcel in) {
        this.dateFrom = in.readString();
        this.dateFromIsSet = in.readByte() != 0;
        this.dateUntil = in.readString();
        this.country = in.readString();
        this.name = in.readString();
        this.place = in.readString();
        this.image = in.readString();
        this.flag = in.readInt();
    }

    public void clickCountryDialog(View view) {
        final CountryPicker picker = CountryPicker.newInstance("Select Country");
        picker.show(mActivity.getSupportFragmentManager(), "COUNTRY_PICKER");
        picker.setListener(new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                // Implement your code here
                setFlag(flagDrawableResID);
                setCountry(name);
                loadImage(name);
                picker.dismiss();
            }
        });
    }

    private void loadImage(String country) {
        ImageService imageService = RetrofitUtility.initImageService();
        imageService.callImageApi(BuildConfig.PIXABAY_API_KEY, country, 3).enqueue(new Callback<PixabayResult>() {
            @Override
            public void onResponse(Call<PixabayResult> call, Response<PixabayResult> response) {
                if (response.isSuccessful() && response.body().getTotal() > 0) {
                    image = response.body().getHits().get(0).getWebformatURL();
                }
            }

            @Override
            public void onFailure(Call<PixabayResult> call, Throwable t) {
                Log.e(LOG_TAG, t.getMessage(), t);
            }
        });
    }

    public void clickFromDateDialog(View view) {
        LocalDateTime date = LocalDateTime.now();

        DatePickerDialog dialog = new DatePickerDialog(mActivity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                LocalDate localDate = LocalDate.of(year, month + 1, dayOfMonth);
                dateFrom = localDate.toString();
                notifyChange();
            }
        }, date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
        dialog.getDatePicker().setMinDate(date.toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        if (dateUntil != null) {
            LocalDate dateTo = LocalDate.parse(dateUntil);
            dialog.getDatePicker().setMaxDate(dateTo.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        }
        dialog.show();
    }

    public void clickToDateDialog(View view) {
        LocalDate date;
        if (dateFrom == null) {
            date = LocalDate.now();
        } else {
            date = LocalDate.parse(dateFrom);
        }
        new Date().getTime();
        DatePickerDialog dialog = new DatePickerDialog(mActivity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                LocalDate localDate = LocalDate.of(year, month + 1, dayOfMonth);
                dateUntil = localDate.toString();
                notifyChange();
            }
        }, date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
        dialog.getDatePicker().setMinDate(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        dialog.show();
    }

    public boolean isValidated() {

        return country != null && dateFrom != null && dateUntil != null && name != null &&
                !country.isEmpty() && !dateFrom.isEmpty() && !dateUntil.isEmpty() &&
                !name.isEmpty();
    }

    public void saveData(View view) {
        Uri uri = TravelCompanionUtility.insertTrip(mActivity, name, country, dateFrom, dateUntil, place, image);
        Log.d(LOG_TAG, uri.toString());
        mActivity.setResult(Activity.RESULT_OK);
        mActivity.finish();
    }

    public void afterTextChanged(Editable s) {
        Log.e("TextWatcherTest", "afterTextChanged:\t" + s.toString());
        name = s.toString();
        notifyChange();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
        notifyChange();
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyChange();
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
        notifyChange();
    }

    public String getDateUntil() {
        return dateUntil;
    }

    public void setDateUntil(String dateUntil) {
        this.dateUntil = dateUntil;
        notifyChange();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
        notifyChange();
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
        notifyChange();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.dateFrom);
        dest.writeByte(this.dateFromIsSet ? (byte) 1 : (byte) 0);
        dest.writeString(this.dateUntil);
        dest.writeString(this.country);
        dest.writeString(this.name);
        dest.writeString(this.place);
        dest.writeString(this.image);
        dest.writeInt(this.flag);
    }
}
