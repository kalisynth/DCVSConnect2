package org.nac.kalisynth.dcvsconnect2;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class landingpage extends AppCompatActivity {
    @BindView(R.id.messageboxtext) TextView txvMessage;

    int hournow = 0;
    Calendar c;
    TextToSpeech t1;
    String utterId = null;
    String newString;
    int randnumber = 0;
    String suggest = null;
    Handler handler = new Handler();
    private Runnable runnableCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landingpage);
        ButterKnife.bind(this);
        t1= new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener(){
           @Override
            public void onInit(int status){
               if(status != TextToSpeech.ERROR) {
                   t1.setLanguage(Locale.ENGLISH);
               }
           }
        });
        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                newString = null;
            } else if(extras.containsKey("GREETINGS")){
                newString = extras.getString("GREETINGS");
            } else if(extras.containsKey("FCM_MESSAGE")){
                newString = extras.getString("FCM_MESSAGE");
            }
        } else {
            newString = (String)savedInstanceState.getSerializable("FCM_MESSAGE");
        }

        txvMessage.setText(newString);
        utterId = "createmessage";
        String toSpeak = txvMessage.getText().toString();
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, utterId);
        }
        Handler handler2 = new Handler();

        Runnable runnable3 = new Runnable(){
            @Override
            public void run(){
                msgrem();
            }
        };
        handler2.postDelayed(runnable3, 600000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnableCode);
    }

    public void msgrem(){
        runnableCode = new Runnable() {
        @Override
            public void run(){
            c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("HH");
            String formattedTime = df.format(c.getTime());
            hournow = Integer.parseInt(formattedTime);
            randnumber();
            randSuggestion();
            txvMessage.setText(suggest);
            utterId = "suggestMsg";
            String toSpeak = txvMessage.getText().toString();
            if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, utterId);
            }
            handler.postDelayed(runnableCode, 600000);
        }
        };
        handler.post(runnableCode);
            }

    private void randnumber(){
        int min = 0;
        int max = 4;
        Random rand = new Random();
        randnumber = rand.nextInt(max - min + 1) + min;
    }

    private void randSuggestion(){
        if(randnumber == 0){
            suggest = "Have you tried listening to the Radio?, to listen to the radio, Tap your finger on the Play button then on the screen that pops up tap your finger on the Radio button";
        } else if (randnumber == 1){
            suggest = "Have you tried one of the games?, there is Backgammon, Solitare, Euchre and more, to find the games, tap your finger on the play button and then on the screen that pops up tap your finger on the games button";
        } else if (randnumber == 2){
            suggest = "If you would like to talk to one of the DCVS volunteers? you don't have to wait for us to call you, you can tap your finger on the chat button and on the screen that pops up you can tap one of the call buttons, if you dont get through try one of the other buttons, and if those dont work we are probably busy but will get back to you as soon as we can";
        } else if (randnumber == 4){
            suggest = "I hope you are having a nice day";
        }
    }

}
