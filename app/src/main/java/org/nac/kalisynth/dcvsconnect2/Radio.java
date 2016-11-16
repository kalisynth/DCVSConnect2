package org.nac.kalisynth.dcvsconnect2;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nisrulz.sensey.Sensey;
import com.github.nisrulz.sensey.TouchTypeDetector;
import com.pddstudio.talking.Talk;
import com.pddstudio.talking.model.SpeechObject;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.mobiwise.library.RadioListener;
import co.mobiwise.library.RadioManager;

public class Radio extends AppCompatActivity implements RadioListener, Talk.Callback {

    //Butterknife Binds
    @BindView(R.id.mTextViewControl) TextView tvc;
    @BindView(R.id.playBTN) Button btnPlay;
    @BindDrawable(R.drawable.radio_button_play) Drawable drwPlay;
    @BindDrawable(R.drawable.radio_button_stop) Drawable drwause;

    //Vars
    RadioManager mRadioManager = RadioManager.with(this);
    String radioplaying;
    String radiopaused;
    String radioloading;
    String radiourl = "http://thassos.cdnstream.com:5046/stream";

    boolean mListening = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);

        //Binds and Inits
        ButterKnife.bind(this);
        mRadioManager.registerListener(this);
        mRadioManager.enableNotification(true);
        Talk.init(this, this);

        //Radio status Strings
        radioplaying = getString(R.string.radioplaying);
        radiopaused = getString(R.string.radiostopped);
        radioloading = getString(R.string.radioloadin);

        Talk.getInstance().addSpeechObjects(playObject, pauseObject);
        startTouchTypeDetection();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // Setup onTouchEvent for detecting type of touch gesture
        Sensey.getInstance().setupDispatchTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    //Play Button
    @OnClick(R.id.playBTN)
    public void onClickPlay(){
        //Check if the radio is playing
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

    private void startTouchTypeDetection() {
        Sensey.getInstance().startTouchTypeDetection(new TouchTypeDetector.TouchTypListener() {
            @Override
            public void onTwoFingerSingleTap() {

            }

            @Override
            public void onThreeFingerSingleTap() {

            }

            @Override
            public void onDoubleTap() {

            }

            @Override
            public void onScroll(int i) {

            }

            @Override
            public void onSingleTap() {

            }

            @Override
            public void onSwipe(int i) {
                switch (i) {
                    case TouchTypeDetector.SWIPE_DIR_UP:
                        Log.d("Gestures", "Swipe Up");
                        DCVSOverlayService.startspeaking();
                        break;
                    case TouchTypeDetector.SWIPE_DIR_DOWN:
                        Log.d("Gestures", "Swipe Down");
                        break;
                    case TouchTypeDetector.SWIPE_DIR_LEFT:
                        Log.d("Gestures","Swipe Left");
                        startActivity(new Intent(Radio.this, FunHub.class));
                        break;
                    case TouchTypeDetector.SWIPE_DIR_RIGHT:
                        Log.d("Gestures", "Swipe Right");
                        onClickPlay();
                        break;
                    default:
                        //do nothing
                        break;
                }

            }

            @Override
            public void onLongPress() {

            }
        });
    }

    @Override
    public void onStartListening() {
        Toast.makeText(this, getResources().getString(R.string.listening), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRmsChanged(float rms) {

    }

    @Override
    public void onFailedListening(int errorCode) {
        Toast.makeText(Radio.this, "Sorry I missed that, please repeat",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFinishedListening(SpeechObject speechObject) {
        if(speechObject != null) {
            speechObject.onSpeechObjectIdentified();
        }
    }

    private SpeechObject playObject = new SpeechObject(){
        @Override
        public void onSpeechObjectIdentified() {
            onClickPlay();
            Talk.getInstance().stopListening();
            mListening = false;
        }

        @Override
        public String getVoiceString() {
            return "play";
        }
    };

    private SpeechObject pauseObject = new SpeechObject(){
        @Override
        public void onSpeechObjectIdentified() {
            mRadioManager.stopRadio();
            Talk.getInstance().stopListening();
            mListening = false;
        }

        @Override
        public String getVoiceString() {
            return "pause";
        }
    };
}
