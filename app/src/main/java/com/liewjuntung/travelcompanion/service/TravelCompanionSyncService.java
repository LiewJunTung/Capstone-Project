package com.liewjuntung.travelcompanion.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class TravelCompanionSyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static TravelCompanionSyncAdapter travelCompanionSyncAdapter = null;

    @Override
    public void onCreate() {
        super.onCreate();
        synchronized (sSyncAdapterLock) {
            if (travelCompanionSyncAdapter == null) {
                travelCompanionSyncAdapter = new TravelCompanionSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.d("SYNC", "syncservice start");
        return travelCompanionSyncAdapter.getSyncAdapterBinder();
    }
}
