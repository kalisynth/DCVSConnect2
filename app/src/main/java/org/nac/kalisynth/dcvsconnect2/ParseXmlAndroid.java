package org.nac.kalisynth.dcvsconnect2;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.webkit.WebView;
import org.nac.kalisynth.dcvsconnect2.Xml2.Entry;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;

public class ParseXmlAndroid extends AppCompatActivity {

    private static final String URL = "http://tim.nactech.org/skypespeeddial.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse_xml_android);
    }

    @Override
    public void onStart(){
        super.onStart();
        loadPage();
    }

    private void loadPage(){
        new DownloadXmlTask().execute(URL);
    }

    private class DownloadXmlTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                return loadXmlFromNetwork(urls[0]);
            } catch (IOException e) {
                return getResources().getString(R.string.connection_error);
            } catch (XmlPullParserException e) {
                return getResources().getString(R.string.xml_error);
            }
        }

        @Override
        protected void onPostExecute(String result) {
            setContentView(R.layout.activity_parse_xml_android);
            WebView myWebView = (WebView) findViewById(R.id.webview);
            myWebView.loadData(result, "text/html", null);
        }
    }

    private String loadXmlFromNetwork(String urlString) throws XmlPullParserException, IOException {
        InputStream stream = null;
        Xml2 feedXmlParser = new Xml2();
        List<Entry> entries = null;
        String url = null;
        StringBuilder skypedetails = new StringBuilder();
        try {
            stream = this.getResources().openRawResource(R.raw.skypespeeddial);
            entries = feedXmlParser.parse(stream);
        } finally {
            if (stream != null){
                stream.close();
            }
        }

        for (Entry entry : entries) {
            skypedetails.append("Skype Name: ");
            skypedetails.append(entry.sname);
            skypedetails.append(" Person Name: ");
            skypedetails.append(entry.pname);
            skypedetails.append(" Slot: ");
            skypedetails.append(entry.slot);
        }
        return skypedetails.toString();
    }

    private InputStream downloadUrl(String urlString) throws IOException{
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();
        InputStream stream = conn.getInputStream();
        return stream;
    }
}
