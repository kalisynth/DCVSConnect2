package org.nac.kalisynth.dcvsconnect2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Dcvsfun extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dcvsfun);
    }

    public void gamesonclick(View v) {
        DCVSOverlayService.DCVSView.addView(DCVSOverlayService.funButton);
        DCVSOverlayService.funv = true;
        startActivity(new Intent(Dcvsfun.this, Dcvsgames.class));
        finish();
    }

    public void internetonClick(View v) {
        DCVSOverlayService.DCVSView.addView(DCVSOverlayService.funButton);
        DCVSOverlayService.funv = true;
        startActivity(new Intent(Dcvsfun.this, Dcvsinternet.class)); finish();}

    public void radioonclick(View v){
        DCVSOverlayService.DCVSView.addView(DCVSOverlayService.funButton);
        DCVSOverlayService.funv = true;
        startActivity(new Intent(Dcvsfun.this, DCVSRadio.class));
        /*Intent radioIntent;
        PackageManager skypeManager = getPackageManager();
        radioIntent = skypeManager.getLaunchIntentForPackage("org.nac.kalisynth.dcvsradio");
        radioIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        startActivity(radioIntent);*/
        finish();
    }

   // public void radioonClick(View v) { startActivity(new Intent(Dcvsfun.this, dcvsRadio.class)); finish();}
}
