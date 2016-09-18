package com.liewjuntung.travelcompanion.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class TravelCompanionAuthenticatorService extends Service {
    private TravelCompanionAuthenticator mAuthenticator;

    public TravelCompanionAuthenticatorService() {
        mAuthenticator = new TravelCompanionAuthenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mAuthenticator.getIBinder();
    }
}
