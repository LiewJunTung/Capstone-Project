package com.liewjuntung.travelcompanion.ui.trip;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.liewjuntung.travelcompanion.BuildConfig;
import com.liewjuntung.travelcompanion.R;
import com.liewjuntung.travelcompanion.databinding.ActivityTripBinding;
import com.liewjuntung.travelcompanion.models.Itinerary;
import com.liewjuntung.travelcompanion.models.Trip;
import com.liewjuntung.travelcompanion.providers.ItinerariesTableColumns;
import com.liewjuntung.travelcompanion.providers.TravelCompanionProvider;
import com.liewjuntung.travelcompanion.ui.create_itinerary.CreateItineraryActivity;
import com.liewjuntung.travelcompanion.ui.itinerary.ItineraryActivity;
import com.liewjuntung.travelcompanion.ui.modify_itinerary.ModifyItineraryActivity;
import com.liewjuntung.travelcompanion.utility.TravelCompanionUtility;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class TripActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String TRIP_INTENT = "TRIP";
    private static final int ITINERARY_LIST_LOADER = 1;
    ItineraryAdapter mItineraryAdapter;
    ActivityTripBinding mBinding;
    InterstitialAd mInterstitialAd;
    private Trip mTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mTrip = intent.getParcelableExtra(TRIP_INTENT);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_trip);
        mBinding.setTrip(mTrip);
        initToolbar();
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(BuildConfig.ADS_INTERSTITIAL_ID);
        mBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(TripActivity.this, CreateItineraryActivity.class);
                intent1.putExtra(CreateItineraryActivity.TRIP_DATE_ID, mTrip.getId());
                intent1.putExtra(CreateItineraryActivity.TRIP_DATE_FROM, mTrip.getDateFrom());
                intent1.putExtra(CreateItineraryActivity.TRIP_DATE_UNTIL, mTrip.getDateUntil());
                startActivityForResult(intent1, CreateItineraryActivity.CREATE_ITINERARY_ACTIVITY_REQUEST);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
        initRecyclerView();
        getSupportLoaderManager().initLoader(ITINERARY_LIST_LOADER, null, this);
        requestNewInterstitial();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CreateItineraryActivity.CREATE_ITINERARY_ACTIVITY_REQUEST) {
            boolean showInterstitial = TravelCompanionUtility.showInterstitial(this);
            if (mInterstitialAd.isLoaded() && showInterstitial) {
                mInterstitialAd.show();
            }
        }
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
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
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(ITINERARY_LIST_LOADER, null, this);
    }

    private void initRecyclerView() {
        mItineraryAdapter = new ItineraryAdapter(null, mBinding.content.emptyView);
        mItineraryAdapter.setItineraryClickListener(new ItineraryClickListener() {
            @Override
            public void clickItinerary(Itinerary itinerary) {
                Intent intent = new Intent(TripActivity.this, ItineraryActivity.class);
                intent.putExtra(ItineraryActivity.INTENT_ITINERARY_ID, itinerary.getId());
                intent.putExtra(ModifyItineraryActivity.MODIFY_TRIP_DATE_FROM, mTrip.getDateFrom());
                intent.putExtra(ModifyItineraryActivity.MODIFY_TRIP_DATE_UNTIL, mTrip.getDateUntil());
                startActivity(intent);
            }
        });
        mBinding.content.contentTripRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.content.contentTripRecyclerView.setAdapter(mItineraryAdapter);
    }

    private void initToolbar() {

        setSupportActionBar(mBinding.toolbarDetail);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(mTrip.getName());
            getSupportActionBar().setSubtitle(mTrip.getDisplayDate());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            mBinding.collapsingToolbar.setContentScrimColor(getResources().getColor(R.color.primary));
            new AsyncTask<Context, Void, Boolean>() {
                @Override
                protected Boolean doInBackground(Context... params) {
                    try {

                        Bitmap bitmap = Picasso.with(params[0])
                                .load(mTrip.getImage())
                                .get();
                        return TravelCompanionUtility.isDark(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return false;
                    }
                }

                @Override
                protected void onPostExecute(Boolean isDark) {
                    if (isDark) {
                        mBinding.collapsingToolbar.setExpandedTitleTextColor(ColorStateList.valueOf(Color.WHITE));
                    } else {
                        mBinding.collapsingToolbar.setExpandedTitleTextColor(ColorStateList.valueOf(getResources().getColor(R.color.black)));
                    }
                }
            }.execute(this);


        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, TravelCompanionProvider.Itineraries.ITINERARIES,
                TravelCompanionUtility.ITINERARY_COLUMNS, ItinerariesTableColumns.TRIP_ID + "= ?", new String[]{Integer.toString(mTrip.getId())}, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mItineraryAdapter.setMainListCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mItineraryAdapter.setMainListCursor(null);
    }
}
