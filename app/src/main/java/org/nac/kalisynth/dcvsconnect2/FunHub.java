//The Entertainment Hub Screen  with branches to the Games, Internet and Radio
//ToDo in future add in  Broadcast / Livestream button

package org.nac.kalisynth.dcvsconnect2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.vstechlab.easyfonts.EasyFonts;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FunHub extends AppCompatActivity {

    LinearLayout.LayoutParams params_radio;
    @BindView(R.id.radioBTN) Button rbtn;
    @BindView(R.id.funinternetbtn) Button ibtn;
    @BindView(R.id.livestreambtn) Button sbtn;
    @BindView(R.id.fungamesbtn) Button gbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fun_hub);
        ButterKnife.bind(this);

        //layout variables for the Fun Screen
        RelativeLayout funView = (RelativeLayout) findViewById(R.id.funlayout);

        //Set Fonts for the Button text
        gbtn.setTypeface(EasyFonts.robotoBlack(this));
        sbtn.setTypeface(EasyFonts.robotoBlack(this));
        ibtn.setTypeface(EasyFonts.robotoBlack(this));
        rbtn.setTypeface(EasyFonts.robotoBlack(this));
    }

    @OnClick(R.id.fungamesbtn)
    public void gamesonclick() {
        DCVSOverlayService.DCVSView.addView(DCVSOverlayService.funButton);
        DCVSOverlayService.funv = true;
        startActivity(new Intent(FunHub.this, GamesHub.class));
        finish();
    }

    @OnClick(R.id.funinternetbtn)
    public void internetonClick() {
        addFunBtn();
        startActivity(new Intent(FunHub.this, WebHub.class));
        finish();
    }

    @OnClick(R.id.radioBTN)
    public void radioonclick(){
        addFunBtn();
        startActivity(new Intent(FunHub.this, Radio.class));
        finish();
    }

    @OnClick(R.id.livestreambtn)
    public void onStreamClick() {
        Uri souri = Uri.parse("https://www.youtube.com/embed/live_stream?channel=UCWRwHEstLTwCNhJORNOlPqg");
        //url https://www.youtube.com/embed/live_stream?channel=UCWRwHEstLTwCNhJORNOlPqg
        Intent SOIntent = new Intent(Intent.ACTION_VIEW, souri);
        startActivity(SOIntent);
        /*startActivity(new Intent(FunHub.this, Broadcast.class));*/
        addFunBtn();
        buttonclicksound();
        finish();
    }

    private void buttonclicksound(){
        MediaPlayer mp = MediaPlayer.create(this, R.raw.btnpush);
        mp.start();
    }

    private void addFunBtn(){
        DCVSOverlayService.DCVSView.addView(DCVSOverlayService.funButton);
        DCVSOverlayService.funv = true;
    }
}
