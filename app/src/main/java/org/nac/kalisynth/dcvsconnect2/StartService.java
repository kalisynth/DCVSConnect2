//Starts the Overlay Service
package org.nac.kalisynth.dcvsconnect2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StartService extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toggleService();
        finish();
    }

    private void toggleService(){
        Intent intent=new Intent(this, DCVSOverlayService.class);
        if(!stopService(intent)){
            startService(intent);
        }
    }
}
