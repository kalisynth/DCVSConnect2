package org.nac.kalisynth.dcvsconnect2;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
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

    private static final String TAG = DCVSOverlayService.class.getSimpleName();

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
    private Boolean showv = true;

    //Buttons
    public static Button chatButton;
    public static Button funButton;
    private static Button helpButton;
    private static Button homeButton;
    private static Button showButton;

    String passtext = null;
    private static String bv;

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

        //Window Manager for the Layout
        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.END | Gravity.CENTER_HORIZONTAL;

        wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.addView(DCVSView, params);

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
                homev = false;
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
    }

    private void gohelp() {
        //Help Intent
        buttonclicksound();
        Intent helpIntent = new Intent(getApplicationContext(), Help.class);
        helpIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(helpIntent);
    }

    private void goHome() {
        //Home Intent
        buttonclicksound();
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeIntent);
        golauncher();
    }

    private void goFun() {
        //Fun intent
        buttonclicksound();
        Intent funIntent = new Intent(getApplicationContext(), FunHub.class);
        funIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(funIntent);
    }

    private void goChat() {
        //chat intent
        buttonclicksound();
        Intent chatIntent = new Intent(getApplicationContext(), ChatHub.class);
        chatIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
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

    private void DCVSAppOnNote() {
        String dn = getName();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_stat_dcson);
        mBuilder.setContentTitle("DCVSConnect");
        mBuilder.setContentText("DCVS Connect is Online, " + bv + getResources().getString(R.string.CurrentVersion) + " Tablet Name: " + dn);
        mBuilder.setOngoing(true);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(2, mBuilder.build());
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
        Intent golaunch = new Intent(getApplicationContext(), Home.class);
        golaunch.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        golaunch.putExtra("GREETINGS", passtext);
        startActivity(golaunch);
    }

    public void StartupApp(){
        Calendar c;
        int hournow = 0;
        Log.i("main", "Constructor fired");
        c = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("HH");
        String formattedTime = df.format(c.getTime());
        hournow = Integer.parseInt(formattedTime);
        if (hournow < 12) {
            passtext = "Hello, My name is ERIC, Good Morning, How are you?";
        } else {
            passtext = "Hello, My name is ERIC, Good Afternoon, How are you?";
        }
    }
}

//Todo Bigger Buttons, sounds

//attribution button press sound Recorded by Marianne Gagnon