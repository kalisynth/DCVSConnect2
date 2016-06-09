package org.nac.kalisynth.dcvsconnect2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Dcvsinternet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dcvsinternet);
    }

    public void mapsonclick(View v) {
        Intent googlemIntent;
        PackageManager googleManager = getPackageManager();
        googlemIntent = googleManager.getLaunchIntentForPackage("com.google.android.apps.maps");
        googlemIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        startActivity(googlemIntent);
        finish();
    }

    public void onGoogleClick(View v) {
        Uri guri = Uri.parse("http://www.google.com");
        Intent googleIntent = new Intent(Intent.ACTION_VIEW, guri);
        startActivity(googleIntent);
        finish();
    }

    public void onABCClick(View v) {
        Uri auri = Uri.parse("http://www.abc.net.au/news/");
        Intent abcIntent = new Intent(Intent.ACTION_VIEW, auri);
        startActivity(abcIntent);
        finish();
    }

    public void onSevenClick(View v) {
        Uri suri = Uri.parse("https://au.news.yahoo.com/");
        Intent sevenIntent = new Intent(Intent.ACTION_VIEW, suri);
        startActivity(sevenIntent);
        finish();
    }

    public void onNACClick(View v) {
        Uri nuri = Uri.parse("http://www.nac.org.au");
        Intent NACIntent = new Intent(Intent.ACTION_VIEW, nuri);
        startActivity(NACIntent);
        finish();
    }

    public void onSeniorClick(View v) {
        Uri souri = Uri.parse("http://www.nationalseniors.com.au/");
        Intent SOIntent = new Intent(Intent.ACTION_VIEW, souri);
        startActivity(SOIntent);
        finish();
    }
}
