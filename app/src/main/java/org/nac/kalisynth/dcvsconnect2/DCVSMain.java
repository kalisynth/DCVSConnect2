package org.nac.kalisynth.dcvsconnect2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DCVSMain extends Activity {

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

    public void goHome(View v){
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /*public void goChat(View v){
        startActivity(new Intent(DCVSMain.this, ))
    }*/
}
