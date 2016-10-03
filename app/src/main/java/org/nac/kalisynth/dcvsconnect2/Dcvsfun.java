package org.nac.kalisynth.dcvsconnect2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import butterknife.OnClick;

public class Dcvsfun extends AppCompatActivity {

    LinearLayout.LayoutParams params_radio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dcvsfun);
        //layout variables for the Fun Screen
        RelativeLayout funView = (RelativeLayout) findViewById(R.id.funlayout);
        ImageButton streambutton = (ImageButton) findViewById(R.id.livestreambtn);
        ImageButton radiobutton = (ImageButton) findViewById(R.id.radioBTN);
    }

    @OnClick(R.id.fungamesbtn)
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
        finish();
    }

    public void onStreamClick(View v) {
        Uri souri = Uri.parse("http://channelnac.org/view/");
        buttonclicksound();
        Intent SOIntent = new Intent(Intent.ACTION_VIEW, souri);
        startActivity(SOIntent);
        finish();
    }

    private void buttonclicksound(){
        MediaPlayer mp = MediaPlayer.create(this, R.raw.btnpush);
        mp.start();
    }
}
