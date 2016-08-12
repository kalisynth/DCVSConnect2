package org.nac.kalisynth.dcvsconnect2;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class DCVSChat extends AppCompatActivity {

    //for the speed dial testing
    public static final String ChatPrefences = "ChatPrefs";

    public String contactlist = "DCVS";

    //Speed Dial 1
    public static String sd1sname = null;
    public static String sd1pname = null;
    public static boolean sd1c = false;

    //Speed Dial 2
    public static String sd2sname = null;
    public static String sd2pname = null;
    public static boolean sd2c = false;

    //Speed Dial 3
    public static String sd3sname = null;
    public static String sd3pname = null;
    public static boolean sd3c = false;

    //Speed Dial 4
    public static String sd4sname = null;
    public static String sd4pname = null;
    public static boolean sd4c = false;

    //Speed Dial 5
    public static String sd5sname = null;
    public static String sd5pname = null;
    public static boolean sd5c = false;

    //Speed Dial 6
    public static String sd6sname = null;
    public static String sd6pname = null;
    public boolean sd6c = false;

    //Speed Dial 7
    public static String sd7sname = null;
    public static String sd7pname = null;
    public boolean sd7c = false;

    //Speed Dial 8
    public static String sd8sname = null;
    public static String sd8pname = null;
    public boolean sd8c = false;

    //Speed Dial 9
    public static String sd9sname = null;
    public static String sd9pname = null;
    public boolean sd9c = false;
    //SharedPreferences sharedPref = getPreferences(MODE_PRIVATE);

    private static final String URL = "http://tim.nactech.org/skypespeeddial.xml";

    Button s1;
    Button s2;
    Button s3;
    Button s4;

    boolean EditEnabled = false;
    //Check Skype is installed
    private boolean isSkypeClientInstalled(Context skypeCall) {
        PackageManager myPackageMgr = skypeCall.getPackageManager();
        try {
            myPackageMgr.getPackageInfo("com.skype.raider", PackageManager.GET_ACTIVITIES);
        }
        catch (PackageManager.NameNotFoundException e) {
            return (false);
        }
        return (true);
    }

    private void goToMarket(Context skypeinstalled) {
        Uri marketUri = Uri.parse("market://details?id=com.skype.raider");
        Intent myIntent = new Intent(Intent.ACTION_VIEW, marketUri);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        skypeinstalled.startActivity(myIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dcvschat);
        String skypename = getName();
        TextView stext = (TextView)findViewById(R.id.skypename);
        if (skypename != null) {
            stext.setText("Your Skype Name is " + skypename);
        }
    }
    //Open Skype
    public void skypeonclick(View v){
        DCVSOverlayService.DCVSView.addView(DCVSOverlayService.chatButton);
        DCVSOverlayService.chatv = true;
        Intent skypeIntent;
        PackageManager skypeManager = getPackageManager();
        skypeIntent = skypeManager.getLaunchIntentForPackage("com.skype.raider");
        skypeIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        startActivity(skypeIntent);
        finish();
    }

    //Use skype to call a username based on method called
    private void skypedcvs1call(Context skypeCalldcvsv1, String mySkypeDCVSv1){
        if (!isSkypeClientInstalled(skypeCalldcvsv1)) {
            goToMarket(skypeCalldcvsv1);
            return;
        }

        Uri skypeURI = Uri.parse(mySkypeDCVSv1);
        Intent skypedcvs1Intent = new Intent(Intent.ACTION_VIEW, skypeURI);
        skypedcvs1Intent.setComponent(new ComponentName("com.skype.raider", "com.skype.raider.Main"));
        skypedcvs1Intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        skypeCalldcvsv1.startActivity(skypedcvs1Intent);

    }

    //Speed Dial 1
    public void speeddial1onclick(View v){
        //skypedcvs1call(this, "skype:volunteer1dcvs?call&video=true");
        SpeedDialOne();
                        }

    public void speeddial2onclick(View v){
        //skypedcvs1call(this, "skype:volunteer2dcvs?call&video=true");
        SpeedDialTwo();
    }

    public void speeddial3onclick(View v){
        //skypedcvs1call(this, "skype:dcvsoffice?call&video=true");
        SpeedDialThree();
    }

    public void speeddial4onclick(View v){
        //skypedcvs1call(this, "skype:dcvsoffice?call&video=true");
        SpeedDialFour();
    }

    public void skypehelpdeskonclick(View v){
        skypedcvs1call(this, "skype:helpdeskdcvs?call&video=true");
    }

    private void SpeedDialOne() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder
                .setMessage("Are you sure?")
                .setPositiveButton("Yes",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Yes-code
                        skypedcvs1call(DCVSChat.this, "skype:volunteer1dcvs?call&video=true");
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    private void SpeedDialTwo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder
                .setMessage("Are you sure?")
                .setPositiveButton("Yes",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Yes-code
                        skypedcvs1call(DCVSChat.this,"skype:volunteer2dcvs?call&video=true");
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    private void SpeedDialThree() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder
                .setMessage("Are you sure?")
                .setPositiveButton("Yes",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Yes-code
                        skypedcvs1call(DCVSChat.this,"skype:volunteer3dcvs?call&video=true");
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    private void SpeedDialFour() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder
                .setMessage("Are you sure?")
                .setPositiveButton("Yes",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Yes-code
                        skypedcvs1call(DCVSChat.this,"skype:officedcvs?call&video=true");
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    public void SpeedDialFive() {

        //if (!EditEnabled){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
            //String speeddial5number = getResources().getString(speeddial5skype);

        builder
                .setMessage("Are you sure?")
                .setPositiveButton("Yes",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Yes-code
                        skypedcvs1call(DCVSChat.this,"skype:" + sd5sname + "?call&video=true");
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    private void SpeedDialSix() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder
                .setMessage("Are you sure?")
                .setPositiveButton("Yes",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Yes-code
                        skypedcvs1call(DCVSChat.this,"skype:volunteer1dcvs?call&video=true");
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    private void SpeedDialSeven() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder
                .setMessage("Are you sure?")
                .setPositiveButton("Yes",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Yes-code
                        skypedcvs1call(DCVSChat.this,"skype:" + sd7sname + "?call&video=true");
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    private void SpeedDialEight() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder
                .setMessage("Are you sure?")
                .setPositiveButton("Yes",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Yes-code
                        skypedcvs1call(DCVSChat.this,"skype:" + sd8sname + "?call&video=true");
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                })
                .show();
    }


    private static Account getAccount(AccountManager accountManager){
        Account[] accounts = accountManager.getAccountsByType("com.google");
        Account account;
        if (accounts.length > 0){
            account = accounts[0];
        } else {
            account = null;
        }
        return account;
    }

    private String getName(){
        Account account = getAccount(AccountManager.get(this));
        String accountName = account.name;
        String fullName = accountName.substring(0,accountName.lastIndexOf("@"));
        return fullName;
    }

    //Todo https://drive.google.com/file/d/0B4EdgIslSa6EYVlHOGgzekZCbkk/view?usp=sharing

    /*private class DownloadXmlTask extends AsyncTask<String, Void, String> {

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
           *//* setContentView(R.layout.activity_parse_xml_android);
            WebView myWebView = (WebView) findViewById(R.id.webview);
            myWebView.loadData(result, "text/html", null);*//*
            TextView contactlist = (TextView)findViewById(R.id.SkypeList);
            contactlist.setText(result);
            if(sd1c){
                ImageButton spbtn = (ImageButton)findViewById(R.id.s1);
                spbtn.setOnClickListener(new ImageButton.OnClickListener(){
                    public void onClick(View v){
                        SpeedDialOne();
                    }
                });
            }
            if(sd2c){
                ImageButton spbtn = (ImageButton)findViewById(R.id.s2);
                spbtn.setOnClickListener(new ImageButton.OnClickListener(){
                    public void onClick(View v){
                        SpeedDialTwo();
                    }
                });
            }
            if(sd3c){
                ImageButton spbtn = (ImageButton)findViewById(R.id.s3);
                spbtn.setOnClickListener(new ImageButton.OnClickListener(){
                    public void onClick(View v){
                        SpeedDialThree();
                    }
                });
            }
            if(sd4c){
                ImageButton spbtn = (ImageButton)findViewById(R.id.s4);
                spbtn.setOnClickListener(new ImageButton.OnClickListener(){
                    public void onClick(View v){
                        SpeedDialFour();
                    }
                });
            }
            if(sd5c){
                ImageButton spbtn = (ImageButton)findViewById(R.id.s5);
                spbtn.setOnClickListener(new ImageButton.OnClickListener(){
                    public void onClick(View v){
                        SpeedDialFive();
                    }
                });
            }
            if (sd6c){
                ImageButton spbtn = (ImageButton)findViewById(R.id.s6);
                spbtn.setOnClickListener(new ImageButton.OnClickListener(){
                    public void onClick(View v){
                        SpeedDialSix();
                    }
                });
            }
            if (sd7c){
                ImageButton spbtn = (ImageButton)findViewById(R.id.s7);
                spbtn.setOnClickListener(new ImageButton.OnClickListener(){
                    public void onClick(View v){
                        SpeedDialSeven();
                    }
                });
            }
            if (sd8c){
                ImageButton spbtn = (ImageButton)findViewById(R.id.s4);
                spbtn.setOnClickListener(new ImageButton.OnClickListener(){
                    public void onClick(View v){
                        SpeedDialEight();
                    }
                });
            }
        }
    }

    private String loadXmlFromNetwork(String urlString) throws XmlPullParserException, IOException {
        InputStream stream = null;
        Xml2 feedXmlParser = new Xml2();
        List<Xml2.Entry> entries = null;
        String url = null;
        StringBuilder skypedetails = new StringBuilder();
        try {
            if(contactlist.equals("DCVS")) {
                stream = this.getResources().openRawResource(R.raw.dcvsnumbers);
            } else if(contactlist.equals("FF")){
                stream = this.getResources().openRawResource(R.raw.friendsandfamily);
            } else if(contactlist.equals("SG")){
                stream = this.getResources().openRawResource(R.raw.socialgroup);
            }
            entries = feedXmlParser.parse(stream);
        } finally {
            if (stream != null){
                stream.close();
            }
        }

        for (Xml2.Entry entry : entries) {
            skypedetails.append(entry.slot);
            skypedetails.append(" ");
            skypedetails.append(entry.pname);
            skypedetails.append("\n");

            if(entry.slot.equals("1")){
                sd1sname = entry.sname;
                sd1pname = entry.pname;
                sd1c = true;
            }
            if(entry.slot.equals("2")){
                sd2sname = entry.sname;
                sd2pname = entry.pname;
                sd2c = true;
            }
            if(entry.slot.equals("3")){
                sd3sname = entry.sname;
                sd3pname = entry.pname;
                sd3c = true;
            }
            if(entry.slot.equals("4")){
                sd4sname = entry.sname;
                sd4pname = entry.pname;
                sd4c = true;
            }
            if(entry.slot.equals("5")){
                sd5sname = entry.sname;
                sd5pname = entry.pname;
                sd5c = true;
            }
            if(entry.slot.equals("6")){
                sd6sname = entry.sname;
                sd6pname = entry.pname;
                sd6c = true;
            }
            if(entry.slot.equals("7")){
                sd7sname = entry.sname;
                sd7pname = entry.pname;
                sd7c = true;
            }
            if(entry.slot.equals("8")){
                sd8sname = entry.sname;
                sd8pname = entry.pname;
                sd8c = true;
            }
            if(entry.slot.equals("9")){
                sd9sname = entry.sname;
                sd9pname = entry.pname;
                sd9c = true;
            }
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

    public void updatexml(){
        new DownloadXmlTask().execute(URL);
    }

    public void onDCVSCClick(View v){
        contactlist = "DCVS";
        updatexml();
    }

    public void onFFCClick(View v){
        contactlist = "FF";
        updatexml();
    }

    public void onSGClick(View v){
        contactlist = "SG";
        updatexml();
    }*/
}
