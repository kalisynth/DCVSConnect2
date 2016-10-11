package org.nac.kalisynth.dcvsconnect2;

import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Cade2 on 5/10/2016.
 */

public class StartupApp extends Application {
    String passtext = null;
    Calendar c;
    int hournow = 0;

    public StartupApp(){
        Log.i("main", "Constructor fired");
        c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH");
        String formattedTime = df.format(c.getTime());
        hournow = Integer.parseInt(formattedTime);
        if (hournow < 12) {
                passtext = "Hello, Good Morning, How are you?";
            } else {
                passtext = "Hello, Good Afternoon, How are you?";
            }

    }

    @Override
    public void onCreate() {
        super.onCreate();

        // this method fires once as well as constructor
        // but also application has context here
        Intent intent = new Intent(this, landingpage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("GREETINGS", passtext);
        startActivity(intent);
        Log.i("main", "onCreate fired");
        toggleService();
    }

    private void toggleService(){
        Intent intent=new Intent(this, DCVSOverlayService.class);
        if(!stopService(intent)){
            startService(intent);
        }
    }
}
