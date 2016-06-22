package org.nac.kalisynth.dcvsconnect2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class Dcvsfun extends AppCompatActivity {

    MediaPlayer mp = null;
    public static RelativeLayout FunView;
    ImageButton streambutton;
    ImageButton radiobutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dcvsfun);

        FunView = (RelativeLayout) findViewById(R.id.funlayout);
        streambutton = (ImageButton) findViewById(R.id.livestreambtn);
        radiobutton = (ImageButton) findViewById(R.id.radioBTN);

        if (BuildConfig.RadioStation.equals("DCVS")){
            //change streaming to invisible
            radiobutton.setBackground(ContextCompat.getDrawable(this, R.drawable.dcvsradio));
            FunView.removeView(streambutton);
        } else if (BuildConfig.RadioStation.equals("Local")){
            //change stream to visible
            //FunView.addView(streambutton);
            radiobutton.setBackground(ContextCompat.getDrawable(this, R.drawable.dcvsnacradiobtn));
        } else {

        }
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

    public void onStreamClick(View v) {
        //Uri souri = Uri.parse("http://bit.ly/ylivestream");
        Uri souri = Uri.parse("http://channelnac.org/view/");
        buttonclicksound();
        Intent SOIntent = new Intent(Intent.ACTION_VIEW, souri);
        startActivity(SOIntent);
        finish();
    }

    public void buttonclicksound(){
        mp = MediaPlayer.create(this, R.raw.btnpush);
        mp.start();
    }

   // public void radioonClick(View v) { startActivity(new Intent(Dcvsfun.this, dcvsRadio.class)); finish();}
}
