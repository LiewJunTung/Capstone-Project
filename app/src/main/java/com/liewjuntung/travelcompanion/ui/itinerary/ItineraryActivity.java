package com.liewjuntung.travelcompanion.ui.itinerary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.liewjuntung.travelcompanion.R;
import com.liewjuntung.travelcompanion.databinding.ActivityItineraryBinding;
import com.liewjuntung.travelcompanion.models.Itinerary;
import com.liewjuntung.travelcompanion.providers.ItinerariesTableColumns;
import com.liewjuntung.travelcompanion.providers.TravelCompanionProvider;
import com.liewjuntung.travelcompanion.ui.modify_itinerary.ModifyItineraryActivity;
import com.liewjuntung.travelcompanion.utility.TravelCompanionUtility;

public class ItineraryActivity extends AppCompatActivity
        implements OnStreetViewPanoramaReadyCallback, OnMapReadyCallback, LoaderManager.LoaderCallbacks<Cursor> {

    public static final int ITINERARY_LOADER_ID = 200;
    public static final String INTENT_ITINERARY = "itinerary_intent";
    public static final String INTENT_ITINERARY_ID = "itinerary_intent_id";
    private static final String LOG_TAG = ItineraryActivity.class.getSimpleName();
    ActivityItineraryBinding mBinding;
    Itinerary mItinerary;
    StreetViewPanorama mPanorama;
    int mItineraryId;

    private String mDateFrom;
    private String mDateUntil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mDateFrom = intent.getStringExtra(ModifyItineraryActivity.MODIFY_TRIP_DATE_FROM);
        mDateUntil = intent.getStringExtra(ModifyItineraryActivity.MODIFY_TRIP_DATE_UNTIL);
        mItineraryId = getIntent().getIntExtra(INTENT_ITINERARY_ID, 0);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_itinerary);
        getSupportLoaderManager().initLoader(ITINERARY_LOADER_ID, null, this);
        Log.d(LOG_TAG, mDateFrom + " " + mDateUntil);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(ITINERARY_LOADER_ID, null, this);
    }

    private void initUi(Cursor c) {
        while (c.moveToNext()) {
            mItinerary = Itinerary.create(c);
        }
        mBinding.setItinerary(mItinerary);
        initGoogleMapFragment();
        initStreetviewPanorama();
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(mBinding.toolbar.toolbar);
        if (getSupportActionBar() != null && mItinerary != null) {
            getSupportActionBar().setTitle(mItinerary.getName());
            getSupportActionBar().setSubtitle(mItinerary.getDateTime());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initGoogleMapFragment() {
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private void initStreetviewPanorama() {
        StreetViewPanoramaFragment streetViewPanoramaFragment =
                (StreetViewPanoramaFragment) getFragmentManager()
                        .findFragmentById(R.id.streetviewpanorama);

        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_itinerary, menu);
        return true;
    }


    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
        if (mItinerary != null) {
            mPanorama = panorama;
            mPanorama.setPosition(new LatLng(mItinerary.getLatitude(), mItinerary.getLongitude()));
            panorama.setPanningGesturesEnabled(true);
            panorama.setUserNavigationEnabled(true);
            panorama.setStreetNamesEnabled(true);
        }

    }

    @Override
    public void onMapReady(GoogleMap map) {
        if (mItinerary != null) {
            LatLng latLng = new LatLng(mItinerary.getLatitude(), mItinerary.getLongitude());
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
            map.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(mItinerary.getPlace()));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_itinerary_delete:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.text_delete_item)
                        .setMessage(R.string.text_are_you_sure_delete)
                        .setPositiveButton(getString(R.string.text_ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteItinerary();
                            }
                        }).show();
                break;
            case R.id.action_itinerary_modify:
                initModifyActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initModifyActivity() {
        Intent intent = new Intent(this, ModifyItineraryActivity.class);
        intent.putExtra(ModifyItineraryActivity.MODIFY_TRIP_ITINARARY, mItinerary);
        intent.putExtra(ModifyItineraryActivity.MODIFY_TRIP_DATE_FROM, mDateFrom);
        intent.putExtra(ModifyItineraryActivity.MODIFY_TRIP_DATE_UNTIL, mDateUntil);
        startActivity(intent);
    }

    private void deleteItinerary() {
        getContentResolver().delete(TravelCompanionProvider.Itineraries.ITINERARIES,
                ItinerariesTableColumns._ID + "= ?", new String[]{
                        "" + mItinerary.getId()
                });
        finish();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, TravelCompanionProvider.Itineraries.withId(mItineraryId), TravelCompanionUtility.ITINERARY_COLUMNS, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        initUi(data);
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
