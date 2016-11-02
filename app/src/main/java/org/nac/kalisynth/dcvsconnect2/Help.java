//Help Screen for Tablet Support
//Todo
package org.nac.kalisynth.dcvsconnect2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vstechlab.easyfonts.EasyFonts;
import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.activities.ConnectionBuddyActivity;
import com.zplesac.connectionbuddy.interfaces.NetworkRequestCheckListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Help extends ConnectionBuddyActivity {
    String fsurl = "http://www.fastsupport.com";
    @BindView(R.id.gtabtn) Button gtab;
    @BindView(R.id.helpguideIB) Button hgbtn;
    @BindView(R.id.aboutbtn) Button abtn;
    @BindView(R.id.ccheckbtn) Button cbtn;
    @BindView(R.id.SkypeVIB) Button sbtn;
    @BindView(R.id.fastsupportbtn) Button fbtn;
    @BindView(R.id.connectiontypetv) TextView ctv;
    @BindView(R.id.signalstrengthtv) TextView stv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ButterKnife.bind(this);
        gtainstalled();
        hgbtn.setTypeface(EasyFonts.robotoBlack(this));
        abtn.setTypeface(EasyFonts.robotoBlack(this));
        cbtn.setTypeface(EasyFonts.robotoBlack(this));
        sbtn.setTypeface(EasyFonts.robotoBlack(this));
        fbtn.setTypeface(EasyFonts.robotoBlack(this));
    }

    //Launch DCVS Help Guide
    @OnClick(R.id.helpguideIB)
    public void helpguideonclick(){
        Intent helpguideIntent;
        PackageManager helpguideManager = getPackageManager();
        helpguideIntent = helpguideManager.getLaunchIntentForPackage("appinventor.ai_tim.DCVSHelpGuide");
        helpguideIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        startActivity(helpguideIntent);
    }

    //open the About screen
    @OnClick(R.id.aboutbtn)
    public void aboutonclick(){
        Intent aboutintent = new Intent(getApplicationContext(), About.class);
        aboutintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(aboutintent);
    }

    //Launch Play Store to check if skype needs updating
    @OnClick(R.id.SkypeVIB)
    public void skypeversionclick(){
        Uri marketUri = Uri.parse("market://details?id=com.skype.raider");
        Intent myIntent = new Intent(Intent.ACTION_VIEW, marketUri);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(myIntent);
    }

    //Launch the Goto Assist app
    @OnClick(R.id.gtabtn)
    public void gtaclick(){
        Intent gotointent;
        PackageManager gotoManager = getPackageManager();
        gotointent = gotoManager.getLaunchIntentForPackage("com.citrix.g2arscustomer");
        gotointent.addCategory(Intent.CATEGORY_LAUNCHER);
        startActivity(gotointent);
        finish();
    }

    //Launch the Fast Support website
    @OnClick(R.id.fastsupportbtn)
    public void fswebclick(){
        Uri guri = Uri.parse(fsurl);
        Intent fswIntent = new Intent(Intent.ACTION_VIEW, guri);
        startActivity(fswIntent);
        finish();
    }

    private boolean isPackageInstalled(String packagename, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public void gtainstalled(){
        PackageManager pm = this.getPackageManager();
        boolean isInstalled = isPackageInstalled("com.citrix.g2arscustomer", pm);
        if(!isInstalled){
            gtab.setVisibility(View.INVISIBLE);
        } else {
            gtab.setTypeface(EasyFonts.robotoBlack(this));
        }
    }

    @OnClick(R.id.ccheckbtn)
    public void ccheckbtn(){
            ConnectionBuddy.getInstance().hasNetworkConnection(new NetworkRequestCheckListener() {
                @Override
                public void onResponseObtained() {
                    Toast.makeText(Help.this, "Internet is Working! You are connected by " + ConnectionBuddy.getInstance().getNetworkType() +  " and the signal strength is " + ConnectionBuddy.getInstance().getSignalStrength(), Toast.LENGTH_LONG).show();
                    String connectiontype = String.format(getString(R.string.ConnectionTypeString), ConnectionBuddy.getInstance().getNetworkType());
                    String signalstrength = String.format(getString(R.string.SignalStrengthString), ConnectionBuddy.getInstance().getSignalStrength());
                    ctv.setText(connectiontype);
                    stv.setText(signalstrength);
                }

                @Override
                public void onNoResponse() {
                    Toast.makeText(Help.this, "Internet is not Working!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }