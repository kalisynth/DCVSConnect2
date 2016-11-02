/*If Setting up tablet for IUIH or Dana or adding new contacts to the Chat screen,
first make sure the skype account on the tablet has added the new contacts,
then change the appropriate boolean to true, if adding custom names that aren't IUIH or dana then
change the skype name in the custom speed dial
        */
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
import android.widget.Button;
import android.widget.TextView;

import com.vstechlab.easyfonts.EasyFonts;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class ChatHub extends AppCompatActivity {

    //Butterknife BindViews
    @BindView(R.id.skypename) TextView skypetxv;
    @BindView(R.id.skypebtn1) Button sb1;
    @BindView(R.id.skypebtn2) Button sb2;
    @BindView(R.id.skypebtn3) Button sb3;
    @BindView(R.id.skypebtn4) Button sb4;
    @BindView(R.id.openskypebtn) Button osb;
    @BindView(R.id.cb1) Button cb1;
    @BindView(R.id.cb2) Button cb2;

    //Set to true the one you want to appear on the chat screen
    Boolean mIuih = false;
    Boolean mDana = false;
    Boolean mCb1 = true;
    Boolean mCb2 = false;

    //Set Custom name to be displayed here
    String mCustom1Name = "Tim";
    String mCustom2Name = null;

    //Set Skype name for the Custom skypes
    String mCustom1Skype = "projectkalisynth";
    String mCustom2Skype = null;

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
        setContentView(R.layout.activity_chat_hub);
        String mSkypeName = getName();
        String mSkypeNameIs = getResources().getString(R.string.skypenameis, mSkypeName);
        ButterKnife.bind(this);
        if (mSkypeName != null) {
            skypetxv.setText(mSkypeNameIs);
        }

        //set font type
        skypetxv.setTypeface(EasyFonts.robotoBlack(this));
        sb1.setTypeface(EasyFonts.robotoBlack(this));
        sb2.setTypeface(EasyFonts.robotoBlack(this));
        sb3.setTypeface(EasyFonts.robotoBlack(this));
        sb4.setTypeface(EasyFonts.robotoBlack(this));
        osb.setTypeface(EasyFonts.robotoBlack(this));
        cb1.setTypeface(EasyFonts.robotoBlack(this));
        cb2.setTypeface(EasyFonts.robotoBlack(this));

        //check if booleans are set to true or false, if false set cb1 to 'gone', if set to true
        //will set the button to visible, then change the text depending on what is set to true
        if(!mIuih && !mDana && !mCb1){
            cb1.setVisibility(GONE);
        } else if(mIuih || mDana || mCb1){
            cb1.setVisibility(VISIBLE);
            if(mIuih){
                cb1.setText(getResources().getString(R.string.SpeedDialIUIH));
            } else if(mDana){
                cb1.setText(getResources().getString(R.string.SpeedDialDana));
            } else {
                cb1.setText(mCustom1Name);
            }
        }
        if(!mCb2){
            cb2.setVisibility(GONE);
        } else if(mCb2 = true){
            cb2.setVisibility(VISIBLE);
            cb2.setText(mCustom2Name);
        }
    }

    //Open Skype
    @OnClick(R.id.openskypebtn)
    public void skypeonclick(){
        //add chat button back to overlay
        DCVSOverlayService.DCVSView.addView(DCVSOverlayService.chatButton);
        DCVSOverlayService.chatv = true;
         Intent mSkypeIntent;
          PackageManager mSkypeManager = getPackageManager();
         mSkypeIntent = mSkypeManager.getLaunchIntentForPackage("com.skype.raider");
         mSkypeIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        startActivity(mSkypeIntent);
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
    @OnClick(R.id.skypebtn4)
    public void speeddial2onclick(){
        SpeedDialTwo();
    }

    //Speed Dial 3
    @OnClick(R.id.skypebtn3)
    public void speeddial3onclick(){
        SpeedDialThree();
    }

    //Speed Dial 4
    @OnClick(R.id.skypebtn2)
    public void speeddial4onclick(){
        SpeedDialFour();
    }

    //Custom 1
    @OnClick(R.id.cb1)
    public void speeddialc1onclick(){
        if(mIuih){
            SpeedDialIUIH();
        } else if (mDana){
            SpeedDialDana();
        } else if (mCb1) {
            SpeedDialC1();
        }
    }

    //Custom 2
    @OnClick(R.id.cb2)
    public void speeddialc2onclick(){
        SpeedDialC2();
    }

    private void SpeedDialOne() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setMessage("Are you sure?")
                .setPositiveButton("Yes",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Yes-code
                        skypedcvs1call(ChatHub.this, "skype:volunteer1dcvs?call&video=true");
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
                        skypedcvs1call(ChatHub.this,"skype:volunteer2dcvs?call&video=true");
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
                        skypedcvs1call(ChatHub.this,"skype:volunteer3dcvs?call&video=true");
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
                        skypedcvs1call(ChatHub.this, "skype:officedcvs?call&video=true");
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

    //If Setting up a tablet for IUIH then change Custom 1 to this and change the visibility for Speeddial 1,2 and 3 to 'gone' and change visibility for cb1 to 'visible'
    private void SpeedDialIUIH(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Yes-code
                        skypedcvs1call(ChatHub.this, "skype:iuih?call&video=true");
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

    //If Setting up a tablet for Dana then change Custom 1 to this and change the visibility for Speeddial 1,2 and 3 to 'gone' and change visibility for cb1 to 'visible'
    private void SpeedDialDana(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Yes-code
                        skypedcvs1call(ChatHub.this, "skype:danadcvs?call&video=true");
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

    public void SpeedDialC1(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Yes-code
                        skypedcvs1call(ChatHub.this, "skype:"+mCustom1Skype+"?call&video=true");
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

    public void SpeedDialC2(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Yes-code
                        skypedcvs1call(ChatHub.this, "skype:"+mCustom2Skype+"?call&video=true");
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


    //Checks skype name by getting the tablet name, if skype name doesn't match tablet name then make sure the tablet is set up correctly
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
