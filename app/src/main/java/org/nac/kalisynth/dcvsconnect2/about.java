//About Screen with credits and links to NAC and DCVS

package org.nac.kalisynth.dcvsconnect2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class About extends AppCompatActivity {

    @BindView(R.id.aboutwebview) WebView aWebView;
    String webdest = "http://www.digitalcvs.org/index.php/About/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        aWebView.setWebViewClient(new AboutWebViewClient());
    }

    @OnClick(R.id.aboutdcvsbtn)
    public void aboutdcvsclick(){
        webdest = "http://www.digitalcvs.org/index.php/About/";
        aWebView.loadUrl(webdest);
    }

    @OnClick(R.id.aboutnactechbtn)
    public void aboutnactechclick(){
        webdest = "http://nactech.org/About/About-nactech/";
        aWebView.loadUrl(webdest);
    }

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
}