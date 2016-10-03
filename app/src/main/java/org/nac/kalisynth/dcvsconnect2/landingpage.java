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
    }

    @Override
    public void onFaceDetected(){
            c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("HH");
            String formattedTime = df.format(c.getTime());
            hournow = Integer.parseInt(formattedTime);
            greeted = true;
            if (hournow < 12) {
                txvMessage.setText("Hello, Good Morning, How are you?");
                utterId = "hellomorning";
                String toSpeak = txvMessage.getText().toString();
                if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                }
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, utterId);
                }
            } else {
                txvMessage.setText("Hello, Good Afternoon, How are you?");
                utterId = "helloafternoon";
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
