package org.nac.kalisynth.dcvsconnect2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import co.mobiwise.library.RadioListener;
import co.mobiwise.library.RadioManager;

//import org.videolan.libvlc.LibVLC;
//import org.videolan.libvlc.MediaPlayer;

public class DCVSRadio extends AppCompatActivity implements RadioListener {

    private RadioManager mRadioManager = RadioManager.with(this);
    private TextView mTextViewControl;
    private String radioplaying;
    private String radiopaused;
    private String radioloading;
    private String radiourl = "http://thassos.cdnstream.com:5049/stream";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dcvsradio);
        mRadioManager.registerListener(this);
        mRadioManager.enableNotification(true);
        mTextViewControl = (TextView)findViewById(R.id.mTextViewControl);
        radioplaying = getString(R.string.radioplaying);
        radiopaused = getString(R.string.radiostopped);
        radioloading = getString(R.string.radioloadin);
        /*radiodcvs = (Button)findViewById(R.id.dcvsbtn);
        radionac = (Button)findViewById(R.id.nac);*/
    }

    public void onClickPlay(View v){
        mRadioManager.startRadio(radiourl);
    }

    public void onClickPauseBad(View v){
        mRadioManager.stopRadio();
    }

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
                mTextViewControl.setText(radioloading);
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
                mTextViewControl.setText(radioplaying);
            }
        });
    }

    @Override
    public void onRadioStopped() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //TODO Do UI works here
                mTextViewControl.setText(radiopaused);
            }
        });
    }

    @Override
    public void onMetaDataReceived(String s, String s1) {
        //TODO Check metadata values. Singer name, song name or whatever you have.

    }

    /*public void onRadioClick(View v){
        if(v.getId() == R.id.dcvs){
            mRadioManager.stopRadio();
            radiourl = "http://thassos.cdnstream.com:5046/stream";
            mRadioManager.startRadio(radiourl);
        } else if (v.getId() == R.id.nac){
            mRadioManager.stopRadio();
            radiourl = "http://thassos.cdnstream.com:5049/stream";
            mRadioManager.startRadio(radiourl);
        }
    }*/
}
