package com.example.mohnish.railwaytickethub;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by mohnish on 03-02-2018.
 */

public class AppKillListner extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.d("TAG","i was killed");
        super.onTaskRemoved(rootIntent);
    }
}
