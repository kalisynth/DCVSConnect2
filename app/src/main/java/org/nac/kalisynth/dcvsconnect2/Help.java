//Help Screen for Tablet Support
//Todo
package org.nac.kalisynth.dcvsconnect2;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nisrulz.sensey.Sensey;
import com.github.nisrulz.sensey.TouchTypeDetector;
import com.pddstudio.talking.Talk;
import com.pddstudio.talking.model.SpeechObject;
import com.vstechlab.easyfonts.EasyFonts;
import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.activities.ConnectionBuddyActivity;
import com.zplesac.connectionbuddy.interfaces.NetworkRequestCheckListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Help extends ConnectionBuddyActivity implements Talk.Callback {
    String fsurl = "http://www.fastsupport.com";

    //Butterknife Bindviews
    @BindView(R.id.gtabtn) Button gtab;
    @BindView(R.id.helpguideIB) Button hgbtn;
    @BindView(R.id.aboutbtn) Button abtn;
    @BindView(R.id.ccheckbtn) Button cbtn;
    @BindView(R.id.SkypeVIB) Button sbtn;
    @BindView(R.id.fastsupportbtn) Button fbtn;
    @BindView(R.id.connectiontypetv) TextView ctv;
    @BindView(R.id.signalstrengthtv) TextView stv;

    boolean mgtai;

    int dot = 200;      // Length of a Morse Code "dot" in milliseconds
    int short_gap = 200;    // Length of Gap Between dots/dashes
    int dash = 400;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        //Bindings and Inits
        ButterKnife.bind(this);
        Talk.init(this, this);

        //Voice Commands Objects
        Talk.getInstance().addSpeechObjects(fsObject, connectinObject, aboutObject, updateskypeObject);

        //Check if Go to assist is installed
        gtainstalled();

        //Set Fonts
        hgbtn.setTypeface(EasyFonts.robotoBlack(this));
        abtn.setTypeface(EasyFonts.robotoBlack(this));
        cbtn.setTypeface(EasyFonts.robotoBlack(this));
        sbtn.setTypeface(EasyFonts.robotoBlack(this));
        fbtn.setTypeface(EasyFonts.robotoBlack(this));

        startTouchTypeDetection();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // Setup onTouchEvent for detecting type of touch gesture
        Sensey.getInstance().setupDispatchTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }
    //Launch DCVS Help Guide
    /*@OnClick(R.id.helpguideIB)
    public void helpguideonclick(){
        Intent helpguideIntent;
        PackageManager helpguideManager = getPackageManager();
        helpguideIntent = helpguideManager.getLaunchIntentForPackage("appinventor.ai_tim.DCVSHelpGuide");
        helpguideIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        startActivity(helpguideIntent);
    }*/

    //open the About screen
    @OnClick(R.id.aboutbtn)
    public void aboutonclick(){
        Intent aboutintent = new Intent(getApplicationContext(), About.class);
        aboutintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(aboutintent);
    }

    //Launch Play Store to check if skype needs updating
    @OnClick(R.id.SkypeVIB)
    public void skypeversionclick(){
        Uri marketUri = Uri.parse("market://details?id=com.skype.raider");
        Intent myIntent = new Intent(Intent.ACTION_VIEW, marketUri);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(myIntent);
    }

    //Launch the Goto Assist app
    @OnClick(R.id.gtabtn)
    public void gtaclick(){
        Intent gotointent;
        PackageManager gotoManager = getPackageManager();
        gotointent = gotoManager.getLaunchIntentForPackage("com.citrix.g2arscustomer");
        gotointent.addCategory(Intent.CATEGORY_LAUNCHER);
        startActivity(gotointent);
        finish();
    }

    //Launch the Fast Support website
    @OnClick(R.id.fastsupportbtn)
    public void fswebclick(){
        Uri guri = Uri.parse(fsurl);
        Intent fswIntent = new Intent(Intent.ACTION_VIEW, guri);
        startActivity(fswIntent);
        finish();
    }

    private boolean isPackageInstalled(String packagename, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public void gtainstalled(){
        PackageManager pm = this.getPackageManager();
        boolean isInstalled = isPackageInstalled("com.citrix.g2arscustomer", pm);
        if(!isInstalled){
            gtab.setVisibility(View.INVISIBLE);
            mgtai = false;
        } else {
            gtab.setTypeface(EasyFonts.robotoBlack(this));
            Talk.getInstance().addSpeechObjects(gtaObject);
            mgtai = true;
        }
    }

    @OnClick(R.id.ccheckbtn)
    public void ccheckbtn(){
            ConnectionBuddy.getInstance().hasNetworkConnection(new NetworkRequestCheckListener() {
                @Override
                public void onResponseObtained() {
                    Toast.makeText(Help.this, "Internet is Working! You are connected by " + ConnectionBuddy.getInstance().getNetworkType() +  " and the signal strength is " + ConnectionBuddy.getInstance().getSignalStrength(), Toast.LENGTH_LONG).show();
                    String connectiontype = String.format(getString(R.string.ConnectionTypeString), ConnectionBuddy.getInstance().getNetworkType());
                    String signalstrength = String.format(getString(R.string.SignalStrengthString), ConnectionBuddy.getInstance().getSignalStrength());
                    ctv.setText(connectiontype);
                    stv.setText(signalstrength);
                    if(signalstrength.equals("EXCELLENT")){
                        excellentStrength();
                    } else if(signalstrength.equals("GOOD")){
                        goodStrength();
                    } else if(signalstrength.equals("POOR")){
                        poorStrength();
                    }
                }

                @Override
                public void onNoResponse() {
                    Toast.makeText(Help.this, "Internet is not Working!", Toast.LENGTH_LONG).show();
                    noSignel();
                }
            });
        }

    //Voice Commands
    @Override
    public void onStartListening() {
        Toast.makeText(this, getResources().getString(R.string.listening), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRmsChanged(float rms) {

    }

    @Override
    public void onFailedListening(int errorCode) {
        Toast.makeText(Help.this, "Sorry I missed that, please repeat",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFinishedListening(SpeechObject speechObject) {
        if(speechObject != null) {
            speechObject.onSpeechObjectIdentified();
        }
    }

    private SpeechObject gtaObject = new SpeechObject(){
        @Override
        public void onSpeechObjectIdentified() {
            gtaclick();
        }

        @Override
        public String getVoiceString() {
            return "Go to Assist";
        }
    };

    private SpeechObject fsObject = new SpeechObject(){
        @Override
        public void onSpeechObjectIdentified() {
            fswebclick();
        }

        @Override
        public String getVoiceString() {
            return "Fast Support";
        }
    };


    private SpeechObject connectinObject = new SpeechObject(){
        @Override
        public void onSpeechObjectIdentified() {
            ccheckbtn();
        }

        @Override
        public String getVoiceString() {
            return "Connection Test";
        }
    };

    private SpeechObject aboutObject = new SpeechObject(){
        @Override
        public void onSpeechObjectIdentified() {
            aboutonclick();
        }

        @Override
        public String getVoiceString() {
            return "About";
        }
    };

    private SpeechObject updateskypeObject = new SpeechObject(){
        @Override
        public void onSpeechObjectIdentified() {
            skypeversionclick();
        }

        @Override
        public String getVoiceString() {
            return "Update Skype";
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
                        skypeversionclick();
                        break;
                    case TouchTypeDetector.SWIPE_DIR_LEFT:
                        Log.d("Gestures","Swipe Left");
                        startActivity(new Intent(Help.this, Home.class));
                        break;
                    case TouchTypeDetector.SWIPE_DIR_RIGHT:
                        Log.d("Gestures", "Swipe Right");
                        if(mgtai){
                            gtaclick();
                        } else {
                            fswebclick();
                        }
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

    public void vyes(){
        long[] pattern = {
                0,  // Start immediately
                dot, short_gap, dot, short_gap, dot};
        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(pattern, -1);
    }

    public void vno(){
        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(500);
    }

    private void excellentStrength(){
        long[] pattern = {
                0,  // Start immediately
                dash, short_gap, dash, short_gap, dash, short_gap, dash, short_gap, dot};
        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(pattern, -1);
    }

    private void goodStrength(){
        long[] pattern = {
                0,  // Start immediately
                dash, short_gap, dash, short_gap, dash, short_gap, dot};
        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(pattern, -1);
    }

    private void poorStrength(){
        long[] pattern = {
                0,  // Start immediately
                dash, short_gap, dash, short_gap, dot};
        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(pattern, -1);
    }

    private void noSignel(){
        long[] pattern = {
                0,  // Start immediately
                dash, short_gap, dot};
        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(pattern, -1);
    }
}