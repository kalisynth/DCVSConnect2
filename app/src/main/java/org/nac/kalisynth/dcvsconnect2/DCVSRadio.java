package org.nac.kalisynth.dcvsconnect2;

import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;

//import org.videolan.libvlc.LibVLC;
//import org.videolan.libvlc.MediaPlayer;

public class DCVSRadio extends AppCompatActivity implements MediaController.MediaPlayerControl {

    /*LibVLC mLibVLC = null;
    MediaPlayer mMediaPlayer = null;
    boolean mPlayingRadio = false;
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dcvsradio);

        TextView radiostation =(TextView)findViewById(R.id.radiostationtxt);

        if (BuildConfig.RadioStation.equals("DCVS")){
            radiostation.setText(getString(R.string.DCVSRadioStation));
        } else if (BuildConfig.RadioStation.equals("Local")){
            radiostation.setText(getString(R.string.NACRadiostation));
        } else {

        }


    }

    private void launchVLC(String url)
    {
        try{
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setComponent(new ComponentName("org.videolan.vlc", "org.videolan.vlc.gui.video.VideoPlayerActivity"));
            i.setData(Uri.parse(url));
            startActivity(i);
        }
        catch (ActivityNotFoundException e){
            Uri uri = Uri.parse("http://play.google.com/store/apps/details?id=org.videolan.vlc");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    private void pausestopVLC(String com)
    {
        try{
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setComponent(new ComponentName("org.videolan.vlc", "org.videolan.libvlc.media.MediaPlayer"));
            i.setData(Uri.parse(com));
            startActivity(i);
        }
        catch (ActivityNotFoundException e){
            Uri uri = Uri.parse("http://play.google.com/store/apps/details?id=org.videolan.vlc");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    public void onClickPlay(View v){
        if (BuildConfig.RadioStation.equals("DCVS")){
            launchVLC("http://bit.ly/dcvsradio");
        } else {
            launchVLC("http://bit.ly/naclocalradio");
            //launchVLC("http://bit.ly/dcvsradio");
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_stat_play);
        if (BuildConfig.RadioStation.equals("DCVS")){
            mBuilder.setContentTitle("DCVS Radio");
        }else if (BuildConfig.RadioStation.equals("Local")){
            mBuilder.setContentTitle("NAC Radio");
        }

        mBuilder.setContentText("Radio is Playing");
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(3, mBuilder.build());
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                startActivity(new Intent(DCVSRadio.this, DCVSRadio.class)); finish();
            }
        },5000);
        /*
        Intent radioIntent = new Intent();
        radioIntent.setAction(Intent.ACTION_VIEW);
        radioIntent.setClassName("org.dcvs.nac.dcvsconnectwidget", "org.dcvs.nac.dcvsconnectwidget.dcvsRadio");
                startActivity(radioIntent);
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);*/
        /*Uri streamUrl = Uri.parse("http://janus.cdnstream.com:5189/live");
        Intent playIntent = new Intent("org.videolan.vlc.gui.tv.audioplayer.AudioPlayerActivity");
        playIntent.setAction(Intent.ACTION_VIEW);
        playIntent.setData(streamUrl);
        startActivity(playIntent);*/
    }

    public void onClickPause(View v){
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        AudioManager mAudioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);

        if(mAudioManager.isMusicActive())
        {
            Intent i = new Intent("com.android.music.musicservicecommand");
            i.putExtra("command", "pause");
            this.sendBroadcast(i);
        }
    }

  /*  public void onClickStop(View v){
        pausestopVLC("stop");
    }
}*/

    public void onClickPauseBad(View v){
        launchVLC("file:///storage/emulated/0/Download/1sec.mp3");
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_stat_pause);
        mBuilder.setContentTitle("DCVS Radio");
        mBuilder.setContentText("Radio is Paused");
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(3, mBuilder.build());
    }

    @Override
    public void start() {

    }

    @Override
    public void pause() {

    }

    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        return 0;
    }

    @Override
    public void seekTo(int pos) {

    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return false;
    }

    @Override
    public boolean canSeekBackward() {
        return false;
    }

    @Override
    public boolean canSeekForward() {
        return false;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    public void playnotify() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.playbutton);
        mBuilder.setContentTitle("DCVS Radio");
        mBuilder.setContentText("Radio is Playing");
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(3, mBuilder.build());
    }
}
