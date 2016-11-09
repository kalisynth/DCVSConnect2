package org.nac.kalisynth.dcvsconnect2;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import com.github.nisrulz.sensey.Sensey;
import com.github.nisrulz.sensey.TouchTypeDetector;
import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.cache.ConnectionBuddyCache;
import com.zplesac.connectionbuddy.interfaces.ConnectivityChangeListener;
import com.zplesac.connectionbuddy.models.ConnectivityEvent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Home extends AppCompatActivity implements ConnectivityChangeListener {

    //Butterknife
    @BindView(R.id.messageboxtext)
    TextView txvMessage;

    //Variables
    int hournow = 0;
    Calendar c;
    String newString;
    int randnumber = 0;
    String suggest = null;
    Handler handler = new Handler();
    private Runnable runnableCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Inits and Bindings
        ButterKnife.bind(this);
        Sensey.getInstance().init(Home.this);

        //Custom Liberation Sans Font
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/libser.ttf");
        txvMessage.setTypeface(custom_font);

        //Checking for messages from Firebase / Setting the Greeting Message
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                newString = null;
            } else if (extras.containsKey("GREETINGS")) {
                newString = extras.getString("GREETINGS");
            } else if (extras.containsKey("FCM_MESSAGE")) {
                newString = extras.getString("FCM_MESSAGE");
            }
        } else {
            newString = (String) savedInstanceState.getSerializable("FCM_MESSAGE");
        }

        //Clearing the last connection tests
        if (savedInstanceState != null) {
            ConnectionBuddyCache.clearLastNetworkState(this);
        }

        //Set text box to new message
        txvMessage.setText(newString);

        //set timer for message change
        Handler handler2 = new Handler();
        Runnable runnable3 = new Runnable() {
            @Override
            public void run() {
                msgrem();
            }
        };
        handler2.postDelayed(runnable3, 600000);

        startTouchTypeDetection();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // Setup onTouchEvent for detecting type of touch gesture
        Sensey.getInstance().setupDispatchTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(runnableCode);
        Sensey.getInstance().stopTouchTypeDetection();
        super.onDestroy();
    }

    public void msgrem() {
        runnableCode = new Runnable() {
            @Override
            public void run() {
                c = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("HH");
                String formattedTime = df.format(c.getTime());
                hournow = Integer.parseInt(formattedTime);
                randnumber();
                randSuggestion();
                txvMessage.setText(suggest);
            /*utterId = "suggestMsg";
            String toSpeak = txvMessage.getText().toString();
            if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, utterId);
            }*/
                handler.postDelayed(runnableCode, 600000);
            }
        };
        handler.post(runnableCode);
    }

    private void randnumber() {
        int min = 0;
        int max = 6;
        Random rand = new Random();
        randnumber = rand.nextInt(max - min + 1) + min;
    }

    private void randSuggestion() {
        if (randnumber == 0) {
            suggest = "Have you tried listening to the Radio?, to listen to the radio, Tap your finger on the Play button then on the screen that pops up tap your finger on the Radio button";
        } else if (randnumber == 1) {
            suggest = "Have you tried one of the games?, there is Backgammon, Solitare, Euchre and more, to find the games, tap your finger on the play button and then on the screen that pops up tap your finger on the games button";
        } else if (randnumber == 2) {
            suggest = "We broadcast from the NAC every thursday, watch the videos by tapping your finger on the Play button then on the NAC BROADCASTS button";
        } else if (randnumber == 4) {
            suggest = "I hope you are having a nice day";
        } else if (randnumber == 5) {
            suggest = "if you are having issues with the tablet, its possible that restarting the device could fix this issue, to restart hold down the power button located if you can see the word samsung on the tablet, the power button is on the edge of the top left of the tablet, hold it down till you see a window pop up and then select the power off or restart options";
        } else if(randnumber == 6) {
            suggest = "You can navigate this app by gesture and voice rather then by buttons for example swipe your finger across the screen away from the word SAMSUNG and you will open the chat window";
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Omit the default configuration - we want to obtain the current network connection state
        // after we register for network connectivity events.
        ConnectionBuddy.getInstance().registerForConnectivityEvents(this, true, this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ConnectionBuddy.getInstance().unregisterFromConnectivityEvents(this);
    }

    @Override
    public void onConnectionChange(ConnectivityEvent event) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_stat_connected);
        mBuilder.setContentTitle("Connection");
        mBuilder.setContentText("Connection Status " + event.getState() + " Type: " + event.getType() + " Strength: " + event.getStrength());
        NotificationManager mNotificationmanager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationmanager.notify(3, mBuilder.build());
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
                        startActivity(new Intent(Home.this, ChatHub.class));
                        break;
                    case TouchTypeDetector.SWIPE_DIR_LEFT:
                        Log.d("Gestures","Swipe Left");
                        startActivity(new Intent(Home.this, Help.class));
                        break;
                    case TouchTypeDetector.SWIPE_DIR_RIGHT:
                        Log.d("Gestures", "Swipe Right");
                        startActivity(new Intent(Home.this, FunHub.class));
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