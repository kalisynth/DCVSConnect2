package org.nac.kalisynth.dcvsconnect2;

import android.app.Application;

import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.ConnectionBuddyConfiguration;

/**
 *  Starts the Connection Test
 */

public class DCVSConnectMain extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ConnectionBuddyConfiguration networkInspectorConfiguration = new ConnectionBuddyConfiguration.Builder(this)
                .setNotifyImmediately(false)
                .build();
        ConnectionBuddy.getInstance().init(networkInspectorConfiguration);
    }
}
