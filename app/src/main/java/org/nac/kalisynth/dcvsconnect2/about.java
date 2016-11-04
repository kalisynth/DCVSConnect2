//About Screen with credits and links to NAC and DCVS, ideally have a way to save the sites so that they can access everything while offline

package org.nac.kalisynth.dcvsconnect2;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.blundell.woody.Woody;
import com.pddstudio.talking.Talk;
import com.pddstudio.talking.model.SpeechObject;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class About extends AppCompatActivity implements Woody.ActivityMonitorListener, Talk.Callback{

    @BindView(R.id.aboutwebview) WebView aWebView;
    String webdest = "http://www.digitalcvs.org/index.php/About/";
    TextToSpeech t1;
    String speech;
    String utterid;
    Boolean mSpeaking = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        aWebView.setWebViewClient(new AboutWebViewClient());
        Woody.onCreateMonitor(this);
        Talk.init(this, this);
        Talk.getInstance().addSpeechObjects(whatObject, homeObject, playObject, chatObject, helpObject, dcvsObject, appcreditsObject, nactechObject, nacObject);
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.ENGLISH);
                }
            }
        });
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
    public void onFaceDetected() {
        if(!t1.isSpeaking()) {
            Talk.getInstance().startListening();
        }else {
            Talk.getInstance().stopListening();
        }
    }

    @Override
    public void onFaceTimedOut() {
        Talk.getInstance().stopListening();
        t1.stop();
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
        Toast.makeText(About.this, "Sorry I missed that, error " + errorCode + " please repeat",Toast.LENGTH_LONG).show();
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

    private SpeechObject playObject = new SpeechObject(){
        @Override
        public void onSpeechObjectIdentified() {
            Intent chatIntent = new Intent(getApplicationContext(), FunHub.class);
            chatIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(chatIntent);
            Talk.getInstance().stopListening();
        }

        @Override
        public String getVoiceString() {
            return "play";
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

    private SpeechObject dcvsObject = new SpeechObject(){
        @Override
        public void onSpeechObjectIdentified() {
            speech = "The Digital Community Visitors Service, is a new program that extends the CVS service to offer its benefits to Clients who are typically living in their own homes and particularly those who are socially or geographically isolated. ";
            t1.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
        }

        @Override
        public String getVoiceString() {
            return "DCVS";
        }
    };

    private SpeechObject appcreditsObject = new SpeechObject(){
        @Override
        public void onSpeechObjectIdentified() {
            speech = "Credits, Developers, Developer and Designer - Tim Hooper, Designer - Sarah Xu, Libraries Used, Radio Player Service, Butter knife, Easy Fonts, ConnectionBuddy, Sound Effects, Button Press - Marianne Gagnon";
            t1.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
        }

        @Override
        public String getVoiceString() {
            return "credit";
        }
    };

    private SpeechObject nactechObject = new SpeechObject(){
        @Override
        public void onSpeechObjectIdentified() {
            speech = "NACtech is a division of NAC, the Nundah Activity Centre, and was started to support the DCVS as it grew. NACtech has now become a service in its own right.";
            t1.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
        }

        @Override
        public String getVoiceString() {
            return "tech";
        }
    };

    private SpeechObject nacObject = new SpeechObject(){
        @Override
        public void onSpeechObjectIdentified() {
            speech = "Nundah Activity Centre is a Not-for-Profit Incorporated Community Organisation. It is dedicated to enriching the lives of everyone in the community by providing services and recreational needs that enhance and energise the lives of our members and clients with dignity, compassion and commitment. Nundah Activity Centre provides a range of services to assist their clients to remain active and independent in their home and community.";
            t1.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
        }

        @Override
        public String getVoiceString() {
            return "nac";
        }
    };

    private SpeechObject whatObject = new SpeechObject(){
        @Override
        public void onSpeechObjectIdentified() {
            speech = "You are on the About Screen, here you can find information about the DCVS, by saying DCVS, about NAC, by saying NAC, about NACTECH by saying Tech and finally about the app by saying Credit, you can also go back to Home by saying Home, open the Chat screen by saying Chat, open the Play screen by saying Play or the Help screen by saying help";
                t1.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
                mSpeaking = true;
        }

        @Override
        public String getVoiceString() {
            return "what";
        }
    };
}