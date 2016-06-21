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
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

public class DCVSOverlayService extends Service {
    //Main Layout View
    public static LinearLayout DCVSView;

    //Layout Params
    private WindowManager.LayoutParams params;
    private LinearLayout.LayoutParams params_home;
    private LinearLayout.LayoutParams params_chat;
    private LinearLayout.LayoutParams params_fun;
    private LinearLayout.LayoutParams params_help;

    //Window Manager
    private WindowManager wm;

    //Booleans for if button is visible
    public static Boolean chatv = true;
    private Boolean helpv = true;
    public static Boolean funv = true;
    private Boolean homev = false;

    //Buttons
    public static Button chatButton;
    public static Button funButton;
    private static Button helpButton;
    private static Button homeButton;

    MediaPlayer mp = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //Linear Layout
        DCVSView = new LinearLayout(this);
        DCVSView.setBackgroundColor(0x00D4FF00);
        DCVSView.setOrientation(LinearLayout.VERTICAL);

        //Relative Layout
        //RDCVSView = new RelativeLayout(this);

        //Home Button style
        homeButton = new Button(this);
        homeButton.setBackground(ContextCompat.getDrawable(this, R.drawable.homesml));
        //Home Button Layout
        params_home = new LinearLayout.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        );
        params_home.setMargins(5,0,0,5);

        //Chat Button Style
        chatButton = new Button (this);
        chatButton.setBackground(ContextCompat.getDrawable(this, R.drawable.chatbig));
        //Chat Button Layout
        params_chat = new LinearLayout.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        );
        params_chat.setMargins(5,0,0,5);
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
        params_fun.setMargins(5,0,0,5);
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
        params_help.setMargins(5,0,0,5);
        //adds help button to the layout
        DCVSView.addView(helpButton, params_help);

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

        wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.addView(DCVSView, params);

        helpButton.setOnClickListener(new View.OnClickListener(){
          @Override
            public void onClick(View view){
              buttoncheck();
              smallbuttons();
              helpv = false;
              DCVSView.removeView(helpButton);
              gohelp();
          }
        });

        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                buttoncheck();
                bigbuttons();
                homev = false;
                DCVSView.removeView(homeButton);
                goHome();
            }
        });

        chatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                buttoncheck();
                smallbuttons();
                DCVSView.removeView(chatButton);
                chatv = false;
                goChat();
            }
        });

        funButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                buttoncheck();
                smallbuttons();
                funv = false;
                DCVSView.removeView(funButton);
                goFun();
            }
        });
        DCVSAppOnNote();
            }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(DCVSView!=null){
            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            wm.removeView(DCVSView);
            NotificationManager cNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            cNotificationManager.cancel(2);
            cNotificationManager.cancel(1);
        }}

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

    public void goHome(){
        //Home Intent
        ConnectionCheck();
        buttonclicksound();
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeIntent);
    }

    public void goFun(){
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

    public void goChat(){
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

    public void ConnectionCheck(){
        ConnectivityManager check = (ConnectivityManager)
                this.getSystemService(Context.CONNECTIVITY_SERVICE);

        //Network[] info = check.getAllNetworks();
        //NetworkInfo[] info = check.getAllNetworkInfo();

        if(check.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || check.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED || check.getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null){
            OnlineInternetNotification();
        }
        else
            OfflineNotification();
    }

    public void connectioncheck2(){
        ConnectivityManager connection = (ConnectivityManager)
                this.getSystemService(this.CONNECTIVITY_SERVICE);
        if(connection !=null){
            NetworkInfo[] info = connection.getAllNetworkInfo();
        }
    }

    public void OnlineInternetNotification(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.connected);
        mBuilder.setContentTitle("Connection");
        mBuilder.setContentText("Internet is Working");
        mBuilder.setOngoing(true);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    //Online Notification
    }

    public void OfflineNotification(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.disconnected);
        mBuilder.setContentTitle("Connection");
        mBuilder.setContentText("Internet is Not Working");
        mBuilder.setOngoing(true);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    //Offline Notification Draw
    }

    public void DCVSAppOnNote(){
        String dn = getName();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_stat_dcson);
        mBuilder.setContentTitle("DCVSConnect");
        mBuilder.setContentText("DCVS Connect is Online, " + "Tablet Name: " + dn);
        mBuilder.setOngoing(true);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(2, mBuilder.build());
        ConnectionCheck();
        //shows the app is running
    }

    //make the buttons bigger
    public void bigbuttons(){
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
            }}, 500);
    }

    //make buttons smaller
    public void smallbuttons(){
        chatButton.setBackground(ContextCompat.getDrawable(DCVSOverlayService.this, R.drawable.chatsml));
        helpButton.setBackground(ContextCompat.getDrawable(DCVSOverlayService.this, R.drawable.helpsml));
        funButton.setBackground(ContextCompat.getDrawable(DCVSOverlayService.this, R.drawable.funsml));
        params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        DCVSView.setOrientation(LinearLayout.HORIZONTAL);
        wm.updateViewLayout(DCVSView, params);
    }

    //button visibility control
    public void buttoncheck(){
        if (!homev){
            DCVSView.addView(homeButton, params_home);
            homev = true;
        }else if(!funv){
            DCVSView.addView(funButton, params_fun);
            funv = true;
        }else if(!chatv){
            DCVSView.addView(chatButton, params_chat);
            chatv = true;
        }else if(!helpv){
            DCVSView.addView(helpButton, params_help);
            helpv = true;
        }else {

        }
    }

    public static Account getAccount(AccountManager accountManager){
        Account[] accounts = accountManager.getAccountsByType("com.google");
        Account account;
        if (accounts.length > 0){
            account = accounts[0];
        } else {
            account = null;
        }
        return account;
    }

    public String getName(){
        Account account = getAccount(AccountManager.get(this));
        String accountName = account.name;
        String fullName = accountName.substring(0,accountName.lastIndexOf("@"));
        return fullName;
    }

    public void buttonclicksound(){
        mp = MediaPlayer.create(this, R.raw.bp2);
        mp.start();
    }

    }

//Todo Bigger Buttons, sounds

//attribution button press sound Recorded by Marianne Gagnon