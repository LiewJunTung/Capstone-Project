package com.liewjuntung.travelcompanion.service;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.liewjuntung.travelcompanion.R;
import com.liewjuntung.travelcompanion.models.Itinerary;
import com.liewjuntung.travelcompanion.models.Weather;
import com.liewjuntung.travelcompanion.models.yahoo.YahooQueryResult;
import com.liewjuntung.travelcompanion.networks.WeatherService;
import com.liewjuntung.travelcompanion.providers.ItinerariesTableColumns;
import com.liewjuntung.travelcompanion.providers.TravelCompanionProvider;
import com.liewjuntung.travelcompanion.utility.RetrofitUtility;
import com.liewjuntung.travelcompanion.utility.TravelCompanionUtility;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

/**
 * Popular Movie App
 * Created by jtlie on 9/18/2016.
 */

public class TravelCompanionSyncAdapter extends AbstractThreadedSyncAdapter {
    private static final int SYNC_INTERVAL = 60 * 60 * 24;
    private static final int SYNC_FLEXTIME = SYNC_INTERVAL / 3;
    private static final String LOG_TAG = TravelCompanionSyncAdapter.class.getSimpleName();

    public TravelCompanionSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    /**
     * Helper method to have the sync adapter sync immediately
     *
     * @param context The context used to access the account service
     */
    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);
    }

    /**
     * Helper method to get the fake account to be used with SyncAdapter, or make a new one
     * if the fake account doesn't exist yet.  If we make a new account, we call the
     * onAccountCreated method so we can initialize things.
     *
     * @param context The context used to access the account service
     * @return a fake account.
     */
    public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        // If the password doesn't exist, the account doesn't exist
        if (null == accountManager.getPassword(newAccount)) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */

            onAccountCreated(newAccount, context);
        }
        return newAccount;
    }

    private static void onAccountCreated(Account newAccount, Context context) {
        /*
         * Since we've created an account
         */
        TravelCompanionSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);

        /*
         * Without calling setSyncAutomatically, our periodic sync will not be enabled.
         */
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);

        /*
         * Finally, let's do a sync to get things started
         */
        syncImmediately(context);
    }

    /**
     * Helper method to schedule the sync adapter periodic execution
     */
    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).
                    setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                    authority, new Bundle(), syncInterval);
        }
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.d(LOG_TAG, "onPerformSync");
        updateWeather();
    }

    private void updateWeather() {
        Cursor cursor = getContext().getContentResolver().query(TravelCompanionProvider.Itineraries.ITINERARIES,
                TravelCompanionUtility.ITINERARY_COLUMNS, null, null, null);
        WeatherService weatherService = RetrofitUtility.initWeatherService();

        while (cursor != null && cursor.moveToNext()) {
            Itinerary itinerary = Itinerary.create(cursor);
            Response<YahooQueryResult> response;
            try {
                if (itinerary.getLongitude() == 0.0 || itinerary.getLatitude() == 0.0) {
                    response = RetrofitUtility.getWeatherByPlaceName(weatherService, itinerary.getPlace()).execute();
                } else {
                    response = RetrofitUtility.getWeatherByLongAndLat(weatherService, itinerary.getLatitude(), itinerary.getLongitude()).execute();
                }
                updateWeatherProcessResponse(itinerary, response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (cursor != null) {
            cursor.close();
        }
    }

    private void updateWeatherProcessResponse(Itinerary itinerary, Response<YahooQueryResult> response) {
        List<Weather> weatherList = response.body().getQuery().getResult().getChannel().getItem().getWeatherList();
        for (Weather weather : weatherList) {
            ContentValues values = new ContentValues();
            if (LocalDate.parse(weather.getDate(), DateTimeFormatter.ofPattern("dd MMM yyyy")).equals(LocalDate.parse(itinerary.getDisplayDate()))) {
                if (weather.getCode() > -1) {
                    values.put(ItinerariesTableColumns.WEATHER_CODE, weather.getCode());
                    values.put(ItinerariesTableColumns.HIGH_TEMP, weather.getHigh());
                    values.put(ItinerariesTableColumns.LOW_TEMP, weather.getLow());
                    getContext().getContentResolver().update(
                            TravelCompanionProvider.Itineraries.ITINERARIES,
                            values,
                            ItinerariesTableColumns._ID + " = ?",
                            new String[]{"" + itinerary.getId()});
                }
                break;
            }
        }
    }
}
