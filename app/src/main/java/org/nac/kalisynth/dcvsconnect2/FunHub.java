//The Entertainment Hub Screen  with branches to the Games, Internet and Radio
//ToDo in future add in  Broadcast / Livestream button

package org.nac.kalisynth.dcvsconnect2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.nisrulz.sensey.Sensey;
import com.github.nisrulz.sensey.TouchTypeDetector;
import com.pddstudio.talking.Talk;
import com.pddstudio.talking.model.SpeechObject;
import com.vstechlab.easyfonts.EasyFonts;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static org.nac.kalisynth.dcvsconnect2.DCVSOverlayService.funv;

public class FunHub extends AppCompatActivity implements Talk.Callback {

    LinearLayout.LayoutParams params_radio;

    //Butterknife Bindviews
    @BindView(R.id.radioBTN) Button rbtn;
    @BindView(R.id.funinternetbtn) Button ibtn;
    @BindView(R.id.livestreambtn) Button sbtn;
    @BindView(R.id.fungamesbtn) Button gbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fun_hub);

        //Bindings
        ButterKnife.bind(this);
        Talk.init(this,this);
        Talk.getInstance().addSpeechObjects(gamesObject, webObject, radioObject, internetObject);

        //Set Fonts for the Button text
        gbtn.setTypeface(EasyFonts.robotoBlack(this));
        sbtn.setTypeface(EasyFonts.robotoBlack(this));
        ibtn.setTypeface(EasyFonts.robotoBlack(this));
        rbtn.setTypeface(EasyFonts.robotoBlack(this));

        startTouchTypeDetection();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // Setup onTouchEvent for detecting type of touch gesture
        Sensey.getInstance().setupDispatchTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    //Opens the Games Hub
    @OnClick(R.id.fungamesbtn)
    public void gamesonclick() {
        buttonclicksound();
        addFunBtn();
        startActivity(new Intent(FunHub.this, GamesHub.class));
        finish();
    }

    //Opens the Web Hub
    @OnClick(R.id.funinternetbtn)
    public void webonClick() {
        buttonclicksound();
        addFunBtn();
        startActivity(new Intent(FunHub.this, WebHub.class));
        finish();
    }

    //Opens the Radio
    @OnClick(R.id.radioBTN)
    public void radioonclick(){
        buttonclicksound();
        addFunBtn();
        startActivity(new Intent(FunHub.this, Radio.class));
        finish();
    }


    //Currently Under Construction for now.
    /*@OnClick(R.id.livestreambtn)
    public void onStreamClick() {
        Uri souri = Uri.parse("https://www.youtube.com/embed/live_stream?channel=UCWRwHEstLTwCNhJORNOlPqg");
        Intent SOIntent = new Intent(Intent.ACTION_VIEW, souri);
        startActivity(SOIntent);
        addFunBtn();
        buttonclicksound();
        finish();
    }*/

    //play sound on button press
    private void buttonclicksound(){
        MediaPlayer mp = MediaPlayer.create(this, R.raw.btnpush);
        mp.start();
    }

    //adds back the fun button to get back to the fun hub rather then have to reopen everything
    private void addFunBtn(){
        if(!funv) {
            DCVSOverlayService.DCVSView.addView(DCVSOverlayService.funButton);
            funv = true;
        }
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
        Toast.makeText(FunHub.this, "Sorry I missed that, please try again",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFinishedListening(SpeechObject speechObject) {
        if(speechObject != null) {
            speechObject.onSpeechObjectIdentified();
        }
    }

    private SpeechObject gamesObject = new SpeechObject(){
        @Override
        public void onSpeechObjectIdentified() {
            Intent chatIntent = new Intent(getApplicationContext(), GamesHub.class);
            startActivity(chatIntent);
            Talk.getInstance().stopListening();
        }

        @Override
        public String getVoiceString() {
            return "games";
        }
    };

    private SpeechObject internetObject = new SpeechObject(){
        @Override
        public void onSpeechObjectIdentified() {
            Intent chatIntent = new Intent(getApplicationContext(), WebHub.class);
            startActivity(chatIntent);
            Talk.getInstance().stopListening();
        }

        @Override
        public String getVoiceString() {
            return "internet";
        }
    };

    private SpeechObject webObject = new SpeechObject(){
        @Override
        public void onSpeechObjectIdentified() {
            Intent chatIntent = new Intent(getApplicationContext(), WebHub.class);
            startActivity(chatIntent);
            Talk.getInstance().stopListening();
        }

        @Override
        public String getVoiceString() {
            return "web";
        }
    };


    private SpeechObject radioObject = new SpeechObject(){
        @Override
        public void onSpeechObjectIdentified() {
            Intent chatIntent = new Intent(getApplicationContext(), Radio.class);
            startActivity(chatIntent);
            Talk.getInstance().stopListening();
        }

        @Override
        public String getVoiceString() {
            return "radio";
        }
    };

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
                        startActivity(new Intent(FunHub.this, WebHub.class));
                        break;
                    case TouchTypeDetector.SWIPE_DIR_LEFT:
                        Log.d("Gestures","Swipe Left");
                        startActivity(new Intent(FunHub.this, Home.class));
                        break;
                    case TouchTypeDetector.SWIPE_DIR_RIGHT:
                        Log.d("Gestures", "Swipe Right");
                        startActivity(new Intent(FunHub.this, Radio.class));
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
}
