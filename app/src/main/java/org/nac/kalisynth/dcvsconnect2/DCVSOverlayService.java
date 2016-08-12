package org.nac.kalisynth.dcvsconnect2;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class DCVSOverlayService extends Service {
    //Main Layout View
    public static LinearLayout DCVSView;
    //public static RelativeLayout reminderview;

    private static final String TAG = DCVSOverlayService.class.getSimpleName();

    //Layout Params
    private WindowManager.LayoutParams params;
    //private WindowManager.LayoutParams rparams;
    private LinearLayout.LayoutParams params_home;
    private LinearLayout.LayoutParams params_chat;
    private LinearLayout.LayoutParams params_fun;
    private LinearLayout.LayoutParams params_help;
    private LinearLayout.LayoutParams params_show;
    //private RelativeLayout.LayoutParams params_reminder;
    private LinearLayout.LayoutParams params_reboot;

    //Window Manager
    private WindowManager wm;
    //private WindowManager rwm;

    //Booleans for if button is visible
    public static Boolean chatv = true;
    private Boolean helpv = true;
    public static Boolean funv = true;
    private Boolean homev = false;
    private Boolean showv = true;
    /*private Boolean reminderv = false;
    private String reminders = "false";*/

    //Buttons
    public static Button chatButton;
    public static Button funButton;
    private static Button helpButton;
    private static Button homeButton;
    private static Button showButton;
    //private TextView remindertxt;


    public static String bv;

    public final static int REQUEST_CODE = -1010101;
    private String URL = null;

    MediaPlayer mp = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.RadioStation.equals("DCVS")) {
            bv = getResources().getString(R.string.BVDCVS);
        } else if (BuildConfig.RadioStation.equals("Local")) {
            bv = getResources().getString(R.string.BVLocal);
        } else {
            bv = getResources().getString(R.string.BVWifi);
        }

        String dn = getName();
        //URL = "http://tim.nactech.org/skypespeeddial.xml";
        URL = "http://tim.nactech.org/" + dn + "/calender.xml";
        Log.d(TAG, URL);

        //Linear Layout
        DCVSView = new LinearLayout(this);
        DCVSView.setBackgroundColor(0x00D4FF00);
        DCVSView.setOrientation(LinearLayout.VERTICAL);

        //Relative Layout
        //RDCVSView = new RelativeLayout(this);
        //reminderview = new RelativeLayout(this);

        //Home Button style
        homeButton = new Button(this);
        homeButton.setBackground(ContextCompat.getDrawable(this, R.drawable.homesml));
        //Home Button Layout
        params_home = new LinearLayout.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        );
        params_home.setMargins(5, 0, 0, 5);

        //Chat Button Style
        chatButton = new Button(this);
        chatButton.setBackground(ContextCompat.getDrawable(this, R.drawable.chatbig));
        //Chat Button Layout
        params_chat = new LinearLayout.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        );
        params_chat.setMargins(5, 0, 0, 5);
        //adds chat button to the layout
        DCVSView.addView(chatButton, params_chat);

        //Fun Button Style
        funButton = new Button(this);
        funButton.setBackground(ContextCompat.getDrawable(this, R.drawable.funbig));
        //Fun Button Layout
        params_fun = new LinearLayout.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        );
        params_fun.setMargins(5, 0, 0, 5);
        //adds fun button to the layout
        DCVSView.addView(funButton, params_fun);

        //Help Button Style
        helpButton = new Button(this);
        helpButton.setBackground(ContextCompat.getDrawable(this, R.drawable.helpbig));
        //Help Button Layout
        params_help = new LinearLayout.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        );
        params_help.setMargins(5, 0, 0, 5);
        //adds help button to the layout
        DCVSView.addView(helpButton, params_help);

        showButton = new Button(this);
        showButton.setBackground(ContextCompat.getDrawable(this, R.drawable.show));
        params_show = new LinearLayout.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        DCVSView.addView(showButton, params_show);

       /* remindertxt = new TextView(this);
        params_reminder = new RelativeLayout.LayoutParams(
                500,
                500
        );

        remindertxt.setGravity(Gravity.CENTER);
        params_reminder.addRule(RelativeLayout.CENTER_IN_PARENT);
        reminderview.addView(remindertxt, params_reminder);*/

        //Window Manager for the Layout
        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT);
        //Window Manager Layout Style
        params.gravity = Gravity.END | Gravity.CENTER_HORIZONTAL;

        /*rparams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT);
        rparams.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;*/

        wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        //rwm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.addView(DCVSView, params);
        //rwm.addView(reminderview, rparams);

        //showreminder();

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttoncheck();
                smallbuttons();
                helpv = false;
                DCVSView.removeView(helpButton);
                gohelp();
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttoncheck();
                bigbuttons();
                //showreminder();
                homev = false;
                //reminderv = true;
                DCVSView.removeView(homeButton);
                goHome();
            }
        });

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttoncheck();
                smallbuttons();
                DCVSView.removeView(chatButton);
                //removereminder();
                chatv = false;
                goChat();
            }
        });

        funButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttoncheck();
                smallbuttons();
                funv = false;
                //removereminder();
                DCVSView.removeView(funButton);
                goFun();
            }
        });

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showv) {
                    Hide();
                } else if (!showv) {
                    Show();
                }
            }
        });
        DCVSAppOnNote();
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_CODE){

        }
    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (DCVSView != null) {
            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            wm.removeView(DCVSView);
            NotificationManager cNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            cNotificationManager.cancel(2);
            cNotificationManager.cancel(1);
        }
        /*if (reminderview != null) {
            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            wm.removeView(reminderview);
        }*/
    }

    public void gohelp() {
        //Button funButton = (Button) findViewById(R.id.funbtnid);
        ConnectionCheck();
        buttonclicksound();
            /*Intent helpIntent =  new Intent(Intent.ACTION_VIEW);
            helpIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            helpIntent.setClassName("org.nac.kalisynth.dcvsconnect2", "org.nac.kalisynth.dcvsconnect2.DCVSHelp");*/
        Intent helpIntent = new Intent(getApplicationContext(), org.nac.kalisynth.dcvsconnect2.DCVSHelp.class);
        helpIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(helpIntent);
    }

    public void goHome() {
        //Home Intent
        ConnectionCheck();
        buttonclicksound();
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeIntent);
    }

    public void goFun() {
        //Fun intent
        ConnectionCheck();
        buttonclicksound();
        /*Intent funIntent =  new Intent(Intent.ACTION_VIEW);
        funIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        funIntent.setClassName("org.nac.kalisynth.dcvsconnect2", "org.nac.kalisynth.dcvsconnect2.Dcvsfun");*/
        Intent funIntent = new Intent(getApplicationContext(), org.nac.kalisynth.dcvsconnect2.Dcvsfun.class);
        funIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(funIntent);
    }

    public void goChat() {
        //chat intent
        ConnectionCheck();
        buttonclicksound();
        Intent chatIntent = new Intent(getApplicationContext(), org.nac.kalisynth.dcvsconnect2.DCVSChat.class);
        chatIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        /*Intent chatIntent =  new Intent(Intent.ACTION_VIEW);
        chatIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        chatIntent.setClassName("org.nac.kalisynth.dcvsconnect2", "org.nac.kalisynth.dcvsconnect2.DCVSChat");*/
        startActivity(chatIntent);
    }

    public void Show() {
        if (homev) {
            DCVSView.addView(homeButton, params_home);
        }
        if (chatv) {
            DCVSView.addView(chatButton, params_chat);
        }
        if (helpv) {
            DCVSView.addView(helpButton, params_help);
        }
        if (funv) {
            DCVSView.addView(funButton, params_fun);
        }
        /*if (reminderv) {
            showreminder();
        }*/
        showv = true;
        showButton.setBackground(ContextCompat.getDrawable(DCVSOverlayService.this, R.drawable.show));
        //Log.d(TAG, reminders);
    }

    public void Hide() {
        DCVSView.removeView(homeButton);
        DCVSView.removeView(chatButton);
        DCVSView.removeView(funButton);
        DCVSView.removeView(helpButton);
        //removereminder();
        showv = false;
        //Log.d(TAG, reminders);
        showButton.setBackground(ContextCompat.getDrawable(DCVSOverlayService.this, R.drawable.hide));
    }

    public void ConnectionCheck() {
        if (BuildConfig.RadioStation.equals("Wifi")) {
            WifiNotification();
        } else {
            ConnectivityManager check = (ConnectivityManager)
                    this.getSystemService(Context.CONNECTIVITY_SERVICE);

            //Network[] info = check.getAllNetworks();
            //NetworkInfo[] info = check.getAllNetworkInfo();

            if (check.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || check.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED || check.getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null) {
                OnlineInternetNotification();
            } else
                OfflineNotification();
        }
    }

    public void connectioncheck2() {
        ConnectivityManager connection = (ConnectivityManager)
                this.getSystemService(this.CONNECTIVITY_SERVICE);
        if (connection != null) {
            NetworkInfo[] info = connection.getAllNetworkInfo();
        }
    }

    public void OnlineInternetNotification() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.connected);
        mBuilder.setContentTitle("Connection");
        mBuilder.setContentText("Internet is Working");
        mBuilder.setOngoing(true);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
        //Online Notification
    }

    public void OfflineNotification() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.disconnected);
        mBuilder.setContentTitle("Connection");
        mBuilder.setContentText("Internet is Not Working");
        mBuilder.setOngoing(true);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
        //Offline Notification Draw
    }

    public void WifiNotification() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.wificonnected);
        mBuilder.setContentTitle("Wifi");
        mBuilder.setContentText("This is a Wifi Only Tablet");
        mBuilder.setOngoing(true);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }

    public void DCVSAppOnNote() {
        String dn = getName();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_stat_dcson);
        mBuilder.setContentTitle("DCVSConnect");
        mBuilder.setContentText("DCVS Connect is Online, " + bv + getResources().getString(R.string.CurrentVersion) + " Tablet Name: " + dn);
        mBuilder.setOngoing(true);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(2, mBuilder.build());
        ConnectionCheck();
        //shows the app is running
    }

    //make the buttons bigger
    public void bigbuttons() {
        //delay so change to big buttons is smoother
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                chatButton.setBackground(ContextCompat.getDrawable(DCVSOverlayService.this, R.drawable.chatbig));
                funButton.setBackground(ContextCompat.getDrawable(DCVSOverlayService.this, R.drawable.funbig));
                helpButton.setBackground(ContextCompat.getDrawable(DCVSOverlayService.this, R.drawable.helpbig));

                params.gravity = Gravity.END | Gravity.CENTER_HORIZONTAL;
                DCVSView.setOrientation(LinearLayout.VERTICAL);
                wm.updateViewLayout(DCVSView, params);
            }
        }, 500);
    }

    //make buttons smaller
    public void smallbuttons() {
        chatButton.setBackground(ContextCompat.getDrawable(DCVSOverlayService.this, R.drawable.chatsml));
        helpButton.setBackground(ContextCompat.getDrawable(DCVSOverlayService.this, R.drawable.helpsml));
        funButton.setBackground(ContextCompat.getDrawable(DCVSOverlayService.this, R.drawable.funsml));
        params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        DCVSView.setOrientation(LinearLayout.HORIZONTAL);
        wm.updateViewLayout(DCVSView, params);
    }

    //button visibility control
    public void buttoncheck() {
        if (!homev) {
            DCVSView.addView(homeButton, params_home);
            homev = true;
        } else if (!funv) {
            DCVSView.addView(funButton, params_fun);
            funv = true;
        } else if (!chatv) {
            DCVSView.addView(chatButton, params_chat);
            chatv = true;
        } else if (!helpv) {
            DCVSView.addView(helpButton, params_help);
            helpv = true;
        } /*else if (!reminderv) {
            reminderview.addView(remindertxt, params_reminder);
            reminderv = true;
            reminders = "true";
        }*/ else {

        }
    }

    public static Account getAccount(AccountManager accountManager) {
        Account[] accounts = accountManager.getAccountsByType("com.google");
        Account account;
        if (accounts.length > 0) {
            account = accounts[0];
        } else {
            account = null;
        }
        return account;
    }

    public String getName() {
        Account account = getAccount(AccountManager.get(this));
        String accountName = account.name;
        String fullName = accountName.substring(0, accountName.lastIndexOf("@"));
        return fullName;
    }

    public void buttonclicksound() {
        mp = MediaPlayer.create(this, R.raw.btnpush);
        mp.start();
    }

    /*@TargetApi(23)
    public void checkDrawOverlayPermission(){
        if(!Settings.canDrawOverlays(this)){
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, REQUEST_CODE);
        }
    }*/

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
            *//*setContentView(R.layout.activity_parse_xml_android);
            WebView myWebView = (WebView) findViewById(R.id.webview);
            myWebView.loadData(result, "text/html", null);*//*
            remindertxt.setText(result);
            remindertxt.setTextColor(0xFF000000);
            remindertxt.setBackground(ContextCompat.getDrawable(DCVSOverlayService.this, R.drawable.speechbig));
        }
    }

    private String loadXmlFromNetwork(String urlString) throws XmlPullParserException, IOException {
        InputStream stream = null;
        xmlcalendar feedXmlParser = new xmlcalendar();
        List<xmlcalendar.Entry> entries = null;
        String url = null;
        StringBuilder skypedetails = new StringBuilder();
        try {
            stream = downloadUrl(urlString);
            entries = feedXmlParser.parse(stream);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }

        for (xmlcalendar.Entry entry : entries) {
            skypedetails.append("Hello, Just a reminder that you have a ");
            skypedetails.append(entry.title);
            skypedetails.append(" on ");
            skypedetails.append(entry.date);
            skypedetails.append(" also ");
            skypedetails.append(entry.notes);
        }
        return skypedetails.toString();
    }

    private InputStream downloadUrl(String urlString) throws IOException {
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

    public void removereminder() {
        if (reminderv) {
            reminderview.setVisibility(View.INVISIBLE);
            reminderv = false;
            reminders = "false";
        }
    }

    public void showreminder() {
        if (!reminderv) {
            reminderview.setVisibility(View.VISIBLE);
            new DownloadXmlTask().execute(URL);
            reminderv = true;
            reminders = "true";
        }
    }
    */
}

//Todo Bigger Buttons, sounds

//attribution button press sound Recorded by Marianne Gagnon