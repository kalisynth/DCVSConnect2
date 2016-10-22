package org.nac.kalisynth.dcvsconnect2;

import android.app.Application;

import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.ConnectionBuddyConfiguration;

/**
 * Created by Tim on 21/10/2016.
 */

public class DcvsConnect extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ConnectionBuddyConfiguration networkInspectorConfiguration = new ConnectionBuddyConfiguration.Builder(this)
                .setNotifyImmediately(false)
                .build();
        ConnectionBuddy.getInstance().init(networkInspectorConfiguration);
    }
}
