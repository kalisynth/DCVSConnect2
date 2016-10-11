package org.nac.kalisynth.dcvsconnect2;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DCVSOverlayService extends Service {
    //Main Layout View
    public static LinearLayout DCVSView;
    public LinearLayout gotoView;
    //public static RelativeLayout reminderview;

    private static final String TAG = DCVSOverlayService.class.getSimpleName();

    //Layout Params
    private WindowManager.LayoutParams params;
    //private WindowManager.LayoutParams rparams;
    private LinearLayout.LayoutParams params_home;
    private LinearLayout.LayoutParams params_chat;
    private LinearLayout.LayoutParams params_fun;
    private LinearLayout.LayoutParams params_help;
    //private RelativeLayout.LayoutParams params_reminder;
    //private LinearLayout.LayoutParams params_reboot;
    private LinearLayout.LayoutParams params_goto;

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


    String passtext = null;
    private static String bv;

    public final static int REQUEST_CODE = -1010101;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.appVer.equals("DCVS")) {
            bv = getResources().getString(R.string.BVDCVS);
        } else if (BuildConfig.appVer.equals("Local")) {
            bv = getResources().getString(R.string.BVLocal);
        } else {
            bv = getResources().getString(R.string.BVWifi);
        }

        String dn = getName();
        //URL = "http://tim.nactech.org/skypespeeddial.xml";
        String URL = "http://tim.nactech.org/" + dn + "/calender.xml";
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
        homeButton.setBackground(ContextCompat.getDrawable(this, R.drawable.buttonhomesquare));
        //Home Button Layout
        params_home = new LinearLayout.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        );
        params_home.setMargins(5, 0, 0, 5);

        //Chat Button Style
        chatButton = new Button(this);
        chatButton.setBackground(ContextCompat.getDrawable(this, R.drawable.chatbutton));
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
        funButton.setBackground(ContextCompat.getDrawable(this, R.drawable.play_button));
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
        helpButton.setBackground(ContextCompat.getDrawable(this, R.drawable.help_button));
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
        LinearLayout.LayoutParams params_show = new LinearLayout.LayoutParams(
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
        golauncher();
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

    private void gohelp() {
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

    private void goHome() {
        //Home Intent
        ConnectionCheck();
        buttonclicksound();
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeIntent);
        golauncher();
    }

    private void goFun() {
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

    private void goChat() {
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

    private void Show() {
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

    private void Hide() {
        DCVSView.removeView(homeButton);
        DCVSView.removeView(chatButton);
        DCVSView.removeView(funButton);
        DCVSView.removeView(helpButton);
        //removereminder();
        showv = false;
        //Log.d(TAG, reminders);
        showButton.setBackground(ContextCompat.getDrawable(DCVSOverlayService.this, R.drawable.hide));
    }

    private void ConnectionCheck() {
        if (BuildConfig.appVer.equals("Wifi")) {
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

    private void OnlineInternetNotification() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_online);
        mBuilder.setContentTitle("Connection");
        mBuilder.setContentText("Internet is Working");
        mBuilder.setOngoing(true);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
        //Online Notification
    }

    private void OfflineNotification() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_offline);
        mBuilder.setContentTitle("Connection");
        mBuilder.setContentText("Internet is Not Working");
        mBuilder.setOngoing(true);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
        //Offline Notification Draw
    }

    private void WifiNotification() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_stat_wifi);
        mBuilder.setContentTitle("Wifi");
        mBuilder.setContentText("This is a Wifi Only Tablet");
        mBuilder.setOngoing(true);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }

    private void DCVSAppOnNote() {
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
    private void bigbuttons() {
        //delay so change to big buttons is smoother
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                chatButton.setBackground(ContextCompat.getDrawable(DCVSOverlayService.this, R.drawable.chatbutton));
                funButton.setBackground(ContextCompat.getDrawable(DCVSOverlayService.this, R.drawable.play_button));
                helpButton.setBackground(ContextCompat.getDrawable(DCVSOverlayService.this, R.drawable.help_button));

                params.gravity = Gravity.END | Gravity.CENTER_HORIZONTAL;
                DCVSView.setOrientation(LinearLayout.VERTICAL);
                wm.updateViewLayout(DCVSView, params);
            }
        }, 500);
    }

    //make buttons smaller
    private void smallbuttons() {
        chatButton.setBackground(ContextCompat.getDrawable(DCVSOverlayService.this, R.drawable.chatsmall));
        helpButton.setBackground(ContextCompat.getDrawable(DCVSOverlayService.this, R.drawable.button_help_small));
        funButton.setBackground(ContextCompat.getDrawable(DCVSOverlayService.this, R.drawable.buttonplaysquare));
        chatButton.setWidth(20);
        chatButton.setHeight(20);
        helpButton.setWidth(20);
        helpButton.setHeight(20);
        funButton.setWidth(20);
        funButton.setHeight(20);
        params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        DCVSView.setOrientation(LinearLayout.HORIZONTAL);
        wm.updateViewLayout(DCVSView, params);
    }

    //button visibility control
    private void buttoncheck() {
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

    private static Account getAccount(AccountManager accountManager) {
        Account[] accounts = accountManager.getAccountsByType("com.google");
        Account account;
        if (accounts.length > 0) {
            account = accounts[0];
        } else {
            account = null;
        }
        return account;
    }

    private String getName() {
        Account account = getAccount(AccountManager.get(this));
        String accountName = account.name;
        String fullName = accountName.substring(0, accountName.lastIndexOf("@"));
        return fullName;
    }

    private void buttonclicksound() {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.btnpush);
        mp.start();
    }
    private void golauncher() {
        StartupApp();
        Intent golaunch = new Intent(getApplicationContext(), org.nac.kalisynth.dcvsconnect2.landingpage.class);
        golaunch.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        golaunch.putExtra("GREETINGS", passtext);
        startActivity(golaunch);
    }

    public void StartupApp(){
        Calendar c;
        int hournow = 0;
        Log.i("main", "Constructor fired");
        c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH");
        String formattedTime = df.format(c.getTime());
        hournow = Integer.parseInt(formattedTime);
        if (hournow < 12) {
            passtext = "Hello, Good Morning, How are you?";
        } else {
            passtext = "Hello, Good Afternoon, How are you?";
        }

    }
}

//Todo Bigger Buttons, sounds

//attribution button press sound Recorded by Marianne Gagnon