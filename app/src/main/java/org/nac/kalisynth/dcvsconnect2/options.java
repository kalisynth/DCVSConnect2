package org.nac.kalisynth.dcvsconnect2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.List;

public class options extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs";

    //Shared Preferences App Settings
    SharedPreferences appSettings;

    //App Setting Variables
    //Game Buttons
    Button g1, g2, g3, g4, g5, g6;

    //Internet Buttons
    Button i1, i2, i3, i4, i5, i6, i7, i8;

    //Skype QC Buttons
    Button s4, s5, s6, s7, s8;

    //Start up Toggle
    ToggleButton startup;

    //Appver
    TextView appvertxt;
    Button appverbtn;

    //Game Var Keys
    public static final String game1 = "game1key";
    public static final String game2 = "game2key";
    public static final String game3 = "game3key";
    public static final String game4 = "game4key";
    public static final String game5 = "game5key";
    public static final String game6 = "game6key";

    //Internet Var Keys
    public static final String web1 = "web1key";
    public static final String web2 = "web2key";
    public static final String web3 = "web3key";
    public static final String web4 = "web4key";
    public static final String web5 = "web5key";
    public static final String web6 = "web6key";
    public static final String web7 = "web7key";
    public static final String web8 = "web8key";

    //Skype Var Keys
    public static final String sk4 = "skype4key";
    public static final String sk5 = "skype5key";
    public static final String sk6 = "skype6key";
    public static final String sk7 = "skype7key";
    public static final String sk8 = "skype8key";

    private PackageManager packageManager = null;
    private List<ApplicationInfo> applist = null;
    private ApplicationAdapter listadapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        packageManager = getPackageManager();

        g1 = (Button)findViewById(R.id.og1btn);
        g1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                gameclick();
            }
        });
    }

    public void gameclick(){
        new LoadApplications().execute();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        boolean result = true;

        switch(item.getItemId()){
            case R.id.menu_about: {
                displayAboutDialog();
                break;
            }
            default: {
                result = super.onOptionsItemSelected(item);

                break;
            }
        }
        return result;
    }

    private void displayAboutDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.about_title));
        builder.setMessage(getString(R.string.about_desc));

        builder.setPositiveButton("Know More", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                Intent browserIntent(Intent.ACTION_VIEW, Uri.parse("http://stacktips.com"));
                startActivity(browserIntent);
                dialog.cancel();
            }
        });
        builder.setNegativeButton("No Thanks!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l,v, position, id);

        ApplicationInfo app = applist.get(position);
        try {
            Intent intent = packageManager
                    .getLaunchIntentForPackage(app.packageName);


        }
    }
}