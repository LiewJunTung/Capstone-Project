package com.liewjuntung.travelcompanion.ui.main;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.liewjuntung.travelcompanion.R;
import com.liewjuntung.travelcompanion.models.Trip;
import com.liewjuntung.travelcompanion.providers.TravelCompanionProvider;
import com.liewjuntung.travelcompanion.service.TravelCompanionSyncAdapter;
import com.liewjuntung.travelcompanion.ui.create_trip.CreateTripActivity;
import com.liewjuntung.travelcompanion.ui.trip.TripActivity;
import com.liewjuntung.travelcompanion.utility.TravelCompanionUtility;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int TRIP_LIST_LOADER = 0;
    MainAdapter mMainAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        initRecyclerView();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CreateTripActivity.class));
            }
        });
        getSupportLoaderManager().initLoader(TRIP_LIST_LOADER, null, this);
        TravelCompanionSyncAdapter.syncImmediately(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(TRIP_LIST_LOADER, null, this);
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_main);
        mMainAdapter = new MainAdapter(this, null, findViewById(R.id.empty_view));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mMainAdapter);
        mMainAdapter.setTripClickListener(new TripClickListener() {
            @Override
            public void clickTrip(View view, Trip trip) {
                Intent intent = new Intent(MainActivity.this, TripActivity.class);
                intent.putExtra(TripActivity.TRIP_INTENT, trip);
//                ActivityOptions options = ActivityOptions
//                        .makeSceneTransitionAnimation(MainActivity.this, view, getString(R.string.trans_main));
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, TravelCompanionProvider.Trips.TRIPS,
                TravelCompanionUtility.TRIP_COLUMNS, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mMainAdapter.setMainListCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mMainAdapter.setMainListCursor(null);
    }

}
