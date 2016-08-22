package org.nac.kalisynth.dcvsconnect2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DCVSHelp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dcvshelp);
    }

    public void helpguideonclick(View v){
        Intent helpguideIntent;
        PackageManager helpguideManager = getPackageManager();
        helpguideIntent = helpguideManager.getLaunchIntentForPackage("appinventor.ai_tim.DCVSHelpGuide");
        helpguideIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        startActivity(helpguideIntent);
        finish();
    }

    public void dcvscheatonclick(View v){
        Intent cheatguideintent = new Intent(getApplicationContext(), dcvscheat.class);
        startActivity(cheatguideintent);

        /*Intent helpIntent = new Intent(getApplicationContext(), org.nac.kalisynth.dcvsconnect2.DCVSHelp.class);
        helpIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(helpIntent);*/
    }

    public void dcvssettingsonclick(View v){
        Intent dsi = new Intent(getApplicationContext(), options.class);
        startActivity(dsi);
    }

    public void skypeversionclick(View v){
        Uri marketUri = Uri.parse("market://details?id=com.skype.raider");
        Intent myIntent = new Intent(Intent.ACTION_VIEW, marketUri);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(myIntent);
    }

    public boolean skypeinstalled(){
        //Skype Check
        PackageManager skypeMgr = this.getPackageManager();
        try{
            skypeMgr.getPackageInfo("com.skype.raider", PackageManager.GET_ACTIVITIES);
        }
        catch(PackageManager.NameNotFoundException e) {
            return (false);
        }
        return (true);
    }
}
