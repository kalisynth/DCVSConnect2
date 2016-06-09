package org.nac.kalisynth.dcvsconnect2;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class DCVSChat extends AppCompatActivity {
    public static final String ChatPrefences = "ChatPrefs";
    public static final String speeddial5skype = " ";
    public static final String speeddial5name = " ";
    //SharedPreferences sharedPref = getPreferences(MODE_PRIVATE);

    boolean EditEnabled = false;
    //Check Skype is installed
    public boolean isSkypeClientInstalled(Context skypeCall) {
        PackageManager myPackageMgr = skypeCall.getPackageManager();
        try {
            myPackageMgr.getPackageInfo("com.skype.raider", PackageManager.GET_ACTIVITIES);
        }
        catch (PackageManager.NameNotFoundException e) {
            return (false);
        }
        return (true);
    }

    public void goToMarket(Context skypeinstalled) {
        Uri marketUri = Uri.parse("market://details?id=com.skype.raider");
        Intent myIntent = new Intent(Intent.ACTION_VIEW, marketUri);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        skypeinstalled.startActivity(myIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dcvschat);
    }
    //Open Skype
    public void skypeonclick(View v){
        Intent skypeIntent;
        PackageManager skypeManager = getPackageManager();
        skypeIntent = skypeManager.getLaunchIntentForPackage("com.skype.raider");
        skypeIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        startActivity(skypeIntent);
        finish();
    }
/* Was Opening Google Hangouts
    public void googleonclick(View v){
        Intent googleIntent;
        PackageManager googleManager = getPackageManager();
        googleIntent = googleManager.getLaunchIntentForPackage("com.google.android.talk");
        googleIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        startActivity(googleIntent);
        finish();
    }*/

    //Use skype to call a username based on method called
    public void skypedcvs1call(Context skypeCalldcvsv1, String mySkypeDCVSv1){
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
                        skypedcvs1call(DCVSChat.this, "skype:" + getString(R.string.SpeedDialOne) + "?call&video=true");
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
                        skypedcvs1call(DCVSChat.this,"skype:" + getString(R.string.SpeedDialTwo) + "?call&video=true");
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
                        skypedcvs1call(DCVSChat.this,"skype:" + getString(R.string.SpeedDialThree) + "?call&video=true");
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
                        skypedcvs1call(DCVSChat.this,"skype:" + getString(R.string.SpeedDialFour) + "?call&video=true");
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

    private void SpeedDialFive() {

        //if (!EditEnabled){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
            //String speeddial5number = getResources().getString(speeddial5skype);

        builder
                .setMessage("Are you sure?")
                .setPositiveButton("Yes",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Yes-code
                        skypedcvs1call(DCVSChat.this,"skype:" + getString(R.string.SpeedDialFive) + "?call&video=true");
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                })
                .show();
    } /*else if (EditEnabled){
            //Open Alert Dialog asking for skypename and Name of Person
            AlertDialog.Builder infoBuilder = new AlertDialog.Builder(this);
            infoBuilder.setTitle("Enter Skype Info");

            LinearLayout skypedlayout = new LinearLayout(this);
            skypedlayout.setOrientation(LinearLayout.VERTICAL);

            //set up the input
            final EditText skypenameinput = new EditText(this);
            //specify the type of input expected
            skypenameinput.setInputType(InputType.TYPE_CLASS_TEXT);
            skypedlayout.addView(skypenameinput);

            final EditText personnameinput = new EditText(this);
            personnameinput.setInputType(InputType.TYPE_CLASS_TEXT);
            skypedlayout.addView(personnameinput);

            infoBuilder.setView(skypedlayout);

            infoBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which){
                   SharedPreferences.Editor skypeedit = sharedPref.edit();
                    String skypename = skypenameinput.getText().toString();
                    String personname = personnameinput.getText().toString();

                    skypeedit.putString(speeddial5name, personname);
                    skypeedit.putString(speeddial5skype, skypename);
                    skypeedit.commit();
                    Toast.makeText(DCVSChat.this, "Speed Dial Button Changed", Toast.LENGTH_LONG).show();
                }
            });
        }*/

    private void SpeedDialSix() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder
                .setMessage("Are you sure?")
                .setPositiveButton("Yes",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Yes-code
                        skypedcvs1call(DCVSChat.this,"skype:" + getString(R.string.SpeedDialSix) + "?call&video=true");
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
                        skypedcvs1call(DCVSChat.this,"skype:" + getString(R.string.SpeedDialSeven) + "?call&video=true");
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
                        skypedcvs1call(DCVSChat.this,"skype:" + getString(R.string.SpeedDialEight) + "?call&video=true");
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

    private void SpeedDialNine() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder
                .setMessage("Are you sure?")
                .setPositiveButton("Yes",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Yes-code
                        skypedcvs1call(DCVSChat.this,"skype:" + getString(R.string.SpeedDialNine) + "?call&video=true");
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

}
