package com.liewjuntung.travelcompanion.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.liewjuntung.travelcompanion.R;
import com.liewjuntung.travelcompanion.models.Itinerary;
import com.liewjuntung.travelcompanion.providers.ItinerariesTableColumns;
import com.liewjuntung.travelcompanion.providers.TravelCompanionProvider;
import com.liewjuntung.travelcompanion.ui.itinerary.ItineraryActivity;
import com.liewjuntung.travelcompanion.utility.TravelCompanionUtility;
import com.liewjuntung.travelcompanion.utility.WeatherUtility;

import static com.liewjuntung.travelcompanion.utility.TravelCompanionUtility.ITINERARY_COLUMNS;


/**
 * Popular Movie App
 * Created by jtlie on 8/25/2016.
 */

public class TravelCompanionWidgetRemoteViewService extends RemoteViewsService {
    public static final String LOG_TAG = TravelCompanionWidgetRemoteViewService.class.getSimpleName();
    public static final String TRIP_ID = "rv_trip_id";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new TravelCompanionRemoteViewFactory(getApplicationContext(), intent);
    }
}

class TravelCompanionRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {
    private Cursor data = null;
    private int tripId;
    private int mAppWidgetId;
    private Context mContext;

    public TravelCompanionRemoteViewFactory(Context context, Intent intent) {
        mContext = context;
        int[] appWidgets = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        tripId = PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(context.getString(R.string.widget_trip_pref_name, mAppWidgetId), 0);

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        // This is triggered when you call AppWidgetManager notifyAppWidgetViewDataChanged
        // on the collection view corresponding to this factory. You can do heaving lifting in
        // here, synchronously. For example, if you need to process an image, fetch something
        // from the network, etc., it is ok to do it here, synchronously. The widget will remain
        // in its current state while work is being done here, so you don't need to worry about
        // locking up the widget.

        if (data != null && tripId != 0) {
            data.close();
        }
        final long identityToken = Binder.clearCallingIdentity();

        //get the most updated
        data = mContext.getContentResolver().query(TravelCompanionProvider.Itineraries.ITINERARIES,
                ITINERARY_COLUMNS,
                ItinerariesTableColumns.TRIP_ID + " = ?",
                new String[]{Integer.toString(tripId)},
                null);
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {
        if (data != null) {
            data.close();
            data = null;
        }
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION ||
                data == null || !data.moveToPosition(position)) {
            return null;
        }
        Itinerary itinerary = Itinerary.create(data);
        RemoteViews views = new RemoteViews(mContext.getPackageName(),
                R.layout.widget_list);
        //set remote view
        views.setTextViewText(R.id.iter_time, itinerary.getDisplayTime());
        views.setTextViewText(R.id.iter_name, itinerary.getName());
        views.setTextViewText(R.id.iter_place, itinerary.getPlace());

        // views.setTextColor(R.id.iter_place, getResources().getColor(R.color.white));
        if (itinerary.getWeatherCode() < 0 || itinerary.getWeatherCode() > 47) {
            views.setViewVisibility(R.id.iter_weather_view, View.GONE);
        } else {
            views.setImageViewResource(R.id.iter_weather_image,
                    WeatherUtility.getWeatherDrawables(itinerary.getWeatherCode()));
            views.setContentDescription(R.id.iter_weather_image,
                    WeatherUtility.getWeatherStringFromCode(mContext, itinerary.getWeatherCode()));
            views.setTextViewText(R.id.iter_temp_high, Integer.toString(itinerary.getHighTemp()));
            views.setTextViewText(R.id.iter_temp_low, Integer.toString(itinerary.getLowTemp()));
        }

        Intent clickIntent = new Intent();
        clickIntent.putExtra(ItineraryActivity.INTENT_ITINERARY_ID, itinerary.getId());
        views.setOnClickFillInIntent(R.id.trip_list_item, clickIntent);
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return new RemoteViews(mContext.getPackageName(), R.layout.widget_view);
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        if (data.moveToPosition(position)) {
            return data.getInt(TravelCompanionUtility.ITER_ID);
        }
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}


