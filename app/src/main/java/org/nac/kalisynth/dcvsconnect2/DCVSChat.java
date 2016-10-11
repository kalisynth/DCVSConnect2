package org.nac.kalisynth.dcvsconnect2;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.appcompat.BuildConfig;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DCVSChat extends AppCompatActivity {

    @BindView(R.id.skypename) TextView skypetxv;
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
        String skypenameis = getResources().getString(R.string.skypenameis, skypename);
        ButterKnife.bind(this);
        if (skypename != null) {
            skypetxv.setText(skypenameis);
        }
    }

    //Open Skype
    @OnClick(R.id.openskypebtn)
    public void skypeonclick(){
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
    @OnClick(R.id.skypebtn1)
    public void speeddial1onclick(){
        SpeedDialOne();
    }

    //Speed Dial 2
    @OnClick(R.id.skypebtn2)
    public void speeddial2onclick(){
        SpeedDialTwo();
    }

    //Speed Dial 3
    @OnClick(R.id.skypebtn3)
    public void speeddial3onclick(){
        SpeedDialThree();
    }

    //Speed Dial 4
    @OnClick(R.id.skypebtn4)
    public void speeddial4onclick(){
        SpeedDialFour();
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
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Yes-code
                        skypedcvs1call(DCVSChat.this, "skype:officedcvs?call&video=true");
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
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
        return accountName.substring(0,accountName.lastIndexOf("@"));
    }

    //Todo https://drive.google.com/file/d/0B4EdgIslSa6EYVlHOGgzekZCbkk/view?usp=sharing
}
