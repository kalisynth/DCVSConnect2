package org.nac.kalisynth.dcvsconnect2;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.NotificationCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class DCVSOverlayService extends Service {

    public static LinearLayout DCVSView;
    RelativeLayout RDCVSView;

    public static Boolean chatv = true;
    public Boolean helpv = true;
    public static Boolean funv = true;
    public Boolean homev = false;

    public static Button chatButton;
    public static Button funButton;

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

        //Relative Layout
        //RDCVSView = new RelativeLayout(this);

        //Home Button style
        final Button homeButton = new Button(this);
        homeButton.setId(R.id.homebtnid);
        homeButton.setBackground(ContextCompat.getDrawable(this, R.drawable.rechomebtn));
        //End Home

        //Chat Button Style
        chatButton = new Button (this);
        chatButton.setId(R.id.chatbtnid);
        chatButton.setBackground(ContextCompat.getDrawable(this, R.drawable.recchatbtn));
        //End Chat

        //Fun Button Style
        funButton = new Button(this);
        funButton.setBackground(ContextCompat.getDrawable(this, R.drawable.recfun));
        funButton.setId(R.id.funbtnid);
        //End Fun

        //Help Button Style
        final Button helpButton = new Button(this);
        helpButton.setId(R.id.helpbtnid);
        helpButton.setBackground(ContextCompat.getDrawable(this, R.drawable.rechelpbtn));
        //End Help

        //Home Button Layout
        final RelativeLayout.LayoutParams params_home = new RelativeLayout.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
                );

        params_home.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        //Chat Button Layout
        final RelativeLayout.LayoutParams params_chat = new RelativeLayout.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        );
        params_chat.addRule(RelativeLayout.ALIGN_RIGHT, R.id.homebtnid);
        params_chat.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        //Fun Button Layout
        final RelativeLayout.LayoutParams params_fun = new RelativeLayout.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        );
        params_fun.addRule(RelativeLayout.ALIGN_END, R.id.chatbtnid);

        //Help Button Layout
        final RelativeLayout.LayoutParams params_help = new RelativeLayout.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        );
        params_chat.addRule(RelativeLayout.ALIGN_END, R.id.helpbtnid);

        //Add buttons to the overview layout
        //DCVSView.addView(homeButton, params_home);
        DCVSView.addView(chatButton, params_chat);
        DCVSView.addView(funButton, params_fun);
        DCVSView.addView(helpButton, params_help);

        //Window Manager for the Layout
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT);
        //Window Manager Layout Style
        params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;

        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        //wm.updateViewLayout(RDCVSView, params);
        wm.addView(DCVSView, params);

        helpButton.setOnClickListener(new View.OnClickListener(){
          @Override
            public void onClick(View view){
               if (!homev){
                  DCVSView.addView(homeButton, params_home);
                  homev = true;
              }else if(!funv){
                  DCVSView.addView(funButton, params_fun);
                  funv = true;
              }else if(!chatv){
                  DCVSView.addView(chatButton, params_chat);
                  chatv = true;
              }else{

               }
              helpv = false;
              DCVSView.removeView(helpButton);
              gohelp(view);
          }
        });

        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (!helpv){
                    DCVSView.addView(helpButton, params_help);
                    helpv = true;
                } else if(!chatv){
                    DCVSView.addView(chatButton, params_chat);
                    chatv = true;
                }else if(!funv){
                    DCVSView.addView(funButton, params_fun);
                    funv = true;
                }else{

                }
                homev = false;
                DCVSView.removeView(homeButton);
                goHome(view);
            }
        });

        chatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (!homev){
                    DCVSView.addView(homeButton, params_home);
                    homev = true;}
                else if (!helpv){
                    DCVSView.addView(helpButton, params_help);
                    helpv = true;
                }else if(!funv){
                    DCVSView.addView(funButton, params_fun);
                    funv = true;
                }else{

                }
                DCVSView.removeView(chatButton);
                chatv = false;
                goChat(view);
            }
        });

        funButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (!homev){
                    DCVSView.addView(homeButton, params_home);
                    homev = true;}
                else if (!helpv){
                    DCVSView.addView(helpButton, params_help);
                    helpv = true;
                }else if(!chatv){
                    DCVSView.addView(chatButton, params_chat);
                    chatv = true;
                }else{

                }
                funv = false;
                DCVSView.removeView(funButton);
                goFun(view);
            }
        });

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.notification);
        mBuilder.setContentTitle("DCVSConnect");
        mBuilder.setContentText("DCVS Connect Widget is now Online");
        mBuilder.setOngoing(true);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(2, mBuilder.build());
        ConnectionCheck();
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

        public void gohelp(View view) {
            //Button funButton = (Button) findViewById(R.id.funbtnid);
            ConnectionCheck();
            Intent helpIntent =  new Intent(Intent.ACTION_VIEW);
            helpIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            helpIntent.setClassName("org.nac.kalisynth.dcvsconnect2", "org.nac.kalisynth.dcvsconnect2.DCVSHelp");
            startActivity(helpIntent);
        }

    public void goHome(View view){
        //Home Intent
        ConnectionCheck();
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeIntent);
    }

    public void goFun(View view){
        //Fun intent
        ConnectionCheck();
        Intent funIntent =  new Intent(Intent.ACTION_VIEW);
        funIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        funIntent.setClassName("org.nac.kalisynth.dcvsconnect2", "org.nac.kalisynth.dcvsconnect2.Dcvsfun");
        startActivity(funIntent);
    }

    public void goChat(View view){
        //chat intent
        ConnectionCheck();
        Intent chatIntent =  new Intent(Intent.ACTION_VIEW);
        chatIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        chatIntent.setClassName("org.nac.kalisynth.dcvsconnect2", "org.nac.kalisynth.dcvsconnect2.DCVSChat");
        startActivity(chatIntent);
    }

    public void ConnectionCheck(){
        ConnectivityManager check = (ConnectivityManager)
                this.getSystemService(Context.CONNECTIVITY_SERVICE);

        //Network[] info = check.getAllNetworks();
        NetworkInfo[] info = check.getAllNetworkInfo();

        if(check.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || check.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED){
            OnlineInternetNotification();
        }
        else
            OfflineNotification();
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

    public Boolean getChatv(){
        return chatv;
    }


    }

//Todo Change button to blue when clicked on and then change back when not clicked and make buttons big on the home page