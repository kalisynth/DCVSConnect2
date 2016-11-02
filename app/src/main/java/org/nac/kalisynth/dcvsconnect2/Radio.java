package org.nac.kalisynth.dcvsconnect2;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.mobiwise.library.RadioListener;
import co.mobiwise.library.RadioManager;

public class Radio extends AppCompatActivity implements RadioListener {

    @BindView(R.id.mTextViewControl) TextView tvc;
    @BindView(R.id.playBTN) Button btnPlay;
    @BindDrawable(R.drawable.radio_button_play) Drawable drwPlay;
    @BindDrawable(R.drawable.radio_button_stop) Drawable drwause;

    RadioManager mRadioManager = RadioManager.with(this);
    String radioplaying;
    String radiopaused;
    String radioloading;
    String radiourl = "http://thassos.cdnstream.com:5046/stream";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);
        ButterKnife.bind(this);
        mRadioManager.registerListener(this);
        mRadioManager.enableNotification(true);
        radioplaying = getString(R.string.radioplaying);
        radiopaused = getString(R.string.radiostopped);
        radioloading = getString(R.string.radioloadin);
    }

    @OnClick(R.id.playBTN)
    public void onClickPlay(){
        if(mRadioManager == null){
            mRadioManager.startRadio(radiourl);
        }
        if (mRadioManager.isPlaying()){
            mRadioManager.stopRadio();
            btnPlay.setBackground(drwause);
        }else{
            mRadioManager.startRadio(radiourl);
            btnPlay.setBackground(drwPlay);
        }
    }

    /*public void onClickPauseBad(View v){
        mRadioManager.stopRadio();
    }*/

    @Override
    public void onStart(){
        super.onStart();
        mRadioManager.connect();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mRadioManager.disconnect();
    }

    public void onRadioLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //TODO Do UI works here.
                tvc.setText(radioloading);
            }
        });
    }

    @Override
    public void onRadioConnected() {

    }

    @Override
    public void onRadioStarted() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //TODO Do UI works here.
                tvc.setText(radioplaying);
            }
        });
    }

    @Override
    public void onRadioStopped() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //TODO Do UI works here
                tvc.setText(radiopaused);
            }
        });
    }

    @Override
    public void onMetaDataReceived(String s, String s1) {
        //TODO Check metadata values. Singer name, song name or whatever you have.
    }
}
