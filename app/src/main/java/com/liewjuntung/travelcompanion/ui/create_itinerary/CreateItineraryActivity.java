package com.liewjuntung.travelcompanion.ui.create_itinerary;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.liewjuntung.travelcompanion.R;
import com.liewjuntung.travelcompanion.databinding.ActivityCreateItineraryBinding;

public class CreateItineraryActivity
        extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener {

    public static final String TRIP_DATE_ID = "trip_date_id";
    public static final String TRIP_DATE_FROM = "trip_date_from";
    public static final String TRIP_DATE_UNTIL = "trip_date_until";
    public static final int PLACE_PICKER_REQUEST = 1;
    public static final int CREATE_ITINERARY_ACTIVITY_REQUEST = 300;

    private static final String BUNDLE_VIEWMODEL = "itinerary_view_model";
    private static final int GET_FINE_LOCATION_PERMISSION = 100;
    ActivityCreateItineraryBinding mBinding;
    private GoogleApiClient mGoogleApiClient;
    private CreateItineraryViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int tripId = intent.getIntExtra(TRIP_DATE_ID, 0);
        String dateFrom = intent.getStringExtra(TRIP_DATE_FROM);
        String dateTo = intent.getStringExtra(TRIP_DATE_UNTIL);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_itinerary);
        if (savedInstanceState != null) {
            mViewModel = savedInstanceState.getParcelable(BUNDLE_VIEWMODEL);
            if (mViewModel != null) {
                mViewModel.setActivity(this);
            } else {
                mViewModel = new CreateItineraryViewModel(this, tripId, dateFrom, dateTo);
            }
        } else {
            mViewModel = new CreateItineraryViewModel(this, tripId, dateFrom, dateTo);
        }
        mBinding.setVm(mViewModel);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();
        mBinding.createTripPlaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPlacePicker();

            }
        });
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(mBinding.toolbar.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Create New Itinerary");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(BUNDLE_VIEWMODEL, mViewModel);
        super.onSaveInstanceState(outState);
    }

    private void initPlacePicker() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, GET_FINE_LOCATION_PERMISSION);
            Snackbar.make(mBinding.getRoot(), R.string.text_permission_location, Snackbar.LENGTH_LONG).show();
            return;
        }
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            mBinding.createTripPlaceButton.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK) {
            Place place = PlacePicker.getPlace(this, data);
            LatLng latLng = place.getLatLng();
            Log.d("latlong", "onActivityResult: " + latLng.longitude + " " + latLng.latitude);
            mBinding.getVm().setPlace(place.getName().toString(), false);
            mBinding.getVm().setLatitude(latLng.latitude);
            mBinding.getVm().setLongitude(latLng.longitude);
            mBinding.getVm().initWeatherForecastLatLong();
            // String toastMsg = String.format("Place: %s", place.getName());
            // Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == GET_FINE_LOCATION_PERMISSION && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initPlacePicker();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
