package org.nac.kalisynth.dcvsconnect2;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import com.blundell.woody.Woody;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class landingpage extends AppCompatActivity implements Woody.ActivityMonitorListener {
    @BindView(R.id.messageboxtext) TextView txvMessage;

    int hournow = 0;
    Calendar c;
    Boolean greeted, farewelled = false;
    TextToSpeech t1;
    String utterId = null;
    String newString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landingpage);
        Woody.onCreateMonitor(this);
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
            } else {
                newString = extras.getString("FCM_MESSAGE");
            }
        } else {
            newString = (String)savedInstanceState.getSerializable("FCM_MESSAGE");
        }
    }

    @Override
    public void onFaceDetected(){
            c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("HH");
            String formattedTime = df.format(c.getTime());
            hournow = Integer.parseInt(formattedTime);
            if (hournow < 12 && !greeted) {
                txvMessage.setText("Hello, Good Morning, How are you?");
                utterId = "hellomorning";
                greeted = true;
                String toSpeak = txvMessage.getText().toString();
                if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                }
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, utterId);
                }
            } else if(hournow >= 12 && !greeted) {
                txvMessage.setText("Hello, Good Afternoon, How are you?");
                utterId = "helloafternoon";
                greeted = true;
                String toSpeak = txvMessage.getText().toString();
                if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                }
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, utterId);
                }
            } else if(greeted){
                txvMessage.setText(newString);
                utterId = "fcmmsg";
                String toSpeak = txvMessage.getText().toString();
                if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                }
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, utterId);
                }
            }
        }

    @Override
    public void onFaceTimedOut() {
            txvMessage.setText("So long, and thanks for all the fish");
            utterId = "bye";
            String toSpeak = txvMessage.getText().toString();
            if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, utterId);
            }
        }

    @Override
    public void onFaceDetectionNonRecoverableError() {

    }

}
