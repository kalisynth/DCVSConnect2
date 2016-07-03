package org.nac.kalisynth.dcvsconnect2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Dcvsgames extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dcvsgames);
    }

    //Opens a game, if speeddial works then will come back and change this to also be
    // a speed dial thing

    public void euchreonclick(View v){
        Intent euchreIntent;
        PackageManager euchreManager = getPackageManager();
        euchreIntent = euchreManager.getLaunchIntentForPackage("uk.co.aifactory.euchre");
        euchreIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        startActivity(euchreIntent);
        finish();
    }

    public void solitareonclick(View v) {
        Intent solitareIntent;
        PackageManager solitareManager = getPackageManager();
        solitareIntent = solitareManager.getLaunchIntentForPackage("uk.co.aifactory.solitaire");
        solitareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        startActivity(solitareIntent);
        finish();
    }

    public void backgammononclick(View v) {
        Intent backgamIntent;
        PackageManager backagamManager = getPackageManager();
        backgamIntent = backagamManager.getLaunchIntentForPackage("uk.co.aifactory.backgammon");
        backgamIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        startActivity(backgamIntent);
        finish();
    }

    public void chessonclick(View v) {
        Intent chessIntent;
        PackageManager chessManager = getPackageManager();
        chessIntent = chessManager.getLaunchIntentForPackage("uk.co.aifactory.chess");
        chessIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        startActivity(chessIntent);
        finish();
    }

    public void finaronclick(View v) {
        Intent fialIntent;
        PackageManager fialManager = getPackageManager();
        fialIntent = fialManager.getLaunchIntentForPackage("uk.co.aifactory.fial");
        fialIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        startActivity(fialIntent);
        finish();
    }

    public void  sudokuonclick(View v) {
        Intent sudokuIntent;
        PackageManager sudokuManager = getPackageManager();
        sudokuIntent = sudokuManager.getLaunchIntentForPackage("uk.co.aifactory.sudoku");
        sudokuIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        startActivity(sudokuIntent);
        finish();
    }
}
