package org.nac.kalisynth.dcvsconnect2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Dcvsfun extends AppCompatActivity {

    LinearLayout.LayoutParams params_radio;
    @BindView(R.id.radioBTN) Button rbtn;
    @BindView(R.id.funinternetbtn) Button ibtn;
    @BindView(R.id.livestreambtn) Button sbtn;
    @BindView(R.id.fungamesbtn) Button gbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dcvsfun);
        ButterKnife.bind(this);
        //layout variables for the Fun Screen
        RelativeLayout funView = (RelativeLayout) findViewById(R.id.funlayout);
    }

    @OnClick(R.id.fungamesbtn)
    public void gamesonclick() {
        DCVSOverlayService.DCVSView.addView(DCVSOverlayService.funButton);
        DCVSOverlayService.funv = true;
        startActivity(new Intent(Dcvsfun.this, Dcvsgames.class));
        finish();
    }

    @OnClick(R.id.funinternetbtn)
    public void internetonClick() {
        DCVSOverlayService.DCVSView.addView(DCVSOverlayService.funButton);
        DCVSOverlayService.funv = true;
        startActivity(new Intent(Dcvsfun.this, Dcvsinternet.class)); finish();}

    @OnClick(R.id.radioBTN)
    public void radioonclick(){
        DCVSOverlayService.DCVSView.addView(DCVSOverlayService.funButton);
        DCVSOverlayService.funv = true;
        startActivity(new Intent(Dcvsfun.this, DCVSRadio.class));
        finish();
    }


    @OnClick(R.id.livestreambtn)
    public void onStreamClick() {
        Uri souri = Uri.parse("https://www.youtube.com/watch?v=0R3sKEM-QeQ");
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
