//The Entertainment Hub Screen  with branches to the Games, Internet and Radio
//ToDo in future add in  Broadcast / Livestream button

package org.nac.kalisynth.dcvsconnect2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.blundell.woody.Woody;
import com.pddstudio.talking.Talk;
import com.pddstudio.talking.model.SpeechObject;
import com.vstechlab.easyfonts.EasyFonts;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FunHub extends AppCompatActivity implements Woody.ActivityMonitorListener, Talk.Callback {

    LinearLayout.LayoutParams params_radio;
    @BindView(R.id.radioBTN) Button rbtn;
    @BindView(R.id.funinternetbtn) Button ibtn;
    @BindView(R.id.livestreambtn) Button sbtn;
    @BindView(R.id.fungamesbtn) Button gbtn;

    TextToSpeech t1;
    String speech;

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

        Woody.onCreateMonitor(this);
        Talk.init(this,this);
        Talk.getInstance().addSpeechObjects(whatObject,homeObject,chatObject, helpObject, gamesObject, webObject, radioObject, internetObject);

        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.ENGLISH);
                }
            }
        });
    }

    //Opens the Games Hub
    @OnClick(R.id.fungamesbtn)
    public void gamesonclick() {
        buttonclicksound();
        DCVSOverlayService.DCVSView.addView(DCVSOverlayService.funButton);
        DCVSOverlayService.funv = true;
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
        DCVSOverlayService.DCVSView.addView(DCVSOverlayService.funButton);
        DCVSOverlayService.funv = true;
    }

    @Override
    public void onFaceDetected() {
            Talk.getInstance().startListening();
        Log.d("FACE", "FACE Detected, Listening");
    }

    @Override
    public void onFaceTimedOut() {
       Log.d("FACE", "Face Timed Out");
    }

    @Override
    public void onFaceDetectionNonRecoverableError() {
        Log.e("Face Error", "Error face broke");
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
        Toast.makeText(FunHub.this, "Sorry I missed that, please repeat",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFinishedListening(SpeechObject speechObject) {
        if(speechObject != null) {
            speechObject.onSpeechObjectIdentified();
        }
    }

    private SpeechObject homeObject = new SpeechObject(){
        @Override
        public void onSpeechObjectIdentified() {
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(homeIntent);
            Talk.getInstance().stopListening();
        }

        @Override
        public String getVoiceString() {
            return "home";
        }
    };

    private SpeechObject chatObject = new SpeechObject(){
        @Override
        public void onSpeechObjectIdentified() {
            Intent chatIntent = new Intent(getApplicationContext(), ChatHub.class);
            chatIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(chatIntent);
            Talk.getInstance().stopListening();
        }

        @Override
        public String getVoiceString() {
            return "chat";
        }
    };

    private SpeechObject helpObject = new SpeechObject(){
        @Override
        public void onSpeechObjectIdentified() {
            Intent chatIntent = new Intent(getApplicationContext(), Help.class);
            chatIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(chatIntent);
            Talk.getInstance().stopListening();
        }

        @Override
        public String getVoiceString() {
            return "help";
        }
    };

    private SpeechObject gamesObject = new SpeechObject(){
        @Override
        public void onSpeechObjectIdentified() {
            Intent chatIntent = new Intent(getApplicationContext(), GamesHub.class);
            chatIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
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
            chatIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
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
            chatIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
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
            chatIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(chatIntent);
            Talk.getInstance().stopListening();
        }

        @Override
        public String getVoiceString() {
            return "radio";
        }
    };

    private SpeechObject whatObject = new SpeechObject(){
        @Override
        public void onSpeechObjectIdentified() {
            speech = "You are on the Play screen, here you can access the Games screen by saying games, the Web screen by saying Web and the Radio by saying radio";
            Talk.getInstance().stopListening();
            t1.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
        }

        @Override
        public String getVoiceString() {
            return "what";
        }
    };
}
