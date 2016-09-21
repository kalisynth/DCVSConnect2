package org.nac.kalisynth.dcvsconnect2;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;

public class DCVSHelp extends AppCompatActivity {
    String fsurl = "http://www.fastsupport.com";
    String tabletname;
    WebView fsweb;
    String supportkey = "122321432";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dcvshelp);
    }

    public void helpguideonclick(View v){
        Intent helpguideIntent;
        PackageManager helpguideManager = getPackageManager();
        helpguideIntent = helpguideManager.getLaunchIntentForPackage("appinventor.ai_tim.DCVSHelpGuide");
        helpguideIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        startActivity(helpguideIntent);
        finish();
    }

    public void skypeversionclick(View v){
        Uri marketUri = Uri.parse("market://details?id=com.skype.raider");
        Intent myIntent = new Intent(Intent.ACTION_VIEW, marketUri);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(myIntent);
    }

    public boolean skypeinstalled(){
        //Skype Check
        PackageManager skypeMgr = this.getPackageManager();
        try{
            skypeMgr.getPackageInfo("com.skype.raider", PackageManager.GET_ACTIVITIES);
        }
        catch(PackageManager.NameNotFoundException e) {
            return (false);
        }
        return (true);
    }

    public void fastsupportclick(View v){
        //fsweb.setWebViewClient(new MyApplicationWebView());
        /*fsweb = (WebView)findViewById(R.id.fastsupport);
        fsweb.getSettings().setDomStorageEnabled(true);
        fsweb.getSettings().setJavaScriptEnabled(true);
        fsweb.loadUrl(fsurl);

        tabletname = getName();
        final String js = "javascript:" +
                "document.getElementById('name').value = '"+tabletname+"';" +
                "document.getElementById('supportSessionId').value = '"+supportkey+"';";

        fsweb.loadUrl(js);*/

        Intent gotointent;
        PackageManager gotoManager = getPackageManager();
        gotointent = gotoManager.getLaunchIntentForPackage("com.citrix.g2arscustomer");
        gotointent.addCategory(Intent.CATEGORY_LAUNCHER);
        gotointent.putExtra("com.citrix.g2arscustomer.supportSessionId", supportkey);
        startActivity(gotointent);
        finish();
    }

    private String getName() {
        Account account = getAccount(AccountManager.get(this));
        String accountName = account.name;
        String fullName = accountName.substring(0, accountName.lastIndexOf("@"));
        return fullName;
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

}