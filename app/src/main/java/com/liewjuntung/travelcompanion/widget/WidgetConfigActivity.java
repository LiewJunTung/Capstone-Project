package com.liewjuntung.travelcompanion.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.liewjuntung.travelcompanion.ui.create_trip.CreateTripActivity;
import com.liewjuntung.travelcompanion.ui.main.MainAdapter;
import com.liewjuntung.travelcompanion.ui.main.TripClickListener;
import com.liewjuntung.travelcompanion.utility.TravelCompanionUtility;

import static android.appwidget.AppWidgetManager.ACTION_APPWIDGET_UPDATE;

public class WidgetConfigActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int TRIP_LIST_LOADER = 0;
    MainAdapter mMainAdapter;
    private int mAppWidgetId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //so that it won't set on screen when user press back button
        setResult(RESULT_CANCELED);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WidgetConfigActivity.this, CreateTripActivity.class));
            }
        });
        initRecyclerView();
        getSupportLoaderManager().initLoader(TRIP_LIST_LOADER, null, this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID,
        // finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }


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

                Intent intent = new Intent(ACTION_APPWIDGET_UPDATE, null,
                        WidgetConfigActivity.this, TravelCompanionWidgetProvider.class);
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, new int[]{mAppWidgetId});
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                intent.putExtra(TravelCompanionWidgetRemoteViewService.TRIP_ID, trip.getId());
                PreferenceManager.getDefaultSharedPreferences(WidgetConfigActivity.this)
                        .edit()
                        .putInt(getString(R.string.widget_trip_pref_name, mAppWidgetId), trip.getId())
                        .apply();
                sendBroadcast(intent);
                setResult(RESULT_OK, intent);
                finish();
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
