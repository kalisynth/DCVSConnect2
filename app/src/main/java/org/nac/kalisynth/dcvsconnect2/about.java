//About Screen with credits and links to NAC and DCVS, ideally have a way to save the sites so that they can access everything while offline

package org.nac.kalisynth.dcvsconnect2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.github.nisrulz.sensey.Sensey;
import com.github.nisrulz.sensey.TouchTypeDetector;
import com.pddstudio.talking.Talk;
import com.pddstudio.talking.model.SpeechObject;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class About extends AppCompatActivity implements Talk.Callback{

    @BindView(R.id.aboutwebview) WebView aWebView;
    String webdest = "http://www.digitalcvs.org/index.php/About/";
    Boolean mListening = false;
    Boolean mOnAboutPage = true;

    int dot = 200;      // Length of a Morse Code "dot" in milliseconds
    int short_gap = 200;    // Length of Gap Between dots/dashes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        aWebView.setWebViewClient(new AboutWebViewClient());
        Talk.init(this, this);
        Talk.getInstance().addSpeechObjects(dcvsObject, appcreditsObject, nactechObject, nacObject);
        startTouchTypeDetection();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // Setup onTouchEvent for detecting type of touch gesture
        Sensey.getInstance().setupDispatchTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    //Opens the DCVS about page
    @OnClick(R.id.aboutdcvsbtn)
    public void aboutdcvsclick(){
        webdest = "http://www.digitalcvs.org/index.php/About/";
        aWebView.loadUrl(webdest);
    }

    //Opens the NACtech about page
    @OnClick(R.id.aboutnactechbtn)
    public void aboutnactechclick(){
        webdest = "http://nactech.org/About/About-nactech/";
        aWebView.loadUrl(webdest);
    }

    //opens the NAC about organisation page
    @OnClick(R.id.aboutnacbtn)
    public void aboutnacclick(){
        webdest = "http://www.nac.org.au/About-us/organisation/";
        aWebView.loadUrl(webdest);
    }

    //Opens HTML file located in the assets folder
    @OnClick(R.id.aboutappbtn)
    public void aboutappclick(){
        aWebView.loadUrl("file:///android_asset/dcvsappcredit.html");
    }

    private class AboutWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView iWebView, String webdest){
            aWebView.loadUrl(webdest);
            return true;
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
        Toast.makeText(About.this, "Sorry I missed that, please try again",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFinishedListening(SpeechObject speechObject) {
        if(speechObject != null) {
            speechObject.onSpeechObjectIdentified();
        }
    }

    private SpeechObject dcvsObject = new SpeechObject(){
        @Override
        public void onSpeechObjectIdentified() {
            if(mOnAboutPage) {
                aboutdcvsclick();
            }
        }

        @Override
        public String getVoiceString() {
            return "digital community visitors service";
        }
    };

    private SpeechObject appcreditsObject = new SpeechObject(){
        @Override
        public void onSpeechObjectIdentified() {
            aboutappclick();
        }

        @Override
        public String getVoiceString() {
            return "credit";
        }
    };

    private SpeechObject nactechObject = new SpeechObject(){
        @Override
        public void onSpeechObjectIdentified() {
            aboutnactechclick();
        }

        @Override
        public String getVoiceString() {
            return "tech";
        }
    };

    private SpeechObject nacObject = new SpeechObject(){
        @Override
        public void onSpeechObjectIdentified() {
            aboutnacclick();
        }

        @Override
        public String getVoiceString() {
            return "nac";
        }
    };

    public void vyes(){
        long[] pattern = {
                0,  // Start immediately
                dot, short_gap, dot, short_gap, dot, short_gap, dot};
        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(pattern, -1);
    }

    public void vno(){
        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(500);
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
                        if (mListening) {
                            vyes();
                            mListening = false;
                        } else {
                            vno();
                            mListening = true;
                        }
                        DCVSOverlayService.startspeaking();
                        break;
                    case TouchTypeDetector.SWIPE_DIR_DOWN:
                        Log.d("Gestures", "Swipe Down");
                        aboutnacclick();
                        break;
                    case TouchTypeDetector.SWIPE_DIR_LEFT:
                        Log.d("Gestures", "Swipe Left");
                        startActivity(new Intent(About.this, Help.class));
                        break;
                    case TouchTypeDetector.SWIPE_DIR_RIGHT:
                        Log.d("Gestures", "Swipe Right");
                        aboutappclick();
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