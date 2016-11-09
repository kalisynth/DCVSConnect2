package org.nac.kalisynth.dcvsconnect2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebHub extends AppCompatActivity implements Talk.Callback {
    @BindView(R.id.iWebView)
    WebView iWebView;
    String webdest = "http://nac.org.au";
    boolean mListening;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_hub);
        ButterKnife.bind(this);
        Talk.init(this, this);

        mListening = false;

        //Voice Commands
        Talk.getInstance().addSpeechObjects(googleObject, googleMObject, nacObject, seniorsObject, abcObject, sevenObject);
        iWebView.getSettings().setBuiltInZoomControls(true);
        iWebView.setWebViewClient(new CustomWebViewClient());
        startTouchTypeDetection();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // Setup onTouchEvent for detecting type of touch gesture
        Sensey.getInstance().setupDispatchTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    @OnClick(R.id.mapwbtn)
    public void mapsonclick() {
        Intent googlemIntent;
        PackageManager googleManager = getPackageManager();
        googlemIntent = googleManager.getLaunchIntentForPackage("com.google.android.apps.maps");
        googlemIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        startActivity(googlemIntent);
        finish();
    }

    @OnClick(R.id.googlewbtn)
    public void onGoogleClick() {
        webdest = "http://google.com/";
        iWebView.loadUrl(webdest);
    }

    @OnClick(R.id.abcnwbtn)
    public void onABCClick() {
        webdest = "http://www.abc.net.au/news/";
        iWebView.loadUrl(webdest);
    }

    @OnClick(R.id.sevennwbtn)
    public void onSevenClick() {
        webdest = "http://au.news.yahoo.com/";
        iWebView.loadUrl(webdest);
    }

    @OnClick(R.id.nacwbtn)
    public void onNACClick() {
        webdest = "http://www.nac.org.au";
        iWebView.loadUrl(webdest);
    }

    @OnClick(R.id.seniorowbtn)
    public void onSeniorClick() {
        webdest = "http://www.nationalseniors.com.au/";
        iWebView.loadUrl(webdest);
    }

    private class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView iWebView, String webdest) {
            iWebView.loadUrl(webdest);
            return true;
        }
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
                        Log.d("Gestures", "Swipe Left");
                        startActivity(new Intent(WebHub.this, FunHub.class));
                        break;
                    case TouchTypeDetector.SWIPE_DIR_RIGHT:
                        Log.d("Gestures", "Swipe Right");
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
        Toast.makeText(WebHub.this, "Sorry I missed that, please repeat",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFinishedListening(SpeechObject speechObject) {
        if(speechObject != null) {
            speechObject.onSpeechObjectIdentified();
        }
    }

    private SpeechObject googleObject = new SpeechObject(){
        @Override
        public void onSpeechObjectIdentified() {
            onGoogleClick();
            Talk.getInstance().stopListening();
            mListening = false;
        }

        @Override
        public String getVoiceString() {
            return "google";
        }
    };

    private SpeechObject googleMObject = new SpeechObject(){
        @Override
        public void onSpeechObjectIdentified() {
            mapsonclick();
            Talk.getInstance().stopListening();
            mListening = false;
        }

        @Override
        public String getVoiceString() {
            return "maps";
        }
    };

    private SpeechObject nacObject = new SpeechObject(){
        @Override
        public void onSpeechObjectIdentified() {
            onNACClick();
            Talk.getInstance().stopListening();
            mListening = false;
        }

        @Override
        public String getVoiceString() {
            return "NAC Website";
        }
    };

    private SpeechObject seniorsObject = new SpeechObject(){
        @Override
        public void onSpeechObjectIdentified() {
            onSeniorClick();
            Talk.getInstance().stopListening();
            mListening = false;
        }

        @Override
        public String getVoiceString() {
            return "Seniors";
        }
    };

    private SpeechObject abcObject = new SpeechObject(){
        @Override
        public void onSpeechObjectIdentified() {
            onABCClick();
            Talk.getInstance().stopListening();
            mListening = false;
        }

        @Override
        public String getVoiceString() {
            return "abc news";
        }
    };

    private SpeechObject sevenObject = new SpeechObject(){
        @Override
        public void onSpeechObjectIdentified() {
            onSevenClick();
            Talk.getInstance().stopListening();
            mListening = false;
        }

        @Override
        public String getVoiceString() {
            return "seven";
        }
    };
}
