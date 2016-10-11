package org.nac.kalisynth.dcvsconnect2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Dcvsinternet extends AppCompatActivity {
    @BindView(R.id.iWebView) WebView iWebView;
    String webdest = "http://nac.org.au";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dcvsinternet);
        ButterKnife.bind(this);
        iWebView.getSettings().setJavaScriptEnabled(true);
        iWebView.getSettings().setBuiltInZoomControls(true);
        iWebView.setWebViewClient(new CustomWebViewClient());
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
        public boolean shouldOverrideUrlLoading(WebView iWebView, String webdest){
            iWebView.loadUrl(webdest);
            return true;
        }
    }
}
